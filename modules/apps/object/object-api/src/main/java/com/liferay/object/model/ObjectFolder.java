/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectFolder service. Represents a row in the &quot;ObjectFolder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectFolderModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectFolderImpl")
@ProviderType
public interface ObjectFolder extends ObjectFolderModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectFolderImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectFolder, Long> OBJECT_FOLDER_ID_ACCESSOR =
		new Accessor<ObjectFolder, Long>() {

			@Override
			public Long get(ObjectFolder objectFolder) {
				return objectFolder.getObjectFolderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectFolder> getTypeClass() {
				return ObjectFolder.class;
			}

		};

	public boolean isUncategorized();

}