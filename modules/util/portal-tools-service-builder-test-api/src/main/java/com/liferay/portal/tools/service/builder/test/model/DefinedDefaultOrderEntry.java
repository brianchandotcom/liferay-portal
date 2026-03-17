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
 * The extended model interface for the DefinedDefaultOrderEntry service. Represents a row in the &quot;DefinedDefaultOrderEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryImpl"
)
@ProviderType
public interface DefinedDefaultOrderEntry
	extends DefinedDefaultOrderEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DefinedDefaultOrderEntry, Long>
		DEFINED_DEFAULT_ORDER_ENTRY_ID_ACCESSOR =
			new Accessor<DefinedDefaultOrderEntry, Long>() {

				@Override
				public Long get(
					DefinedDefaultOrderEntry definedDefaultOrderEntry) {

					return definedDefaultOrderEntry.
						getDefinedDefaultOrderEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DefinedDefaultOrderEntry> getTypeClass() {
					return DefinedDefaultOrderEntry.class;
				}

			};

}
// SB-Hash:-1365404862