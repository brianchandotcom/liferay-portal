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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class AutoCategorizeAgentTest extends BaseAgentInstanceResourceTestCase {

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
		_testPostAgentInstanceWithTypeAutoCategorizeAbstains();
		_testPostAgentInstanceWithTypeAutoCategorizeDefaultsCount();
		_testPostAgentInstanceWithTypeAutoCategorizeMatches();
	}

	private void _assertContains(String line, String... texts) {
		for (String text : texts) {
			Assert.assertTrue(line, line.contains(text));
		}
	}

	private long _nextCandidateId(Set<Long> candidateIds) {
		long id = RandomTestUtil.randomLong();

		candidateIds.add(id);

		return id;
	}

	private String _postAutoCategorize(JSONObject contextJSONObject)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(4);

		List<String> lines = new ArrayList<>();

		JSONObject tokenJSONObject = TokenTestUtil.postToken();

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"agentDefinitionExternalReferenceCode", "L_AUTO_CATEGORIZE"
			).put(
				"context", contextJSONObject
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
		Assert.assertEquals("event: L_AUTO_CATEGORIZE", lines.get(2));

		JSONObject outputJSONObject = _jsonFactory.createJSONObject(
			StringUtil.removeSubstring(lines.get(3), "data: "));

		SseUtil.closeAll();

		return outputJSONObject.getString("data");
	}

	private String _postAutoCategorize(
			String content, JSONArray candidateCategoriesJSONArray)
		throws Exception {

		return _postAutoCategorize(
			JSONUtil.put(
				"candidateCategories", candidateCategoriesJSONArray.toString()
			).put(
				"content", content
			));
	}

	private String _postAutoCategorize(
			String content, JSONArray candidateCategoriesJSONArray, int count)
		throws Exception {

		return _postAutoCategorize(
			JSONUtil.put(
				"candidateCategories", candidateCategoriesJSONArray.toString()
			).put(
				"content", content
			).put(
				"count", String.valueOf(count)
			));
	}

	private void _testPostAgentInstanceWithTypeAutoCategorizeAbstains()
		throws Exception {

		Set<Long> candidateIds = new HashSet<>();

		JSONArray candidateCategoriesJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Astrophysics"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Marine Biology"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Medieval History"
			));

		String dataString = _postAutoCategorize(
			"How to change a flat tire on a bicycle in five quick steps.",
			candidateCategoriesJSONArray, 3);

		_assertContains(dataString, "suggestions");

		Assert.assertEquals(
			dataString, 0, StringUtil.count(dataString, "confidence"));
		Assert.assertFalse(dataString, dataString.contains("Astrophysics"));
		Assert.assertFalse(dataString, dataString.contains("Marine Biology"));
		Assert.assertFalse(dataString, dataString.contains("Medieval History"));
	}

	private void _testPostAgentInstanceWithTypeAutoCategorizeDefaultsCount()
		throws Exception {

		Set<Long> candidateIds = new HashSet<>();

		JSONArray candidateCategoriesJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Technology"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Cooking"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Sports"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Science"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Health"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Travel"
			));

		String dataString = _postAutoCategorize(
			"A balanced diet and regular exercise improve your health, while " +
				"new wearable technology helps athletes track their training " +
					"during every sport.",
			candidateCategoriesJSONArray);

		_assertContains(dataString, "suggestions");

		Assert.assertTrue(
			dataString, StringUtil.count(dataString, "confidence") <= 3);
	}

	private void _testPostAgentInstanceWithTypeAutoCategorizeMatches()
		throws Exception {

		Set<Long> candidateIds = new HashSet<>();

		long technologyId = _nextCandidateId(candidateIds);

		JSONArray candidateCategoriesJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"id", technologyId
			).put(
				"name", "Technology"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Cooking"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Sports"
			),
			JSONUtil.put(
				"id", _nextCandidateId(candidateIds)
			).put(
				"name", "Travel"
			));

		int count = 2;

		String dataString = _postAutoCategorize(
			"Our new smartphone ships with a faster processor, a larger " +
				"display, and an upgraded camera powered by machine learning.",
			candidateCategoriesJSONArray, count);

		_assertContains(
			dataString, "Technology", "confidence", "suggestions",
			String.valueOf(technologyId));

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