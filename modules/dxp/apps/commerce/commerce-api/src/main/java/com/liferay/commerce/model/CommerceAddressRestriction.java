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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceAddressRestriction service. Represents a row in the &quot;CommerceAddressRestriction&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionModel
 * @see com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl
 * @see com.liferay.commerce.model.impl.CommerceAddressRestrictionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl")
@ProviderType
public interface CommerceAddressRestriction
	extends CommerceAddressRestrictionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAddressRestriction, Long> COMMERCE_ADDRESS_RESTRICTION_ID_ACCESSOR =
		new Accessor<CommerceAddressRestriction, Long>() {
			@Override
			public Long get(
				CommerceAddressRestriction commerceAddressRestriction) {
				return commerceAddressRestriction.getCommerceAddressRestrictionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceAddressRestriction> getTypeClass() {
				return CommerceAddressRestriction.class;
			}
		};

	public CommerceCountry getCommerceCountry()
		throws com.liferay.portal.kernel.exception.PortalException;
}