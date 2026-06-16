/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the StyleBookTokenSet service. Represents a row in the &quot;StyleBookTokenSet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.style.book.model.impl.StyleBookTokenSetImpl"
)
@ProviderType
public interface StyleBookTokenSet
	extends PersistedModel, StyleBookTokenSetModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.style.book.model.impl.StyleBookTokenSetImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<StyleBookTokenSet, Long>
		STYLE_BOOK_TOKEN_SET_ID_ACCESSOR =
			new Accessor<StyleBookTokenSet, Long>() {

				@Override
				public Long get(StyleBookTokenSet styleBookTokenSet) {
					return styleBookTokenSet.getStyleBookTokenSetId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<StyleBookTokenSet> getTypeClass() {
					return StyleBookTokenSet.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1917504444