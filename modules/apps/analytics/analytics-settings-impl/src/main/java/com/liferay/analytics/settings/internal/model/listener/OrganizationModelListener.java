/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class OrganizationModelListener
	extends BaseAnalyticsDXPEntityModelListener<Organization> {

	@Override
	public Class<?> getModelClass() {
		return Organization.class;
	}

	@Override
	public void onAfterRemove(Organization organization)
		throws ModelListenerException {

		super.onAfterRemove(organization);

		if (!analyticsConfigurationRegistry.isActive() ||
			!isTracked(organization)) {

			return;
		}

		updateConfigurationProperties(
			organization.getCompanyId(), "syncedOrganizationIds",
			String.valueOf(organization.getOrganizationId()), null);
	}

	@Override
	protected Organization getModel(Object classPK) {
		return _organizationLocalService.fetchOrganization((long)classPK);
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}