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

import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountOrganizationRelImpl
	extends CommerceAccountOrganizationRelBaseImpl {

	public static CommerceAccountOrganizationRelImpl
		fromAccountEntryOrganizationRel(
			AccountEntryOrganizationRel accountEntryOrganizationRel) {

		if (accountEntryOrganizationRel == null) {
			return null;
		}

		CommerceAccountOrganizationRelImpl commerceAccountOrganizationRelImpl =
			new CommerceAccountOrganizationRelImpl();

		Map<String, BiConsumer<CommerceAccountOrganizationRel, Object>>
			attributeSetterBiConsumers =
				commerceAccountOrganizationRelImpl.
					getAttributeSetterBiConsumers();

		Map<String, Object> modelAttributes =
			accountEntryOrganizationRel.getModelAttributes();

		for (Map.Entry<String, Object> entry : modelAttributes.entrySet()) {
			BiConsumer<CommerceAccountOrganizationRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					entry.getKey());

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					commerceAccountOrganizationRelImpl, entry.getValue());
			}
		}

		commerceAccountOrganizationRelImpl.setCommerceAccountId(
			accountEntryOrganizationRel.getAccountEntryId());

		return commerceAccountOrganizationRelImpl;
	}

	public CommerceAccountOrganizationRelImpl() {
	}

	@Override
	public Organization getOrganization() throws PortalException {
		return OrganizationLocalServiceUtil.getOrganization(
			getOrganizationId());
	}

}