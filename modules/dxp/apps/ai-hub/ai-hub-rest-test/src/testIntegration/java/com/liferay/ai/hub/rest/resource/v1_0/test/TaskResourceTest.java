/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowNode;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class TaskResourceTest extends BaseTaskResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer("ai-hub-initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());

		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), _WORKFLOW_DEFINITION_NAME,
			_getContentBytes("workflow-definition.json"));
		_workflowDefinitionManager.deployWorkflowDefinition(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			StringUtil.randomId(), "AI Decision Node Workflow Definition",
			_getContentBytes("ai-decision-node-workflow-definition.json"));
	}

	@After
	public void tearDown() {
		SseUtil.closeAll();
	}

	@Override
	@Test
	public void testGetTaskSubscribe() throws Exception {
		_testGetTaskSubscribe(null, new ArrayList<>());
	}

	@Override
	@Test
	public void testPostByExternalReferenceCodeTask() throws Exception {
		_testPostByExternalReferenceCodeTask();
		_testPostByExternalReferenceCodeTaskWithScope();
	}

	@Ignore
	@Test
	public void testPostTaskWithTypeAIDecisionWorkflowDefinition()
		throws Exception {

		_testPostTaskWithTypeAIDecisionWorkflowDefinition(
			"Blue banana, or Blue Java, is a variety of a banana that grows " +
				"in Brazil.",
			"approved");
		_testPostTaskWithTypeAIDecisionWorkflowDefinition(
			"Innovative technology transforms everyday life with smarter " +
				"digital solutions.",
			"rejected");
	}

	@Ignore
	@Test
	public void testPostTaskWithTypeFixSpellingAndGrammar() throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		List<String> lines = new ArrayList<>();

		_testGetTaskSubscribe(countDownLatch, lines);

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", "Thi text ix wrong.")
			).put(
				"type",
				WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				StringUtil.replaceFirst(lines.get(1), "data: ", "") + "/tasks",
			Http.Method.POST);

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Fix Spelling and Grammar", lines.get(2));
		Assert.assertEquals("data: This text is wrong.", lines.get(3));
	}

	private static byte[] _getContentBytes(String fileName) throws Exception {
		InputStream inputStream = TaskResourceTest.class.getResourceAsStream(
			"dependencies/" + fileName);

		String content = StringUtil.read(inputStream);

		return content.getBytes();
	}

	private void _testGetTaskSubscribe(
			CountDownLatch countDownLatch2, List<String> lines)
		throws Exception {

		CountDownLatch countDownLatch1 = new CountDownLatch(2);

		HttpClient httpClient = HttpClient.newBuilder(
		).connectTimeout(
			Duration.ofSeconds(5)
		).build();

		String credentials =
			"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

		CompletableFuture<HttpResponse<InputStream>> completableFuture =
			httpClient.sendAsync(
				HttpRequest.newBuilder(
				).header(
					"Accept", "text/event-stream"
				).header(
					"Authorization",
					"Basic " + Base64.encode(credentials.getBytes())
				).uri(
					URI.create(
						"http://localhost:8080/o/ai-hub/v1.0/tasks/subscribe")
				).GET(
				).build(),
				HttpResponse.BodyHandlers.ofInputStream());

		completableFuture.thenAccept(
			response -> {
				try (InputStream inputStream = response.body();
					BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream))) {

					String line = "";

					while ((line = bufferedReader.readLine()) != null) {
						if (line.isEmpty()) {
							continue;
						}

						countDownLatch1.countDown();

						if (countDownLatch2 != null) {
							countDownLatch2.countDown();
						}

						lines.add(line);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});

		Assert.assertTrue(countDownLatch1.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 2, lines.size());
		Assert.assertEquals("event: Subscribe", lines.get(0));

		Set<String> sseEventSinksKeys = SseUtil.getSSEEventSinksKeys();

		Assert.assertEquals(
			sseEventSinksKeys.toString(), 1, sseEventSinksKeys.size());

		Iterator<String> iterator = sseEventSinksKeys.iterator();

		Assert.assertEquals("data: " + iterator.next(), lines.get(1));
	}

	private void _testPostByExternalReferenceCodeTask() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"type", _WORKFLOW_DEFINITION_NAME
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(
			_WORKFLOW_DEFINITION_NAME,
			workflowInstance.getWorkflowDefinitionName());
	}

	private void _testPostByExternalReferenceCodeTaskWithScope()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"scope",
				JSONUtil.put(
					"externalReferenceCode", group.getExternalReferenceCode())
			).put(
				"type", _WORKFLOW_DEFINITION_NAME
			).toString(),
			"ai-hub/v1.0/by-external-reference-code/" +
				RandomTestUtil.randomString() + "/tasks",
			Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(group.getGroupId(), workflowInstance.getGroupId());
		Assert.assertEquals(
			_WORKFLOW_DEFINITION_NAME,
			workflowInstance.getWorkflowDefinitionName());
	}

	private void _testPostTaskWithTypeAIDecisionWorkflowDefinition(
			String content, String workflowNodeName)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("content", content)
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

	private static final String _WORKFLOW_DEFINITION_NAME =
		"Workflow Definition";

	private static final Log _log = LogFactoryUtil.getLog(
		TaskResourceTest.class);

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

}