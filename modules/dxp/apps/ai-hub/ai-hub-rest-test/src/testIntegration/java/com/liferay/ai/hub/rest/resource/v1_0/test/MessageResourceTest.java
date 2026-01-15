/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

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
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class MessageResourceTest extends BaseMessageResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@After
	public void tearDown() {
		SseUtil.closeAll();
	}

	@Ignore
	@Override
	@Test
	public void testPostChatByExternalReferenceCodeMessage() throws Exception {
		CountDownLatch countDownLatch1 = new CountDownLatch(4);
		CountDownLatch countDownLatch2 = new CountDownLatch(6);

		List<String> lines = new ArrayList<>();

		String sseEventSinkKey = SseEventSourceTestUtil.open(
			List.of(countDownLatch1, countDownLatch2), lines,
			"chats/subscribe");

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"text", "Hello"
			).toString(),
			"ai-hub/v1.0/chats/by-external-reference-code/" + sseEventSinkKey +
				"/messages",
			Http.Method.POST);

		Assert.assertEquals("Hello", jsonObject.getString("text"));

		Assert.assertEquals(lines.toString(), 2, lines.size());

		Assert.assertTrue(countDownLatch1.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: Chat Message Sent", lines.get(2));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"text", "What was the first message sent in this chat?"
			).toString(),
			"ai-hub/v1.0/chats/by-external-reference-code/" + sseEventSinkKey +
				"/messages",
			Http.Method.POST);

		Assert.assertEquals(
			"What is the first message sent in this chat?",
			jsonObject.getString("text"));

		Assert.assertEquals(lines.toString(), 4, lines.size());

		Assert.assertTrue(countDownLatch2.await(10, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 6, lines.size());
		Assert.assertEquals("event: Chat Message Sent", lines.get(4));

		String line = lines.get(5);

		Assert.assertTrue(line.contains("Hello"));
	}

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

}