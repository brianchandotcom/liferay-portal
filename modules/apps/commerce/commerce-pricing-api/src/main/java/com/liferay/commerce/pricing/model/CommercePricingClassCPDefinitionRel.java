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
 * The extended model interface for the CommercePricingClassCPDefinitionRel service. Represents a row in the &quot;CPricingClassCPDefinitionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelImpl"
)
@ProviderType
public interface CommercePricingClassCPDefinitionRel
	extends CommercePricingClassCPDefinitionRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.pricing.model.impl.CommercePricingClassCPDefinitionRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePricingClassCPDefinitionRel, Long>
		COMMERCE_PRICING_CLASS_CP_DEFINITION_REL_ID_ACCESSOR =
			new Accessor<CommercePricingClassCPDefinitionRel, Long>() {

				@Override
				public Long get(
					CommercePricingClassCPDefinitionRel
						commercePricingClassCPDefinitionRel) {

					return commercePricingClassCPDefinitionRel.
						getCommercePricingClassCPDefinitionRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePricingClassCPDefinitionRel>
					getTypeClass() {

					return CommercePricingClassCPDefinitionRel.class;
				}

			};

	public CommercePricingClass getCommercePricingClass()
		throws com.liferay.portal.kernel.exception.PortalException;

}