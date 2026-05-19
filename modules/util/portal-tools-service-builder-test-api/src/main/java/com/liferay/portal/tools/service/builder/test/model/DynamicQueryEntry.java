/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DynamicQueryEntry service. Represents a row in the &quot;DynamicQueryEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DynamicQueryEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.DynamicQueryEntryImpl"
)
@ProviderType
public interface DynamicQueryEntry
	extends DynamicQueryEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.DynamicQueryEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DynamicQueryEntry, Long>
		DYNAMIC_QUERY_ENTRY_ID_ACCESSOR =
			new Accessor<DynamicQueryEntry, Long>() {

				@Override
				public Long get(DynamicQueryEntry dynamicQueryEntry) {
					return dynamicQueryEntry.getDynamicQueryEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DynamicQueryEntry> getTypeClass() {
					return DynamicQueryEntry.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:1779554138