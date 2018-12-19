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
import com.liferay.portal.apio.user.CurrentUser;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author Sarai Díaz
 */
public class BaseMyUserAccountCollectionResourceTest {

	protected PageItems<UserWrapper> getPageItems(
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

	private CollectionResource _getCollectionResource() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Collection<CollectionResource> collection = registry.getServices(
			CollectionResource.class,
			"(component.name=com.liferay.person.apio.internal.architect." +
				"resource." + "MyUserAccountCollectionResource)");

		Iterator<CollectionResource> iterator = collection.iterator();

		return iterator.next();
	}

}