/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.internal.model.listener;

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(service = ModelListener.class)
public class CommerceChannelModelListener
	extends BaseModelListener<CommerceChannel> {

	@Override
	public void onBeforeRemove(CommerceChannel commerceChannel) {
		try {
			_commerceTaxMethodLocalService.deleteCommerceTaxMethods(
				commerceChannel.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelModelListener.class);

	@Reference
	private CommerceTaxMethodLocalService _commerceTaxMethodLocalService;

}