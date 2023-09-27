/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KBFolder service. Represents a row in the &quot;KBFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KBFolderModel
 * @generated
 */
@ImplementationClassName("com.liferay.knowledge.base.model.impl.KBFolderImpl")
@ProviderType
public interface KBFolder extends KBFolderModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.knowledge.base.model.impl.KBFolderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KBFolder, Long> KB_FOLDER_ID_ACCESSOR =
		new Accessor<KBFolder, Long>() {

			@Override
			public Long get(KBFolder kbFolder) {
				return kbFolder.getKbFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KBFolder> getTypeClass() {
				return KBFolder.class;
			}

		};

	public java.util.List<Long> getAncestorKBFolderIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getClassNameId();

	public KBFolder getParentKBFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getParentTitle(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isEmpty()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRoot();

}