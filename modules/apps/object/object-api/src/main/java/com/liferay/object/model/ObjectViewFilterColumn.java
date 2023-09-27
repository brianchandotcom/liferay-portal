/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectViewFilterColumn service. Represents a row in the &quot;ObjectViewFilterColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectViewFilterColumnModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.object.model.impl.ObjectViewFilterColumnImpl"
)
@ProviderType
public interface ObjectViewFilterColumn
	extends ObjectViewFilterColumnModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectViewFilterColumnImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectViewFilterColumn, Long>
		OBJECT_VIEW_FILTER_COLUMN_ID_ACCESSOR =
			new Accessor<ObjectViewFilterColumn, Long>() {

				@Override
				public Long get(ObjectViewFilterColumn objectViewFilterColumn) {
					return objectViewFilterColumn.getObjectViewFilterColumnId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectViewFilterColumn> getTypeClass() {
					return ObjectViewFilterColumn.class;
				}

			};

	public com.liferay.portal.kernel.json.JSONArray getJSONArray()
		throws com.liferay.portal.kernel.json.JSONException;

}