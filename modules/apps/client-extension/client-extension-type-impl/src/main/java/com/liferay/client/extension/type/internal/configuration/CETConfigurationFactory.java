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

package com.liferay.client.extension.type.internal.configuration;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.osgi.util.factory.FactoryUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.client.extension.type.configuration.CETConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = CETConfigurationFactory.class
)
public class CETConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		CETConfiguration cetConfiguration = ConfigurableUtil.createConfigurable(
			CETConfiguration.class, properties);

		_cet = _cetManager.addCET(
			cetConfiguration, _getCompanyId(properties),
			_getExternalReferenceCode(properties));
	}

	@Deactivate
	protected void deactivate() {
		_cetManager.deleteCET(_cet);
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws Exception {
		_cetManager.deleteCET(_cet);

		CETConfiguration cetConfiguration = ConfigurableUtil.createConfigurable(
			CETConfiguration.class, properties);

		_cet = _cetManager.addCET(
			cetConfiguration, _getCompanyId(properties),
			_getExternalReferenceCode(properties));
	}

	private long _getCompanyId(Map<String, Object> properties)
		throws Exception {

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		Company company = null;

		if (companyId == 0) {
			company = _companyLocalService.getCompanyByWebId(
				PropsValues.COMPANY_DEFAULT_WEB_ID);
		}
		else {
			company = _companyLocalService.getCompanyById(companyId);
		}

		return company.getCompanyId();
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		return "LXC:" + FactoryUtil.getExternalReferenceCode(properties);
	}

	private volatile CET _cet;

	@Reference
	private CETManager _cetManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}