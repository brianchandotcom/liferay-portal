/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWishListImpl extends CommerceWishListBaseImpl {

	@Override
	public boolean isGuestWishList() throws PortalException {
		User user = UserLocalServiceUtil.getUser(getUserId());

		return user.isGuestUser();
	}

}