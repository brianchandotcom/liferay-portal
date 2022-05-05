/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.testray.internal.dispatch.executor;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray",
		"dispatch.task.executor.type=testray"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Invoking doExecute");
		}

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		_validateUnicodeProperties(unicodeProperties);

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		_invoke(() -> _loadCache(dispatchTrigger.getCompanyId()));
		_invoke(() -> _uploadToTestray(unicodeProperties));

		if (_log.isInfoEnabled()) {
			_log.info("Done!");
		}
	}

	@Override
	public String getName() {
		return "testray";
	}

	private String _getAttributeValue(String attributeName, Node node) {
		NamedNodeMap namedNodeMap = node.getAttributes();

		if (namedNodeMap == null) {
			return null;
		}

		Node attributeNode = namedNodeMap.getNamedItem(attributeName);

		if (attributeNode == null) {
			return null;
		}

		return attributeNode.getTextContent();
	}

	private List<ObjectEntry> _getObjectEntries(
			long companyId, String objectDefinitionShortName)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitions.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			_log.error("Object Definition not found");

			throw new PortalException("Object Definition not found");
		}

		Filter filter = null;

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			objectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, filter, null, null,
				null);

		return (List<ObjectEntry>)objectEntriesPage.getItems();
	}

	private Map<String, String> _getPropertiesMap(Element element) {
		Map<String, String> map = new HashMap<>();

		NodeList propertiesNodeList = element.getElementsByTagName(
			"properties");

		Node propertiesNode = propertiesNodeList.item(0);

		Element propertiesElement = (Element)propertiesNode;

		NodeList propertyNodeList = propertiesElement.getElementsByTagName(
			"property");

		for (int i = 0; i < propertyNodeList.getLength(); i++) {
			Node propertyNode = propertyNodeList.item(i);

			if (!propertyNode.hasAttributes()) {
				continue;
			}

			map.put(
				_getAttributeValue("name", propertyNode),
				_getAttributeValue("value", propertyNode));
		}

		return map;
	}

	private void _invoke(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		long startTime = System.currentTimeMillis();

		unsafeRunnable.run();

		if (_log.isInfoEnabled()) {
			Thread thread = Thread.currentThread();

			StackTraceElement stackTraceElement = thread.getStackTrace()[2];

			_log.info(
				StringBundler.concat(
					"Invoking line ", stackTraceElement.getLineNumber(),
					" took ", System.currentTimeMillis() - startTime, " ms"));
		}
	}

	private void _loadCache(long companyId) throws Exception {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitions.put(
				objectDefinition.getShortName(), objectDefinition);
		}

		_loadTestrayCaseTypes(companyId);
		_loadTestrayComponents(companyId);
		_loadTestrayFactorCategories(companyId);
		_loadTestrayFactorOptions(companyId);
		_loadTestrayProjects(companyId);
		_loadTestrayTeams(companyId);
	}

	private void _loadTestrayCaseTypes(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "CaseType");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				"CaseType#" + (String)properties.get("name"),
				objectEntry.getId());
		}
	}

	private void _loadTestrayComponents(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "Component");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				StringBundler.concat(
					"Component#", (String)properties.get("name"), "#TeamId#",
					(Long)properties.get("r_teamToComponents_c_teamId")),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorCategories(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "FactorCategory");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				"FactorCategory#" + (String)properties.get("name"),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorOptions(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "FactorOption");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				StringBundler.concat(
					"FactorOption#", (String)properties.get("name"),
					"#FactorCategoryId#",
					(Long)properties.get(
						"r_factorCategoryToOptions_c_factorCategoryId")),
				objectEntry.getId());
		}
	}

	private void _loadTestrayProjects(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "Project");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				"Project#" + (String)properties.get("name"),
				objectEntry.getId());
		}
	}

	private void _loadTestrayTeams(long companyId) throws Exception {
		List<ObjectEntry> objectEntriesList = _getObjectEntries(
			companyId, "Team");

		if (ListUtil.isEmpty(objectEntriesList)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntriesList) {
			Map<String, Object> properties = objectEntry.getProperties();

			_objectEntryIds.put(
				StringBundler.concat(
					"Team#", (String)properties.get("name"), "#ProjectId#",
					(Long)properties.get("r_projectToTeams_c_projectIds")),
				objectEntry.getId());
		}
	}

	private void _processArchive(byte[] bytes) throws Exception {
		Path tempDirectoryPath = null;
		Path tempFilePath = null;

		try {
			tempDirectoryPath = Files.createTempDirectory(null);

			tempFilePath = Files.createTempFile(null, null);

			Files.write(tempFilePath, bytes);

			Archiver archiver = ArchiverFactory.createArchiver("tar");

			File tempDirectoryFile = tempDirectoryPath.toFile();

			archiver.extract(tempFilePath.toFile(), tempDirectoryFile);

			DocumentBuilderFactory documentBuilderFactory =
				SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			for (File file : tempDirectoryFile.listFiles()) {
				try {
					Document document = documentBuilder.parse(file);

					_invoke(() -> _processDocument(document));
				}
				catch (Exception exception) {
					_log.error(exception);
				}
				finally {
					file.delete();
				}
			}
		}
		finally {
			if (tempDirectoryPath != null) {
				Files.deleteIfExists(tempDirectoryPath);
			}

			if (tempFilePath != null) {
				Files.deleteIfExists(tempFilePath);
			}
		}
	}

	private void _processDocument(Document document) throws Exception {
		Element element = document.getDocumentElement();

		_getPropertiesMap(element);
	}

	private void _uploadToTestray(UnicodeProperties unicodeProperties)
		throws Exception {

		String s3APIKey = unicodeProperties.getProperty("s3APIKey");

		try (InputStream inputStream = new ByteArrayInputStream(
				s3APIKey.getBytes())) {

			Storage storage = StorageOptions.newBuilder(
			).setCredentials(
				GoogleCredentials.fromStream(inputStream)
			).build(
			).getService();

			String s3InboxFolderName = unicodeProperties.getProperty(
				"s3InboxFolderName");

			Page<Blob> page = storage.list(
				unicodeProperties.getProperty("s3BucketName"),
				Storage.BlobListOption.prefix(s3InboxFolderName + "/"));

			for (Blob blob : page.iterateAll()) {
				String name = blob.getName();

				if (name.equals(s3InboxFolderName + "/")) {
					continue;
				}

				try {
					_processArchive(blob.getContent());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	private void _validateUnicodeProperties(UnicodeProperties unicodeProperties)
		throws Exception {

		if (Validator.isNull(unicodeProperties.getProperty("s3APIKey")) ||
			Validator.isNull(unicodeProperties.getProperty("s3BucketName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ErroredFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3InboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ProcessedFolderName"))) {

			_log.error("At least one property is not defined");

			throw new PortalException("At least one property is not defined");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();
	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}