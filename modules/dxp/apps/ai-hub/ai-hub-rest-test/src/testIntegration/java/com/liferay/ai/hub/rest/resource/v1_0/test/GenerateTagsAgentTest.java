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
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
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
 * @author Iliyan Peychev
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag(value = "LPD-62272"), @FeatureFlag(value = "LPD-63311")
	}
)
@RunWith(Arquillian.class)
public class GenerateTagsAgentTest extends BaseAgentInstanceResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseAgentInstanceResourceTestCase.setUpClass();

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
				"serviceURL",
				"http://localhost:" + PortalUtil.getPortalServerPort(false)
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

		SseUtil.closeAll();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();

		SseUtil.closeAll();

		ConfigurationTestUtil.deleteConfiguration(
			AIHubCellConfiguration.class.getName());
	}

	@Ignore
	@Test
	public void testPostAgentInstance() throws Exception {
		_testPostAgentInstanceWithTypeGenerateTagsProposesNewTags();
		_testPostAgentInstanceWithTypeGenerateTagsReusesExistingTags();
	}

	private void _assertContains(String line, String... texts) {
		for (String text : texts) {
			Assert.assertTrue(line, line.contains(text));
		}
	}

	private String _postGenerateTags(
			String content, JSONArray existingTagsJSONArray, int count)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(4);

		List<String> lines = new ArrayList<>();

		JSONObject tokenJSONObject = TokenTestUtil.postToken();

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"agentDefinitionExternalReferenceCode", "L_GENERATE_TAGS"
			).put(
				"context",
				JSONUtil.put(
					"content", content
				).put(
					"count", String.valueOf(count)
				).put(
					"existingTags", existingTagsJSONArray.toString()
				)
			).put(
				"sseEventSinkKey",
				SseEventSourceTestUtil.open(
					List.of(countDownLatch), lines, "agent-instances/subscribe")
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

		Assert.assertTrue(countDownLatch.await(30, TimeUnit.SECONDS));

		Assert.assertEquals(lines.toString(), 4, lines.size());
		Assert.assertEquals("event: L_GENERATE_TAGS", lines.get(2));

		JSONObject outputJSONObject = _jsonFactory.createJSONObject(
			StringUtil.removeSubstring(lines.get(3), "data: "));

		SseUtil.closeAll();

		return outputJSONObject.getString("data");
	}

	private void _testPostAgentInstanceWithTypeGenerateTagsProposesNewTags()
		throws Exception {

		JSONArray existingTagsJSONArray = JSONUtil.putAll(
			"gardening", "cooking", "home improvement");

		int count = 5;

		String dataString = _postGenerateTags(
			"This guide covers training a new puppy, choosing the right " +
				"leash, and scheduling veterinary checkups for your dog.",
			existingTagsJSONArray, count);

		_assertContains(
			dataString, "confidence", "isNew", "suggestions", "true");

		String lowerCaseDataString = StringUtil.toLowerCase(dataString);

		Assert.assertFalse(
			dataString, lowerCaseDataString.contains("gardening"));
		Assert.assertFalse(
			dataString, lowerCaseDataString.contains("home improvement"));

		Assert.assertTrue(
			dataString, StringUtil.count(dataString, "confidence") <= count);
	}

	private void _testPostAgentInstanceWithTypeGenerateTagsReusesExistingTags()
		throws Exception {

		JSONArray existingTagsJSONArray = JSONUtil.putAll(
			"machine learning", "neural networks", "data science");

		int count = 5;

		String dataString = _postGenerateTags(
			"This article explains how neural networks are trained for " +
				"machine learning tasks and why data science teams rely on " +
					"them for prediction.",
			existingTagsJSONArray, count);

		_assertContains(
			dataString, "confidence", "false", "isNew", "suggestions");

		String lowerCaseDataString = StringUtil.toLowerCase(dataString);

		Assert.assertTrue(
			dataString,
			lowerCaseDataString.contains("data science") ||
			lowerCaseDataString.contains("machine learning") ||
			lowerCaseDataString.contains("neural networks"));

		Assert.assertTrue(
			dataString, StringUtil.count(dataString, "confidence") <= count);
	}

	private static AccountEntry _accountEntry;

	@Inject
	private static AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private static AccountEntryUserRelLocalService
		_accountEntryUserRelLocalService;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	private static String _originalName;
	private static PermissionChecker _originalPermissionChecker;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

	@Inject
	private JSONFactory _jsonFactory;

}