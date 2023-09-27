/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectLayoutBox service. Represents a row in the &quot;ObjectLayoutBox&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectLayoutBoxModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectLayoutBoxImpl")
@ProviderType
public interface ObjectLayoutBox extends ObjectLayoutBoxModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectLayoutBoxImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectLayoutBox, Long>
		OBJECT_LAYOUT_BOX_ID_ACCESSOR = new Accessor<ObjectLayoutBox, Long>() {

			@Override
			public Long get(ObjectLayoutBox objectLayoutBox) {
				return objectLayoutBox.getObjectLayoutBoxId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectLayoutBox> getTypeClass() {
				return ObjectLayoutBox.class;
			}

		};

	public java.util.List<ObjectLayoutRow> getObjectLayoutRows();

	public void setObjectLayoutRows(
		java.util.List<ObjectLayoutRow> objectLayoutRows);

}