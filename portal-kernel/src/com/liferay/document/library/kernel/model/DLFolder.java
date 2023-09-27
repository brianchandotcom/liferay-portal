/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLFolder service. Represents a row in the &quot;DLFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl"
)
@ProviderType
public interface DLFolder extends DLFolderModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFolder, Long> FOLDER_ID_ACCESSOR =
		new Accessor<DLFolder, Long>() {

			@Override
			public Long get(DLFolder dlFolder) {
				return dlFolder.getFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLFolder> getTypeClass() {
				return DLFolder.class;
			}

		};

	public java.util.List<Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<DLFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DLFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getPath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String[] getPathArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasInheritableLock();

	public boolean hasLock();

	public boolean isInHiddenFolder();

	public boolean isLocked();

	public boolean isRoot();

}