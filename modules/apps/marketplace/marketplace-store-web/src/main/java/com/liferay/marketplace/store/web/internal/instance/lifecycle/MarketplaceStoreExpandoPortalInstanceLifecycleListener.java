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
		ExpandoTable table = _expandoTableLocalService.fetchTable(
			company.getCompanyId(),
			_classNameLocalService.getClassNameId(User.class.getName()), "MP");

		if (table != null) {
			return;
		}

		table = _expandoTableLocalService.addTable(
			company.getCompanyId(), User.class.getName(), "MP");

		_expandoColumnLocalService.addColumn(
			table.getTableId(), "accessSecret", ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			table.getTableId(), "accessToken", ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			table.getTableId(), "requestSecret", ExpandoColumnConstants.STRING);
		_expandoColumnLocalService.addColumn(
			table.getTableId(), "requestToken", ExpandoColumnConstants.STRING);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}