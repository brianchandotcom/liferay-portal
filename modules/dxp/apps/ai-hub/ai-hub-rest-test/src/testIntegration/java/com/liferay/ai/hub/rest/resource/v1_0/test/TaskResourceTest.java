/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.configuration.AIHubConfiguration;
import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.ai.hub.security.JWTTokenUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowNode;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Feliphe Marinho
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag(value = "LPD-62272"), @FeatureFlag(value = "LPD-63311")
	}
)
@RunWith(Arquillian.class)
public class TaskResourceTest extends BaseTaskResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_classNameLocalService.invalidate();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		ConfigurationTestUtil.saveConfiguration(
			AIHubConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"clientId", RandomTestUtil.randomString()
			).put(
				"clientSecret", RandomTestUtil.randomString()
			).put(
				"serviceURL", "http://localhost:8080"
			).build());

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		_group = GroupTestUtil.addGroup();
		_mcpServerObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_MCP_SERVER", TestPropsValues.getCompanyId());
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			List.of(
				new LongTextObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).name(
					"description"
				).indexed(
					true
				).build(),
				new TextObjectFieldBuilder(
				).labelMap(
					RandomTestUtil.randomLocaleStringMap()
				).name(
					"name"
				).indexed(
					true
				).indexedAsKeyword(
					true
				).build()));

		_objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			_mcpServerObjectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			HashMapBuilder.<String, Serializable>put(
				"authArguments",
				JSONUtil.put(
					"password", PropsValues.DEFAULT_ADMIN_PASSWORD
				).put(
					"userName", "test@liferay.com"
				).toString()
			).put(
				"externalReferenceCode", "L_LIFERAY_MCP_SERVER"
			).put(
				"url", "http://localhost:8080/o/mcp/sse"
			).build(),
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "AI Decision Node Workflow Definition",
			_getContentBytes("ai-decision-node-workflow-definition.json"));
		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(),
			"AI Decision Node With Tool Workflow Definition",
			_getContentBytes(
				"ai-decision-node-with-tool-workflow-definition.json"));
		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "LLM Node With RAG Workflow Definition",
			_getContentBytes("llm-node-with-rag-workflow-definition.json"));
		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "LLM Node With Tool Workflow Definition",
			_getContentBytes("llm-node-with-tool-workflow-definition.json"));
		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "Workflow Definition",
			_getContentBytes("workflow-definition.json"));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		_objectDefinitionLocalService.deleteObjectDefinition(
			_mcpServerObjectDefinition.getObjectDefinitionId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		PrincipalThreadLocal.setName(_originalName);

		ConfigurationTestUtil.deleteConfiguration(
			AIHubConfiguration.class.getName());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		SseUtil.closeAll();
	}

	@Override
	@Test
	public void testGetTaskSubscribe() throws Exception {
		Assert.assertNotNull(
			SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "tasks/subscribe"));
	}

	@Ignore
	@Override
	@Test
	public void testPostTask() throws Exception {
		_testPostTask();
		_testPostTaskWithScope();
		_testPostTaskWithTypeAIDecisionNodeWithToolWorkflowDefinition();
		_testPostTaskWithTypeAIDecisionNodeWorkflowDefinition();
		_testPostTaskWithTypeFixSpellingAndGrammar();
		_testPostTaskWithTypeLLMNodeWithRAGWorkflowDefinition();
		_testPostTaskWithTypeLLMNodeWithRAGWorkflowDefinitionWithRestrictedUser();
		_testPostTaskWithTypeLLMNodeWithToolWorkflowDefinition();
	}

	private static byte[] _getContentBytes(String fileName) throws Exception {
		InputStream inputStream = TaskResourceTest.class.getResourceAsStream(
			"dependencies/" + fileName);

		String content = StringUtil.read(inputStream);

		return content.getBytes();
	}

	private String _generateToken(long userId) throws Exception {
		return JWTTokenUtil.generateToken(
			TimeUnit.MINUTES.toMillis(1), RandomTestUtil.randomString(),
			userId);
	}

	private void _testPostTask() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(
			"Workflow Definition",
			workflowInstance.getWorkflowDefinitionName());

		Assert.assertEquals(1, workflowInstance.getWorkflowDefinitionVersion());

		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "Workflow Definition",
			_getContentBytes("workflow-definition.json"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		workflowInstance = _workflowInstanceManager.getWorkflowInstance(
			TestPropsValues.getCompanyId(),
			jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(2, workflowInstance.getWorkflowDefinitionVersion());
	}

	private void _testPostTaskWithScope() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(_group.getGroupId(), workflowInstance.getGroupId());
		Assert.assertEquals(
			"Workflow Definition",
			workflowInstance.getWorkflowDefinitionName());
	}

	private void _testPostTaskWithTypeAIDecisionNodeWithToolWorkflowDefinition()
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context",
				JSONUtil.put(
					"question", "Is the \"get_openapis\" tool available?")
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"type", "AI Decision Node With Tool Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				WorkflowInstance workflowInstance =
					_workflowInstanceManager.getWorkflowInstance(
						TestPropsValues.getCompanyId(),
						jsonObject.getLong("externalReferenceCode"));

				List<WorkflowNode> workflowNodes =
					workflowInstance.getCurrentWorkflowNodes();

				WorkflowNode workflowNode = workflowNodes.get(0);

				Assert.assertEquals("approved", workflowNode.getName());

				return null;
			});
	}

	private void _testPostTaskWithTypeAIDecisionNodeWorkflowDefinition()
		throws Exception {

		_testPostTaskWithTypeAIDecisionNodeWorkflowDefinition(
			"Blue banana, or Blue Java, is a variety of a banana that grows " +
				"in Brazil.",
			"approved");
		_testPostTaskWithTypeAIDecisionNodeWorkflowDefinition(
			"Innovative technology transforms everyday life with smarter " +
				"digital solutions.",
			"rejected");
	}

	private void _testPostTaskWithTypeAIDecisionNodeWorkflowDefinition(
			String content, String workflowNodeName)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("content", content)
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "AI Decision Node Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				WorkflowInstance workflowInstance =
					_workflowInstanceManager.getWorkflowInstance(
						TestPropsValues.getCompanyId(),
						jsonObject.getLong("externalReferenceCode"));

				List<WorkflowNode> workflowNodes =
					workflowInstance.getCurrentWorkflowNodes();

				WorkflowNode workflowNode = workflowNodes.get(0);

				Assert.assertEquals(workflowNodeName, workflowNode.getName());

				return null;
			});
	}

	private void _testPostTaskWithTypeFixSpellingAndGrammar() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "tasks/subscribe");

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", "Thi text ix wrong.")
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).put(
				"type",
				WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Fix Spelling and Grammar", lines.get(2));
		JSONAssert.assertEquals(
			JSONUtil.put(
				"data", "This text is wrong."
			).put(
				"nodeName", "fixSpellingAndGrammar"
			).toString(),
			StringUtil.removeSubstring(lines.get(3), "data: "),
			JSONCompareMode.LENIENT);

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				WorkflowInstance workflowInstance =
					_workflowInstanceManager.getWorkflowInstance(
						TestPropsValues.getCompanyId(),
						jsonObject.getLong("externalReferenceCode"));

				Map<String, Serializable> workflowContext =
					workflowInstance.getWorkflowContext();

				String rewrittenText = GetterUtil.getString(
					workflowContext.get("rewrittenText"));

				Assert.assertEquals("This text is wrong.", rewrittenText);

				return null;
			});

		SseUtil.closeAll();
	}

	private void _testPostTaskWithTypeLLMNodeWithRAGWorkflowDefinition()
		throws Exception {

		CountDownLatch countDownLatch1 = new CountDownLatch(4);
		CountDownLatch countDownLatch2 = new CountDownLatch(6);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch1, countDownLatch2), lines,
			"tasks/subscribe");

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context",
				JSONUtil.put("userMessage", "What is Feliphe's favorite food?")
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).put(
				"type", "LLM Node With RAG Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks",
			HashMapBuilder.put(
				"Liferay-AI-Hub-On-Behalf-Of",
				_generateToken(TestPropsValues.getUserId())
			).build(),
			Http.Method.POST);

		Assert.assertTrue(countDownLatch1.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		String response = StringUtil.toLowerCase(lines.get(3));

		Assert.assertFalse(response, response.contains("brazilian barbecue"));
		Assert.assertTrue(response, response.contains("\"nodename\":\"llm\""));

		_objectEntryLocalService.addObjectEntry(
			0L, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			HashMapBuilder.<String, Serializable>put(
				"description", "His favorite food is Brazilian barbecue."
			).put(
				"name", "Feliphe"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context",
				JSONUtil.put("userMessage", "What is Feliphe's favorite food?")
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).put(
				"type", "LLM Node With RAG Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks",
			HashMapBuilder.put(
				"Liferay-AI-Hub-On-Behalf-Of",
				_generateToken(TestPropsValues.getUserId())
			).build(),
			Http.Method.POST);

		Assert.assertTrue(countDownLatch2.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 6, lines.size());

		response = StringUtil.toLowerCase(lines.get(5));

		Assert.assertTrue(response, response.contains("brazilian barbecue"));
		Assert.assertTrue(response, response.contains("\"nodename\":\"llm\""));

		SseUtil.closeAll();
	}

	private void _testPostTaskWithTypeLLMNodeWithRAGWorkflowDefinitionWithRestrictedUser()
		throws Exception {

		CountDownLatch countDownLatch1 = new CountDownLatch(4);
		CountDownLatch countDownLatch2 = new CountDownLatch(6);
		List<String> lines = new ArrayList<>();

		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			password, RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		user.setEmailAddressVerified(true);

		user = _userLocalService.updateUser(user);

		long userId = user.getUserId();

		String userToken = _generateToken(userId);

		_objectEntryLocalService.addObjectEntry(
			0L, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0,
			LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
			HashMapBuilder.<String, Serializable>put(
				"description", "His favorite food is Brazilian barbecue."
			).put(
				"name", "Feliphe"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch1, countDownLatch2), lines,
			"tasks/subscribe");

		HTTPTestUtil.customize(
		).withCredentials(
			user.getEmailAddress(), password
		).apply(
			() -> {
				HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"context",
						JSONUtil.put(
							"userMessage", "What is Feliphe's favorite food?")
					).put(
						"scope",
						JSONUtil.put(
							"externalReferenceCode",
							_group.getExternalReferenceCode())
					).put(
						"sseEventSinkKey", sseEventSinkKey
					).put(
						"type", "LLM Node With RAG Workflow Definition"
					).toString(),
					"ai-hub/v1.0/tasks",
					HashMapBuilder.put(
						"Liferay-AI-Hub-On-Behalf-Of", userToken
					).build(),
					Http.Method.POST);

				Assert.assertTrue(countDownLatch1.await(10, TimeUnit.SECONDS));

				Assert.assertEquals(lines.toString(), 4, lines.size());

				String response = StringUtil.toLowerCase(lines.get(3));

				Assert.assertFalse(
					response, response.contains("brazilian barbecue"));
				Assert.assertTrue(
					response, response.contains("\"nodename\":\"llm\""));

				Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(),
					_objectDefinition.getClassName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(TestPropsValues.getCompanyId()),
					role.getRoleId(), new String[] {ActionKeys.VIEW});

				_userLocalService.addRoleUser(role.getRoleId(), userId);

				HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"context",
						JSONUtil.put(
							"userMessage", "What is Feliphe's favorite food?")
					).put(
						"scope",
						JSONUtil.put(
							"externalReferenceCode",
							_group.getExternalReferenceCode())
					).put(
						"sseEventSinkKey", sseEventSinkKey
					).put(
						"type", "LLM Node With RAG Workflow Definition"
					).toString(),
					"ai-hub/v1.0/tasks",
					HashMapBuilder.put(
						"Liferay-AI-Hub-On-Behalf-Of", userToken
					).build(),
					Http.Method.POST);

				Assert.assertTrue(countDownLatch2.await(10, TimeUnit.SECONDS));

				Assert.assertEquals(lines.toString(), 6, lines.size());

				response = StringUtil.toLowerCase(lines.get(5));

				Assert.assertTrue(
					response, response.contains("brazilian barbecue"));
				Assert.assertTrue(
					response, response.contains("\"nodename\":\"llm\""));
			}
		);

		SseUtil.closeAll();
	}

	private void _testPostTaskWithTypeLLMNodeWithToolWorkflowDefinition()
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "tasks/subscribe");

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context",
				JSONUtil.put(
					"userMessage", "Is the \"get_openapi\" tool available?")
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).put(
				"type", "LLM Node With Tool Workflow Definition"
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		String response = StringUtil.toLowerCase(lines.get(3));

		Assert.assertTrue(response, response.contains("\"nodename\":\"llm\""));
		Assert.assertTrue(response, response.contains("yes"));

		SseUtil.closeAll();
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	private static Group _group;
	private static ObjectDefinition _mcpServerObjectDefinition;
	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private static ObjectEntryLocalService _objectEntryLocalService;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

}