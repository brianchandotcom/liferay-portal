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
 * The extended model interface for the DLFileShortcut service. Represents a row in the &quot;DLFileShortcut&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl
 * @generated
 */
@ProviderType
public interface DLFileShortcut extends DLFileShortcutModel, PersistedModel,
	TreeModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileShortcut, Long> FILE_SHORTCUT_ID_ACCESSOR =
		new Accessor<DLFileShortcut, Long>() {
			@Override
			public Long get(DLFileShortcut dlFileShortcut) {
				return dlFileShortcut.getFileShortcutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLFileShortcut> getTypeClass() {
				return DLFileShortcut.class;
			}
		};

	@Override
	public java.lang.String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFolder getDLFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileVersion getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.Folder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getToTitle();

	public boolean isInHiddenFolder();
}