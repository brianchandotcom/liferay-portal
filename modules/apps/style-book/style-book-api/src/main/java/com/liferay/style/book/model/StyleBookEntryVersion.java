/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the StyleBookEntryVersion service. Represents a row in the &quot;StyleBookEntryVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.style.book.model.impl.StyleBookEntryVersionImpl"
)
@ProviderType
public interface StyleBookEntryVersion extends StyleBookEntryVersionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.style.book.model.impl.StyleBookEntryVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<StyleBookEntryVersion, Long>
		STYLE_BOOK_ENTRY_VERSION_ID_ACCESSOR =
			new Accessor<StyleBookEntryVersion, Long>() {

				@Override
				public Long get(StyleBookEntryVersion styleBookEntryVersion) {
					return styleBookEntryVersion.getStyleBookEntryVersionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<StyleBookEntryVersion> getTypeClass() {
					return StyleBookEntryVersion.class;
				}

			};

}