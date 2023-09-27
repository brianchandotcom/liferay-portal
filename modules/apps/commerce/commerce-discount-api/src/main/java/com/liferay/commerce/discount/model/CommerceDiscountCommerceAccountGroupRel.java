/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceDiscountCommerceAccountGroupRel service. Represents a row in the &quot;CDiscountCAccountGroupRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelImpl"
)
@ProviderType
public interface CommerceDiscountCommerceAccountGroupRel
	extends CommerceDiscountCommerceAccountGroupRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.discount.model.impl.CommerceDiscountCommerceAccountGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountCommerceAccountGroupRel, Long>
		COMMERCE_DISCOUNT_COMMERCE_ACCOUNT_GROUP_REL_ID_ACCESSOR =
			new Accessor<CommerceDiscountCommerceAccountGroupRel, Long>() {

				@Override
				public Long get(
					CommerceDiscountCommerceAccountGroupRel
						commerceDiscountCommerceAccountGroupRel) {

					return commerceDiscountCommerceAccountGroupRel.
						getCommerceDiscountCommerceAccountGroupRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceDiscountCommerceAccountGroupRel>
					getTypeClass() {

					return CommerceDiscountCommerceAccountGroupRel.class;
				}

			};

	public com.liferay.account.model.AccountGroup getAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException;

}