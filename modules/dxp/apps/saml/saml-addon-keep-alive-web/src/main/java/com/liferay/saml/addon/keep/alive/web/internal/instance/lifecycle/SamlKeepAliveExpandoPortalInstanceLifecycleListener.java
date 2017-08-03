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

package com.liferay.saml.addon.keep.alive.web.internal.instance.lifecycle;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.saml.addon.keep.alive.web.internal.constants.SamlKeepAliveConstants;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SamlKeepAliveExpandoPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		addExpandoColumn(company.getCompanyId(), SamlIdpSpConnection.class);
		addExpandoColumn(company.getCompanyId(), SamlSpIdpConnection.class);
	}

	protected void addExpandoColumn(long companyId, Class<?> clazz)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Add field " + clazz.getName() + " for company " + companyId);
		}

		ExpandoTable expandoTable = null;

		try {
			expandoTable = _expandoTableLocalService.getDefaultTable(
				companyId, clazz.getName());
		}
		catch (NoSuchTableException nste) {
			if (_log.isDebugEnabled()) {
				_log.debug(nste, nste);
			}

			expandoTable = _expandoTableLocalService.addDefaultTable(
				companyId, clazz.getName());
		}

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(),
			SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);

		if (expandoColumn == null) {
			_expandoColumnLocalService.addColumn(
				expandoTable.getTableId(),
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL,
				ExpandoColumnConstants.STRING,
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlKeepAliveExpandoPortalInstanceLifecycleListener.class);

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}