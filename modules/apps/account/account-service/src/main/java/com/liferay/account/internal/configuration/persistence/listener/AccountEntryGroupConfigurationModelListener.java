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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.internal.configuration.AccountEntryGroupConfiguration;
import com.liferay.account.internal.settings.AccountEntryGroupSettingsImpl;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	property = "model.class.name=com.liferay.account.internal.configuration.AccountEntryGroupConfiguration",
	service = ConfigurationModelListener.class
)
public class AccountEntryGroupConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String[] allowedTypes = GetterUtil.getStringValues(
			properties.get("allowedTypes"));

		if (allowedTypes.length == 0) {
			throw new ConfigurationModelListenerException(
				"A group must allow at least one account type",
				AccountEntryGroupConfiguration.class,
				AccountEntryGroupSettingsImpl.class, properties);
		}

		List<String> invalidAllowedTypes = new ArrayList<>();

		for (String allowedType : allowedTypes) {
			if (!ArrayUtil.contains(
					AccountConstants.ACCOUNT_ENTRY_TYPES_DEFAULT_ALLOWED_TYPES,
					allowedType)) {

				invalidAllowedTypes.add(allowedType);
			}
		}

		if (!invalidAllowedTypes.isEmpty()) {
			throw new ConfigurationModelListenerException(
				"Invalid account types: " +
					ListUtil.toString(invalidAllowedTypes, (String)null),
				AccountEntryGroupConfiguration.class,
				AccountEntryGroupSettingsImpl.class, properties);
		}

		properties.put("allowedTypes", ArrayUtil.distinct(allowedTypes));
	}

}