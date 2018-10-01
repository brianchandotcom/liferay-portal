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

package com.liferay.segments.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SegmentsEntry service. Represents a row in the &quot;SegmentsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryModel
 * @see com.liferay.segments.model.impl.SegmentsEntryImpl
 * @see com.liferay.segments.model.impl.SegmentsEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.segments.model.impl.SegmentsEntryImpl")
@ProviderType
public interface SegmentsEntry extends SegmentsEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.segments.model.impl.SegmentsEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsEntry, Long> SEGMENTS_ENTRY_ID_ACCESSOR =
		new Accessor<SegmentsEntry, Long>() {
			@Override
			public Long get(SegmentsEntry segmentsEntry) {
				return segmentsEntry.getSegmentsEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SegmentsEntry> getTypeClass() {
				return SegmentsEntry.class;
			}
		};
}