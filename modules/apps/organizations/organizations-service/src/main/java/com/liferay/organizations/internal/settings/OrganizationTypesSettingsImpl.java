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

package com.liferay.organizations.internal.settings;

import com.liferay.organizations.internal.configuration.OrganizationTypeConfigurationWrapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.users.admin.kernel.organization.types.OrganizationTypesSettings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = OrganizationTypesSettings.class)
public class OrganizationTypesSettingsImpl
	implements OrganizationTypesSettings {

	@Override
	public String[] getChildrenTypes(String type) {
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper =
				_getOrganizationTypeConfigurationWrapper(type);

		if (organizationTypeConfigurationWrapper == null) {
			return new String[0];
		}

		return organizationTypeConfigurationWrapper.getChildrenTypes();
	}

	@Override
	public String[] getTypes() {
		return ArrayUtil.toStringArray(
			_organizationTypeConfigurationWrappers.keySet());
	}

	@Override
	public boolean isCountryEnabled(String type) {
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper =
				_getOrganizationTypeConfigurationWrapper(type);

		if (organizationTypeConfigurationWrapper == null) {
			return false;
		}

		return organizationTypeConfigurationWrapper.isCountryEnabled();
	}

	@Override
	public boolean isCountryRequired(String type) {
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper =
				_getOrganizationTypeConfigurationWrapper(type);

		if (organizationTypeConfigurationWrapper == null) {
			return false;
		}

		return organizationTypeConfigurationWrapper.isCountryRequired();
	}

	@Override
	public boolean isRootable(String type) {
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper =
				_getOrganizationTypeConfigurationWrapper(type);

		if (organizationTypeConfigurationWrapper == null) {
			return false;
		}

		return organizationTypeConfigurationWrapper.isRootable();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addOrganizationTypeConfigurationWrapper(
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper) {

		_organizationTypeConfigurationWrappers.put(
			organizationTypeConfigurationWrapper.getName(),
			organizationTypeConfigurationWrapper);
	}

	protected void removeOrganizationTypeConfigurationWrapper(
		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper) {

		_organizationTypeConfigurationWrappers.remove(
			organizationTypeConfigurationWrapper.getName());
	}

	private OrganizationTypeConfigurationWrapper
		_getOrganizationTypeConfigurationWrapper(String type) {

		OrganizationTypeConfigurationWrapper
			organizationTypeConfigurationWrapper =
				_organizationTypeConfigurationWrappers.get(type);

		if (organizationTypeConfigurationWrapper == null) {
			_log.error("Unable to get organization type: " + type);
		}

		return organizationTypeConfigurationWrapper;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationTypesSettingsImpl.class);

	private final Map<String, OrganizationTypeConfigurationWrapper>
		_organizationTypeConfigurationWrappers = new ConcurrentHashMap<>();

}