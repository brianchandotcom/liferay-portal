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

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class OrganizationModelListener
	extends BaseEntityModelListener<Organization> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	public void onAfterRemove(Organization organization)
		throws ModelListenerException {

		updateConfigurationProperties(
			organization.getCompanyId(), "syncedOrganizationIds",
			String.valueOf(organization.getOrganizationId()), null);
	}

	@Override
	protected Organization getOriginalModel(Organization organization)
		throws Exception {

		return _organizationLocalService.getOrganization(
			organization.getOrganizationId());
	}

	@Override
	protected String getPrimaryKeyName() {
		return "organizationId";
	}

	@Override
	protected boolean isExcluded(Organization organization) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				organization.getCompanyId());

		if (!ArrayUtil.contains(
				analyticsConfiguration.syncedOrganizationIds(),
				organization.getOrganizationId())) {

			return true;
		}

		return false;
	}

	private static final List<String> _attributeNames = Arrays.asList(
		"comments", "companyId", "countryId", "createDate",
		"externalReferenceCode", "logoId", "name", "parentOrganizationId",
		"recursable", "regionId", "statusId", "treePath", "type", "userId",
		"userName", "uuid");

	@Reference
	private OrganizationLocalService _organizationLocalService;

}