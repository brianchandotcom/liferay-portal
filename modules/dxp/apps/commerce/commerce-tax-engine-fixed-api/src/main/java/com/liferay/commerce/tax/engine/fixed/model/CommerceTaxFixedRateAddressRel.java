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

package com.liferay.commerce.tax.engine.fixed.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceTaxFixedRateAddressRel service. Represents a row in the &quot;CommerceTaxFixedRateAddressRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelModel
 * @see com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl
 * @see com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl")
@ProviderType
public interface CommerceTaxFixedRateAddressRel
	extends CommerceTaxFixedRateAddressRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxFixedRateAddressRel, Long> COMMERCE_TAX_FIXED_RATE_ADDRESS_REL_ID_ACCESSOR =
		new Accessor<CommerceTaxFixedRateAddressRel, Long>() {
			@Override
			public Long get(
				CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel) {
				return commerceTaxFixedRateAddressRel.getCommerceTaxFixedRateAddressRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceTaxFixedRateAddressRel> getTypeClass() {
				return CommerceTaxFixedRateAddressRel.class;
			}
		};

	public com.liferay.commerce.model.CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceRegion getCommerceRegion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory()
		throws com.liferay.portal.kernel.exception.PortalException;
}