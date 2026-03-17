/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ConvertNullEntry service. Represents a row in the &quot;ConvertNullEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ConvertNullEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat710.model.impl.ConvertNullEntryImpl"
)
@ProviderType
public interface ConvertNullEntry
	extends ConvertNullEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.ConvertNullEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ConvertNullEntry, Long>
		CONVERT_NULL_ENTRY_ID_ACCESSOR =
			new Accessor<ConvertNullEntry, Long>() {

				@Override
				public Long get(ConvertNullEntry convertNullEntry) {
					return convertNullEntry.getConvertNullEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ConvertNullEntry> getTypeClass() {
					return ConvertNullEntry.class;
				}

			};

}
// SB-Hash:381570636