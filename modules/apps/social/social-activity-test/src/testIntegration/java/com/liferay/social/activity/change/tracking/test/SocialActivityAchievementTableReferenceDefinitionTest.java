/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.social.kernel.model.BaseSocialAchievement;
import com.liferay.social.kernel.model.SocialAchievement;
import com.liferay.social.kernel.service.SocialActivityAchievementLocalService;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class SocialActivityAchievementTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		SocialAchievement socialAchievement = new BaseSocialAchievement();

		socialAchievement.setName(RandomTestUtil.randomString());

		_socialActivityAchievementLocalService.addActivityAchievement(
			TestPropsValues.getUserId(), group.getGroupId(), socialAchievement);

		return _socialActivityAchievementLocalService.fetchUserAchievement(
			TestPropsValues.getUserId(), group.getGroupId(),
			socialAchievement.getName());
	}

	@Inject
	private SocialActivityAchievementLocalService
		_socialActivityAchievementLocalService;

}