/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MVCCEntry service. Represents a row in the &quot;MVCCEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MVCCEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat720.model.impl.MVCCEntryImpl"
)
@ProviderType
public interface MVCCEntry extends MVCCEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat720.model.impl.MVCCEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MVCCEntry, Long> MVCC_ENTRY_ID_ACCESSOR =
		new Accessor<MVCCEntry, Long>() {

			@Override
			public Long get(MVCCEntry mvccEntry) {
				return mvccEntry.getMvccEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MVCCEntry> getTypeClass() {
				return MVCCEntry.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:-615536038