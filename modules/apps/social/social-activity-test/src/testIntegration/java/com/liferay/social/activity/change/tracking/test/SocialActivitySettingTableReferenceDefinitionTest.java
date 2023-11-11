/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.social.kernel.model.SocialActivitySetting;
import com.liferay.social.kernel.service.SocialActivitySettingLocalService;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brooke Dalton
 */
@RunWith(Arquillian.class)
public class SocialActivitySettingTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		SocialActivitySetting socialActivitySetting =
			_socialActivitySettingLocalService.createSocialActivitySetting(
				_counterLocalService.increment(
					SocialActivitySetting.class.getName()));

		socialActivitySetting.setGroupId(group.getGroupId());
		socialActivitySetting.setCompanyId(group.getCompanyId());
		socialActivitySetting.setClassNameId(
			_classNameLocalService.getClassNameId(Group.class));
		socialActivitySetting.setName(RandomTestUtil.randomString());
		socialActivitySetting.setValue(Boolean.TRUE.toString());

		return _socialActivitySettingLocalService.addSocialActivitySetting(
			socialActivitySetting);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private SocialActivitySettingLocalService
		_socialActivitySettingLocalService;

}