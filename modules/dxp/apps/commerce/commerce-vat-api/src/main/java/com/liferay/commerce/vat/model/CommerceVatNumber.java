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

package com.liferay.commerce.vat.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceVatNumber service. Represents a row in the &quot;CommerceVatNumber&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberModel
 * @see com.liferay.commerce.vat.model.impl.CommerceVatNumberImpl
 * @see com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.vat.model.impl.CommerceVatNumberImpl")
@ProviderType
public interface CommerceVatNumber extends CommerceVatNumberModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceVatNumber, Long> COMMERCE_VAT_NUMBER_ID_ACCESSOR =
		new Accessor<CommerceVatNumber, Long>() {
			@Override
			public Long get(CommerceVatNumber commerceVatNumber) {
				return commerceVatNumber.getCommerceVatNumberId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceVatNumber> getTypeClass() {
				return CommerceVatNumber.class;
			}
		};
}