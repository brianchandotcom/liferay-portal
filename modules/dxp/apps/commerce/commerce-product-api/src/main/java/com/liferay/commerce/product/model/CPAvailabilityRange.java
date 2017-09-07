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
 * The extended model interface for the CPAvailabilityRange service. Represents a row in the &quot;CPAvailabilityRange&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPAvailabilityRangeModel
 * @see com.liferay.commerce.product.model.impl.CPAvailabilityRangeImpl
 * @see com.liferay.commerce.product.model.impl.CPAvailabilityRangeModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPAvailabilityRangeImpl")
@ProviderType
public interface CPAvailabilityRange extends CPAvailabilityRangeModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPAvailabilityRangeImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPAvailabilityRange, Long> CP_AVAILABILITY_RANGE_ID_ACCESSOR =
		new Accessor<CPAvailabilityRange, Long>() {
			@Override
			public Long get(CPAvailabilityRange cpAvailabilityRange) {
				return cpAvailabilityRange.getCPAvailabilityRangeId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPAvailabilityRange> getTypeClass() {
				return CPAvailabilityRange.class;
			}
		};
}