/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ChangesetEntry service. Represents a row in the &quot;ChangesetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.changeset.model.impl.ChangesetEntryImpl")
@ProviderType
public interface ChangesetEntry extends ChangesetEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.changeset.model.impl.ChangesetEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ChangesetEntry, Long>
		CHANGESET_ENTRY_ID_ACCESSOR = new Accessor<ChangesetEntry, Long>() {

			@Override
			public Long get(ChangesetEntry changesetEntry) {
				return changesetEntry.getChangesetEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ChangesetEntry> getTypeClass() {
				return ChangesetEntry.class;
			}

		};

}