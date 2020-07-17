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
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;
import com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib.ui.OrganizationScreenNavigationCategory;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = {})
public class OrganizationScreenNavigationRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		registerScreenNavigationCategories();

		registerScreenNavigationEntries();
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	protected void registerScreenNavigationCategories() {
		_registerScreenNavigationCategory(
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL),
			10);

		_registerScreenNavigationCategory(
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT),
			20);
	}

	protected void registerScreenNavigationEntries() {
		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL
			).setEntryKey(
				"information"
			).setIsVisibleBiFunction(
				(user, organization) -> true
			).setJspPath(
				"/organization/information.jsp"
			).setMvcActionCommandName(
				"/users_admin/edit_organization"
			).build(),
			10);

		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL
			).setEntryKey(
				"organization-site"
			).setJspPath(
				"/organization/organization_site.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_organization_organization_site"
			).setShowControls(
				false
			).build(),
			20);

		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL
			).setEntryKey(
				"security-questions"
			).setJspPath(
				"/organization/reminder_queries.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_organization_reminder_queries"
			).build(),
			30);

		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT
			).setEntryKey(
				"addresses"
			).setJspPath(
				"/organization/addresses.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_contact_information"
			).setShowControls(
				false
			).setShowTitle(
				false
			).build(),
			10);

		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT
			).setEntryKey(
				"contact-information"
			).setJspPath(
				"/organization/contact_information.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_contact_information"
			).setShowControls(
				false
			).build(),
			20);

		_registerScreenNavigationEntry(
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT
			).setEntryKey(
				"opening-hours"
			).setJspPath(
				"/organization/opening_hours.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_contact_information"
			).setShowControls(
				false
			).setShowTitle(
				false
			).build(),
			30);
	}

	private OrganizationScreenNavigationEntry.Builder _getBuilder() {
		return OrganizationScreenNavigationEntry.builder(
		).setJspRenderer(
			_jspRenderer
		).setOrganizationService(
			_organizationService
		);
	}

	private Dictionary<String, Object> _getProperties(Integer serviceRanking) {
		return new HashMapDictionary<String, Object>() {
			{
				if (serviceRanking != null) {
					put("screen.navigation.category.order", serviceRanking);
					put("screen.navigation.entry.order", serviceRanking);
				}
			}
		};
	}

	private void _registerScreenNavigationCategory(
		ScreenNavigationCategory screenNavigationCategory,
		Dictionary<String, Object> properties) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				ScreenNavigationCategory.class, screenNavigationCategory,
				properties));
	}

	private void _registerScreenNavigationCategory(
		ScreenNavigationCategory screenNavigationCategory,
		Integer serviceRanking) {

		_registerScreenNavigationCategory(
			screenNavigationCategory, _getProperties(serviceRanking));
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry<?> screenNavigationEntry,
		Dictionary<String, Object> properties) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				(Class<ScreenNavigationEntry<?>>)
					(Class<?>)ScreenNavigationEntry.class,
				screenNavigationEntry, properties));
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry<?> screenNavigationEntry,
		Integer serviceRanking) {

		_registerScreenNavigationEntry(
			screenNavigationEntry, _getProperties(serviceRanking));
	}

	private BundleContext _bundleContext;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}