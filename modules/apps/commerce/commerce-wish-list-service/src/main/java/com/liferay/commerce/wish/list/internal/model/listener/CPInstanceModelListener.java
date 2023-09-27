/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.internal.model.listener;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.wish.list.service.CommerceWishListItemLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = ModelListener.class)
public class CPInstanceModelListener extends BaseModelListener<CPInstance> {

	@Override
	public void onBeforeRemove(CPInstance cpInstance) {
		_commerceWishListItemLocalService.
			deleteCommerceWishListItemsByCPInstanceId(
				cpInstance.getCPInstanceId());
	}

	@Reference
	private CommerceWishListItemLocalService _commerceWishListItemLocalService;

}