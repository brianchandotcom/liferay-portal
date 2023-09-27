/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPMeasurementUnit service. Represents a row in the &quot;CPMeasurementUnit&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPMeasurementUnitModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl"
)
@ProviderType
public interface CPMeasurementUnit
	extends CPMeasurementUnitModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPMeasurementUnit, Long>
		CP_MEASUREMENT_UNIT_ID_ACCESSOR =
			new Accessor<CPMeasurementUnit, Long>() {

				@Override
				public Long get(CPMeasurementUnit cpMeasurementUnit) {
					return cpMeasurementUnit.getCPMeasurementUnitId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPMeasurementUnit> getTypeClass() {
					return CPMeasurementUnit.class;
				}

			};

}