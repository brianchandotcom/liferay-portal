/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.filter;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.FDSFilterCET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseClientExtensionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOMIZED,
	service = FDSFilter.class
)
public class CustomizedClientExtensionFDSFilter
	extends BaseClientExtensionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public String getId() {
		return "id";
	}

	@Override
	public String getLabel() {
		return "Client Extension";
	}

	@Override
	public String getModuleURL() {
		String moduleURL = null;

		try {
			List<FDSFilterCET> fdsFilterCETs = (List)_cetManager.getCETs(
				CompanyThreadLocal.getCompanyId(), null,
				ClientExtensionEntryConstants.TYPE_FDS_FILTER,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null);

			// Use the UI client extension if available

			for (FDSFilterCET fdsFilterCET : fdsFilterCETs) {
				if (!fdsFilterCET.isReadOnly()) {
					moduleURL = fdsFilterCET.getURL();

					break;
				}
			}

			// Use the workspace client extension if available

			if (moduleURL == null) {
				for (FDSFilterCET fdsFilterCET : fdsFilterCETs) {
					if (Objects.equals(
							fdsFilterCET.getExternalReferenceCode(),
							"LXC:liferay-sample-fds-filter")) {

						moduleURL = fdsFilterCET.getURL();

						break;
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to check if client extension is deployed", exception);
		}

		return moduleURL;
	}

	@Override
	public boolean isEnabled() {
		if (getModuleURL() != null) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomizedClientExtensionFDSFilter.class);

	@Reference
	private CETManager _cetManager;

}