/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BookmarksEntry service. Represents a row in the &quot;BookmarksEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.bookmarks.model.impl.BookmarksEntryImpl")
@ProviderType
public interface BookmarksEntry
	extends BookmarksEntryModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.bookmarks.model.impl.BookmarksEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BookmarksEntry, Long> ENTRY_ID_ACCESSOR =
		new Accessor<BookmarksEntry, Long>() {

			@Override
			public Long get(BookmarksEntry bookmarksEntry) {
				return bookmarksEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BookmarksEntry> getTypeClass() {
				return BookmarksEntry.class;
			}

		};

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public BookmarksFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getVisits();

}