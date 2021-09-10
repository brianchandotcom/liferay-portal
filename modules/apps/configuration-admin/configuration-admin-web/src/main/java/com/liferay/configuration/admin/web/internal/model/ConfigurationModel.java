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

package com.liferay.configuration.admin.web.internal.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.ConfigurationOverridePropertiesUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Raymond Augé
 */
public class ConfigurationModel implements ExtendedObjectClassDefinition {

	public static final String PROPERTY_KEY_COMPANY_ID = "companyId";

	public static final String PROPERTY_VALUE_COMPANY_ID_DEFAULT = "0";

	public ConfigurationModel(
		String bundleLocation, String bundleSymbolicName,
		ClassLoader classLoader, Configuration configuration,
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		boolean factory) {

		_bundleLocation = bundleLocation;
		_bundleSymbolicName = bundleSymbolicName;
		_classLoader = classLoader;
		_configuration = configuration;
		_extendedObjectClassDefinition = extendedObjectClassDefinition;
		_factory = factory;

		_configurationOverrideProperties =
			ConfigurationOverridePropertiesUtil.getOverrideProperties(
				_extendedObjectClassDefinition.getID());

		if (_configurationOverrideProperties == null) {
			_configurationOverrideProperties = Collections.emptyMap();
		}
	}

	public ConfigurationModel(
		String bundleLocation, String bundleSymbolicName,
		Configuration configuration,
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		boolean factory) {

		this(
			bundleLocation, bundleSymbolicName,
			ConfigurationModel.class.getClassLoader(), configuration,
			extendedObjectClassDefinition, factory);
	}

	@Override
	public boolean equals(Object object) {
		ConfigurationModel configurationModel = (ConfigurationModel)object;

		return Objects.equals(getID(), configurationModel.getID());
	}

	@Override
	public ExtendedAttributeDefinition[] getAttributeDefinitions(int filter) {
		return _extendedObjectClassDefinition.getAttributeDefinitions(filter);
	}

	public String getBaseID() {
		return _extendedObjectClassDefinition.getID();
	}

	public String getBundleLocation() {
		return _bundleLocation;
	}

	public String getBundleSymbolicName() {
		return _bundleSymbolicName;
	}

	public String getCategory() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return GetterUtil.getString(
			extensionAttributes.get("category"), "third-party");
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public String getDescription() {
		return _extendedObjectClassDefinition.getDescription();
	}

