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