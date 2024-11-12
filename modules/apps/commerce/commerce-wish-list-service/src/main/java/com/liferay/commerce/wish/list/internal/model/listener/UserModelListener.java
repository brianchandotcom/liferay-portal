/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.internal.model.listener;

import com.liferay.commerce.wish.list.service.CommerceWishListLocalService;
import com.liferay.commerce.wish.list.util.CommerceWishListThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeRemove(User user) {
		try {
			CommerceWishListThreadLocal.setDefaultWishListDeletable(true);
			_commerceWishListLocalService.deleteCommerceWishListsByUserId(
				user.getUserId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
		finally {
			CommerceWishListThreadLocal.setDefaultWishListDeletable(false);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	@Reference
	private CommerceWishListLocalService _commerceWishListLocalService;

}