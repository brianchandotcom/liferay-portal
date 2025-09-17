/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.group.criterion.GroupItemSelectorCriterion;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotItemSelectorViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testIsVisible() {
		Assert.assertTrue(_depotItemSelectorView.isVisible(null, null));
	}

	@Test
	public void testIsVisibleForAnUnsupportedApplication() {
		GroupItemSelectorCriterion groupItemSelectorCriterion =
			new GroupItemSelectorCriterion();

		groupItemSelectorCriterion.setPortletId(RandomTestUtil.randomString());

		Assert.assertFalse(
			_depotItemSelectorView.isVisible(groupItemSelectorCriterion, null));
	}

	@Test
	public void testIsVisibleForASupportedApplication() {
		GroupItemSelectorCriterion groupItemSelectorCriterion =
			new GroupItemSelectorCriterion();

		groupItemSelectorCriterion.setPortletId(
			DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);

		Assert.assertTrue(
			_depotItemSelectorView.isVisible(groupItemSelectorCriterion, null));
	}

	@Inject(
		filter = "component.name=com.liferay.depot.web.internal.item.selector.DepotItemSelectorView"
	)
	private ItemSelectorView<GroupItemSelectorCriterion> _depotItemSelectorView;

	@Inject
	private GroupLocalService _groupLocalService;

}