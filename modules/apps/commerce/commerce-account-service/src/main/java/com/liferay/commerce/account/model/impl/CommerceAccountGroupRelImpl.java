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

package com.liferay.commerce.account.model.impl;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupRelImpl
	extends CommerceAccountGroupRelBaseImpl {

	public CommerceAccountGroupRelImpl() {
	}

	@Override
	public CommerceAccountGroup getCommerceAccountGroup()
		throws PortalException {

		return CommerceAccountGroupLocalServiceUtil.getCommerceAccountGroup(
			getCommerceAccountGroupId());
	}

}