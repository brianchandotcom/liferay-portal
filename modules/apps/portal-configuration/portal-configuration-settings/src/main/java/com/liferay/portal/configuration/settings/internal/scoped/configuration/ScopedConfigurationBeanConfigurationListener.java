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

package com.liferay.portal.configuration.settings.internal.scoped.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.util.ConfigurationScopedPidUtil;
import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationEvent;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	service = {
		ConfigurationListener.class,
		ScopedConfigurationBeanConfigurationListener.class
	}
)
public class ScopedConfigurationBeanConfigurationListener
	implements ConfigurationListener {

	@Override
	public void configurationEvent(ConfigurationEvent event) {
		if (event.getType() != ConfigurationEvent.CM_UPDATED) {
			return;
		}

		String pid = event.getFactoryPid();

		if (pid == null) {
			pid = event.getPid();
		}

		Scope scope = ConfigurationScopedPidUtil.getScope(pid);

		if (Scope.SYSTEM == scope) {
			return;
		}

		try {
			ScopeKey scopeKey = new ScopeKey(
				_configurationBeanClasses.get(
					ConfigurationScopedPidUtil.getBasePid(pid)),
				scope, ConfigurationScopedPidUtil.getScopePrimKey(pid));

			if (_scopedConfigurationBeans.containsKey(scopeKey)) {
				return;
			}

			ScopedConfigurationBeanManagedService
				configurationBeanManagedService =
					new ScopedConfigurationBeanManagedService(
						scopeKey,
						configurationBean -> {
							if (configurationBean == null) {
								_scopedConfigurationBeans.remove(scopeKey);
							}
							else {
								_scopedConfigurationBeans.put(
									scopeKey, configurationBean);
							}
						});

			configurationBeanManagedService.register(_bundleContext, pid);
		}
		catch (IllegalArgumentException | NullPointerException e) {
			if (_log.isInfoEnabled()) {
				_log.info(e);
			}
		}
	}

	public Object get(ScopeKey scopeKey) {
		return _scopedConfigurationBeans.get(scopeKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeConfigurationBeanDeclaration"
	)
	protected void addConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		Class<?> configurationBeanClass =
			configurationBeanDeclaration.getConfigurationBeanClass();

		String configurationPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);

		_configurationBeanClasses.put(configurationPid, configurationBeanClass);
	}

	protected void removeConfigurationBeanDeclaration(
		ConfigurationBeanDeclaration configurationBeanDeclaration) {

		String configurationPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanDeclaration.getConfigurationBeanClass());

		_configurationBeanClasses.remove(configurationPid);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScopedConfigurationBeanConfigurationListener.class);

	private BundleContext _bundleContext;
	private final Map<String, Class<?>> _configurationBeanClasses =
		new ConcurrentHashMap<>();
	private final Map<ScopeKey, Object> _scopedConfigurationBeans =
		new ConcurrentHashMap<>();

}