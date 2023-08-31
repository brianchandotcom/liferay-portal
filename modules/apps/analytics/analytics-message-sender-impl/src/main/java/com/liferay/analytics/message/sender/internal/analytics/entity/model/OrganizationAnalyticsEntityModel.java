/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=organization",
	service = AnalyticsEntityModel.class
)
public class OrganizationAnalyticsEntityModel
	extends BaseAnalyticsEntityModel<Organization> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _organizationAttributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) throws Exception {
		return user.getOrganizationIds();
	}

	@Override
	public Organization getModel(long id) throws Exception {
		return _organizationLocalService.getOrganization(id);
	}

	@Override
	public String getModelClassName() {
		return Organization.class.getName();
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return _organizationLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "organizationId";
	}

	private static final List<String> _organizationAttributeNames =
		Arrays.asList(
			"expando", "modifiedDate", "name", "parentOrganizationId",
			"treePath", "type");

	@Reference
	private OrganizationLocalService _organizationLocalService;

}