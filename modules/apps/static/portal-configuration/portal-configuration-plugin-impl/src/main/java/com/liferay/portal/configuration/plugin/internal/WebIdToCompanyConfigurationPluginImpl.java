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

package com.liferay.portal.configuration.plugin.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationPlugin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		ConfigurationPlugin.CM_RANKING + ":Integer=400",
		"config.plugin.id=com.liferay.portal.configuration.plugin.web.id.to.company"
	},
	service = ConfigurationPlugin.class
)
public class WebIdToCompanyConfigurationPluginImpl
	implements ConfigurationPlugin {

	@Override
	public void modifyConfiguration(
		ServiceReference<?> serviceReference,
		Dictionary<String, Object> properties) {

		String companyWebId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Validator.isNull(companyWebId)) {
			return;
		}

		if (Objects.equals("default", companyWebId)) {
			companyWebId = PropsValues.COMPANY_DEFAULT_WEB_ID;
		}

		Object pid = properties.get(Constants.SERVICE_PID);

		DynamicQuery dynamicQuery = _companyLocalService.dynamicQuery();

		Property property = PropertyFactoryUtil.forName("webId");

		dynamicQuery.add(property.eq(companyWebId));

		List<Company> companies = _companyLocalService.dynamicQuery(
			dynamicQuery);

		if (companies.isEmpty()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"No company matching {webId=", companyWebId,
						"}. Skipping the injection of companyId for ", pid));
			}

			return;
		}

		Company company = companies.get(0);

		properties.put("companyId", company.getCompanyId());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Injected {companyId=", company.getCompanyId(),
					"} for {webId=", companyWebId, "} into ", pid));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebIdToCompanyConfigurationPluginImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}