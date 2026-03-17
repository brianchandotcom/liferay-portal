/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UndefinedDefaultOrderEntry service. Represents a row in the &quot;UndefinedDefaultOrderEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UndefinedDefaultOrderEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryImpl"
)
@ProviderType
public interface UndefinedDefaultOrderEntry
	extends PersistedModel, UndefinedDefaultOrderEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.UndefinedDefaultOrderEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UndefinedDefaultOrderEntry, Long>
		UNDEFINED_DEFAULT_ORDER_ENTRY_ID_ACCESSOR =
			new Accessor<UndefinedDefaultOrderEntry, Long>() {

				@Override
				public Long get(
					UndefinedDefaultOrderEntry undefinedDefaultOrderEntry) {

					return undefinedDefaultOrderEntry.
						getUndefinedDefaultOrderEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<UndefinedDefaultOrderEntry> getTypeClass() {
					return UndefinedDefaultOrderEntry.class;
				}

			};

}
// SB-Hash:2062157580