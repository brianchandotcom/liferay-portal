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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.settings.internal.util.ConfigurationPidUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Drew Brokke
 */
public class ScopedConfigurationManager implements ManagedServiceFactory {

	public ScopedConfigurationManager(
		BundleContext bundleContext, Class<?> configurationBeanClass,
		LocationVariableResolver locationVariableResolver) {

		_bundleContext = bundleContext;
		_configurationBeanClass = configurationBeanClass;
		_locationVariableResolver = locationVariableResolver;

		_factoryPid = ConfigurationPidUtil.getConfigurationPid(
			configurationBeanClass);
	}

	@Override
	public void deleted(String pid) {
		_removePidConfigurations(pid);
	}

	public Object getConfiguration(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		Map<String, Object> scopeConfigurationBeans = _configurationBeans.get(
			new ScopeKey(scopePK, scope));

		if (!MapUtil.isEmpty(scopeConfigurationBeans)) {
			List<Object> valuesList = ListUtil.fromCollection(
				scopeConfigurationBeans.values());

			return valuesList.get(valuesList.size() - 1);
		}

		return null;
	}

	public LocationVariableResolver getLocationVariableResolver() {
		return _locationVariableResolver;
	}

	@Override
	public String getName() {
		return _factoryPid;
	}

	public void register() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_PID, _factoryPid + ".scoped");

		_managedServiceFactoryServiceRegistration =
			_bundleContext.registerService(
				ManagedServiceFactory.class, this, properties);
	}

	public void unregister() {
		_managedServiceFactoryServiceRegistration.unregister();
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> properties)
		throws ConfigurationException {

		long companyId = GetterUtil.getLong(
			properties.get(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey()),
			CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_updateEntries(
				pid, companyId, ExtendedObjectClassDefinition.Scope.COMPANY,
				properties);
		}

		long groupId = GetterUtil.getLong(
			properties.get(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey()),
			GroupConstants.ANY_PARENT_GROUP_ID);

		if (groupId != GroupConstants.ANY_PARENT_GROUP_ID) {
			_updateEntries(
				pid, groupId, ExtendedObjectClassDefinition.Scope.GROUP,
				properties);
		}

		String portletInstanceId = GetterUtil.getString(
			properties.get(
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
					getPropertyKey()));

		if (Validator.isNotNull(portletInstanceId)) {
			_updateEntries(
				pid, portletInstanceId,
				ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE,
				properties);
		}
	}

	private void _removePidConfigurations(String pid) {
		_pidScopeKeys.computeIfPresent(
			pid,
			(key, scopeKeys) -> {
				for (ScopeKey scopeKey : scopeKeys) {
					_removeScopePidConfigurations(pid, scopeKey);
				}

				return null;
			});
	}

	private void _removeScopePidConfigurations(String pid, ScopeKey scopeKey) {
		_configurationBeans.computeIfPresent(
			scopeKey,
			(key, scopeConfigurations) -> {
				scopeConfigurations.remove(pid);

				if (!scopeConfigurations.isEmpty()) {
					return scopeConfigurations;
				}

				return null;
			});
	}

	private void _updateEntries(
		String pid, Serializable scopePK,
		ExtendedObjectClassDefinition.Scope scope, Dictionary properties) {

		ScopeKey scopeKey = new ScopeKey(scopePK, scope);

		_pidScopeKeys.compute(
			pid,
			(key, scopeKeys) -> {
				if (scopeKeys == null) {
					scopeKeys = new HashSet<>();
				}

				scopeKeys.add(scopeKey);

				return scopeKeys;
			});

		_configurationBeans.compute(
			scopeKey,
			(key, scopeConfigurations) -> {
				if (scopeConfigurations == null) {
					scopeConfigurations = new LinkedHashMap<>();
				}

				scopeConfigurations.remove(pid);

				scopeConfigurations.put(
					pid,
					ConfigurableUtil.createConfigurable(
						_configurationBeanClass, properties));

				return scopeConfigurations;
			});
	}

	private final BundleContext _bundleContext;
	private final Class<?> _configurationBeanClass;
	private final Map<ScopeKey, Map<String, Object>> _configurationBeans =
		new ConcurrentHashMap<>();
	private final String _factoryPid;
	private final LocationVariableResolver _locationVariableResolver;
	private ServiceRegistration<ManagedServiceFactory>
		_managedServiceFactoryServiceRegistration;
	private final Map<String, Set<ScopeKey>> _pidScopeKeys =
		new ConcurrentHashMap<>();

	private class ScopeKey {

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ScopeKey)) {
				return false;
			}

			ScopeKey scopeKey = (ScopeKey)obj;

			if (Objects.equals(_scope, scopeKey.getScope()) &&
				Objects.equals(_scopePK, scopeKey.getScopePK())) {

				return true;
			}

			return false;
		}

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		public Serializable getScopePK() {
			return _scopePK;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_scope.getValue(), _scopePK);
		}

		private ScopeKey(
			Serializable scopePK, ExtendedObjectClassDefinition.Scope scope) {

			_scopePK = scopePK;
			_scope = scope;
		}

		private final ExtendedObjectClassDefinition.Scope _scope;
		private final Serializable _scopePK;

	}

}