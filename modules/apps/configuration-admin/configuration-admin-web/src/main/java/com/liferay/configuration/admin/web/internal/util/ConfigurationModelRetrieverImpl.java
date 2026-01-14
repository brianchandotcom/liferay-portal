/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.util.ConfigurationFilterStringUtil;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContext;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
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
@Component(service = ConfigurationModelRetriever.class)
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
					_getConfigurationModelComparator());

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

		return getConfiguration(pid, scope, scopePK, true);
	}

	@Override
	public Configuration getConfiguration(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK, boolean strictScope) {

		Configuration[] configurations = _getConfigurations(
			pid, scope, String.valueOf(scopePK));

		if (ArrayUtil.isNotEmpty(configurations)) {
			for (Configuration configuration : configurations) {
				if (scope.equals(ExtendedObjectClassDefinition.Scope.SYSTEM)) {
					return configuration;
				}

				Dictionary<String, Object> properties =
					configuration.getProcessedProperties(null);

				if (Objects.equals(
						properties.get(scope.getPropertyKey()), scopePK)) {

					return configuration;
				}
			}
		}

		if (!strictScope &&
			scope.equals(ExtendedObjectClassDefinition.Scope.COMPANY)) {

			return getConfiguration(
				pid, ExtendedObjectClassDefinition.Scope.SYSTEM, null, false);
		}
		else if (!strictScope &&
				 scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {

			long companyId = 0;

			Group group = _groupLocalService.fetchGroup((Long)scopePK);

			if (group != null) {
				companyId = group.getCompanyId();
			}

			return getConfiguration(
				pid, ExtendedObjectClassDefinition.Scope.COMPANY, companyId,
				false);
		}

		return null;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		Bundle bundle, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels = new HashMap<>();

		_collectConfigurationModels(
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

			_collectConfigurationModels(
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

		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			new ConfigurationScopeDisplayContext(scope, scopePK);

		return TransformUtil.transformToList(
			_getConfigurations(
				factoryConfigurationModel.getFactoryPid(), scope,
				String.valueOf(scopePK)),
			configuration -> new ConfigurationModel(
				configuration.getBundleLocation(),
				factoryConfigurationModel.getBundleSymbolicName(),
				factoryConfigurationModel.getClassLoader(), configuration,
				configurationScopeDisplayContext, factoryConfigurationModel,
				false));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected String getPidFilterString(
		String pid, ExtendedObjectClassDefinition.Scope scope) {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.SYSTEM)) {
			return ConfigurationFilterStringUtil.getSystemScopedFilterString(
				pid);
		}

		return _getScopedPidFilterString(pid, scope);
	}

	private void _collectConfigurationModels(
		Bundle bundle, Map<String, ConfigurationModel> configurationModels,
		String locale, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (extendedMetaTypeInformation == null) {
			return;
		}

		for (String pid : extendedMetaTypeInformation.getFactoryPids()) {
			ConfigurationModel configurationModel = _getConfigurationModel(
				bundle, extendedMetaTypeInformation, pid, true, locale, scope,
				scopePK);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.put(pid, configurationModel);
		}

		for (String pid : extendedMetaTypeInformation.getPids()) {
			ConfigurationModel configurationModel = _getConfigurationModel(
				bundle, extendedMetaTypeInformation, pid, false, locale, scope,
				scopePK);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.put(pid, configurationModel);
		}
	}

	private ConfigurationModel _getConfigurationModel(
		Bundle bundle, ExtendedMetaTypeInformation extendedMetaTypeInformation,
		String pid, boolean factory, String locale,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);
		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			new ConfigurationScopeDisplayContext(scope, scopePK);

		ConfigurationModel configurationModel = new ConfigurationModel(
			StringPool.QUESTION, bundle.getSymbolicName(),
			bundleWiring.getClassLoader(),
			getConfiguration(pid, scope, scopePK),
			configurationScopeDisplayContext,
			extendedMetaTypeInformation.getObjectClassDefinition(pid, locale),
			factory);

		if (!StringUtil.equals(configurationModel.getFactoryPid(), pid)) {
			configurationModel.setFactoryPid(pid);
		}

		if (scope.equals(scope.COMPANY) && configurationModel.isSystemScope()) {
			return null;
		}

		if (scope.equals(scope.GROUP) && !configurationModel.isGroupScope()) {
			return null;
		}

		return configurationModel;
	}

	private Comparator<ConfigurationModel> _getConfigurationModelComparator() {
		return new ConfigurationModelComparator();
	}

	private Configuration[] _getConfigurations(
		String pid, ExtendedObjectClassDefinition.Scope scope, String value) {

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					getPidFilterString(pid, scope));

			if (configurations == null) {
				return new Configuration[0];
			}

			String propertyFilterString = _getPropertyFilterString(
				scope.getPropertyKey(), value);

			if (Validator.isNull(propertyFilterString)) {
				return configurations;
			}

			Filter filter = FrameworkUtil.createFilter(propertyFilterString);

			if (filter == null) {
				return configurations;
			}

			return TransformUtil.transform(
				configurations,
				configuration -> {
					Dictionary<String, Object> properties =
						configuration.getProcessedProperties(null);

					if (filter.match(properties)) {
						return configuration;
					}

					return null;
				},
				Configuration.class);
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String _getPropertyFilterString(String key, String value) {
		if (Validator.isNull(key) || Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			StringPool.OPEN_PARENTHESIS, key, StringPool.EQUAL, value,
			StringPool.CLOSE_PARENTHESIS);
	}

	private String _getScopedPidFilterString(
		String pid, ExtendedObjectClassDefinition.Scope scope) {

		if (scope.equals(ExtendedObjectClassDefinition.Scope.COMPANY)) {
			return ConfigurationFilterStringUtil.getCompanyScopedFilterString(
				null, pid, null);
		}

		if (scope.equals(ExtendedObjectClassDefinition.Scope.GROUP)) {
			return ConfigurationFilterStringUtil.getGroupScopedFilterString(
				null, pid, null);
		}

		return ConfigurationFilterStringUtil.getPortletScopedFilterString(
			pid, null);
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