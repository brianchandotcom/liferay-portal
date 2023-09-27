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
 * The extended model interface for the BookmarksFolder service. Represents a row in the &quot;BookmarksFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderModel
 * @generated
 */
@ImplementationClassName("com.liferay.bookmarks.model.impl.BookmarksFolderImpl")
@ProviderType
public interface BookmarksFolder
	extends BookmarksFolderModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.bookmarks.model.impl.BookmarksFolderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BookmarksFolder, Long> FOLDER_ID_ACCESSOR =
		new Accessor<BookmarksFolder, Long>() {

			@Override
			public Long get(BookmarksFolder bookmarksFolder) {
				return bookmarksFolder.getFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BookmarksFolder> getTypeClass() {
				return BookmarksFolder.class;
			}

		};

	public java.util.List<Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<BookmarksFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public BookmarksFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRoot();

}