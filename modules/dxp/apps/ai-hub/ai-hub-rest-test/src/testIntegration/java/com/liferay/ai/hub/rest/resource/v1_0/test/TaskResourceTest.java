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
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
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
import java.util.List;
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
	}

	@After
	public void tearDown() throws Exception {
		SseUtil.close();
	}

	@Override
	@Test
	public void testGetTaskSubscribe() throws Exception {
		List<String> lines = new ArrayList<>();

		CountDownLatch countDownLatch = new CountDownLatch(2);

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

						lines.add(line);
						countDownLatch.countDown();
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});

		Assert.assertTrue(countDownLatch.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 2, lines.size());

		String line1 = lines.get(0);

		Assert.assertEquals("event: " + SseUtil.EVENT_NAME_SUBSCRIBE, line1);

		String line2 = lines.get(1);

		Assert.assertEquals(
			"data: " + SseUtil.EVENT_DATA_SUCCESSFULLY_SUBSCRIBED, line2);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLPostTask() throws Exception {
	}

	@Override
	@Test
	public void testPostTask() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"context", JSONUtil.put("text", RandomTestUtil.randomString())
			).put(
				"type", WorkflowDefinitionConstants.NAME_IMPROVE_WRITING
			).toString(),
			"ai-hub/v1.0/tasks", Http.Method.POST);

		WorkflowInstance workflowInstance =
			_workflowInstanceManager.getWorkflowInstance(
				TestPropsValues.getCompanyId(),
				jsonObject.getLong("externalReferenceCode"));

		Assert.assertEquals(
			WorkflowDefinitionConstants.NAME_IMPROVE_WRITING,
			workflowInstance.getWorkflowDefinitionName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaskResourceTest.class);

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private WorkflowInstanceManager _workflowInstanceManager;

}