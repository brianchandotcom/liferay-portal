/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

	@Inject(filter = "component.name=*.DepotItemSelectorView")
	private ItemSelectorView<GroupItemSelectorCriterion> _depotItemSelectorView;

	@Inject
	private GroupLocalService _groupLocalService;

}