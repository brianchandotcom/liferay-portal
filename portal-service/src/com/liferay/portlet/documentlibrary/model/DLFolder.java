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

package com.liferay.portlet.documentlibrary.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.TreeModel;

/**
 * The extended model interface for the DLFolder service. Represents a row in the &quot;DLFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl
 * @generated
 */
@ProviderType
public interface DLFolder extends DLFolderModel, PersistedModel, TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFolder, Long> FOLDER_ID_ACCESSOR = new Accessor<DLFolder, Long>() {
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

	public java.util.List<java.lang.Long> getAncestorFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getPath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String[] getPathArray()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasInheritableLock();

	public boolean hasLock();

	public boolean isInHiddenFolder();

	public boolean isLocked();

	public boolean isRoot();
}