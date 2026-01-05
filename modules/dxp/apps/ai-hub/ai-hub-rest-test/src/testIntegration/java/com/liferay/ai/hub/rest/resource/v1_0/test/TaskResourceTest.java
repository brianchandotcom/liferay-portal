/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

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

		_originalName = PrincipalThreadLocal.getName();

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
	public static void tearDownClass() throws PortalException {
		_objectDefinitionLocalService.deleteObjectDefinition(
			_mcpServerObjectDefinition.getObjectDefinitionId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		PrincipalThreadLocal.setName(_originalName);
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
	public void testPostByExternalReferenceCodeTask() throws Exception {
		_testPostByExternalReferenceCodeTask();
		_testPostByExternalReferenceCodeTaskWithScope();
		_testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWithToolWorkflowDefinition();
		_testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWorkflowDefinition();
		_testPostByExternalReferenceCodeTaskWithTypeFixSpellingAndGrammar();
		_testPostByExternalReferenceCodeTaskWithTypeLLMNodeWithRAGWorkflowDefinition();
		_testPostByExternalReferenceCodeTaskWithTypeLLMNodeWithToolWorkflowDefinition();
	}

	private static byte[] _getContentBytes(String fileName) throws Exception {
		InputStream inputStream = TaskResourceTest.class.getResourceAsStream(
			"dependencies/" + fileName);

		String content = StringUtil.read(inputStream);

		return content.getBytes();
	}

	private void _testPostByExternalReferenceCodeTask() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(
			"Workflow Definition",
			workflowInstance.getWorkflowDefinitionName());
	}

	private void _testPostByExternalReferenceCodeTaskWithScope()
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", _group.getExternalReferenceCode())
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(_group.getGroupId(), workflowInstance.getGroupId());
		Assert.assertEquals(
			"Workflow Definition",
			workflowInstance.getWorkflowDefinitionName());
	}

	private void _testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWithToolWorkflowDefinition()
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
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

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

	private void _testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWorkflowDefinition()
		throws Exception {

		_testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWorkflowDefinition(
			"Blue banana, or Blue Java, is a variety of a banana that grows " +
				"in Brazil.",
			"approved");
		_testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWorkflowDefinition(
			"Innovative technology transforms everyday life with smarter " +
				"digital solutions.",
			"rejected");
	}

	private void
			_testPostByExternalReferenceCodeTaskWithTypeAIDecisionNodeWorkflowDefinition(
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
				"type", "AI Decision Node Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

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

	private void _testPostByExternalReferenceCodeTaskWithTypeFixSpellingAndGrammar()
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "tasks/subscribe");

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", "Thi text ix wrong.")
			).put(
				"type",
				WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" + sseEventSinkKey +
				"/tasks",
			Http.Method.POST);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Fix Spelling and Grammar", lines.get(2));
		Assert.assertEquals(
			"data: {\"data\":\"This text is wrong.\"}", lines.get(3));

		SseUtil.closeAll();
	}

	private void _testPostByExternalReferenceCodeTaskWithTypeLLMNodeWithRAGWorkflowDefinition()
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
				"type", "LLM Node With RAG Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" + sseEventSinkKey +
				"/tasks",
			Http.Method.POST);

		Assert.assertTrue(countDownLatch1.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		String response = StringUtil.toLowerCase(lines.get(3));

		Assert.assertFalse(response, response.contains("brazilian barbecue"));

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
				"type", "LLM Node With RAG Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" + sseEventSinkKey +
				"/tasks",
			Http.Method.POST);

		Assert.assertTrue(countDownLatch2.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 6, lines.size());

		response = StringUtil.toLowerCase(lines.get(5));

		Assert.assertTrue(response, response.contains("brazilian barbecue"));

		SseUtil.closeAll();
	}

	private void _testPostByExternalReferenceCodeTaskWithTypeLLMNodeWithToolWorkflowDefinition()
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
				"type", "LLM Node With Tool Workflow Definition"
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" + sseEventSinkKey +
				"/tasks",
			Http.Method.POST);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		String response = StringUtil.toLowerCase(lines.get(3));

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

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

}