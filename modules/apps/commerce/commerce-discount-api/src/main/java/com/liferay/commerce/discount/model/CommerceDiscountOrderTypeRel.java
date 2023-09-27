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
 * The extended model interface for the CommerceDiscountOrderTypeRel service. Represents a row in the &quot;CommerceDiscountOrderTypeRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelImpl"
)
@ProviderType
public interface CommerceDiscountOrderTypeRel
	extends CommerceDiscountOrderTypeRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceDiscountOrderTypeRel, Long>
		COMMERCE_DISCOUNT_ORDER_TYPE_REL_ID_ACCESSOR =
			new Accessor<CommerceDiscountOrderTypeRel, Long>() {

				@Override
				public Long get(
					CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

					return commerceDiscountOrderTypeRel.
						getCommerceDiscountOrderTypeRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceDiscountOrderTypeRel> getTypeClass() {
					return CommerceDiscountOrderTypeRel.class;
				}

			};

	public CommerceDiscount getCommerceDiscount()
		throws com.liferay.portal.kernel.exception.PortalException;

}