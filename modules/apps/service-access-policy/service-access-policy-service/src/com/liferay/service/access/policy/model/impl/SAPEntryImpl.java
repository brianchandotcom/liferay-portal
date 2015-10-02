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

package com.liferay.service.access.policy.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.service.access.policy.configuration.SAPConfiguration;
import com.liferay.service.access.policy.constants.SAPConstants;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SAPEntryImpl extends SAPEntryBaseImpl {

	@Override
	public List<String> getAllowedServiceSignaturesList() {
		String[] allowedServiceSignatures = StringUtil.split(
			getAllowedServiceSignatures(), StringPool.NEW_LINE);

		return ListUtil.toList(allowedServiceSignatures);
	}

	@Override
	public boolean isSystem() throws ConfigurationException {
		SAPConfiguration sapConfiguration =
			ConfigurationFactoryUtil.getConfiguration(
				SAPConfiguration.class,
				new CompanyServiceSettingsLocator(
					getCompanyId(), SAPConstants.SERVICE_NAME));

		if (getName().equals(sapConfiguration.systemDefaultSAPEntryName())) {
			return true;
		}

		if (getName().equals(
				sapConfiguration.systemUserPasswordSAPEntryName())) {

			return true;
		}

		return false;
	}

}