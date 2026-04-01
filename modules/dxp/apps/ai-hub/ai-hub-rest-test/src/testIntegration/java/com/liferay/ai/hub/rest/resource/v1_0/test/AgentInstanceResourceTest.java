/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.ai.hub.cell.configuration.AIHubCellConfiguration;
import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.test.util.TokenTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowNode;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
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
public class AgentInstanceResourceTest
	extends BaseAgentInstanceResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_accountEntry = _accountEntryLocalService.addAccountEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString() + "@liferay.com", null,
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), TestPropsValues.getUserId());

		_classNameLocalService.invalidate();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		ConfigurationTestUtil.saveConfiguration(
			AIHubCellConfiguration.class.getName(),
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

		AccountEntry aiHubAccountEntry =
			_accountEntryLocalService.getAccountEntryByExternalReferenceCode(
				"L_AI_HUB", TestPropsValues.getCompanyId());

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			aiHubAccountEntry.getAccountEntryId(), TestPropsValues.getUserId());

		_instructionObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_INSTRUCTION_DEFINITION",
					TestPropsValues.getCompanyId());
		_mcpServerObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_AI_HUB_MCP_SERVER", TestPropsValues.getCompanyId());
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
			0, TestPropsValues.getUserId(),
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
				"externalReferenceCode", "L_LIFERAY_AI_HUB_MCP_SERVER"
			).put(
				"r_accountToAIHubMCPServers_accountEntryId",
				aiHubAccountEntry.getAccountEntryId()
			).put(
				"url", "http://localhost:8080/o/mcp"
			).build(),
			ServiceContextTestUtil.getServiceContext(
				GroupTestUtil.addGroup(), TestPropsValues.getUserId()));

		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes("ai-decision-node-workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(),
			"AI Decision Node Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());
		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes(
				"ai-decision-node-with-tool-workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(),
			"AI Decision Node With Tool Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());
		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes("llm-node-with-rag-workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(),
			"LLM Node With RAG Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());
		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes("llm-node-with-tool-workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(),
			"LLM Node With Tool Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());
		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes("workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(), "Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		_objectDefinitionLocalService.deleteObjectDefinition(
			_mcpServerObjectDefinition.getObjectDefinitionId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition.getObjectDefinitionId());

		PrincipalThreadLocal.setName(_originalName);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
		SseUtil.closeAll();
		ConfigurationTestUtil.deleteConfiguration(
			AIHubCellConfiguration.class.getName());
	}

	@Override
	@Test
	public void testGetAgentInstanceSubscribe() throws Exception {
		Assert.assertNotNull(
			SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "agent-instances/subscribe"));
	}

	@Ignore
	@Override
	@Test
	public void testPostAgentInstance() throws Exception {
		_testPostAgentInstance();
		_testPostAgentInstanceWithTypeAIDecisionNodeWithToolWorkflowDefinition();
		_testPostAgentInstanceWithTypeAIDecisionNodeWorkflowDefinition();
		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction();
		_testPostAgentInstanceWithTypeLLMNodeWithRAGWorkflowDefinition();
		_testPostAgentInstanceWithTypeLLMNodeWithRAGWorkflowDefinitionWithRestrictedUser();
		_testPostAgentInstanceWithTypeLLMNodeWithToolWorkflowDefinition();
		_testPostAgentInstanceWithTypeMakeShorter();
	}

	private static byte[] _getContentBytes(String fileName) throws Exception {
		String content = _read(fileName);

		return content.getBytes();
	}

	private static String _read(String fileName) throws Exception {
		InputStream inputStream =
			AgentInstanceResourceTest.class.getResourceAsStream(
				"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private String _getExpectedPromptInput(String instruction, String occasion)
		throws Exception {

		String promptInput = StringBundler.concat(
			"You are an expert linguistic editor. Your sole task is to ",
			"correct all grammatical, spelling, and punctuation errors in the ",
			"provided text while preserving its meaning, tone, and style. Do ",
			"not alter structure or wording beyond what is necessary for ",
			"grammatical precision and natural fluency. Output only the ",
			"corrected text, with no explanations or commentary. If the text ",
			"is already correct, return it unchanged.");

		if (Validator.isNull(instruction)) {
			return promptInput;
		}

		if (Validator.isNull(occasion)) {
			return StringUtil.replace(
				_read("expected-prompt-input-with-instruction.txt"),
				new String[] {"${instruction}", "${prompt}"},
				new String[] {instruction, promptInput});
		}

		return StringUtil.replace(
			_read("expected-prompt-input-with-instruction-and-occasion.txt"),
			new String[] {"${instruction}", "${occasion}", "${prompt}"},
			new String[] {
				StringUtil.lowerCaseFirstLetter(instruction),
				StringUtil.removeLast(occasion, StringPool.PERIOD), promptInput
			});
	}

	private JSONObject _postAgentInstance(
			String inputText, String inputVariable, String sseEventSinkKey,
			String type)
		throws Exception {

		JSONObject tokenJSONObject = TokenTestUtil.postToken();

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put(inputVariable, inputText)
			).put(
				"sseEventSinkKey", sseEventSinkKey
			).put(
				"type", type
			).toString(),
			"ai-hub/v1.0/agent-instances",
			HashMapBuilder.put(
				"Authorization",
				"Bearer " + tokenJSONObject.getString("accessToken")
			).put(
				"Liferay-AI-Hub-Cell-On-Behalf-Of",
				tokenJSONObject.getString("userToken")
			).build(),
			Http.Method.POST);
	}

	private void _testPostAgentInstance() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/agent-instances", Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(
			"Workflow Definition",
			workflowInstance.getWorkflowDefinitionName());

		Assert.assertEquals(1, workflowInstance.getWorkflowDefinitionVersion());

		_workflowDefinitionManager.deployWorkflowDefinition(
			_getContentBytes("workflow-definition.json"),
			TestPropsValues.getCompanyId(), null,
			_accountEntry.getAccountEntryGroupId(), "Workflow Definition",
			WorkflowDefinitionConstants.SCOPE_AI, StringUtil.randomId(),
			TestPropsValues.getUserId());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"sseEventSinkKey", RandomTestUtil.randomString()
			).put(
				"type", "Workflow Definition"
			).toString(),
			"ai-hub/v1.0/agent-instances", Http.Method.POST);

		workflowInstance = _workflowInstanceManager.getWorkflowInstance(
			TestPropsValues.getCompanyId(),
			jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(2, workflowInstance.getWorkflowDefinitionVersion());
	}

	private void _testPostAgentInstanceWithTypeAIDecisionNodeWithToolWorkflowDefinition()
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		JSONObject jsonObject = _postAgentInstance(
			"Is the \"get_openapis\" tool available?", "question",
			RandomTestUtil.randomString(),
			"AI Decision Node With Tool Workflow Definition");

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

		Assert.assertEquals(
			originalPermissionChecker,
			PermissionThreadLocal.getPermissionChecker());
	}

	private void _testPostAgentInstanceWithTypeAIDecisionNodeWorkflowDefinition()
		throws Exception {

		_testPostAgentInstanceWithTypeAIDecisionNodeWorkflowDefinition(
			"Blue banana, or Blue Java, is a variety of a banana that grows " +
				"in Brazil.",
			"approved");
		_testPostAgentInstanceWithTypeAIDecisionNodeWorkflowDefinition(
			"Innovative technology transforms everyday life with smarter " +
				"digital solutions.",
			"rejected");
	}

	private void _testPostAgentInstanceWithTypeAIDecisionNodeWorkflowDefinition(
			String content, String workflowNodeName)
		throws Exception {

		JSONObject jsonObject = _postAgentInstance(
			content, "content", RandomTestUtil.randomString(),
			"AI Decision Node Workflow Definition");

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
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

	private void _testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction()
		throws Exception {

		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
			true, "Song she sang to me, song she brang to me.",
			"Song she sang to me, song she brang to me.",
			"Preserve all grammar errors exactly as they appear.",
			"When the text is a poem or song lyrics.");
		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
			true, "This text is wrong.", "Thi text ix wrong.",
			"Preserve all grammar errors exactly as they appear.",
			"When the text is a poem or song lyrics.");
		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
			true, "Thi text ix wrong.", "Thi text ix wrong.",
			"Preserve all grammar errors exactly as they appear.", null);
		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
			true, "THIS TEXT IS WRONG.", "Thi text ix wrong.",
			"Respond in ALL CAPS.", null);
		_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
			false, "This text is wrong.", "Thi text ix wrong.",
			"Respond in ALL CAPS.", null);
	}

	private void
			_testPostAgentInstanceWithTypeFixSpellingAndGrammarWithInstruction(
				boolean active, String expectedOutput, String input,
				String instruction, String occasion)
		throws Exception {

		_objectEntryLocalService.addOrUpdateObjectEntry(
			"L_AI_HUB_INSTRUCTION", 0, TestPropsValues.getUserId(),
			_instructionObjectDefinition.getObjectDefinitionId(), 0,
			HashMapBuilder.<String, Serializable>put(
				"active", active
			).put(
				"instruction", instruction
			).put(
				"occasion", occasion
			).put(
				"r_accountToAIHubInstructionDefinitions_accountEntryId",
				_accountEntry.getAccountEntryId()
			).put(
				"scope", "clickToChat"
			).put(
				"title_i18n",
				(Serializable)RandomTestUtil.randomLanguageIdStringMap()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "agent-instances/subscribe");

		JSONObject jsonObject = _postAgentInstance(
			input, "text", sseEventSinkKey,
			WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Fix Spelling and Grammar", lines.get(2));
		JSONAssert.assertEquals(
			JSONUtil.put(
				"data", expectedOutput
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

				Assert.assertEquals(
					expectedOutput,
					MapUtil.getString(
						workflowInstance.getWorkflowContext(),
						"rewrittenText"));

				List<WorkflowLog> workflowLogs =
					_workflowLogManager.getWorkflowLogsByWorkflowInstance(
						TestPropsValues.getCompanyId(),
						workflowInstance.getWorkflowInstanceId(),
						List.of(WorkflowLog.NODE_USAGE_METADATA),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

				Assert.assertEquals(
					workflowLogs.toString(), 1, workflowLogs.size());

				WorkflowLog workflowLog = workflowLogs.get(0);

				Map<String, Serializable> workflowContext =
					WorkflowContextUtil.convert(
						workflowLog.getWorkflowContext());

				int inputTokensCount = GetterUtil.getInteger(
					workflowContext.get("inputTokensCount"));

				Assert.assertTrue(inputTokensCount > 0);

				Assert.assertEquals(
					expectedOutput, workflowContext.get("output"));

				int outputTokensCount = GetterUtil.getInteger(
					workflowContext.get("outputTokensCount"));

				Assert.assertTrue(outputTokensCount > 0);

				Assert.assertEquals(
					_getExpectedPromptInput(
						active ? instruction : null, occasion),
					workflowContext.get("promptInput"));
				Assert.assertEquals(
					inputTokensCount + outputTokensCount,
					GetterUtil.getInteger(
						workflowContext.get("totalTokenCount")));
				Assert.assertEquals(
					"This is the text to be fixed: " + input,
					workflowContext.get("userMessageInput"));

				return null;
			});

		SseUtil.closeAll();
	}

	private void _testPostAgentInstanceWithTypeLLMNodeWithRAGWorkflowDefinition()
		throws Exception {

		CountDownLatch countDownLatch1 = new CountDownLatch(4);
		CountDownLatch countDownLatch2 = new CountDownLatch(6);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch1, countDownLatch2), lines,
			"agent-instances/subscribe");

		_postAgentInstance(
			"What is Feliphe's favorite food?", "userMessage", sseEventSinkKey,
			"LLM Node With RAG Workflow Definition");

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

		_postAgentInstance(
			"What is Feliphe's favorite food?", "userMessage", sseEventSinkKey,
			"LLM Node With RAG Workflow Definition");

		Assert.assertTrue(countDownLatch2.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 6, lines.size());

		response = StringUtil.toLowerCase(lines.get(5));

		Assert.assertTrue(response, response.contains("brazilian barbecue"));
		Assert.assertTrue(response, response.contains("\"nodename\":\"llm\""));

		SseUtil.closeAll();
	}

	private void _testPostAgentInstanceWithTypeLLMNodeWithRAGWorkflowDefinitionWithRestrictedUser()
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
			"agent-instances/subscribe");

		HTTPTestUtil.customize(
		).withCredentials(
			user.getEmailAddress(), password
		).apply(
			() -> {
				_postAgentInstance(
					"What is Feliphe's favorite food?", "userMessage",
					sseEventSinkKey, "LLM Node With RAG Workflow Definition");

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

				_postAgentInstance(
					"What is Feliphe's favorite food?", "userMessage",
					sseEventSinkKey, "LLM Node With RAG Workflow Definition");

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

	private void _testPostAgentInstanceWithTypeLLMNodeWithToolWorkflowDefinition()
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "agent-instances/subscribe");

		_postAgentInstance(
			"Is the \"get_openapi\" tool available?", "userMessage",
			sseEventSinkKey, "LLM Node With Tool Workflow Definition");

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		String response = StringUtil.toLowerCase(lines.get(3));

		Assert.assertTrue(response, response.contains("\"nodename\":\"llm\""));
		Assert.assertTrue(response, response.contains("yes"));

		SseUtil.closeAll();
	}

	private void _testPostAgentInstanceWithTypeMakeShorter() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch), lines, "agent-instances/subscribe");

		String inputText =
			"This is a long and detailed sentence that should be shortened " +
				"by the AI model for testing purposes.";

		JSONObject jsonObject = _postAgentInstance(
			inputText, "text", sseEventSinkKey,
			WorkflowDefinitionConstants.NAME_MAKE_SHORTER);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Make Shorter", lines.get(2));

		JSONObject outputJSONObject = _jsonFactory.createJSONObject(
			StringUtil.removeSubstring(lines.get(3), "data: "));

		Assert.assertEquals(
			"makeShorter", outputJSONObject.getString("nodeName"));

		String output = outputJSONObject.getString("data");

		Assert.assertTrue(output.length() < inputText.length());

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

				Assert.assertTrue(rewrittenText.length() < inputText.length());

				return null;
			});

		SseUtil.closeAll();
	}

	private static AccountEntry _accountEntry;

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	private static ObjectDefinition _instructionObjectDefinition;
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
	private JSONFactory _jsonFactory;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

	@Inject
	private WorkflowLogManager _workflowLogManager;

}