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

package com.liferay.site.apio.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.test.util.pagination.PaginationRequest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
public class WebSiteCollectionResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetActiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			ThemeDisplay themeDisplay = getThemeDisplay(
				group, Locale.getDefault());

			PageItems<Group> groupPageItems = _getPageItems(
				PaginationRequest.of(10, 1), themeDisplay);

			List<Group> groups = (List<Group>)groupPageItems.getItems();

			Assert.assertTrue(groups.contains(group));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testGetInactiveGroup() throws Throwable {
		Group group = GroupTestUtil.addGroup();

		try {
			_deactivateGroup(group);

			ThemeDisplay themeDisplay = getThemeDisplay(
				group, Locale.getDefault());

			PageItems<Group> groupPageItems = _getPageItems(
				PaginationRequest.of(10, 1), themeDisplay);

			List<Group> groups = (List<Group>)groupPageItems.getItems();

			Assert.assertFalse(groups.contains(group));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	protected ThemeDisplay getThemeDisplay(Group group, Locale locale)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(locale);
		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	private Group _deactivateGroup(Group group) throws PortalException {
		return _groupLocalService.updateGroup(
			group.getGroupId(), group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), false,
			new ServiceContext());
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, ThemeDisplay themeDisplay)
		throws Exception {

		Class<? extends CollectionResource> clazz =
			_collectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, ThemeDisplay.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_collectionResource, pagination, themeDisplay);
	}

	@Inject(
		filter = "component.name=com.liferay.site.apio.internal.architect.resource.WebSiteCollectionResource"
	)
	private CollectionResource _collectionResource;

	@Inject
	private GroupLocalService _groupLocalService;

}