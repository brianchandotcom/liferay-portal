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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {EntityModelListener.class, ModelListener.class})
public class OrganizationModelListener
	extends BaseEntityModelListener<Organization> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return getOrganizationAttributeNames();
	}

	@Override
	public long[] getMembershipIds(User user) throws Exception {
		return user.getOrganizationIds();
	}

	@Override
	public String getModelClassName() {
		return Organization.class.getName();
	}

	@Override
	public void onAfterRemove(Organization organization)
		throws ModelListenerException {

		if (!analyticsConfigurationTracker.isActive() ||
			isExcluded(organization)) {

			return;
		}

		updateConfigurationProperties(
			organization.getCompanyId(), "syncedOrganizationIds",
			String.valueOf(organization.getOrganizationId()), null);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return _organizationLocalService.getActionableDynamicQuery();
	}

	@Override
	protected Organization getModel(long id) throws Exception {
		return _organizationLocalService.getOrganization(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "organizationId";
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}