/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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