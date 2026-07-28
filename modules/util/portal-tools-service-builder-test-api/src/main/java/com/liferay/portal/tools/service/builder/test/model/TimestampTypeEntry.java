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
 * The extended model interface for the TimestampTypeEntry service. Represents a row in the &quot;TimestampTypeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TimestampTypeEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryImpl"
)
@ProviderType
public interface TimestampTypeEntry
	extends PersistedModel, TimestampTypeEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TimestampTypeEntry, Long>
		TIMESTAMP_TYPE_ENTRY_ID_ACCESSOR =
			new Accessor<TimestampTypeEntry, Long>() {

				@Override
				public Long get(TimestampTypeEntry timestampTypeEntry) {
					return timestampTypeEntry.getTimestampTypeEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<TimestampTypeEntry> getTypeClass() {
					return TimestampTypeEntry.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1225353086