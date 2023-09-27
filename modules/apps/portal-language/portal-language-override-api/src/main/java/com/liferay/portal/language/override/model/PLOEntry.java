/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PLOEntry service. Represents a row in the &quot;PLOEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Drew Brokke
 * @see PLOEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.language.override.model.impl.PLOEntryImpl"
)
@ProviderType
public interface PLOEntry extends PersistedModel, PLOEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.language.override.model.impl.PLOEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PLOEntry, Long> PLO_ENTRY_ID_ACCESSOR =
		new Accessor<PLOEntry, Long>() {

			@Override
			public Long get(PLOEntry ploEntry) {
				return ploEntry.getPloEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PLOEntry> getTypeClass() {
				return PLOEntry.class;
			}

		};

}