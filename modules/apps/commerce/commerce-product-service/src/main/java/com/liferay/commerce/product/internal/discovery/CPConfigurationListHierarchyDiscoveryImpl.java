/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.discovery;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.product.constants.CPConfigurationListConstants;
import com.liferay.commerce.product.discovery.CPConfigurationListDiscovery;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = CPConfigurationListDiscovery.class)
public class CPConfigurationListHierarchyDiscoveryImpl
	implements CPConfigurationListDiscovery {

	@Override
	public CPConfigurationList getCPConfigurationList(
			long companyId, long groupId, long accountEntryId,
			long commerceChannelId, long commerceOrderTypeId)
		throws PortalException {

		long[] accountGroupIds = _accountGroupLocalService.getAccountGroupIds(
			accountEntryId);

		List<CPConfigurationList> cpConfigurationLists =
			_cpConfigurationListLocalService.getCPConfigurationLists(
				companyId, groupId, accountEntryId, accountGroupIds,
				commerceChannelId, commerceOrderTypeId);

		if (ListUtil.isEmpty(cpConfigurationLists)) {
			return _cpConfigurationListLocalService.
				getMasterCPConfigurationList(groupId);
		}

		return cpConfigurationLists.get(0);
	}

	@Override
	public String getCPConfigurationListDiscoveryKey() {
		return CPConfigurationListConstants.ORDER_BY_HIERARCHY;
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private CPConfigurationListLocalService _cpConfigurationListLocalService;

}