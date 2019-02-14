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
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
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
		ScopeKey scopeKey = _stringScopeKeyMap.remove(pid);

		if (scopeKey != null) {
			_configurationBeans.remove(scopeKey);
		}
	}

	public Object getConfiguration(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		return _configurationBeans.get(new ScopeKey(scopePK, scope));
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

		properties.put(Constants.SERVICE_PID, _factoryPid);

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

		ScopeKey scopeKey = _stringScopeKeyMap.get(pid);

		if (scopeKey != null) {
			_configurationBeans.remove(scopeKey);
		}

		long companyId = GetterUtil.getLong(
			properties.get(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey()),
			CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_updateEntries(
				pid, companyId, ExtendedObjectClassDefinition.Scope.COMPANY,
				properties);

			return;
		}

		long groupId = GetterUtil.getLong(
			properties.get(
				ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey()),
			GroupConstants.ANY_PARENT_GROUP_ID);

		if (groupId != GroupConstants.ANY_PARENT_GROUP_ID) {
			_updateEntries(
				pid, groupId, ExtendedObjectClassDefinition.Scope.GROUP,
				properties);

			return;
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

			return;
		}
	}

	private void _updateEntries(
		String pid, Serializable scopePK,
		ExtendedObjectClassDefinition.Scope scope, Dictionary properties) {

		ScopeKey scopeKey = new ScopeKey(scopePK, scope);

		_stringScopeKeyMap.put(pid, scopeKey);

		_configurationBeans.put(
			scopeKey,
			ConfigurableUtil.createConfigurable(
				_configurationBeanClass, properties));
	}

	private final BundleContext _bundleContext;
	private final Class<?> _configurationBeanClass;
	private final Map<ScopeKey, Object> _configurationBeans =
		new ConcurrentHashMap<>();
	private final String _factoryPid;
	private final LocationVariableResolver _locationVariableResolver;
	private ServiceRegistration<ManagedServiceFactory>
		_managedServiceFactoryServiceRegistration;
	private final Map<String, ScopeKey> _stringScopeKeyMap =
		new ConcurrentHashMap<>();

	private class ScopeKey {

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ScopeKey)) {
				return false;
			}

			ScopeKey scopeKey = (ScopeKey)obj;

			if (Objects.equals(_scope, scopeKey.getScope()) &&
				Objects.equals(_scopePK, scopeKey.getScopePk())) {

				return true;
			}

			return false;
		}

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		public Serializable getScopePk() {
			return _scopePK;
		}

		@Override
		public int hashCode() {
			Serializable s = _scope.getValue() + _scopePK;

			return s.hashCode();
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