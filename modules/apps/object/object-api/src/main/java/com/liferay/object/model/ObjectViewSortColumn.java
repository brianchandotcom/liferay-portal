/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectViewSortColumn service. Represents a row in the &quot;ObjectViewSortColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectViewSortColumnModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.object.model.impl.ObjectViewSortColumnImpl"
)
@ProviderType
public interface ObjectViewSortColumn extends ObjectViewSortColumnModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectViewSortColumnImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectViewSortColumn, Long>
		OBJECT_VIEW_SORT_COLUMN_ID_ACCESSOR =
			new Accessor<ObjectViewSortColumn, Long>() {

				@Override
				public Long get(ObjectViewSortColumn objectViewSortColumn) {
					return objectViewSortColumn.getObjectViewSortColumnId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectViewSortColumn> getTypeClass() {
					return ObjectViewSortColumn.class;
				}

			};

}