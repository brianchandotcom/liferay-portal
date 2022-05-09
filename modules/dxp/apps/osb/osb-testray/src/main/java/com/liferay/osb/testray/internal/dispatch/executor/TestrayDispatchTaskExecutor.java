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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
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

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		_validateUnicodeProperties(unicodeProperties);

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);

		String originalPrincipalThreadLocalName =
			PrincipalThreadLocal.getName();

		PermissionChecker originalPermissionThreadLocalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		try {
			_invoke(() -> _loadCache(dispatchTrigger.getCompanyId()));
			_invoke(
				() -> _uploadToTestray(
					dispatchTrigger.getCompanyId(), unicodeProperties));
		}
		finally {
			PrincipalThreadLocal.setName(originalPrincipalThreadLocalName);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionThreadLocalPermissionChecker);
		}
	}

	@Override
	public String getName() {
		return "testray";
	}

	private ObjectEntry _addObjectEntry(
			String objectDefinitionShortName, ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitions.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			_log.error("Object Definition not found");

			throw new PortalException("Object Definition not found");
		}

		return _objectEntryManager.addObjectEntry(
			_defaultDTOConverterContext, objectDefinition, objectEntry, null);
	}

	private void _addTestrayFactor(
			long testrayFactorCategoryId, String testrayFactorCategoryName,
			long testrayFactorOptionId, String testrayFactorOptionName,
			long testrayRunId)
		throws Exception {

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"classNameId", testrayRunId
			).put(
				"classPK", testrayRunId
			).put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				testrayFactorCategoryId
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				testrayFactorOptionId
			).put(
				"testrayFactorCategoryName", testrayFactorCategoryName
			).put(
				"testrayFactorOptionName", testrayFactorOptionName
			).build());

		_addObjectEntry("Factor", objectEntry);
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

	private long _getObjectEntryId(
			long companyId, String name, String objectDefinitionShortName)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			objectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext,
				StringBundler.concat("name eq '", name, "'"), null, null, null);

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 0;
		}

		return objectEntry.getId();
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

	private String _getTestrayBuildDescription(
		Map<String, String> propertiesMap) {

		StringBundler sb = new StringBundler(15);

		if (propertiesMap.get("liferay.portal.bundle") != null) {
			sb.append("Bundle: ");
			sb.append(propertiesMap.get("liferay.portal.bundle"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.plugins.git.id") != null) {
			sb.append("Plugins hash: ");
			sb.append(propertiesMap.get("liferay.plugins.git.id"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.branch") != null) {
			sb.append("Portal branch: ");
			sb.append(propertiesMap.get("liferay.portal.branch"));
			sb.append(StringPool.SEMICOLON);
			sb.append(StringPool.NEW_LINE);
		}

		if (propertiesMap.get("liferay.portal.git.id") != null) {
			sb.append("Portal hash: ");
			sb.append(propertiesMap.get("liferay.portal.git.id"));
			sb.append(StringPool.SEMICOLON);
		}

		return sb.toString();
	}

	private long _getTestrayBuildId(
			long companyId, Map<String, String> propertiesMap,
			String testrayBuildName, long testrayProjectId)
		throws Exception {

		long testrayBuildId = _getObjectEntryId(
			companyId, testrayBuildName, "Build");

		if (testrayBuildId != 0) {
			return testrayBuildId;
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			companyId, propertiesMap.get("testray.product.version"),
			testrayProjectId);
		long testrayRoutineId = _getTestrayRoutineId(
			companyId, testrayProjectId,
			propertiesMap.get("testray.build.type"));

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"description", _getTestrayBuildDescription(propertiesMap)
			).put(
				"dueDate", propertiesMap.get("testray.build.time")
			).put(
				"gitHash", propertiesMap.get("git.id")
			).put(
				"githubCompareURLs", propertiesMap.get("liferay.compare.urls")
			).put(
				"name", testrayBuildName
			).put(
				"r_productVersionToBuilds_c_productVersionId",
				testrayProductVersionId
			).put(
				"r_projectToBuilds_c_projectId", testrayProjectId
			).put(
				"r_routineToBuilds_c_routineId", testrayRoutineId
			).build());

		objectEntry = _addObjectEntry("Build", objectEntry);

		return objectEntry.getId();
	}

	private long _getTestrayFactorCategoryId(
			long companyId, String testrayFactorCategoryName)
		throws Exception {

		long testrayFactorCategoryId = _getObjectEntryId(
			companyId, testrayFactorCategoryName, "FactorCategory");

		if (testrayFactorCategoryId != 0) {
			return testrayFactorCategoryId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"name", testrayFactorCategoryName
			).build());

		objectEntry = _addObjectEntry("FactorCategory", objectEntry);

		return objectEntry.getId();
	}

	private long _getTestrayFactorOptionId(
			long companyId, long testrayFactorCategoryId,
			String testrayFactorOptionName)
		throws Exception {

		long testrayFactorOptionId = _getObjectEntryId(
			companyId, testrayFactorOptionName, "FactorOption");

		if (testrayFactorOptionId != 0) {
			return testrayFactorOptionId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"name", testrayFactorOptionName
			).put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				testrayFactorCategoryId
			).build());

		objectEntry = _addObjectEntry("FactorOption", objectEntry);

		return objectEntry.getId();
	}

	private long _getTestrayProductVersionId(
			long companyId, String testrayProductVersionName,
			long testrayProjectId)
		throws Exception {

		long testrayProductVersionId = _getObjectEntryId(
			companyId, testrayProductVersionName, "ProductVersion");

		if (testrayProductVersionId != 0) {
			return testrayProductVersionId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"name", testrayProductVersionName
			).put(
				"r_projectToProductVersions_c_projectId", testrayProjectId
			).build());

		objectEntry = _addObjectEntry("ProductVersion", objectEntry);

		return objectEntry.getId();
	}

	private long _getTestrayProjectId(long companyId, String testrayProjectName)
		throws Exception {

		long testrayProjectId = _getObjectEntryId(
			companyId, testrayProjectName, "Project");

		if (testrayProjectId != 0) {
			return testrayProjectId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"name", testrayProjectName
			).build());

		objectEntry = _addObjectEntry("Project", objectEntry);

		return objectEntry.getId();
	}

	private long _getTestrayRoutineId(
			long companyId, long testrayProjectId, String testrayRoutineName)
		throws Exception {

		long testrayRoutineId = _getObjectEntryId(
			companyId, testrayRoutineName, "Routine");

		if (testrayRoutineId != 0) {
			return testrayRoutineId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"name", testrayRoutineName
			).put(
				"r_routineToProjects_c_projectId", testrayProjectId
			).build());

		objectEntry = _addObjectEntry("Routine", objectEntry);

		return objectEntry.getId();
	}

	private String _getTestrayRunEnvironmentHash(
			long companyId, Element element, long testrayRunId)
		throws Exception {

		StringBundler sb = new StringBundler();

		NodeList environmentNodeList = element.getElementsByTagName(
			"environment");

		for (int i = 0; i < environmentNodeList.getLength(); i++) {
			Node node = environmentNodeList.item(i);

			if (!node.hasAttributes()) {
				continue;
			}

			String testrayFactorCategoryName = _getAttributeValue("type", node);

			long testrayFactorCategoryId = _getTestrayFactorCategoryId(
				companyId, testrayFactorCategoryName);

			String testrayFactorOptionName = _getAttributeValue("option", node);

			long testrayFactorOptionId = _getTestrayFactorOptionId(
				companyId, testrayFactorCategoryId, testrayFactorOptionName);

			_addTestrayFactor(
				testrayFactorCategoryId, testrayFactorCategoryName,
				testrayFactorOptionId, testrayFactorOptionName, testrayRunId);

			sb.append(testrayFactorCategoryId);
			sb.append(testrayFactorOptionId);
		}

		String testrayFactorsString = sb.toString();

		return String.valueOf(testrayFactorsString.hashCode());
	}

	private long _getTestrayRunId(
			long companyId, Element element, Map<String, String> propertiesMap,
			long testrayBuildId, String testrayRunName)
		throws Exception {

		long testrayRunId = _getObjectEntryId(companyId, testrayRunName, "Run");

		if (testrayRunId != 0) {
			return testrayRunId;
		}

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(
			HashMapBuilder.<String, Object>put(
				"externalReferencePK", propertiesMap.get("testray.run.id")
			).put(
				"externalReferenceType",
				_TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI
			).put(
				"jenkinsJobKey", propertiesMap.get("jenkins.job.id")
			).put(
				"name", testrayRunName
			).put(
				"number", 0
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build());

		objectEntry = _addObjectEntry("Run", objectEntry);

		objectEntry.getProperties(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(companyId, element, testrayRunId)
		);

		_objectEntryManager.updateObjectEntry(
			_defaultDTOConverterContext, _objectDefinitions.get("Run"),
			objectEntry.getId(), objectEntry);

		return objectEntry.getId();
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

	private void _processArchive(long companyId, byte[] bytes)
		throws Exception {

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

					_invoke(() -> _processDocument(companyId, document));
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

	private void _processDocument(long companyId, Document document)
		throws Exception {

		Element element = document.getDocumentElement();

		Map<String, String> propertiesMap = _getPropertiesMap(element);

		long testrayProjectId = _getTestrayProjectId(
			companyId, propertiesMap.get("testray.project.name"));

		long testrayBuildId = _getTestrayBuildId(
			companyId, propertiesMap, propertiesMap.get("testray.build.name"),
			testrayProjectId);

		_getTestrayRunId(
			companyId, element, propertiesMap, testrayBuildId,
			propertiesMap.get("testray.run.id"));
	}

	private void _uploadToTestray(
			long companyId, UnicodeProperties unicodeProperties)
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
					_processArchive(companyId, blob.getContent());
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

	private static final int _TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();
	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}