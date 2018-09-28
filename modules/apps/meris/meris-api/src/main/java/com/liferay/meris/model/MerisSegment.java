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

package com.liferay.meris.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the MerisSegment service. Represents a row in the &quot;MerisSegment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see MerisSegmentModel
 * @see com.liferay.meris.model.impl.MerisSegmentImpl
 * @see com.liferay.meris.model.impl.MerisSegmentModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.meris.model.impl.MerisSegmentImpl")
@ProviderType
public interface MerisSegment extends MerisSegmentModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.meris.model.impl.MerisSegmentImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MerisSegment, Long> MERIS_SEGMENT_ID_ACCESSOR = new Accessor<MerisSegment, Long>() {
			@Override
			public Long get(MerisSegment merisSegment) {
				return merisSegment.getMerisSegmentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MerisSegment> getTypeClass() {
				return MerisSegment.class;
			}
		};
}