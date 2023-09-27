/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLFileVersionPreview service. Represents a row in the &quot;DLFileVersionPreview&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPreviewModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.document.library.model.impl.DLFileVersionPreviewImpl"
)
@ProviderType
public interface DLFileVersionPreview
	extends DLFileVersionPreviewModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.document.library.model.impl.DLFileVersionPreviewImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLFileVersionPreview, Long>
		DL_FILE_VERSION_PREVIEW_ID_ACCESSOR =
			new Accessor<DLFileVersionPreview, Long>() {

				@Override
				public Long get(DLFileVersionPreview dlFileVersionPreview) {
					return dlFileVersionPreview.getDlFileVersionPreviewId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DLFileVersionPreview> getTypeClass() {
					return DLFileVersionPreview.class;
				}

			};

}