	public String[] getDescriptionArguments() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return StringUtil.split(
			extensionAttributes.get("description-arguments"));
	}

	public ExtendedAttributeDefinition getExtendedAttributeDefinition(
		String id) {

		ExtendedAttributeDefinition[] extendedAttributeDefinitions =
			_extendedObjectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.ALL);

		for (ExtendedAttributeDefinition extendedAttributeDefinition :
				extendedAttributeDefinitions) {

			if (id.equals(extendedAttributeDefinition.getID())) {
				return extendedAttributeDefinition;
			}
		}

		return null;
	}

	public ExtendedObjectClassDefinition getExtendedObjectClassDefinition() {
		return _extendedObjectClassDefinition;
	}

	@Override
	public Map<String, String> getExtensionAttributes(String uri) {
		return _extendedObjectClassDefinition.getExtensionAttributes(uri);
	}

	@Override
	public Set<String> getExtensionUris() {
		return _extendedObjectClassDefinition.getExtensionUris();
	}

	public String getFactoryPid() {
		if (_extendedObjectClassDefinition instanceof ConfigurationModel) {
			ConfigurationModel configurationModel =
				(ConfigurationModel)_extendedObjectClassDefinition;

			return configurationModel.getFactoryPid();
		}

		return _extendedObjectClassDefinition.getID();
	}

	public Map<String, String> getHintAttributes() {
		return _extendedObjectClassDefinition.getExtensionAttributes(
			"http://www.liferay.com/xsd/meta-type-hints_7_0_0");
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		return _extendedObjectClassDefinition.getIcon(size);
	}

	@Override
	public String getID() {
		if (_configuration != null) {
			return _configuration.getPid();
		}

		return _extendedObjectClassDefinition.getID();
	}

	public String getLabel() {
		String value = getLabelAttributeValue();

		if (value == null) {
			return getName();
		}

		return value;
	}

	public String getLabelAttribute() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return GetterUtil.getString(
			extensionAttributes.get("factoryInstanceLabelAttribute"));
	}

	public String getLiferayLearnMessageKey() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return extensionAttributes.get("liferayLearnMessageKey");
	}

	public String getLiferayLearnMessageResource() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return extensionAttributes.get("liferayLearnMessageResource");
	}

	@Override
	public String getName() {
		return _extendedObjectClassDefinition.getName();
	}

	public String[] getNameArguments() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return StringUtil.split(extensionAttributes.get("name-arguments"));
	}

	public String getScope() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return GetterUtil.getString(
			extensionAttributes.get("scope"), Scope.SYSTEM.toString());
	}

	public boolean hasConfiguration() {
		if (getConfiguration() == null) {
			return false;
		}

		return true;
	}

	public Boolean hasConfigurationOverrideProperty(String key) {
		return _configurationOverrideProperties.containsKey(key);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getID());
	}

	public boolean hasScopeConfiguration(Scope scope) {
		if (!hasConfiguration()) {
			return false;
		}

		Dictionary<String, Object> properties = _configuration.getProperties();

		if (properties == null) {
			return false;
		}

		long groupId = GetterUtil.getLong(
			properties.get(Scope.GROUP.getPropertyKey()),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		if ((groupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) &&
			Scope.GROUP.equals(scope.getValue())) {

			return true;
		}

		long companyId = GetterUtil.getLong(
			properties.get(Scope.COMPANY.getPropertyKey()),
			CompanyConstants.SYSTEM);

		if (((companyId != CompanyConstants.SYSTEM) &&
			 Scope.COMPANY.equals(scope.getValue())) ||
			Scope.SYSTEM.equals(scope.getValue())) {

			return true;
		}

		return false;
	}

	public boolean isCompanyFactory() {
		if (!isFactory()) {
			return false;
		}

		if (Objects.equals(getScope(), Scope.COMPANY.toString()) &&
			Objects.equals(getLabelAttribute(), PROPERTY_KEY_COMPANY_ID)) {

			return true;
		}

		return false;
	}

	public boolean isCompanyScope() {
		return isScope(Scope.COMPANY);
	}

	public boolean isFactory() {
		return _factory;
	}

	public boolean isGenerateUI() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return GetterUtil.getBoolean(
			extensionAttributes.get("generateUI"), true);
	}

	public boolean isGroupScope() {
		return isScope(Scope.GROUP);
	}

	public boolean isPortletInstanceScope() {
		return isScope(Scope.PORTLET_INSTANCE);
	}

	public boolean isStrictScope() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				com.liferay.portal.configuration.metatype.annotations.
					ExtendedObjectClassDefinition.XML_NAMESPACE);

		return GetterUtil.getBoolean(extensionAttributes.get("strictScope"));
	}

	public boolean isSystemScope() {
		return isScope(Scope.SYSTEM);
	}

	protected String getLabelAttributeValue() {
		String factoryInstanceLabelAttribute = getLabelAttribute();

		String value = null;

		if (Validator.isNotNull(factoryInstanceLabelAttribute)) {
			Dictionary<String, Object> properties =
				_configuration.getProperties();

			Object valueObject = properties.get(factoryInstanceLabelAttribute);

			if (valueObject instanceof Object[]) {
				value = StringUtil.merge(
					(Object[])valueObject, StringPool.COMMA_AND_SPACE);
			}
			else {
				value = String.valueOf(valueObject);
			}
		}

		return value;
	}

	protected boolean isScope(Scope scope) {
		return scope.equals(getScope());
	}

	private final String _bundleLocation;
	private final String _bundleSymbolicName;
	private final ClassLoader _classLoader;
	private final Configuration _configuration;
	private Map<String, Object> _configurationOverrideProperties;
	private final ExtendedObjectClassDefinition _extendedObjectClassDefinition;
	private final boolean _factory;

}