/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.order.model.impl;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItemFileEntry;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemFileEntryLocalServiceUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceVirtualOrderItemImpl
	extends CommerceVirtualOrderItemBaseImpl {

	@Override
	public CommerceOrderItem getCommerceOrderItem() throws PortalException {
		return CommerceOrderItemLocalServiceUtil.getCommerceOrderItem(
			getCommerceOrderItemId());
	}

	@Override
	public List<CommerceVirtualOrderItemFileEntry>
		getCommerceVirtualOrderItemFileEntries() {

		return CommerceVirtualOrderItemFileEntryLocalServiceUtil.
			getCommerceVirtualOrderItemFileEntries(
				getCommerceVirtualOrderItemId());
	}

	@Override
	public int getCommerceVirtualOrderItemFileEntriesCount() {
		return CommerceVirtualOrderItemFileEntryLocalServiceUtil.
			getCommerceVirtualOrderItemFileEntriesCount(
				getCommerceVirtualOrderItemId());
	}

	@Override
	public CommerceVirtualOrderItemFileEntry
			getCommerceVirtualOrderItemFileEntry(
				long commerceVirtualOrderItemFileEntryId)
		throws PortalException {

		return CommerceVirtualOrderItemFileEntryLocalServiceUtil.
			getCommerceVirtualOrderItemFileEntry(
				commerceVirtualOrderItemFileEntryId);
	}

}