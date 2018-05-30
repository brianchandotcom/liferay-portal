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
 * @see com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl
 * @see com.liferay.commerce.product.model.impl.CPMeasurementUnitModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl")
@ProviderType
public interface CPMeasurementUnit extends CPMeasurementUnitModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPMeasurementUnitImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPMeasurementUnit, Long> CP_MEASUREMENT_UNIT_ID_ACCESSOR =
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