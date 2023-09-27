/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectLayoutRow service. Represents a row in the &quot;ObjectLayoutRow&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectLayoutRowModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectLayoutRowImpl")
@ProviderType
public interface ObjectLayoutRow extends ObjectLayoutRowModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectLayoutRowImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectLayoutRow, Long>
		OBJECT_LAYOUT_ROW_ID_ACCESSOR = new Accessor<ObjectLayoutRow, Long>() {

			@Override
			public Long get(ObjectLayoutRow objectLayoutRow) {
				return objectLayoutRow.getObjectLayoutRowId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectLayoutRow> getTypeClass() {
				return ObjectLayoutRow.class;
			}

		};

	public java.util.List<ObjectLayoutColumn> getObjectLayoutColumns();

	public void setObjectLayoutColumns(
		java.util.List<ObjectLayoutColumn> objectLayoutColumns);

}