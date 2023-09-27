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
 * The extended model interface for the SegmentsEntryRel service. Represents a row in the &quot;SegmentsEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRelModel
 * @generated
 */
@ImplementationClassName("com.liferay.segments.model.impl.SegmentsEntryRelImpl")
@ProviderType
public interface SegmentsEntryRel
	extends PersistedModel, SegmentsEntryRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsEntryRel, Long>
		SEGMENTS_ENTRY_REL_ID_ACCESSOR =
			new Accessor<SegmentsEntryRel, Long>() {

				@Override
				public Long get(SegmentsEntryRel segmentsEntryRel) {
					return segmentsEntryRel.getSegmentsEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SegmentsEntryRel> getTypeClass() {
					return SegmentsEntryRel.class;
				}

			};

}