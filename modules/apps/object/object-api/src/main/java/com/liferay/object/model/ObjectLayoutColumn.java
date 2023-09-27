/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectLayoutColumn service. Represents a row in the &quot;ObjectLayoutColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectLayoutColumnModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectLayoutColumnImpl")
@ProviderType
public interface ObjectLayoutColumn extends ObjectLayoutColumnModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectLayoutColumnImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectLayoutColumn, Long>
		OBJECT_LAYOUT_COLUMN_ID_ACCESSOR =
			new Accessor<ObjectLayoutColumn, Long>() {

				@Override
				public Long get(ObjectLayoutColumn objectLayoutColumn) {
					return objectLayoutColumn.getObjectLayoutColumnId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectLayoutColumn> getTypeClass() {
					return ObjectLayoutColumn.class;
				}

			};

}