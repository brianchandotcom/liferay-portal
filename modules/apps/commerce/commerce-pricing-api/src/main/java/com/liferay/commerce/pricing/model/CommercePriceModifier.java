/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePriceModifier service. Represents a row in the &quot;CommercePriceModifier&quot; database table, with each column mapped to a property of this class.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.pricing.model.impl.CommercePriceModifierImpl"
)
@ProviderType
public interface CommercePriceModifier
	extends CommercePriceModifierModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.pricing.model.impl.CommercePriceModifierImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceModifier, Long>
		COMMERCE_PRICE_MODIFIER_ID_ACCESSOR =
			new Accessor<CommercePriceModifier, Long>() {

				@Override
				public Long get(CommercePriceModifier commercePriceModifier) {
					return commercePriceModifier.getCommercePriceModifierId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePriceModifier> getTypeClass() {
					return CommercePriceModifier.class;
				}

			};

}