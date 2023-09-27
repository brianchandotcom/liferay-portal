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
 * The extended model interface for the SegmentsEntryRole service. Represents a row in the &quot;SegmentsEntryRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRoleModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.segments.model.impl.SegmentsEntryRoleImpl"
)
@ProviderType
public interface SegmentsEntryRole
	extends PersistedModel, SegmentsEntryRoleModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.segments.model.impl.SegmentsEntryRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SegmentsEntryRole, Long>
		SEGMENTS_ENTRY_ROLE_ID_ACCESSOR =
			new Accessor<SegmentsEntryRole, Long>() {

				@Override
				public Long get(SegmentsEntryRole segmentsEntryRole) {
					return segmentsEntryRole.getSegmentsEntryRoleId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SegmentsEntryRole> getTypeClass() {
					return SegmentsEntryRole.class;
				}

			};

}