/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SavedContentEntry service. Represents a row in the &quot;SavedContentEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saved.content.model.impl.SavedContentEntryImpl"
)
@ProviderType
public interface SavedContentEntry
	extends PersistedModel, SavedContentEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saved.content.model.impl.SavedContentEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SavedContentEntry, Long>
		SAVED_CONTENT_ENTRY_ID_ACCESSOR =
			new Accessor<SavedContentEntry, Long>() {

				@Override
				public Long get(SavedContentEntry savedContentEntry) {
					return savedContentEntry.getSavedContentEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SavedContentEntry> getTypeClass() {
					return SavedContentEntry.class;
				}

			};

}