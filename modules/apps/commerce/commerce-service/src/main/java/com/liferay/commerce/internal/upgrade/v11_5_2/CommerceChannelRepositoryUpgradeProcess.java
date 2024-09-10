/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_5_2;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Stefano Motta
 */
public class CommerceChannelRepositoryUpgradeProcess extends UpgradeProcess {

	public CommerceChannelRepositoryUpgradeProcess(
		CommerceChannelLocalService commerceChannelLocalService) {

		_commerceChannelLocalService = commerceChannelLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<CommerceChannel> commerceChannels =
			_commerceChannelLocalService.getCommerceChannels(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceChannel commerceChannel : commerceChannels) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setCompanyId(commerceChannel.getCompanyId());
			serviceContext.setUserId(commerceChannel.getUserId());

			PortletFileRepositoryUtil.addPortletRepository(
				commerceChannel.getGroupId(),
				CommerceConstants.SERVICE_NAME_COMMERCE_ORDER, serviceContext);
		}
	}

	private final CommerceChannelLocalService _commerceChannelLocalService;

}