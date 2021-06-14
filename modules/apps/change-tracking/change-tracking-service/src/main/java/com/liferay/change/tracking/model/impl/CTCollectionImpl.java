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

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public class CTCollectionImpl extends CTCollectionBaseImpl {

	@Override
	public String getUserName() {
		User user = UserLocalServiceUtil.fetchUser(getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getFullName();
	}

	@Override
	public boolean isProduction() {
		if (CTConstants.CT_COLLECTION_ID_PRODUCTION == getCtCollectionId()) {
			return true;
		}

		return false;
	}

}