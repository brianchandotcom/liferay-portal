/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SegmentsEntry service. Represents a row in the &quot;SegmentsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.segments.model.impl.SegmentsEntryImpl")
@ProviderType
public interface SegmentsEntry extends PersistedModel, SegmentsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsEntry, Long>
		SEGMENTS_ENTRY_ID_ACCESSOR = new Accessor<SegmentsEntry, Long>() {

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

	public com.liferay.segments.criteria.Criteria getCriteriaObj();

	public long[] getRoleIds();

}