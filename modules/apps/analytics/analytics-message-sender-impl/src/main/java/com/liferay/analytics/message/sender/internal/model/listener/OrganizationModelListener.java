/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class OrganizationModelListener extends BaseModelListener<Organization> {

	@Override
	public void onAfterRemove(Organization organization)
		throws ModelListenerException {

		if (!analyticsConfigurationRegistry.isActive() ||
			isExcluded(organization)) {

			return;
		}

		updateConfigurationProperties(
			organization.getCompanyId(), "syncedOrganizationIds",
			String.valueOf(organization.getOrganizationId()), null);
	}

	@Override
	protected AnalyticsEntityModel<Organization> getAnalyticsEntityModel() {
		return _organizationAnalyticsEntityModel;
	}

	@Override
	protected Organization getModel(long id) throws Exception {
		return _organizationLocalService.getOrganization(id);
	}

	@Reference(target = "(analytics.entity.model.type=organization)")
	private AnalyticsEntityModel<Organization>
		_organizationAnalyticsEntityModel;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}