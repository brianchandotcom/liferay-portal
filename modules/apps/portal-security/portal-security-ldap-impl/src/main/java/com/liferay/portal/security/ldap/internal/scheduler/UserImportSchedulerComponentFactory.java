/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.scheduler;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class UserImportSchedulerComponentFactory {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_serviceRegistration = bundleContext.registerService(
			ConfigurationModelListener.class,
			new LDAPImportConfigurationModelListener(),
			HashMapDictionaryBuilder.<String, Object>put(
				"model.class.name", _PID
			).build());

		Dictionary<String, Object> properties = _EMPTY_PROPERTIES;

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat("(", Constants.SERVICE_PID, "=", _PID, ")"));

		if (configurations != null) {
			properties = configurations[0].getProperties();
		}

		_updateComponentInstance(properties);
	}

	@Deactivate
	protected void deactivate() {
		if (_componentInstance != null) {
			_componentInstance.dispose();
		}

		_serviceRegistration.unregister();
	}

	private void _updateComponentInstance(
		Dictionary<String, Object> properties) {

		if (_componentInstance != null) {
			_componentInstance.dispose();
		}

		_componentInstance = _componentFactory.newInstance(
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", CompanyConstants.SYSTEM
			).put(
				"configuration",
				ConfigurableUtil.createConfigurable(
					LDAPImportConfiguration.class, properties)
			).build());
	}

	private static final Dictionary<String, Object> _EMPTY_PROPERTIES =
		new HashMapDictionary<>();

	private static final String _PID =
		"com.liferay.portal.security.ldap.exportimport.configuration." +
			"LDAPImportConfiguration";

	@Reference(
		target = "(component.factory=com.liferay.portal.security.ldap.internal.scheduler.UserImportSchedulerJobConfiguration)"
	)
	private ComponentFactory _componentFactory;

	private ComponentInstance<?> _componentInstance;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private ServiceRegistration<ConfigurationModelListener>
		_serviceRegistration;

	private class LDAPImportConfigurationModelListener
		implements ConfigurationModelListener {

		@Override
		public void onAfterDelete(String pid) {
			if (!StringUtil.equals(pid, _PID)) {
				return;
			}

			_updateComponentInstance(_EMPTY_PROPERTIES);
		}

		@Override
		public void onAfterSave(
			String pid, Dictionary<String, Object> properties) {

			if (!StringUtil.equals(pid, _PID)) {
				return;
			}

			_updateComponentInstance(properties);
		}

	}

}