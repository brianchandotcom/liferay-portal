/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SharingEntry service. Represents a row in the &quot;SharingEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.sharing.model.impl.SharingEntryImpl")
@ProviderType
public interface SharingEntry extends PersistedModel, SharingEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.sharing.model.impl.SharingEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SharingEntry, Long> SHARING_ENTRY_ID_ACCESSOR =
		new Accessor<SharingEntry, Long>() {

			@Override
			public Long get(SharingEntry sharingEntry) {
				return sharingEntry.getSharingEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SharingEntry> getTypeClass() {
				return SharingEntry.class;
			}

		};

	/**
	 * Returns {@code true} if the sharing entry has the sharing entry action.
	 *
	 * @param sharingEntryAction the sharing entry action
	 * @return {@code true} if the sharing entry has the sharing entry action;
	 {@code false} otherwise
	 * @review
	 */
	public boolean hasSharingPermission(
		com.liferay.sharing.security.permission.SharingEntryAction
			sharingEntryAction);

}