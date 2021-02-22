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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountGroup;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccountGroup;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Marco Leo
 */
public class CommerceAccountGroupImpl extends CommerceAccountGroupBaseImpl {

	public static CommerceAccountGroup fromAccountGroup(
		AccountGroup accountGroup) {

		if (accountGroup == null) {
			return null;
		}

		CommerceAccountGroup commerceAccountGroup =
			new CommerceAccountGroupImpl();

		Map<String, BiConsumer<CommerceAccountGroup, Object>>
			attributeSetterBiConsumers =
				commerceAccountGroup.getAttributeSetterBiConsumers();

		Map<String, Object> modelAttributes = accountGroup.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			String key = entry.getKey();

			BiConsumer<CommerceAccountGroup, Object>
				commerceAccountGroupObjectBiConsumer =
					attributeSetterBiConsumers.get(key);

			if (commerceAccountGroupObjectBiConsumer != null) {
				Object value = entry.getValue();

				if (key.equals("type")) {
					value = toCommerceAccountGroupType((String)value);
				}

				commerceAccountGroupObjectBiConsumer.accept(
					commerceAccountGroup, value);
			}
		}

		commerceAccountGroup.setCommerceAccountGroupId(
			accountGroup.getAccountGroupId());
		commerceAccountGroup.setType(
			toCommerceAccountGroupType(accountGroup.getType()));
		commerceAccountGroup.setSystem(accountGroup.isDefaultAccountGroup());

		return commerceAccountGroup;
	}

	public static String toAccountGroupType(Integer type) {
		if (type != null) {
			if (type == CommerceAccountConstants.ACCOUNT_GROUP_TYPE_GUEST) {
				return AccountConstants.ACCOUNT_GROUP_TYPE_GUEST;
			}
			else if (type ==
						CommerceAccountConstants.ACCOUNT_GROUP_TYPE_DYNAMIC) {

				return AccountConstants.ACCOUNT_GROUP_TYPE_DYNAMIC;
			}
			else if (type ==
						CommerceAccountConstants.ACCOUNT_GROUP_TYPE_STATIC) {

				return AccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
			}
		}

		return AccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
	}

	public static int toCommerceAccountGroupType(String type) {
		if (type != null) {
			if (type.equals(AccountConstants.ACCOUNT_GROUP_TYPE_GUEST)) {
				return CommerceAccountConstants.ACCOUNT_GROUP_TYPE_GUEST;
			}
			else if (type.equals(AccountConstants.ACCOUNT_GROUP_TYPE_DYNAMIC)) {
				return CommerceAccountConstants.ACCOUNT_GROUP_TYPE_DYNAMIC;
			}
			else if (type.equals(AccountConstants.ACCOUNT_GROUP_TYPE_STATIC)) {
				return CommerceAccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
			}
		}

		return CommerceAccountConstants.ACCOUNT_GROUP_TYPE_STATIC;
	}

	public CommerceAccountGroupImpl() {
	}

}