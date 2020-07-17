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

		_registerService(
			ScreenNavigationCategory.class, 10,
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL));

		_registerService(
			ScreenNavigationCategory.class, 20,
			new OrganizationScreenNavigationCategory(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT));

		_registerService(
			ScreenNavigationEntry.class, 10,
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
			).build());

		_registerService(
			ScreenNavigationEntry.class, 20,
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
			).build());

		_registerService(
			ScreenNavigationEntry.class, 30,
			_getBuilder(
			).setCategoryKey(
				UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL
			).setEntryKey(
				"security-questions"
			).setJspPath(
				"/organization/reminder_queries.jsp"
			).setMvcActionCommandName(
				"/users_admin/update_organization_reminder_queries"
			).build());

		_registerService(
			ScreenNavigationEntry.class, 10,
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
			).build());

		_registerService(
			ScreenNavigationEntry.class, 20,
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
			).build());

		_registerService(
			ScreenNavigationEntry.class, 30,
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
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		_serviceRegistrations.clear();
	}

	private OrganizationScreenNavigationEntry.Builder _getBuilder() {
		return OrganizationScreenNavigationEntry.builder(
		).setJspRenderer(
			_jspRenderer
		).setOrganizationService(
			_organizationService
		);
	}

	private <T> void _registerService(
		Class<T> clazz, int order, T serviceObject) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				clazz, serviceObject,
				new HashMapDictionary<String, Object>() {
					{
						put("screen.navigation.category.order", order);
						put("screen.navigation.entry.order", order);
					}
				}));
	}

	private BundleContext _bundleContext;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}