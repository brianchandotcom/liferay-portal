/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JournalFolder service. Represents a row in the &quot;JournalFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderModel
 * @generated
 */
@ImplementationClassName("com.liferay.journal.model.impl.JournalFolderImpl")
@ProviderType
public interface JournalFolder
	extends JournalFolderModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.journal.model.impl.JournalFolderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalFolder, Long> FOLDER_ID_ACCESSOR =
		new Accessor<JournalFolder, Long>() {

			@Override
			public Long get(JournalFolder journalFolder) {
				return journalFolder.getFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<JournalFolder> getTypeClass() {
				return JournalFolder.class;
			}

		};

	public java.util.List<Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<JournalFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public JournalFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRoot();

}