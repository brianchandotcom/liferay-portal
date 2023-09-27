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
 * The extended model interface for the DLFileShortcut service. Represents a row in the &quot;DLFileShortcut&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl"
)
@ProviderType
public interface DLFileShortcut
	extends DLFileShortcutModel, PersistedModel, TreeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileShortcut, Long>
		FILE_SHORTCUT_ID_ACCESSOR = new Accessor<DLFileShortcut, Long>() {

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
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DLFolder getDLFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileVersion
			getFileVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.Folder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getToTitle();

	public boolean isInHiddenFolder();

}