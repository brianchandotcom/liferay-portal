/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class StagingLayoutFriendlyURLTest extends BaseLocalStagingTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testLayoutFriendlyURL() throws Exception {
		String originalFriendlyURL = stagingLayout.getFriendlyURL();

		String testFriendlyURL = "/test";

		stagingLayout = LayoutTestUtil.updateFriendlyURL(
			stagingLayout,
			HashMapBuilder.put(
				LocaleUtil.US, testFriendlyURL
			).build());

		Layout stagingLayout2 = LayoutTestUtil.addTypePortletLayout(
			stagingGroup);

		publishLayouts();

		stagingLayout = LayoutTestUtil.updateFriendlyURL(
			stagingLayout,
			HashMapBuilder.put(
				LocaleUtil.US, originalFriendlyURL
			).build());

		long layoutClassNameId = _layoutFriendlyURLEntryHelper.getClassNameId(
			false);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				stagingGroup.getGroupId(), layoutClassNameId, testFriendlyURL);

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			friendlyURLEntryLocalization.getFriendlyURLEntryId(),
			friendlyURLEntryLocalization.getLanguageId());

		stagingLayout2 = LayoutTestUtil.updateFriendlyURL(
			stagingLayout2,
			HashMapBuilder.put(
				LocaleUtil.US, testFriendlyURL
			).build());

		publishLayouts(new long[] {stagingLayout2.getLayoutId()});

		Layout liveLayout2 = _layoutLocalService.getLayoutByUuidAndGroupId(
			stagingLayout2.getUuid(), liveGroup.getGroupId(),
			stagingLayout2.isPrivateLayout());

		Assert.assertEquals(
			testFriendlyURL + "1", liveLayout2.getFriendlyURL(LocaleUtil.US));

		publishLayouts(new long[] {stagingLayout2.getLayoutId()});

		List<BackgroundTask> failedBackgroundTasks =
			_backgroundTaskLocalService.getBackgroundTasks(
				stagingGroup.getGroupId(),
				"com.liferay.exportimport.internal.background.task." +
					"LayoutStagingBackgroundTaskExecutor",
				BackgroundTaskConstants.STATUS_FAILED);

		Assert.assertTrue(
			"There should not be a failed staging publication",
			failedBackgroundTasks.isEmpty());

		publishLayouts();

		Assert.assertEquals(
			originalFriendlyURL, liveLayout.getFriendlyURL(LocaleUtil.US));
		Assert.assertEquals(
			testFriendlyURL, liveLayout2.getFriendlyURL(LocaleUtil.US));

		FriendlyURLEntryLocalization liveFriendlyURLEntryLocalization1 =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				liveGroup.getGroupId(), layoutClassNameId, "en_US",
				originalFriendlyURL);

		FriendlyURLEntryLocalization liveFriendlyURLEntryLocalization2 =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				liveGroup.getGroupId(), layoutClassNameId, "en_US",
				testFriendlyURL);

		Assert.assertEquals(
			liveLayout.getPlid(),
			liveFriendlyURLEntryLocalization1.getClassPK());
		Assert.assertEquals(
			liveLayout2.getPlid(),
			liveFriendlyURLEntryLocalization2.getClassPK());
	}

	@Inject
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Inject
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Inject
	private LayoutLocalService _layoutLocalService;

}