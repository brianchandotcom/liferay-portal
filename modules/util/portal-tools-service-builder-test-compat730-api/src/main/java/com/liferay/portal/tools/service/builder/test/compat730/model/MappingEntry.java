/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat730.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MappingEntry service. Represents a row in the &quot;MappingEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat730.model.impl.MappingEntryImpl"
)
@ProviderType
public interface MappingEntry extends MappingEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat730.model.impl.MappingEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MappingEntry, Long> MAPPING_ENTRY_ID_ACCESSOR =
		new Accessor<MappingEntry, Long>() {

			@Override
			public Long get(MappingEntry mappingEntry) {
				return mappingEntry.getMappingEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MappingEntry> getTypeClass() {
				return MappingEntry.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:1435807401