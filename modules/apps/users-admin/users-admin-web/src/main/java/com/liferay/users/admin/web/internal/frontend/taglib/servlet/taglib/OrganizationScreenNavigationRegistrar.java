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
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.function.BiFunction;

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
		_screenNavigationEntryServiceRegistrations.forEach(
			ServiceRegistration::unregister);

		_screenNavigationEntryServiceRegistrations.clear();

		_screenNavigationCategoryServiceRegistrations.forEach(
			ServiceRegistration::unregister);

		_screenNavigationCategoryServiceRegistrations.clear();
	}

	protected void registerScreenNavigationCategories() {
		_registerScreenNavigationCategory(
			new OrganizationScreenNavigationCategory(_CATEGORY_GENERAL), 10);

		_registerScreenNavigationCategory(
			new OrganizationScreenNavigationCategory(_CATEGORY_CONTACT), 20);
	}

	protected void registerScreenNavigationEntries() {
		_registerScreenNavigationEntry(
			_createScreenNavigationEntry(
				"information", _CATEGORY_GENERAL,
				"/organization/information.jsp",
				"/users_admin/edit_organization"),
			10);

		_registerScreenNavigationEntry(
			_createUpdateOnlyScreenNavigationEntry(
				"organization-site", _CATEGORY_GENERAL,
				"/organization/organization_site.jsp",
				"/users_admin/update_organization_organization_site", false,
				true),
			20);

		_registerScreenNavigationEntry(
			_createUpdateOnlyScreenNavigationEntry(
				"security-questions", _CATEGORY_GENERAL,
				"/organization/reminder_queries.jsp",
				"/users_admin/update_organization_reminder_queries"),
			30);

		_registerScreenNavigationEntry(
			_createUpdateOnlyScreenNavigationEntry(
				"addresses", _CATEGORY_CONTACT, "/organization/addresses.jsp",
				"/users_admin/update_contact_information", false, false),
			10);

		_registerScreenNavigationEntry(
			_createUpdateOnlyScreenNavigationEntry(
				"contact-information", _CATEGORY_CONTACT,
				"/organization/contact_information.jsp",
				"/users_admin/update_contact_information", false, true),
			20);

		_registerScreenNavigationEntry(
			_createUpdateOnlyScreenNavigationEntry(
				"opening-hours", _CATEGORY_CONTACT,
				"/organization/opening_hours.jsp",
				"/users_admin/update_contact_information", false, false),
			30);
	}

	private ScreenNavigationEntry<Organization> _createScreenNavigationEntry(
		String entryKey, String categoryKey, String jspPath,
		String mvcActionCommandName) {

		return _createScreenNavigationEntry(
			entryKey, categoryKey, jspPath, mvcActionCommandName, true, true,
			(user, organization) -> true);
	}

	private ScreenNavigationEntry<Organization> _createScreenNavigationEntry(
		String entryKey, String categoryKey, String jspPath,
		String mvcActionCommandName, boolean showControls, boolean showTitle,
		BiFunction<User, Organization, Boolean> isVisibleBiFunction) {

		return new OrganizationScreenNavigationEntry(
			_jspRenderer, _organizationService, _portal, _portletURLFactory,
			entryKey, categoryKey, jspPath, mvcActionCommandName, showControls,
			showTitle, isVisibleBiFunction);
	}

	private ScreenNavigationEntry<Organization>
		_createUpdateOnlyScreenNavigationEntry(
			String entryKey, String categoryKey, String jspPath,
			String mvcActionCommandName) {

		return _createUpdateOnlyScreenNavigationEntry(
			entryKey, categoryKey, jspPath, mvcActionCommandName, true, true);
	}

	private ScreenNavigationEntry<Organization>
		_createUpdateOnlyScreenNavigationEntry(
			String entryKey, String categoryKey, String jspPath,
			String mvcActionCommandName, boolean showControls,
			boolean showTitle) {

		return _createScreenNavigationEntry(
			entryKey, categoryKey, jspPath, mvcActionCommandName, showControls,
			showTitle,
			(user, organization) -> {
				if (organization == null) {
					return false;
				}

				return true;
			});
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

		_screenNavigationCategoryServiceRegistrations.add(
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
		ScreenNavigationEntry screenNavigationEntry,
		Dictionary<String, Object> properties) {

		_screenNavigationEntryServiceRegistrations.add(
			_bundleContext.registerService(
				ScreenNavigationEntry.class, screenNavigationEntry,
				properties));
	}

	private void _registerScreenNavigationEntry(
		ScreenNavigationEntry screenNavigationEntry, Integer serviceRanking) {

		_registerScreenNavigationEntry(
			screenNavigationEntry, _getProperties(serviceRanking));
	}

	private static final String _CATEGORY_CONTACT =
		UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT;

	private static final String _CATEGORY_GENERAL =
		UserScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL;

	private BundleContext _bundleContext;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	private final List<ServiceRegistration<ScreenNavigationCategory>>
		_screenNavigationCategoryServiceRegistrations = new ArrayList<>();
	private final List<ServiceRegistration<ScreenNavigationEntry>>
		_screenNavigationEntryServiceRegistrations = new ArrayList<>();

}