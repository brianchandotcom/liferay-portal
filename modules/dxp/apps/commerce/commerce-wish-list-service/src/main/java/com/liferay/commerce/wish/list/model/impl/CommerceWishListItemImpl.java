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

package com.liferay.commerce.wish.list.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CommerceWishListItemImpl extends CommerceWishListItemBaseImpl {

	public CommerceWishListItemImpl() {
	}

	@Override
	public CPInstance fetchCPInstance() throws PortalException {
		long cpInstanceId = getCPInstanceId();

		if (cpInstanceId > 0) {
			return CPInstanceLocalServiceUtil.getCPInstance(cpInstanceId);
		}

		return null;
	}

	@Override
	public CommerceWishList getCommerceWishList() throws PortalException {
		return CommerceWishListLocalServiceUtil.getCommerceWishList(
			getCommerceWishListId());
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public boolean isIgnoreSKUCombinations() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();
		CPInstance cpInstance = fetchCPInstance();

		if (cpDefinition.isIgnoreSKUCombinations() || (cpInstance != null)) {
			return true;
		}

		return false;
	}

}