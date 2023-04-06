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

package com.liferay.commerce.price.list.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePriceListCommerceAccountGroupRel service. Represents a row in the &quot;CPLCommerceGroupAccountRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelImpl"
)
@ProviderType
public interface CommercePriceListCommerceAccountGroupRel
	extends CommercePriceListCommerceAccountGroupRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceListCommerceAccountGroupRel, Long>
		COMMERCE_PRICE_LIST_COMMERCE_ACCOUNT_GROUP_REL_ID_ACCESSOR =
			new Accessor<CommercePriceListCommerceAccountGroupRel, Long>() {

				@Override
				public Long get(
					CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel) {

					return commercePriceListCommerceAccountGroupRel.
						getCommercePriceListCommerceAccountGroupRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePriceListCommerceAccountGroupRel>
					getTypeClass() {

					return CommercePriceListCommerceAccountGroupRel.class;
				}

			};

	public com.liferay.account.model.AccountGroup getAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException;

}