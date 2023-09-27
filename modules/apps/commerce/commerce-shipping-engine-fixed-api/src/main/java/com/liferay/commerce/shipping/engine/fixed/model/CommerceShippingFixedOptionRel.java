/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceShippingFixedOptionRel service. Represents a row in the &quot;CShippingFixedOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl"
)
@ProviderType
public interface CommerceShippingFixedOptionRel
	extends CommerceShippingFixedOptionRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShippingFixedOptionRel, Long>
		COMMERCE_SHIPPING_FIXED_OPTION_REL_ID_ACCESSOR =
			new Accessor<CommerceShippingFixedOptionRel, Long>() {

				@Override
				public Long get(
					CommerceShippingFixedOptionRel
						commerceShippingFixedOptionRel) {

					return commerceShippingFixedOptionRel.
						getCommerceShippingFixedOptionRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceShippingFixedOptionRel> getTypeClass() {
					return CommerceShippingFixedOptionRel.class;
				}

			};

	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouse
			getCommerceInventoryWarehouse()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceShippingFixedOption getCommerceShippingFixedOption()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceShippingMethod
			getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.model.Country getCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.model.Region getRegion()
		throws com.liferay.portal.kernel.exception.PortalException;

}