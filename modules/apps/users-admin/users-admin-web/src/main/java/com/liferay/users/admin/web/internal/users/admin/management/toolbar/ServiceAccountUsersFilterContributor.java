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

package com.liferay.users.admin.web.internal.users.admin.management.toolbar;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.users.admin.constants.UsersAdminManagementToolbarKeys;
import com.liferay.users.admin.management.toolbar.FilterContributor;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = FilterContributor.class)
public class ServiceAccountUsersFilterContributor implements FilterContributor {

	@Override
	public String getDefaultValue() {
		return "all";
	}

	@Override
	public String getLabel(Locale locale) {
		return _getMessage(locale, "filter-by-type");
	}

	@Override
	public String getManagementToolbarKey() {
		return UsersAdminManagementToolbarKeys.VIEW_SERVICE_ACCOUNTS;
	}

	@Override
	public String getParameter() {
		return "types";
	}

	@Override
	public Map<String, Object> getSearchParameters(String currentValue) {
		return LinkedHashMapBuilder.<String, Object>put(
			"types",
			new long[] {
				UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT,
				UserConstants.TYPE_SERVICE_ACCOUNT
			}
		).build();
	}

	@Override
	public String getShortLabel(Locale locale) {
		return _getMessage(locale, "type");
	}

	@Override
	public String getValueLabel(Locale locale, String value) {
		return _getMessage(locale, value);
	}

	@Override
	public String[] getValues() {
		return new String[] {"all"};
	}

	private String _getMessage(Locale locale, String key) {
		return _language.get(locale, key);
	}

	@Reference
	private Language _language;

}