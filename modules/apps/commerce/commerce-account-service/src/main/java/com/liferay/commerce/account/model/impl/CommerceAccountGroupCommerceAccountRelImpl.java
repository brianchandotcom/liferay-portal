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

import com.liferay.account.model.AccountGroupRel;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountGroupCommerceAccountRelImpl
	extends CommerceAccountGroupCommerceAccountRelBaseImpl {

	public static CommerceAccountGroupCommerceAccountRel fromAccountGroupRel(
		AccountGroupRel accountGroupRel) {

		if (accountGroupRel == null) {
			return null;
		}

		CommerceAccountGroupCommerceAccountRel commerceAccountGroupRel =
			new CommerceAccountGroupCommerceAccountRelImpl();

		Map<String, BiConsumer<CommerceAccountGroupCommerceAccountRel, Object>>
			attributeSetterBiConsumers =
				commerceAccountGroupRel.getAttributeSetterBiConsumers();

		Map<String, Object> modelAttributes =
			accountGroupRel.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			BiConsumer<CommerceAccountGroupCommerceAccountRel, Object>
				commerceAccountGroupRelObjectBiConsumer =
					attributeSetterBiConsumers.get(entry.getKey());

			if (commerceAccountGroupRelObjectBiConsumer != null) {
				commerceAccountGroupRelObjectBiConsumer.accept(
					commerceAccountGroupRel, entry.getValue());
			}
		}

		commerceAccountGroupRel.setCommerceAccountGroupCommerceAccountRelId(
			accountGroupRel.getAccountGroupRelId());
		commerceAccountGroupRel.setCommerceAccountGroupId(
			accountGroupRel.getAccountGroupId());

		return commerceAccountGroupRel;
	}

	@Override
	public CommerceAccount getCommerceAccount() throws PortalException {
		return CommerceAccountLocalServiceUtil.getCommerceAccount(
			getCommerceAccountId());
	}

}