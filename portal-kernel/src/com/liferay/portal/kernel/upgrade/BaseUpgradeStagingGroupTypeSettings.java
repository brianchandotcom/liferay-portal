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

package com.liferay.portal.kernel.upgrade;

import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Gergely Mathe
 * @author Balázs Sáfrány-Kovalik
 */
public class BaseUpgradeStagingGroupTypeSettings extends UpgradeProcess {

	public BaseUpgradeStagingGroupTypeSettings(
		CompanyLocalService companyLocalService,
		GroupLocalService groupLocalService, String oldPortletId,
		String newPortletId) {

		_companyLocalService = companyLocalService;
		_groupLocalService = groupLocalService;
		_oldPortletId = oldPortletId;
		_newPortletId = newPortletId;
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateStagedPortletNames();
	}

	protected void updateStagedPortletNames() throws PortalException {
		for (Company company : _companyLocalService.getCompanies()) {
			updateStagedPortletNames(company.getCompanyId());
		}
	}

	protected void updateStagedPortletNames(Long companyId)
		throws PortalException {

		ActionableDynamicQuery groupActionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property siteProperty = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(siteProperty.eq(Boolean.TRUE));
			});
		groupActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Group>)group -> {
				UnicodeProperties typeSettingsUnicodeProperties =
					group.getTypeSettingsProperties();

				if (typeSettingsUnicodeProperties.isEmpty()) {
					return;
				}

				String oldPropertyKey = _getStagedPortletId(_oldPortletId);

				String oldPropertyValue =
					typeSettingsUnicodeProperties.getProperty(oldPropertyKey);

				typeSettingsUnicodeProperties.remove(oldPropertyKey);

				if (Validator.isNull(oldPropertyValue)) {
					return;
				}

				String newPropertyKey = _getStagedPortletId(_newPortletId);

				String newPropertyValue =
					typeSettingsUnicodeProperties.getProperty(newPropertyKey);

				if (Validator.isNull(newPropertyValue)) {
					typeSettingsUnicodeProperties.put(
						newPropertyKey, oldPropertyValue);
				}

				group.setTypeSettingsProperties(typeSettingsUnicodeProperties);

				_groupLocalService.updateGroup(group);
			});

		groupActionableDynamicQuery.performActions();
	}

	private String _getStagedPortletId(String portletId) {
		if (portletId.startsWith(StagingConstants.STAGED_PORTLET)) {
			return portletId;
		}

		return StagingConstants.STAGED_PORTLET.concat(portletId);
	}

	private final CompanyLocalService _companyLocalService;
	private final GroupLocalService _groupLocalService;
	private final String _newPortletId;
	private final String _oldPortletId;

}