/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.configuration;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 */
@Component(
	configurationPid = "com.liferay.client.extension.type.configuration.CETConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class CETConfigurationFactory {

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, properties,
			companyId -> {
				CETConfiguration cetConfiguration =
					ConfigurableUtil.createConfigurable(
						CETConfiguration.class, properties);

				_cet = _cetManager.addCET(
					cetConfiguration, companyId,
					_getExternalReferenceCode(properties));
			});

		_properties = properties;
	}

	@Deactivate
	protected void deactivate() {
		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, _properties,
			companyId -> _cetManager.deleteCET(_cet));

		_properties = null;
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws Exception {
		ConfigurationFactoryUtil.executeAsCompany(
			_companyLocalService, properties,
			companyId -> {
				_cetManager.deleteCET(_cet);

				CETConfiguration cetConfiguration =
					ConfigurableUtil.createConfigurable(
						CETConfiguration.class, properties);

				_cet = _cetManager.addCET(
					cetConfiguration, companyId,
					_getExternalReferenceCode(properties));
			});

		_properties = properties;
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		return "LXC:" +
			ConfigurationFactoryUtil.getExternalReferenceCode(properties);
	}

	private volatile CET _cet;

	@Reference
	private CETManager _cetManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private volatile Map<String, Object> _properties;

}