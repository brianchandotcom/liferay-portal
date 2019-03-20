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

package com.liferay.configuration.admin.web.internal.model.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;

import java.io.IOException;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class CompanyConfigurationCleaner {

	public CompanyConfigurationCleaner(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	public void clean(Company company) throws Exception {
		String filterString = StringBundler.concat(
			StringPool.OPEN_PARENTHESIS,
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			StringPool.EQUAL, company.getCompanyId(),
			StringPool.CLOSE_PARENTHESIS);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			try {
				configuration.delete();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe, ioe);
				}
			}
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private Log _log = LogFactoryUtil.getLog(CompanyConfigurationCleaner.class);

}