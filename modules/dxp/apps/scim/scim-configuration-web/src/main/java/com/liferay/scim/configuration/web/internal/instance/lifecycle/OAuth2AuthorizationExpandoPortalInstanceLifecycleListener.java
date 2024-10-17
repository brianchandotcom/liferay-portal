/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.instance.lifecycle;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Istvan Sajtos
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class OAuth2AuthorizationExpandoPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			long classNameId = _classNameLocalService.getClassNameId(
				OAuth2Authorization.class.getName());

			ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
				company.getCompanyId(), classNameId,
				ExpandoTableConstants.DEFAULT_TABLE_NAME);

			if (expandoTable == null) {
				expandoTable = _expandoTableLocalService.addTable(
					company.getCompanyId(), classNameId,
					ExpandoTableConstants.DEFAULT_TABLE_NAME);
			}

			ExpandoColumn expandoColumn =
				_expandoColumnLocalService.fetchColumn(
					expandoTable.getTableId(), "lastNotificationDate");

			if (expandoColumn != null) {
				return;
			}

			_expandoColumnLocalService.addColumn(
				expandoTable.getTableId(), "lastNotificationDate",
				ExpandoColumnConstants.DATE);
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}