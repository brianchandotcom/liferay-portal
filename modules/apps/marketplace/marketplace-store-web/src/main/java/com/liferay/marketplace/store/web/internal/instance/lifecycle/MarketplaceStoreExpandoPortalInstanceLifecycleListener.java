/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.store.web.internal.instance.lifecycle;

import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class MarketplaceStoreExpandoPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			company.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()), "MP");

		if (expandoTable != null) {
			return;
		}

		expandoTable = _expandoTableLocalService.addTable(
			company.getCompanyId(), User.class.getName(), "MP");

		_expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "accessSecret",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "accessToken",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "requestSecret",
			ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), "requestToken",
			ExpandoColumnConstants.STRING);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}