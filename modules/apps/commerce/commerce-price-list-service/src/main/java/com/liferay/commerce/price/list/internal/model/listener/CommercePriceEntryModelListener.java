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

package com.liferay.commerce.price.list.internal.model.listener;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(enabled = false, immediate = true, service = ModelListener.class)
public class CommercePriceEntryModelListener
	extends BaseModelListener<CommercePriceEntry> {

	@Override
	public void onBeforeRemove(CommercePriceEntry commercePriceEntry) {
		try {
			_commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntries(
				commercePriceEntry.getCommercePriceEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryModelListener.class);

	@Reference
	private CommerceTierPriceEntryLocalService
		_commerceTierPriceEntryLocalService;

}