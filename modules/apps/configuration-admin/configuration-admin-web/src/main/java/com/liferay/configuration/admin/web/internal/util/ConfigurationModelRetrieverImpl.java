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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Michael C. Han
 */
@Component(immediate = true, service = ConfigurationModelRetriever.class)
public class ConfigurationModelRetrieverImpl
	implements ConfigurationModelRetriever {

	@Override
	public Map<String, Set<ConfigurationModel>> categorizeConfigurationModels(
		Map<String, ConfigurationModel> configurationModels) {

		Map<String, Set<ConfigurationModel>> categorizedConfigurationModels =
			new HashMap<>();

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			String configurationCategory = configurationModel.getCategory();

			Set<ConfigurationModel> curConfigurationModels =
				categorizedConfigurationModels.get(configurationCategory);

			if (curConfigurationModels == null) {
				curConfigurationModels = new TreeSet<>(
					getConfigurationModelComparator());

				categorizedConfigurationModels.put(
					configurationCategory, curConfigurationModels);
			}

			curConfigurationModels.add(configurationModel);
		}

		return categorizedConfigurationModels;
	}

	@Override
	public Configuration getConfiguration(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		try {
			String pidFilter = getPidFilterString(pid, scope, scopePK);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(pidFilter);

			if (configurations != null) {
				return configurations[0];
			}
			else if (scope.equals(
						ExtendedObjectClassDefinition.Scope.COMPANY)) {

				return getConfiguration(
					pid, ExtendedObjectClassDefinition.Scope.SYSTEM, null);
			}
			else if (scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {
				long companyId = 0;

				Group group = _groupLocalService.fetchGroup((Long)scopePK);

				if (group != null) {
					companyId = group.getCompanyId();
				}

				return getConfiguration(
					pid, ExtendedObjectClassDefinition.Scope.COMPANY,
					companyId);
			}
		}
		catch (InvalidSyntaxException | IOException exception) {
			ReflectionUtil.throwException(exception);
		}

		return null;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		Bundle bundle, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels = new HashMap<>();

		collectConfigurationModels(
			bundle, configurationModels, null, scope, scopePK);

		return configurationModels;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		return getConfigurationModels((String)null, scope, scopePK);
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		String locale, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels = new HashMap<>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			if (bundle.getState() != Bundle.ACTIVE) {
				continue;
			}

			collectConfigurationModels(
				bundle, configurationModels, locale, scope, scopePK);
		}

		return configurationModels;
	}

	@Override
	public Set<ConfigurationModel> getConfigurationModels(
		String configurationCategory, String languageId,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModelsMap =
			getConfigurationModels(languageId, scope, scopePK);

		Map<String, Set<ConfigurationModel>> categorizedConfigurationModels =
			categorizeConfigurationModels(configurationModelsMap);

		Set<ConfigurationModel> configurationModels =
			categorizedConfigurationModels.get(configurationCategory);

		if (configurationModels == null) {
			configurationModels = Collections.emptySet();
		}

		return configurationModels;
	}

	@Override
	public List<ConfigurationModel> getFactoryInstances(
			ConfigurationModel factoryConfigurationModel,
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws IOException {

		Configuration[] configurations = getFactoryConfigurations(
			factoryConfigurationModel.getFactoryPid(), scope.getPropertyKey(),
			String.valueOf(scopePK));

		if (configurations == null) {
			return Collections.emptyList();
		}

		List<ConfigurationModel> factoryInstances = new ArrayList<>();

		for (Configuration configuration : configurations) {
			ConfigurationModel curConfigurationModel = new ConfigurationModel(
				configuration.getBundleLocation(),
				factoryConfigurationModel.getBundleSymbolicName(),
				factoryConfigurationModel.getClassLoader(), configuration,
				factoryConfigurationModel, false);

			factoryInstances.add(curConfigurationModel);
		}

		return factoryInstances;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected void collectConfigurationModels(
		Bundle bundle, Map<String, ConfigurationModel> configurationModels,
		String locale, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (extendedMetaTypeInformation == null) {
			return;
		}

		for (String pid : extendedMetaTypeInformation.getFactoryPids()) {
			ConfigurationModel configurationModel = getConfigurationModel(
				bundle, extendedMetaTypeInformation, pid, true, locale, scope,
				scopePK);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.put(pid, configurationModel);
		}

		for (String pid : extendedMetaTypeInformation.getPids()) {
			ConfigurationModel configurationModel = getConfigurationModel(
				bundle, extendedMetaTypeInformation, pid, false, locale, scope,
				scopePK);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.put(pid, configurationModel);
		}
	}

	protected String getAndFilterString(String... filterStrings) {
		return getLogicalOperatorFilterString(
			StringPool.AMPERSAND, filterStrings);
	}

	protected ConfigurationModel getConfigurationModel(
		Bundle bundle, ExtendedMetaTypeInformation extendedMetaTypeInformation,
		String pid, boolean factory, String locale,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		ConfigurationModel configurationModel = new ConfigurationModel(
			StringPool.QUESTION, bundle.getSymbolicName(),
			bundleWiring.getClassLoader(),
			getConfiguration(pid, scope, scopePK),
			extendedMetaTypeInformation.getObjectClassDefinition(pid, locale),
			factory);

		if (scope.equals(scope.COMPANY) && configurationModel.isSystemScope()) {
			return null;
		}

		if (scope.equals(scope.GROUP) && !configurationModel.isGroupScope()) {
			return null;
		}

		return configurationModel;
	}

	protected Comparator<ConfigurationModel> getConfigurationModelComparator() {
		return new ConfigurationModelComparator();
	}

	protected String getExcludedPropertyFilterString(String propertyName) {
		return StringBundler.concat(
			"(!", getPropertyFilterString(propertyName, "*"), ")");
	}

	protected Configuration[] getFactoryConfigurations(String factoryPid)
		throws IOException {

		return getFactoryConfigurations(factoryPid, null, null);
	}

	protected Configuration[] getFactoryConfigurations(
			String factoryPid, String property, String value)
		throws IOException {

		Configuration[] configurations = null;

		String filterString = getPropertyFilterString(
			ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid);

		String propertyFilterString = getPropertyFilterString(property, value);

		if (Validator.isNotNull(propertyFilterString)) {
			filterString = getAndFilterString(
				filterString, propertyFilterString);
		}
		else {
			filterString = getAndFilterString(
				filterString,
				getOrFilterString(
					getExcludedPropertyFilterString(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getPropertyKey()),
					getPropertyFilterString(
						ExtendedObjectClassDefinition.Scope.COMPANY.
							getPropertyKey(),
						String.valueOf(CompanyConstants.SYSTEM))),
				getExcludedPropertyFilterString(
					ExtendedObjectClassDefinition.Scope.GROUP.getPropertyKey()),
				getExcludedPropertyFilterString(
					ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.
						getPropertyKey()));
		}

		try {
			configurations = _configurationAdmin.listConfigurations(
				filterString);
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			ReflectionUtil.throwException(invalidSyntaxException);
		}

		return configurations;
	}

	protected String getLogicalOperatorFilterString(
		String logicalOperator, String... filterStrings) {

		StringBundler sb = new StringBundler(filterStrings.length + 3);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(logicalOperator);

		for (String filterString : filterStrings) {
			if (Validator.isNull(filterString)) {
				return StringPool.BLANK;
			}

			sb.append(filterString);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getOrFilterString(String... filterStrings) {
		return getLogicalOperatorFilterString(StringPool.PIPE, filterStrings);
	}

	protected String getPidFilterString(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.SYSTEM)) {
			return getPropertyFilterString(Constants.SERVICE_PID, pid);
		}

		return getAndFilterString(
			getPropertyFilterString(
				ConfigurationAdmin.SERVICE_FACTORYPID,
				getUnscopedPid(pid) + ".scoped"),
			getPropertyFilterString(
				scope.getPropertyKey(), String.valueOf(scopePK)));
	}

	protected String getPropertyFilterString(String key, String value) {
		if (Validator.isNull(key) || Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, key, StringPool.EQUAL, value,
			StringPool.CLOSE_PARENTHESIS);
	}

	protected String getUnscopedPid(String pid) {
		return pid.replaceFirst("\\.scoped.*", StringPool.BLANK);
	}

	private BundleContext _bundleContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExtendedMetaTypeService _extendedMetaTypeService;

	@Reference
	private GroupLocalService _groupLocalService;

	private static class ConfigurationModelComparator
		implements Comparator<ConfigurationModel> {

		@Override
		public int compare(
			ConfigurationModel configurationModel1,
			ConfigurationModel configurationModel2) {

			String name1 = configurationModel1.getName();
			String name2 = configurationModel2.getName();

			return name1.compareTo(name2);
		}

	}

}