/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.content.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DLContent service. Represents a row in the &quot;DLContent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DLContentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.document.library.content.model.impl.DLContentImpl"
)
@ProviderType
public interface DLContent extends DLContentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.document.library.content.model.impl.DLContentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DLContent, Long> CONTENT_ID_ACCESSOR =
		new Accessor<DLContent, Long>() {

			@Override
			public Long get(DLContent dlContent) {
				return dlContent.getContentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DLContent> getTypeClass() {
				return DLContent.class;
			}

		};

}