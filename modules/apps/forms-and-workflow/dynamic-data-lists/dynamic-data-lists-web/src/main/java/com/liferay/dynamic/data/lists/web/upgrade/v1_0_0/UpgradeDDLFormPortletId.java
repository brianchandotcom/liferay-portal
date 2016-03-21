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

package com.liferay.dynamic.data.lists.web.upgrade.v1_0_0;

import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class UpgradeDDLFormPortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	public UpgradeDDLFormPortletId(
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_portletPreferencesLocalService = portletPreferencesLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	protected void deleteResourcePermissions(
		String oldRootPortletId, String newRootPortletId) {

		DynamicQuery resourcePermissionQuery =
			_resourcePermissionLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");
		Property nameProperty = PropertyFactoryUtil.forName("name");
		Property roleIdProperty = PropertyFactoryUtil.forName("roleId");
		Property scopeProperty = PropertyFactoryUtil.forName("scope");

		Criterion newNameCriterion = nameProperty.eq(
			new String(newRootPortletId));

		Criterion oldNameCriterion = nameProperty.eq(
			new String(oldRootPortletId));

		resourcePermissionQuery = resourcePermissionQuery.add(oldNameCriterion);

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.dynamicQuery(
				resourcePermissionQuery);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			long total = hasResourcePermission(
				resourcePermission.getCompanyId(),
				resourcePermission.getRoleId(), resourcePermission.getScope(),
				companyIdProperty, roleIdProperty, scopeProperty,
				newNameCriterion);

			if (total > 0) {
				_resourcePermissionLocalService.deleteResourcePermission(
					resourcePermission);
			}
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"1_WAR_ddlformportlet",
				DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY
			}
		};
	}

	protected long hasResourcePermission(
		long companyId, long roleId, int scope, Property companyIdProperty,
		Property roleIdProperty, Property scopeProperty,
		Criterion newNameCriterion) {

		DynamicQuery resourcePermissionQuery =
			_resourcePermissionLocalService.dynamicQuery();

		resourcePermissionQuery =
			resourcePermissionQuery.add(newNameCriterion).add(
				companyIdProperty.eq(Long.valueOf(companyId))).add(
					roleIdProperty.eq(Long.valueOf(roleId))).add(
						scopeProperty.eq(Integer.valueOf(scope)));

		return _resourcePermissionLocalService.dynamicQueryCount(
			resourcePermissionQuery);
	}

	@Override
	protected void updateInstanceablePortletPreferences(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		DynamicQuery portletPreferencesQuery =
			_portletPreferencesLocalService.dynamicQuery();

		Property portletIdProperty = PropertyFactoryUtil.forName("portletId");

		Junction junction = RestrictionsFactoryUtil.disjunction();
		junction.add(portletIdProperty.eq(oldRootPortletId));
		junction.add(portletIdProperty.like(oldRootPortletId + "_INSTANCE_%"));
		junction.add(
			portletIdProperty.like(oldRootPortletId + "_USER_%_INSTANCE_%"));

		portletPreferencesQuery = portletPreferencesQuery.add(junction);

		List<PortletPreferences> portletPreferences =
			_portletPreferencesLocalService.dynamicQuery(
				portletPreferencesQuery);

		for (PortletPreferences portletPreference : portletPreferences) {
			String newPortletId = StringUtil.replace(
				portletPreference.getPortletId(), oldRootPortletId,
				newRootPortletId);

			String newPreferences = StringUtil.replace(
				portletPreference.getPreferences(), "</portlet-preferences>",
				"<preference><name>formView</name><value>true</value>" +
					"</preference></portlet-preferences>");

			portletPreference.setPortletId(newPortletId);
			portletPreference.setPreferences(newPreferences);

			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreference);
		}
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			updateResourcePermission(oldRootPortletId, newRootPortletId, true);

			updateInstanceablePortletPreferences(
				oldRootPortletId, newRootPortletId);

			updateLayouts(oldRootPortletId, newRootPortletId, false);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		deleteResourcePermissions(oldRootPortletId, newRootPortletId);

		super.updateResourcePermission(
			oldRootPortletId, newRootPortletId, updateName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDDLFormPortletId.class);

	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}