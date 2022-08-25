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

package com.liferay.account.internal.configuration.persistence.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration",
	service = ConfigurationModelListener.class
)
public class AccountEntryEmailDomainsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		long companyId = _getCompanyId(properties);

		if (companyId == CompanyConstants.SYSTEM) {
			return;
		}

		String[] blockedEmailDomains = _getBlockedEmailDomains(properties);

		if (ArrayUtil.isEmpty(blockedEmailDomains)) {
			return;
		}

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryLocalService.searchAccountEntries(
				companyId, null,
				new LinkedHashMap<>(
					Collections.singletonMap("domains", blockedEmailDomains)),
				-1, -1, null, false);

		for (AccountEntry accountEntry :
				baseModelSearchResult.getBaseModels()) {

			String[] domains = accountEntry.getDomainsArray();

			for (String blockedEmailDomain : blockedEmailDomains) {
				domains = ArrayUtil.remove(domains, blockedEmailDomain);
			}

			accountEntry.setDomains(StringUtil.merge(domains));

			_accountEntryLocalService.updateAccountEntry(accountEntry);
		}
	}

	@Override
	public void onBeforeSave(
		String pid, Dictionary<String, Object> properties) {

		if (_getCompanyId(properties) == CompanyConstants.SYSTEM) {
			return;
		}

		String[] blockedEmailDomains = _getBlockedEmailDomains(properties);

		if (ArrayUtil.isEmpty(blockedEmailDomains)) {
			return;
		}

		Arrays.setAll(
			blockedEmailDomains,
			i -> StringUtil.lowerCase(StringUtil.trim(blockedEmailDomains[i])));

		properties.put(
			"blockedEmailDomains",
			StringUtil.merge(blockedEmailDomains, StringPool.NEW_LINE));
	}

	private String[] _getBlockedEmailDomains(
		Dictionary<String, Object> properties) {

		return StringUtil.split(
			GetterUtil.getString(properties.get("blockedEmailDomains")),
			CharPool.NEW_LINE);
	}

	private long _getCompanyId(Dictionary<String, Object> properties) {
		return GetterUtil.getLong(
			properties.get("companyId"), CompanyConstants.SYSTEM);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

}