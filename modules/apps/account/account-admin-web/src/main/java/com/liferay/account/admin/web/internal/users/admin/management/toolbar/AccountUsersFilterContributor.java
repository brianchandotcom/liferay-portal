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

package com.liferay.account.admin.web.internal.users.admin.management.toolbar;

import com.liferay.account.constants.AccountConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.users.admin.constants.UsersAdminManagementToolbarKeys;
import com.liferay.users.admin.management.toolbar.FilterContributor;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(service = FilterContributor.class)
public class AccountUsersFilterContributor implements FilterContributor {

	@Override
	public String getDefaultValue() {
		return "all";
	}

	@Override
	public String getLabel(Locale locale) {
		return _getMessage(locale, "filter-by-domain");
	}

	@Override
	public String getManagementToolbarKey() {
		return UsersAdminManagementToolbarKeys.VIEW_FLAT_USERS;
	}

	@Override
	public String getParameter() {
		return "domain";
	}

	@Override
	public Map<String, Object> getSearchParameters(String currentValue) {
		Map<String, Object> params = new LinkedHashMap<>();

		if (currentValue.equals("company-users")) {
			params.put("accountEntryIds", new long[0]);
		}
		else if (currentValue.equals("account-users")) {
			params.put(
				"accountEntryIds",
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY});
		}

		return params;
	}

	@Override
	public String getShortLabel(Locale locale) {
		return _getMessage(locale, "domain");
	}

	@Override
	public String getValueLabel(Locale locale, String value) {
		return _getMessage(locale, value);
	}

	@Override
	public String[] getValues() {
		return new String[] {"all", "company-users", "account-users"};
	}

	private String _getMessage(Locale locale, String key) {
		return LanguageUtil.get(_getResourceBundle(locale), key);
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(locale, "com.liferay.account.lang"),
			ResourceBundleUtil.getBundle(
				locale, "com.liferay.users.admin.web"));
	}

}