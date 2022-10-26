/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
import com.liferay.object.model.ObjectDefinition;
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
		"dispatch.task.executor.cluster.mode=single-node",
		"dispatch.task.executor.feature.flag=LPS-163118",
		"dispatch.task.executor.name=testray",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray"
	},
	service = DispatchTaskExecutor.class
)
public class SiteInitializerTestrayDispatchTaskExecutor
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
		return "testray";
	}

	private ObjectEntry _addObjectEntry(
			String objectDefinitionShortName, Map<String, Object> properties)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			objectDefinitionShortName);

		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setProperties(properties);

		return _objectEntryManager.addObjectEntry(
			_defaultDTOConverterContext, objectDefinition, objectEntry, null);
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
			ObjectEntry objectEntry = _addObjectEntry(
				"Case",
				HashMapBuilder.<String, Object>put(
					"caseNumber",
					_increment(
						companyId, "caseNumber",
						"projectId eq '" + testrayProjectId + "'", "Case")
				).put(
					"description",
					testrayCasePropertiesMap.get("testray.testcase.description")
				).put(
					"name",
					(String)testrayCasePropertiesMap.get(
						"testray.testcase.name")
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

		_addTestrayCaseResultIssue(
			companyId, testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.defect"));
		_addTestrayCaseResultIssue(
			companyId, testrayCaseResultId,
			(String)testrayCasePropertiesMap.get("testray.case.issue"));
	}

	private void _addTestrayCaseResultIssue(
			long companyId, long testrayCaseResultId, String testrayIssueName)
		throws Exception {

		String objectEntryIdsKey = "Issue#" + testrayIssueName;

		if (_isEmpty(testrayIssueName)) {
			return;
		}

		_addObjectEntry(
			"CaseResultsIssues",
			HashMapBuilder.<String, Object>put(
				"r_caseResultToCaseResultsIssues_c_caseResultId",
				testrayCaseResultId
			).put(
				"r_issueToCaseResultsIssues_c_issueId",
				() -> {
					long testrayIssueId = _getObjectEntryId(
						companyId, "name eq '" + testrayIssueName + "'",
						"Issue", objectEntryIdsKey);

					if (testrayIssueId > 0) {
						return testrayIssueId;
					}

					testrayIssueId = _addTestrayIssue(testrayIssueName);

					_objectEntryIds.put(objectEntryIdsKey, testrayIssueId);

					return testrayIssueId;
				}
			).build());
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

		_addObjectEntry(
			"Factor",
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

	private long _addTestrayIssue(String testrayIssueName) throws Exception {
		ObjectEntry objectEntry = _addObjectEntry(
			"Issue",
			HashMapBuilder.<String, Object>put(
				"name", testrayIssueName
			).build());

		return objectEntry.getId();
	}

	private void _autofill(
			long companyId, ObjectEntry testrayCaseResultObjectEntry1,
			ObjectEntry testrayCaseResultObjectEntry2)
		throws Exception {

		ObjectEntry destinationTestrayCaseResultObjectEntry = null;
		ObjectEntry sourceTestrayCaseResultObjectEntry = null;
		List<ObjectEntry> sourceTestrayCaseResultsIssuesObjectEntries = null;

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			testrayCaseResultsIssuesObjectEntriesPage1 =
				_objectEntryManager.getObjectEntries(
					companyId, _objectDefinitions.get("CaseResultsIssues"),
					null, null, _defaultDTOConverterContext,
					"caseResultId eq '" +
						testrayCaseResultObjectEntry1.getId() + "'",
					null, null, null);

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries1 =
			(List<ObjectEntry>)
				testrayCaseResultsIssuesObjectEntriesPage1.getItems();

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			testrayCaseResultsIssuesObjectEntriesPage2 =
				_objectEntryManager.getObjectEntries(
					companyId, _objectDefinitions.get("CaseResultsIssues"),
					null, null, _defaultDTOConverterContext,
					"caseResultId eq '" +
						testrayCaseResultObjectEntry2.getId() + "'",
					null, null, null);

		List<ObjectEntry> testrayCaseResultsIssuesObjectEntries2 =
			(List<ObjectEntry>)
				testrayCaseResultsIssuesObjectEntriesPage2.getItems();

		if (((Long)_getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry1) >
					0) &&
			!testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
			((Long)_getProperty(
				"r_userToCaseResults_userId", testrayCaseResultObjectEntry2) <=
					0) &&
			testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries1;
		}
		else if (((Long)_getProperty(
					"r_userToCaseResults_userId",
					testrayCaseResultObjectEntry1) <= 0) &&
				 testrayCaseResultsIssuesObjectEntries1.isEmpty() &&
				 ((Long)_getProperty(
					 "r_userToCaseResults_userId",
					 testrayCaseResultObjectEntry2) > 0) &&
				 !testrayCaseResultsIssuesObjectEntries2.isEmpty()) {

			destinationTestrayCaseResultObjectEntry =
				testrayCaseResultObjectEntry1;
			sourceTestrayCaseResultObjectEntry = testrayCaseResultObjectEntry2;
			sourceTestrayCaseResultsIssuesObjectEntries =
				testrayCaseResultsIssuesObjectEntries2;
		}

		if ((destinationTestrayCaseResultObjectEntry == null) ||
			(sourceTestrayCaseResultObjectEntry == null)) {

			return;
		}

		Map<String, Object> properties =
			destinationTestrayCaseResultObjectEntry.getProperties();

		properties.put(
			"dueStatus",
			_getProperty("dueStatus", sourceTestrayCaseResultObjectEntry));
		properties.put(
			"r_userToCaseResults_userId",
			_getProperty(
				"r_userToCaseResults_userId",
				sourceTestrayCaseResultObjectEntry));

		_objectEntryManager.updateObjectEntry(
			_defaultDTOConverterContext, _objectDefinitions.get("CaseResult"),
			destinationTestrayCaseResultObjectEntry.getId(),
			destinationTestrayCaseResultObjectEntry);

		for (ObjectEntry sourceTestrayCaseResultsIssuesObjectEntry :
				sourceTestrayCaseResultsIssuesObjectEntries) {

			String testrayIssueId = String.valueOf(
				_getProperty(
					"r_issueToCaseResultsIssues_c_issueId",
					sourceTestrayCaseResultsIssuesObjectEntry));

			com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
				testrayIssueObjectEntriesPage =
					_objectEntryManager.getObjectEntries(
						companyId, _objectDefinitions.get("Issue"), null, null,
						_defaultDTOConverterContext,
						"id eq '" + testrayIssueId + "'", null, null, null);

			ObjectEntry testrayIssueObjectEntry =
				testrayIssueObjectEntriesPage.fetchFirstItem();

			if (testrayIssueObjectEntry == null) {
				continue;
			}

			_addTestrayCaseResultIssue(
				companyId, destinationTestrayCaseResultObjectEntry.getId(),
				(String)_getProperty("name", testrayIssueObjectEntry));
		}
	}

	private ObjectEntry _fetchLatestTestrayRunObjectEntry(
			long companyId, String environmentHash, long testrayRoutineId,
			long testrayRunId)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			testrayBuildsObjectEntriesPage =
				_objectEntryManager.getObjectEntries(
					companyId, _objectDefinitions.get("Build"), null, null,
					_defaultDTOConverterContext,
					"routineId eq '" + testrayRoutineId + "'", null, null,
					null);

		List<ObjectEntry> testrayBuildsObjectEntries =
			(List<ObjectEntry>)testrayBuildsObjectEntriesPage.getItems();

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			testrayRunsObjectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get("Run"), null, null,
				_defaultDTOConverterContext,
				StringBundler.concat(
					"environmentHash eq '", environmentHash, "' and id ne '",
					testrayRunId, "'"),
				null, null, new Sort[] {new Sort("createDate", 3, true)});

		List<ObjectEntry> testrayRunsObjectEntries =
			(List<ObjectEntry>)testrayRunsObjectEntriesPage.getItems();

		for (ObjectEntry testrayRunObjectEntry : testrayRunsObjectEntries) {
			for (ObjectEntry testrayBuildObjectEntry :
					testrayBuildsObjectEntries) {

				if (Objects.equals(
						testrayBuildObjectEntry.getId(),
						_getProperty(
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

	private ObjectDefinition _getObjectDefinition(
			String objectDefinitionShortName)
		throws Exception {

		ObjectDefinition objectDefinition = _objectDefinitions.get(
			objectDefinitionShortName);

		if (objectDefinition == null) {
			throw new PortalException(
				"No object definition found with short name " +
					objectDefinitionShortName);
		}

		return objectDefinition;
	}

	private List<ObjectEntry> _getObjectEntries(
			long companyId, String objectDefinitionShortName)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			objectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _getObjectDefinition(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, (Filter)null, null,
				null, null);

		return (List<ObjectEntry>)objectEntriesPage.getItems();
	}

	private long _getObjectEntryId(
			long companyId, String filterString,
			String objectDefinitionShortName, String objectEntryIdsKey)
		throws Exception {

		Long objectEntryId = _objectEntryIds.get(objectEntryIdsKey);

		if (objectEntryId != null) {
			return objectEntryId;
		}

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			objectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, filterString, null,
				null, null);

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

	private Object _getProperty(String key, ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		return properties.get(key);
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

		ObjectEntry objectEntry = _addObjectEntry(
			"Build",
			HashMapBuilder.<String, Object>put(
				"description", _getTestrayBuildDescription(propertiesMap)
			).put(
				"dueDate", propertiesMap.get("testray.build.time")
			).put(
				"dueStatus", _TESTRAY_BUILD_STATUS_ACTIVE
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
					return _TESTRAY_CASE_RESULT_STATUS_BLOCKED;
				}
				else if (testrayTestcaseStatus.equals("dnr")) {
					return _TESTRAY_CASE_RESULT_STATUS_DID_NOT_RUN;
				}
				else if (testrayTestcaseStatus.equals("failed")) {
					return _TESTRAY_CASE_RESULT_STATUS_FAILED;
				}
				else if (testrayTestcaseStatus.equals("in-progress")) {
					return _TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS;
				}
				else if (testrayTestcaseStatus.equals("passed")) {
					return _TESTRAY_CASE_RESULT_STATUS_PASSED;
				}
				else if (testrayTestcaseStatus.equals("test-fix")) {
					return _TESTRAY_CASE_RESULT_STATUS_TEST_FIX;
				}

				return _TESTRAY_CASE_RESULT_STATUS_UNTESTED;
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

		ObjectEntry objectEntry = _addObjectEntry("CaseResult", properties);

		return objectEntry.getId();
	}

	private Map<Long, ObjectEntry> _getTestrayCaseResultObjectEntries(
			long companyId, ObjectEntry testrayRunObjectEntry)
		throws Exception {

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries = new HashMap<>();

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry> page =
			_objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get("CaseResult"), null, null,
				_defaultDTOConverterContext,
				"runId eq '" + testrayRunObjectEntry.getId() + "'", null, null,
				null);

		for (ObjectEntry objectEntry : page.getItems()) {
			testrayCaseResultObjectEntries.put(
				(Long)_getProperty("r_caseToCaseResult_c_caseId", objectEntry),
				objectEntry);
		}

		return testrayCaseResultObjectEntries;
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

		ObjectEntry objectEntry = _addObjectEntry(
			"CaseType",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"Component",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorCategory",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"FactorOption",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"ProductVersion",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"Project",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"Routine",
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

		ObjectEntry objectEntry = _addObjectEntry(
			"Run",
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
				"number",
				_increment(
					companyId, "number", "buildId eq '" + testrayBuildId + "'",
					"Run")
			).put(
				"r_buildToRuns_c_buildId", testrayBuildId
			).build());

		testrayRunId = objectEntry.getId();

		objectEntry.getProperties(
		).put(
			"environmentHash",
			_getTestrayRunEnvironmentHash(companyId, element, testrayRunId)
		);

		_objectEntryManager.updateObjectEntry(
			_defaultDTOConverterContext, _objectDefinitions.get("Run"),
			objectEntry.getId(), objectEntry);

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

		ObjectEntry objectEntry = _addObjectEntry(
			"Team",
			HashMapBuilder.<String, Object>put(
				"name", testrayTeamName
			).put(
				"r_projectToTeams_c_projectId", testrayProjectId
			).build());

		_objectEntryIds.put(objectEntryIdsKey, objectEntry.getId());

		return objectEntry.getId();
	}

	private long _increment(
			long companyId, String fieldName, String filterString,
			String objectDefinitionShortName)
		throws Exception {

		com.liferay.portal.vulcan.pagination.Page<ObjectEntry>
			objectEntriesPage = _objectEntryManager.getObjectEntries(
				companyId, _objectDefinitions.get(objectDefinitionShortName),
				null, null, _defaultDTOConverterContext, filterString, null,
				null,
				new Sort[] {
					new Sort("nestedFieldArray.value_long#" + fieldName, true)
				});

		ObjectEntry objectEntry = objectEntriesPage.fetchFirstItem();

		if (objectEntry == null) {
			return 1;
		}

		Long fieldValue = (Long)_getProperty(fieldName, objectEntry);

		if (fieldValue == null) {
			return 1;
		}

		return fieldValue.longValue() + 1;
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

	private boolean _isEmpty(String value) {
		if (value == null) {
			return true;
		}

		String trimmedValue = value.trim();

		return trimmedValue.isEmpty();
	}

	private void _load(long companyId) throws Exception {
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
		List<ObjectEntry> objectEntries = _getObjectEntries(
			companyId, "CaseType");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"CaseType#" + (String)_getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayComponents(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = _getObjectEntries(
			companyId, "Component");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Component#", (String)_getProperty("name", objectEntry),
					"#TeamId#",
					(Long)_getProperty(
						"r_teamToComponents_c_teamId", objectEntry)),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorCategories(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = _getObjectEntries(
			companyId, "FactorCategory");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"FactorCategory#" + (String)_getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayFactorOptions(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = _getObjectEntries(
			companyId, "FactorOption");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"FactorOption#", (String)_getProperty("name", objectEntry),
					"#FactorCategoryId#",
					(Long)_getProperty(
						"r_factorCategoryToOptions_c_factorCategoryId",
						objectEntry)),
				objectEntry.getId());
		}
	}

	private void _loadTestrayProjects(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = _getObjectEntries(
			companyId, "Project");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				"Project#" + (String)_getProperty("name", objectEntry),
				objectEntry.getId());
		}
	}

	private void _loadTestrayTeams(long companyId) throws Exception {
		List<ObjectEntry> objectEntries = _getObjectEntries(companyId, "Team");

		if (ListUtil.isEmpty(objectEntries)) {
			return;
		}

		for (ObjectEntry objectEntry : objectEntries) {
			_objectEntryIds.put(
				StringBundler.concat(
					"Team#", (String)_getProperty("name", objectEntry),
					"#ProjectId#",
					(Long)_getProperty(
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

		ObjectEntry testrayRoutineObjectEntry =
			_objectEntryManager.getObjectEntry(
				_defaultDTOConverterContext, _objectDefinitions.get("Routine"),
				testrayRoutineId);

		if (!(Boolean)_getProperty("autoanalyze", testrayRoutineObjectEntry)) {
			return;
		}

		ObjectEntry testrayRunObjectEntry1 = _objectEntryManager.getObjectEntry(
			_defaultDTOConverterContext, _objectDefinitions.get("Run"),
			testrayRunId);

		ObjectEntry testrayRunObjectEntry2 = _fetchLatestTestrayRunObjectEntry(
			companyId,
			(String)_getProperty("environmentHash", testrayRunObjectEntry1),
			testrayRoutineObjectEntry.getId(), testrayRunId);

		if (testrayRunObjectEntry2 == null) {
			return;
		}

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries1 =
			_getTestrayCaseResultObjectEntries(
				companyId, testrayRunObjectEntry1);

		Map<Long, ObjectEntry> testrayCaseResultObjectEntries2 =
			_getTestrayCaseResultObjectEntries(
				companyId, testrayRunObjectEntry2);

		for (Map.Entry<Long, ObjectEntry> entry :
				testrayCaseResultObjectEntries1.entrySet()) {

			ObjectEntry testrayCaseResultObjectEntry2 =
				testrayCaseResultObjectEntries2.get(entry.getKey());

			if (testrayCaseResultObjectEntry2 == null) {
				continue;
			}

			ObjectEntry testrayCaseResultObjectEntry1 = entry.getValue();

			String testrayCaseResultErrors1 = (String)_getProperty(
				"errors", testrayCaseResultObjectEntry1);

			String testrayCaseResultErrors2 = (String)_getProperty(
				"errors", testrayCaseResultObjectEntry2);

			if (Validator.isNull(testrayCaseResultErrors1) ||
				Validator.isNull(testrayCaseResultErrors2) ||
				!Objects.equals(
					testrayCaseResultErrors1, testrayCaseResultErrors2)) {

				continue;
			}

			_autofill(
				companyId, testrayCaseResultObjectEntry1,
				testrayCaseResultObjectEntry2);
		}
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

	private static final String _TESTRAY_BUILD_STATUS_ACTIVE = "ACTIVE";

	private static final String _TESTRAY_CASE_RESULT_STATUS_BLOCKED = "BLOCKED";

	private static final String _TESTRAY_CASE_RESULT_STATUS_DID_NOT_RUN = "DIDNOTRUN";

	private static final String _TESTRAY_CASE_RESULT_STATUS_FAILED = "FAILED";

	private static final String _TESTRAY_CASE_RESULT_STATUS_IN_PROGRESS = "INPROGRESS";

	private static final String _TESTRAY_CASE_RESULT_STATUS_PASSED = "PASSED";

	private static final String _TESTRAY_CASE_RESULT_STATUS_TEST_FIX = "TESTFIX";

	private static final String _TESTRAY_CASE_RESULT_STATUS_UNTESTED = "UNTESTED";

	private static final int _TESTRAY_RUN_EXTERNAL_REFERENCE_TYPE_POSHI = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerTestrayDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();
	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}