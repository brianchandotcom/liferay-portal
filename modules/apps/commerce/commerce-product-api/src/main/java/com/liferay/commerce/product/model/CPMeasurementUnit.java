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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

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