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

package com.liferay.person.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sarai Díaz
 */
@RunWith(Arquillian.class)
public class MyUserAccountCollectionResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetPageItems() throws Exception {
		CurrentUser currentUser = new CurrentUser(
			_userService.getCurrentUser());

		PageItems<UserWrapper> pageItems = _getPageItems(
			PaginationRequest.of(1, 1),
			_getThemeDisplay(_group, LocaleUtil.getDefault()), currentUser);

		Assert.assertEquals(1, pageItems.getTotalCount());
	}

	private CollectionResource _getCollectionResource() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Collection<CollectionResource> collection = registry.getServices(
			CollectionResource.class,
			"(component.name=com.liferay.person.apio.internal.architect." +
				"resource." + "MyUserAccountCollectionResource)");

		Iterator<CollectionResource> iterator = collection.iterator();

		return iterator.next();
	}

	private PageItems<UserWrapper> _getPageItems(
			Pagination pagination, ThemeDisplay themeDisplay,
			CurrentUser currentUser)
		throws Exception {

		CollectionResource collectionResource = _getCollectionResource();

		Class<? extends CollectionResource> clazz =
			collectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, ThemeDisplay.class,
			CurrentUser.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			collectionResource, pagination, themeDisplay, currentUser);
	}

	private ThemeDisplay _getThemeDisplay(Group group, Locale locale)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(locale);
		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserService _userService;

}