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
 * The extended model interface for the CommerceAvailabilityEstimate service. Represents a row in the &quot;CommerceAvailabilityEstimate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceAvailabilityEstimateImpl"
)
@ProviderType
public interface CommerceAvailabilityEstimate
	extends CommerceAvailabilityEstimateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceAvailabilityEstimateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAvailabilityEstimate, Long>
		COMMERCE_AVAILABILITY_ESTIMATE_ID_ACCESSOR =
			new Accessor<CommerceAvailabilityEstimate, Long>() {

				@Override
				public Long get(
					CommerceAvailabilityEstimate commerceAvailabilityEstimate) {

					return commerceAvailabilityEstimate.
						getCommerceAvailabilityEstimateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAvailabilityEstimate> getTypeClass() {
					return CommerceAvailabilityEstimate.class;
				}

			};

}