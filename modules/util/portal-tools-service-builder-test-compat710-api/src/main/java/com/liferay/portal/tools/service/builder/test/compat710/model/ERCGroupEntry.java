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
 * The extended model interface for the ERCGroupEntry service. Represents a row in the &quot;ERCGroupEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat710.model.impl.ERCGroupEntryImpl"
)
@ProviderType
public interface ERCGroupEntry extends ERCGroupEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.ERCGroupEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ERCGroupEntry, Long>
		ERC_GROUP_ENTRY_ID_ACCESSOR = new Accessor<ERCGroupEntry, Long>() {

			@Override
			public Long get(ERCGroupEntry ercGroupEntry) {
				return ercGroupEntry.getErcGroupEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ERCGroupEntry> getTypeClass() {
				return ERCGroupEntry.class;
			}

		};

}
// SB-Hash:-840431008