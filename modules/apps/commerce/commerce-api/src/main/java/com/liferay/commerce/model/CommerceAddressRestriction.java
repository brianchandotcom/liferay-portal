/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceAddressRestriction service. Represents a row in the &quot;CommerceAddressRestriction&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl"
)
@ProviderType
public interface CommerceAddressRestriction
	extends CommerceAddressRestrictionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceAddressRestrictionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAddressRestriction, Long>
		COMMERCE_ADDRESS_RESTRICTION_ID_ACCESSOR =
			new Accessor<CommerceAddressRestriction, Long>() {

				@Override
				public Long get(
					CommerceAddressRestriction commerceAddressRestriction) {

					return commerceAddressRestriction.
						getCommerceAddressRestrictionId();
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

	public com.liferay.portal.kernel.model.Country getCountry()
		throws com.liferay.portal.kernel.exception.PortalException;

}