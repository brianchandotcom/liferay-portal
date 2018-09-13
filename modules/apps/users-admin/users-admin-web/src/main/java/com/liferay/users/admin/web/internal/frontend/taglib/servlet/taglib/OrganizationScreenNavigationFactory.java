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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

import java.util.function.BiFunction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, service = OrganizationScreenNavigationFactory.class
)
public class OrganizationScreenNavigationFactory {

	public ScreenNavigationCategory createCategory(String categoryKey) {
		return new OrganizationScreenNavigationCategory(categoryKey);
	}

	public ScreenNavigationEntry<Organization> createEntry(
		String entryKey, String categoryKey, String jspPath,
		String mvcActionCommandName) {

		return createEntry(
			entryKey, categoryKey, jspPath, mvcActionCommandName,
			_IS_VISIBLE_PREDICATE_ALWAYS);
	}

	public ScreenNavigationEntry<Organization> createEntry(
		String entryKey, String categoryKey, String jspPath,
		String mvcActionCommandName,
		BiFunction<User, Organization, Boolean> isVisiblePredicate) {

		return new OrganizationScreenNavigationEntry(
			_jspRenderer, _portal, entryKey, categoryKey, jspPath,
			mvcActionCommandName, isVisiblePredicate);
	}

	public ScreenNavigationEntry<Organization> createUpdateOnlyEntry(
		String entryKey, String categoryKey, String jspPath,
		String mvcActionCommandName) {

		return createEntry(
			entryKey, categoryKey, jspPath, mvcActionCommandName,
			_IS_VISIBLE_PREDICATE_ORGANIZATION_EXISTS);
	}

	private static final BiFunction<User, Organization, Boolean>
		_IS_VISIBLE_PREDICATE_ALWAYS = (user, organization) -> true;

	private static final BiFunction<User, Organization, Boolean>
		_IS_VISIBLE_PREDICATE_ORGANIZATION_EXISTS = (user, organization) -> {
			if (organization != null) {
				return true;
			}

			return false;
		};

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

}