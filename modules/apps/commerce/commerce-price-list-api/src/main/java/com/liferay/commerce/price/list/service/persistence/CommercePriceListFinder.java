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

package com.liferay.commerce.price.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@ProviderType
public interface CommercePriceListFinder {

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByCommerceAccountAndChannelId(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByExpirationDate(
				java.util.Date expirationDate,
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByCommerceAccountId(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByCommerceAccountGroupIds(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByCommerceAccountGroupsAndChannelId(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByCommerceChannelId(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
			findByUnqualified(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

	public java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
			findByLowestPrice(
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.commerce.price.list.model.CommercePriceList>
						queryDefinition);

}