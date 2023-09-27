/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
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
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.ObjectEntryUtil;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.TestrayUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
		"dispatch.task.executor.feature.flag=LPS-163118",
		"dispatch.task.executor.name=testray-import-results",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-import-results"
	},
	service = DispatchTaskExecutor.class
)
public class SiteInitializerTestrayImportResultsDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("s3APIKey")) ||
			Validator.isNull(unicodeProperties.getProperty("s3BucketName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ErroredFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3InboxFolderName")) ||
			Validator.isNull(
				unicodeProperties.getProperty("s3ProcessedFolderName"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			_invoke(() -> _load(dispatchTrigger.getCompanyId()));

			_invoke(
				() -> _uploadToTestray(
					dispatchTrigger.getCompanyId(), unicodeProperties));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-import-results";
	}

	@Override
	public boolean isClusterModeSingle() {
		return true;
	}

	private JSONArray _addTestrayAttachments(Node testcaseNode)
		throws Exception {

		JSONArray jsonArray = null;

		Element testcaseElement = (Element)testcaseNode;

		NodeList attachmentsNodeList = testcaseElement.getElementsByTagName(
			"attachments");

		for (int i = 0; i < attachmentsNodeList.getLength(); i++) {
			Node attachmentsNode = attachmentsNodeList.item(i);

			if (attachmentsNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element attachmentsElement = (Element)attachmentsNode;

			NodeList fileNodeList = attachmentsElement.getElementsByTagName(
				"file");

			for (int j = 0; j < fileNodeList.getLength(); j++) {
				Node fileNode = fileNodeList.item(j);

				if (fileNode.getNodeType() != Node.ELEMENT_NODE) {
					continue;
				}

				Element fileElement = (Element)fileNode;

				jsonArray = JSONUtil.put(
					JSONUtil.put(
						"name", fileElement.getAttribute("name")
					).put(
						"url", fileElement.getAttribute("url")
					).put(
						"value", fileElement.getAttribute("value")
					));
			}
		}

		return jsonArray;
	}

	private void _addTestrayCase(
			long companyId, Node testcaseNode, long testrayBuildId,
			String testrayBuildTime,
			Map<String, Object> testrayCasePropertiesMap, long testrayProjectId,
			long testrayRunId)
		throws Exception {

		String testrayCaseName = (String)testrayCasePropertiesMap.get(
			"testray.testcase.name");

		String objectEntryIdsKey = StringBundler.concat(
			"Case#", testrayCaseName, "#ProjectId#", testrayProjectId);

		long testrayCaseId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayCaseName, "'"),
			"Case", objectEntryIdsKey);

		long testrayTeamId = _getTestrayTeamId(
			companyId, testrayProjectId,
			(String)testrayCasePropertiesMap.get("testray.team.name"));

		long testrayComponentId = _getTestrayComponentId(
			companyId,
			(String)testrayCasePropertiesMap.get("testray.main.component.name"),
			testrayProjectId, testrayTeamId);

		if (testrayCaseId == 0) {
			ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
				_defaultDTOConverterContext, "Case", _objectEntryManager,
				HashMapBuilder.<String, Object>put(
					"description",
					testrayCasePropertiesMap.get("testray.testcase.description")
				).put(
					"name",
					(String)testrayCasePropertiesMap.get(
						"testray.testcase.name")
				).put(
					"number",
					ObjectEntryUtil.increment(
						companyId, _defaultDTOConverterContext,
						"projectId eq '" + testrayProjectId + "'", "number",
						"Case", _objectEntryManager,
						new Sort[] {
							new Sort("nestedFieldArray.value_long#number", true)
						})
				).put(
					"priority",
					testrayCasePropertiesMap.get("testray.testcase.priority")
				).put(
					"r_caseTypeToCases_c_caseTypeId",
					_getTestrayCaseTypeId(
						companyId,
						(String)testrayCasePropertiesMap.get(
							"testray.case.type.name"))
				).put(
					"r_componentToCases_c_componentId", testrayComponentId
				).put(
					"r_projectToCases_c_projectId", testrayProjectId
				).build());

			testrayCaseId = objectEntry.getId();

			_objectEntryIds.put(objectEntryIdsKey, testrayCaseId);
		}

		long testrayCaseResultId = _getTestrayCaseResultId(
			testcaseNode, testrayBuildId, testrayBuildTime, testrayCaseId,
			testrayCasePropertiesMap, testrayComponentId, testrayRunId);

		TestrayUtil.addTestrayCaseResultIssue(
			companyId, _defaultDTOConverterContext, _objectEntryManager,
			testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.defect"));
		TestrayUtil.addTestrayCaseResultIssue(
			companyId, _defaultDTOConverterContext, _objectEntryManager,
			testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.issue"));
	}

	private void _addTestrayCases(
			long companyId, Element element, long testrayBuildId,
			String testrayBuildTime, long testrayProjectId, long testrayRunId)
		throws Exception {

		NodeList testCaseNodeList = element.getElementsByTagName("testcase");

		for (int i = 0; i < testCaseNodeList.getLength(); i++) {
			Node testcaseNode = testCaseNodeList.item(i);

			Map<String, Object> testrayCasePropertiesMap =
				_getTestrayCaseProperties((Element)testcaseNode);

			_addTestrayCase(
				companyId, testcaseNode, testrayBuildId, testrayBuildTime,
				testrayCasePropertiesMap, testrayProjectId, testrayRunId);
		}
	}

	private void _addTestrayFactor(
			long testrayFactorCategoryId, String testrayFactorCategoryName,
			long testrayFactorOptionId, String testrayFactorOptionName,
			long testrayRunId)
		throws Exception {

		ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Factor", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"r_factorCategoryToFactors_c_factorCategoryId",
				testrayFactorCategoryId
			).put(
				"r_factorOptionToFactors_c_factorOptionId",
				testrayFactorOptionId
			).put(
				"r_runToFactors_c_runId", testrayRunId
			).put(
				"testrayFactorCategoryName", testrayFactorCategoryName
			).put(
				"testrayFactorOptionName", testrayFactorOptionName
			).build());
	}

	private ObjectEntry _fetchLatestTestrayRunObjectEntry(
			long companyId, String environmentHash, long testrayRoutineId,
			long testrayRunId)
		throws Exception {

		List<ObjectEntry> testrayBuildsObjectEntries =
			ObjectEntryUtil.getObjectEntries(
				null, companyId, _defaultDTOConverterContext,
				"routineId eq '" + testrayRoutineId + "'", "Build",
				_objectEntryManager, null);

		List<ObjectEntry> testrayRunsObjectEntries =
			ObjectEntryUtil.getObjectEntries(
				null, companyId, _defaultDTOConverterContext,
				StringBundler.concat(
					"environmentHash eq '", environmentHash, "' and id ne '",
					testrayRunId, "'"),
				"Run", _objectEntryManager,
				new Sort[] {new Sort("createDate", 3, true)});

		for (ObjectEntry testrayRunObjectEntry : testrayRunsObjectEntries) {
			for (ObjectEntry testrayBuildObjectEntry :
					testrayBuildsObjectEntries) {

				if (Objects.equals(
						testrayBuildObjectEntry.getId(),
						ObjectEntryUtil.getProperty(
							"r_buildToRuns_c_buildId",
							testrayRunObjectEntry))) {

					return testrayRunObjectEntry;
				}
			}
		}

		return null;
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

	private long _getObjectEntryId(
			long companyId, String filterString,
			String objectDefinitionShortName, String objectEntryIdsKey)
		throws Exception {

		Long objectEntryId = _objectEntryIds.get(objectEntryIdsKey);

		if (objectEntryId != null) {
			return objectEntryId;
		}

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry> page =
			ObjectEntryUtil.getObjectEntriesPage(
				null, companyId, _defaultDTOConverterContext, filterString,
				objectDefinitionShortName, _objectEntryManager, null);

		ObjectEntry objectEntry = page.fetchFirstItem();

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
			String testrayBuildName, long testrayProjectId,
			long testrayRoutineId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Build#", testrayBuildName, "#ProjectId#", testrayProjectId);

		long testrayBuildId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayBuildName, "'"),
			"Build", objectEntryIdsKey);

		if (testrayBuildId != 0) {
			return testrayBuildId;
		}

		long testrayProductVersionId = _getTestrayProductVersionId(
			companyId, propertiesMap.get("testray.product.version"),
			testrayProjectId);

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Build", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"description", _getTestrayBuildDescription(propertiesMap)
			).put(
				"dueDate", propertiesMap.get("testray.build.time")
			).put(
				"dueStatus", "ACTIVATED"
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

		testrayBuildId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayBuildId);

		return testrayBuildId;
	}

	private Map<String, Object> _getTestrayCaseProperties(Element element) {
		Map<String, Object> map = new HashMap<>();

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

	private long _getTestrayCaseResultId(
			Node testcaseNode, long testrayBuildId, String testrayBuildTime,
			long testrayCaseId, Map<String, Object> testrayCasePropertiesMap,
			long testrayComponentId, long testrayRunId)
		throws Exception {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"attachments", _addTestrayAttachments(testcaseNode)
		).put(
			"closedDate", testrayBuildTime
		).put(
			"dueStatus",
			() -> {
				String testrayTestcaseStatus =
					(String)testrayCasePropertiesMap.get(
						"testray.testcase.status");

				if (testrayTestcaseStatus.equals("blocked")) {
					return "BLOCKED";
				}
				else if (testrayTestcaseStatus.equals("dnr")) {
					return "DIDNOTRUN";
				}
				else if (testrayTestcaseStatus.equals("failed")) {
					return "FAILED";
				}
				else if (testrayTestcaseStatus.equals("in-progress")) {
					return "INPROGRESS";
				}
				else if (testrayTestcaseStatus.equals("passed")) {
					return "PASSED";
				}
				else if (testrayTestcaseStatus.equals("test-fix")) {
					return "TESTFIX";
				}

				return "UNTESTED";
			}
		).put(
			"r_buildToCaseResult_c_buildId", testrayBuildId
		).put(
			"r_caseToCaseResult_c_caseId", testrayCaseId
		).put(
			"r_componentToCaseResult_c_componentId", testrayComponentId
		).put(
			"r_runToCaseResult_c_runId", testrayRunId
		).put(
			"startDate", testrayBuildTime
		).put(
			"warnings",
			(Integer)testrayCasePropertiesMap.get("testray.testcase.warnings")
		).build();

		Element element = (Element)testcaseNode;

		NodeList nodeList = element.getElementsByTagName("failure");

		Node failureNode = nodeList.item(0);

		if (failureNode != null) {
			String message = _getAttributeValue("message", failureNode);

			if (!message.isEmpty()) {
				properties.put("errors", message);
			}
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "CaseResult", _objectEntryManager,
			properties);

		return objectEntry.getId();
	}

	private long _getTestrayCaseTypeId(
			long companyId, String testrayCaseTypeName)
		throws Exception {

		String objectEntryIdsKey = "CaseType#" + testrayCaseTypeName;

		long testrayCaseTypeId = _getObjectEntryId(
			companyId, "name eq '" + testrayCaseTypeName + "'", "CaseType",
			objectEntryIdsKey);

		if (testrayCaseTypeId != 0) {
			return testrayCaseTypeId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "CaseType", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayCaseTypeName
			).build());

		testrayCaseTypeId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayCaseTypeId);

		return testrayCaseTypeId;
	}

	private long _getTestrayComponentId(
			long companyId, String testrayComponentName, long testrayProjectId,
			long testrayTeamId)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Component#", testrayComponentName, "#ProjectId#",
			testrayProjectId);

		long testrayComponentId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayComponentName, "'"),
			"Component", objectEntryIdsKey);

		if (testrayComponentId != 0) {
			return testrayComponentId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Component", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayComponentName
			).put(
				"r_projectToComponents_c_projectId", testrayProjectId
			).put(
				"r_teamToComponents_c_teamId", testrayTeamId
			).build());

		testrayComponentId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayComponentId);

		return testrayComponentId;
	}

	private long _getTestrayFactorCategoryId(
			long companyId, String testrayFactorCategoryName)
		throws Exception {

		String objectEntryIdsKey =
			"FactorCategory#" + testrayFactorCategoryName;

		long testrayFactorCategoryId = _getObjectEntryId(
			companyId, "name eq '" + testrayFactorCategoryName + "'",
			"FactorCategory", objectEntryIdsKey);

		if (testrayFactorCategoryId != 0) {
			return testrayFactorCategoryId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "FactorCategory", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayFactorCategoryName
			).build());

		testrayFactorCategoryId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayFactorCategoryId);

		return testrayFactorCategoryId;
	}

	private long _getTestrayFactorOptionId(
			long companyId, long testrayFactorCategoryId,
			String testrayFactorOptionName)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"FactorOption#", testrayFactorOptionName, "#FactorCategoryId#",
			testrayFactorCategoryId);

		long testrayFactorOptionId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"factorCategoryId eq '", testrayFactorCategoryId,
				"' and name eq '", testrayFactorOptionName, "'"),
			"FactorOption", objectEntryIdsKey);

		if (testrayFactorOptionId != 0) {
			return testrayFactorOptionId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "FactorOption", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayFactorOptionName
			).put(
				"r_factorCategoryToOptions_c_factorCategoryId",
				testrayFactorCategoryId
			).build());

		testrayFactorOptionId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayFactorOptionId);

		return testrayFactorOptionId;
	}

	private long _getTestrayProductVersionId(
			long companyId, String testrayProductVersionName,
			long testrayProjectId)
		throws Exception {

		String objectEntryIdsKey =
			"ProductVersion#" + testrayProductVersionName;

		long testrayProductVersionId = _getObjectEntryId(
			companyId, "name eq '" + testrayProductVersionName + "'",
			"ProductVersion", objectEntryIdsKey);

		if (testrayProductVersionId != 0) {
			return testrayProductVersionId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "ProductVersion", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayProductVersionName
			).put(
				"r_projectToProductVersions_c_projectId", testrayProjectId
			).build());

		testrayProductVersionId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayProductVersionId);

		return testrayProductVersionId;
	}

	private long _getTestrayProjectId(long companyId, String testrayProjectName)
		throws Exception {

		String objectEntryIdsKey = "Project#" + testrayProjectName;

		long testrayProjectId = _getObjectEntryId(
			companyId, "name eq '" + testrayProjectName + "'", "Project",
			objectEntryIdsKey);

		if (testrayProjectId != 0) {
			return testrayProjectId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Project", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayProjectName
			).build());

		testrayProjectId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayProjectId);

		return testrayProjectId;
	}

	private long _getTestrayRoutineId(
			long companyId, long testrayProjectId, String testrayRoutineName)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Routine#", testrayRoutineName, "#ProjectId#", testrayProjectId);

		long testrayRoutineId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayRoutineName, "'"),
			"Routine", objectEntryIdsKey);

		if (testrayRoutineId != 0) {
			return testrayRoutineId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Routine", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayRoutineName
			).put(
				"r_routineToProjects_c_projectId", testrayProjectId
			).build());

		testrayRoutineId = objectEntry.getId();

		_objectEntryIds.put(objectEntryIdsKey, testrayRoutineId);

		return testrayRoutineId;
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

		String objectEntryIdsKey = StringBundler.concat(
			"Run#", testrayRunName, "#BuildId#", testrayBuildId);

		long testrayRunId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"buildId eq '", testrayBuildId, "' and name eq '",
				testrayRunName, "'"),
			"Run", objectEntryIdsKey);

		if (testrayRunId != 0) {
			return testrayRunId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Run", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"externalReferencePK", propertiesMap.get("testray.run.id")
			).put(
				"externalReferenceType", 1
			).put(
				"jenkinsJobKey", propertiesMap.get("jenkins.job.id")
			).put(
				"name", testrayRunName
			).put(
				"number",
				ObjectEntryUtil.increment(
					companyId, _defaultDTOConverterContext,
					"buildId eq '" + testrayBuildId + "'", "number", "Run",
					_objectEntryManager,
					new Sort[] {
						new Sort("nestedFieldArray.value_long#number", true)
					})
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build());

		testrayRunId = objectEntry.getId();

		objectEntry.getProperties(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(companyId, element, testrayRunId)
		);

		ObjectEntryUtil.updateObjectEntry(
			_defaultDTOConverterContext, "Run", objectEntry,
			objectEntry.getId(), _objectEntryManager);

		_objectEntryIds.put(objectEntryIdsKey, testrayRunId);

		return testrayRunId;
	}

	private long _getTestrayTeamId(
			long companyId, long testrayProjectId, String testrayTeamName)
		throws Exception {

		String objectEntryIdsKey = StringBundler.concat(
			"Team#", testrayTeamName, "#ProjectId#", testrayProjectId);

		long testrayTeamId = _getObjectEntryId(
			companyId,
			StringBundler.concat(
				"projectId eq '", testrayProjectId, "' and name eq '",
				testrayTeamName, "'"),
			"Team", objectEntryIdsKey);

		if (testrayTeamId != 0) {
			return testrayTeamId;
		}

		ObjectEntry objectEntry = ObjectEntryUtil.addObjectEntry(
			_defaultDTOConverterContext, "Team", _objectEntryManager,
			HashMapBuilder.<String, Object>put(
				"name", testrayTeamName
			).put(
				"r_projectToTeams_c_projectId", testrayProjectId
			).build());

		_objectEntryIds.put(objectEntryIdsKey, objectEntry.getId());

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

	private void _load(long companyId) throws Exception {
		ObjectEntryUtil.loadObjectDefinitions(
			companyId, _objectDefinitionLocalService);

		_loadTestrayCaseTypes(companyId);
		_loadTestrayComponents(companyId);
		_loadTestrayFactorCategories(companyId);
		_loadTestrayFactorOptions(companyId);
		_loadTestrayProjects(companyId);
		_loadTestrayTeams(companyId);
	}

	private void _loadTestrayCaseTypes(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null, "CaseType",
			_objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"CaseType#" +
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayComponents(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null, "Component",
			_objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Component#",
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
					"#TeamId#",
					(Long)ObjectEntryUtil.getProperty(
						"r_teamToComponents_c_teamId", objectEntry)),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorCategories(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null,
			"FactorCategory", _objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"FactorCategory#" +
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorOptions(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null, "FactorOption",
			_objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"FactorOption#",
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
					"#FactorCategoryId#",
					(Long)ObjectEntryUtil.getProperty(
						"r_factorCategoryToOptions_c_factorCategoryId",
						objectEntry)),
				objectEntry.getId());
		}
	}

	private void _loadTestrayProjects(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null, "Project",
			_objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"Project#" +
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayTeams(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = ObjectEntryUtil.getObjectEntries(
			null, companyId, _defaultDTOConverterContext, null, "Team",
			_objectEntryManager, null);

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Team#",
					(String)ObjectEntryUtil.getProperty("name", objectEntry),
					"#ProjectId#",
					(Long)ObjectEntryUtil.getProperty(
						"r_projectToTeams_c_projectIds", objectEntry)),
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

		long testrayRoutineId = _getTestrayRoutineId(
			companyId, testrayProjectId,
			propertiesMap.get("testray.build.type"));

		long testrayBuildId = _getTestrayBuildId(
			companyId, propertiesMap, propertiesMap.get("testray.build.name"),
			testrayProjectId, testrayRoutineId);

		long testrayRunId = _getTestrayRunId(
			companyId, element, propertiesMap, testrayBuildId,
			propertiesMap.get("testray.run.id"));

		_addTestrayCases(
			companyId, element, testrayBuildId,
			propertiesMap.get("testray.build.time"), testrayProjectId,
			testrayRunId);

		ObjectEntry testrayRoutineObjectEntry = ObjectEntryUtil.getObjectEntry(
			_defaultDTOConverterContext, "Routine", testrayRoutineId,
			_objectEntryManager);

		if (!(Boolean)ObjectEntryUtil.getProperty(
				"autoanalyze", testrayRoutineObjectEntry)) {

			return;
		}

		ObjectEntry testrayRunObjectEntry1 = ObjectEntryUtil.getObjectEntry(
			_defaultDTOConverterContext, "Run", testrayRunId,
			_objectEntryManager);

		ObjectEntry testrayRunObjectEntry2 = _fetchLatestTestrayRunObjectEntry(
			companyId,
			(String)ObjectEntryUtil.getProperty(
				"environmentHash", testrayRunObjectEntry1),
			testrayRoutineObjectEntry.getId(), testrayRunId);

		if (testrayRunObjectEntry2 == null) {
			return;
		}

		TestrayUtil.autofillTestrayRuns(
			companyId, _defaultDTOConverterContext, _objectEntryManager,
			testrayRunObjectEntry1, testrayRunObjectEntry2);
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

					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ProcessedFolderName")));
				}
				catch (Exception exception) {
					_log.error(exception);
					blob.copyTo(
						unicodeProperties.getProperty("s3BucketName"),
						name.replaceFirst(
							s3InboxFolderName,
							unicodeProperties.getProperty(
								"s3ErroredFolderName")));
				}

				blob.delete();
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to authenticate with GCP");

			throw new PortalException(
				"Unable to authenticate with GCP", ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerTestrayImportResultsDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}