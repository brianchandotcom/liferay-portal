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

package com.liferay.fragment.web.internal.configuration.admin.service;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.fragment.configuration.FragmentServiceConfiguration",
	property = Constants.SERVICE_PID + "=com.liferay.fragment.configuration.FragmentServiceConfiguration.scoped",
	service = {
		FragmentServiceManagedServiceFactory.class, ManagedServiceFactory.class
	}
)
public class FragmentServiceManagedServiceFactory
	implements ManagedServiceFactory {

	@Override
	public void deleted(String pid) {
		_unmapPid(pid);
	}

	@Override
	public String getName() {
		return "com.liferay.fragment.configuration." +
			"FragmentServiceConfiguration.scoped";
	}

	public boolean isPropagateChanges(String scope, long scopePK) {
		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _isCompanyPropagateChanges(scopePK);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _isSystemPropagateChanges();
		}

		throw new IllegalArgumentException("Unsupported scope: " + scope);
	}

	public boolean isPropagateContributedFragmentChanges(
		String scope, long scopePK) {

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _isCompanyPropagateContributedFragmentChanges(scopePK);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _isSystemPropagateContributedFragmentChanges();
		}

		throw new IllegalArgumentException("Unsupported scope: " + scope);
	}

	@Override
	public void updated(String pid, Dictionary<String, ?> dictionary)
		throws ConfigurationException {

		_unmapPid(pid);

		long companyId = GetterUtil.getLong(
			dictionary.get("companyId"), CompanyConstants.SYSTEM);

		if (companyId != CompanyConstants.SYSTEM) {
			_updateCompanyConfiguration(companyId, pid, dictionary);
		}
	}

	public void updatePropagateChanges(
			boolean propagateChanges,
			boolean propagateContributedFragmentChanges, String scope,
			long scopePK)
		throws Exception {

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			_updateCompanyFragmentServiceConfiguration(
				scopePK, propagateChanges, propagateContributedFragmentChanges);
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			_updateSystemFragmentServiceConfiguration(
				propagateChanges, propagateContributedFragmentChanges);
		}
		else {
			throw new PortalException("Unsupported scope: " + scope);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemFragmentServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				FragmentServiceConfiguration.class, properties);
	}

	private FragmentServiceConfiguration _getFragmentServiceConfiguration(
		long companyId) {

		if (_companyConfigurationBeans.containsKey(companyId)) {
			return _companyConfigurationBeans.get(companyId);
		}

		return _systemFragmentServiceConfiguration;
	}

	private Configuration _getScopedConfiguration(long companyId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			String.format(
				"(&(service.factoryPid=%s)(%s=%d))",
				FragmentServiceConfiguration.class.getName() + ".scoped",
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId));

		if (configurations == null) {
			return null;
		}

		return configurations[0];
	}

	private boolean _isCompanyPropagateChanges(long companyId) {
		FragmentServiceConfiguration fragmentServiceConfiguration =
			_getFragmentServiceConfiguration(companyId);

		return fragmentServiceConfiguration.propagateChanges();
	}

	private boolean _isCompanyPropagateContributedFragmentChanges(
		long companyId) {

		FragmentServiceConfiguration fragmentServiceConfiguration =
			_getFragmentServiceConfiguration(companyId);

		return fragmentServiceConfiguration.
			propagateContributedFragmentChanges();
	}

	private boolean _isSystemPropagateChanges() {
		return _systemFragmentServiceConfiguration.propagateChanges();
	}

	private boolean _isSystemPropagateContributedFragmentChanges() {
		return _systemFragmentServiceConfiguration.
			propagateContributedFragmentChanges();
	}

	private void _unmapPid(String pid) {
		if (_companyIds.containsKey(pid)) {
			long companyId = _companyIds.remove(pid);

			_companyConfigurationBeans.remove(companyId);
		}
	}

	private void _updateCompanyConfiguration(
		long companyId, String pid, Dictionary<String, ?> dictionary) {

		_companyConfigurationBeans.put(
			companyId,
			ConfigurableUtil.createConfigurable(
				FragmentServiceConfiguration.class, dictionary));
		_companyIds.put(pid, companyId);
	}

	private void _updateCompanyFragmentServiceConfiguration(
			long companyId, boolean propagateChanges,
			boolean propagateContributedFragmentChanges)
		throws Exception {

		_updateScopedConfiguration(
			propagateChanges, propagateContributedFragmentChanges, companyId);
	}

	private void _updateScopedConfiguration(
			boolean propagateChanges,
			boolean propagateContributedFragmentChanges, long companyId)
		throws Exception {

		Dictionary<String, Object> properties;
		Configuration configuration = _getScopedConfiguration(companyId);

		if (configuration == null) {
			configuration = _configurationAdmin.createFactoryConfiguration(
				FragmentServiceConfiguration.class.getName() + ".scoped",
				StringPool.QUESTION);

			properties = HashMapDictionaryBuilder.<String, Object>put(
				ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
				companyId
			).build();
		}
		else {
			properties = configuration.getProperties();
		}

		properties.put("propagateChanges", propagateChanges);
		properties.put(
			"propagateContributedFragmentChanges",
			propagateContributedFragmentChanges);

		configuration.update(properties);
	}

	private void _updateSystemFragmentServiceConfiguration(
			boolean propagateChanges,
			boolean propagateContributedFragmentChanges)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			FragmentServiceConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		properties.put("propagateChanges", propagateChanges);
		properties.put(
			"propagateContributedFragmentChanges",
			propagateContributedFragmentChanges);

		configuration.update(properties);
	}

	private final Map<Long, FragmentServiceConfiguration>
		_companyConfigurationBeans = new ConcurrentHashMap<>();
	private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private volatile FragmentServiceConfiguration
		_systemFragmentServiceConfiguration;

}