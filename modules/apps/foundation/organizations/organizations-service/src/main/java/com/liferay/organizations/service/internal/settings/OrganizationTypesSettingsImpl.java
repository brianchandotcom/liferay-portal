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

package com.liferay.organizations.service.internal.settings;

import com.liferay.organizations.service.internal.configuration.OrganizationTypeSettings;
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
		OrganizationTypeSettings organizationTypeSettings =
			getOrganizationTypeSettings(type);

		if (organizationTypeSettings == null) {
			return new String[0];
		}

		return organizationTypeSettings.getChildrenTypes();
	}

	@Override
	public String[] getTypes() {
		return ArrayUtil.toStringArray(_organizationTypes.keySet());
	}

	@Override
	public boolean isCountryEnabled(String type) {
		OrganizationTypeSettings organizationTypeSettings =
			getOrganizationTypeSettings(type);

		if (organizationTypeSettings == null) {
			return false;
		}

		return organizationTypeSettings.isCountryEnabled();
	}

	@Override
	public boolean isCountryRequired(String type) {
		OrganizationTypeSettings organizationTypeSettings =
			getOrganizationTypeSettings(type);

		if (organizationTypeSettings == null) {
			return false;
		}

		return organizationTypeSettings.isCountryRequired();
	}

	@Override
	public boolean isRootable(String type) {
		OrganizationTypeSettings organizationTypeSettings =
			getOrganizationTypeSettings(type);

		if (organizationTypeSettings == null) {
			return false;
		}

		return organizationTypeSettings.isRootable();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeOrganizationTypeSettings"
	)
	protected void addOrganizationTypeSettings(
		OrganizationTypeSettings organizationTypeSettings,
		Map<String, Object> properties) {

		_organizationTypes.put(
			organizationTypeSettings.getName(), organizationTypeSettings);
	}

	protected OrganizationTypeSettings getOrganizationTypeSettings(
		String type) {

		OrganizationTypeSettings organizationTypeSettings =
			_organizationTypes.get(type);

		if (organizationTypeSettings == null) {
			_log.error("Unable to get organization type: " + type);
		}

		return organizationTypeSettings;
	}

	protected void removeOrganizationTypeSettings(
		OrganizationTypeSettings organizationTypeSettings,
		Map<String, Object> properties) {

		_organizationTypes.remove(organizationTypeSettings.getName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrganizationTypesSettingsImpl.class);

	private final Map<String, OrganizationTypeSettings> _organizationTypes =
		new ConcurrentHashMap<>();

}