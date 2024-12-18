/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalServiceUtil;
import com.liferay.commerce.product.service.CPConfigurationListLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Marco Leo
 */
public class CPConfigurationListImpl extends CPConfigurationListBaseImpl {

	@Override
	public CommerceCatalog fetchCommerceCatalog() {
		return CommerceCatalogLocalServiceUtil.fetchCommerceCatalogByGroupId(
			getGroupId());
	}

	@Override
	public CPConfigurationEntry fetchTemplateCPConfigurationEntry() {
		return CPConfigurationEntryLocalServiceUtil.fetchCPConfigurationEntry(
			PortalUtil.getClassNameId(CPConfigurationList.class),
			getCPConfigurationListId(), getCPConfigurationListId());
	}

	public CPConfigurationList getParentCPConfigurationList()
		throws PortalException {

		if (getParentCPConfigurationListId() == 0) {
			return null;
		}

		return CPConfigurationListLocalServiceUtil.getCPConfigurationList(
			getParentCPConfigurationListId());
	}

	@Override
	public long getTemplateCPConfigurationEntryId() {
		CPConfigurationEntry cpConfigurationEntry =
			fetchTemplateCPConfigurationEntry();

		if (cpConfigurationEntry == null) {
			return 0;
		}

		return cpConfigurationEntry.getCPConfigurationEntryId();
	}

}