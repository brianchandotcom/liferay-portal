/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.revert.schema.version.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RSVEntry service. Represents a row in the &quot;RSVEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RSVEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.revert.schema.version.model.impl.RSVEntryImpl"
)
@ProviderType
public interface RSVEntry extends PersistedModel, RSVEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.revert.schema.version.model.impl.RSVEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RSVEntry, Long> RSV_ENTRY_ID_ACCESSOR =
		new Accessor<RSVEntry, Long>() {

			@Override
			public Long get(RSVEntry rsvEntry) {
				return rsvEntry.getRsvEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RSVEntry> getTypeClass() {
				return RSVEntry.class;
			}

		};

}