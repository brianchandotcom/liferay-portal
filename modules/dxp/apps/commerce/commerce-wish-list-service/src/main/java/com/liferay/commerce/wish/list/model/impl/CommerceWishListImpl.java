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

package com.liferay.commerce.wish.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceWishListImpl extends CommerceWishListBaseImpl {

	public CommerceWishListImpl() {
	}

	@Override
	public boolean isGuestWishList() throws PortalException {
		User user = UserLocalServiceUtil.getUser(getUserId());

		return user.isDefaultUser();
	}

}