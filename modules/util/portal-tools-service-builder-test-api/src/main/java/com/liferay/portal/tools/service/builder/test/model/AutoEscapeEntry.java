/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AutoEscapeEntry service. Represents a row in the &quot;AutoEscapeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AutoEscapeEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.AutoEscapeEntryImpl"
)
@ProviderType
public interface AutoEscapeEntry extends AutoEscapeEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.AutoEscapeEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AutoEscapeEntry, Long>
		AUTO_ESCAPE_ENTRY_ID_ACCESSOR = new Accessor<AutoEscapeEntry, Long>() {

			@Override
			public Long get(AutoEscapeEntry autoEscapeEntry) {
				return autoEscapeEntry.getAutoEscapeEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AutoEscapeEntry> getTypeClass() {
				return AutoEscapeEntry.class;
			}

		};

}