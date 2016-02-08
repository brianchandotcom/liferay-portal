/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the JournalFolder service. Represents a row in the &quot;JournalFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderModel
 * @see com.liferay.journal.model.impl.JournalFolderImpl
 * @see com.liferay.journal.model.impl.JournalFolderModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.journal.model.impl.JournalFolderImpl")
@ProviderType
public interface JournalFolder extends JournalFolderModel, PersistedModel,
	TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.journal.model.impl.JournalFolderImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalFolder, Long> FOLDER_ID_ACCESSOR = new Accessor<JournalFolder, Long>() {
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

	public java.util.List<java.lang.Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.journal.model.JournalFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.journal.model.JournalFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRoot();
}