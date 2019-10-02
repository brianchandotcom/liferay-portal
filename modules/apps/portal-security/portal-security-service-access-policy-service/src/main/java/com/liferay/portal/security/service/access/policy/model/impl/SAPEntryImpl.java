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

package com.liferay.portal.security.service.access.policy.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.service.access.policy.configuration.SAPConfiguration;
import com.liferay.portal.security.service.access.policy.constants.SAPConstants;
import com.liferay.portal.security.service.access.policy.internal.tracker.SAPSystemEntryTracker;

import java.util.List;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class SAPEntryImpl extends SAPEntryBaseImpl {

	@Override
	public List<String> getAllowedServiceSignaturesList() {
		String[] allowedServiceSignatures = StringUtil.split(
			getAllowedServiceSignatures(), StringPool.NEW_LINE);

		return ListUtil.fromArray(allowedServiceSignatures);
	}

	@Override
	public boolean isSystem() throws ConfigurationException {
		SAPConfiguration sapConfiguration =
			ConfigurationProviderUtil.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					getCompanyId(), SAPConstants.SERVICE_NAME));

		if (Objects.equals(
				getName(), sapConfiguration.systemDefaultSAPEntryName())) {

			return true;
		}

		if (Objects.equals(
				getName(), sapConfiguration.systemUserPasswordSAPEntryName())) {

			return true;
		}

		if (SAPSystemEntryTracker.isSystemEntry(getName())) {
			return true;
		}

		return false;
	}

}