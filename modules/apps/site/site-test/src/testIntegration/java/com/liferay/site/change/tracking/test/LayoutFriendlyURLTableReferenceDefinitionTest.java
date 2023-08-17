/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class LayoutFriendlyURLTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _layoutFriendlyURLLocalService.addLayoutFriendlyURL(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			group.getGroupId(), _layout.getPlid(), _layout.isPrivateLayout(),
			_layout.getFriendlyURL(), _layout.getDefaultLanguageId(),
			ServiceContextTestUtil.getServiceContext());
	}

	private Layout _layout;

	@Inject
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}