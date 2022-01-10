/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectViewColumn service. Represents a row in the &quot;ObjectViewColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectViewColumnModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectViewColumnImpl")
@ProviderType
public interface ObjectViewColumn extends ObjectViewColumnModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectViewColumnImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectViewColumn, Long>
		OBJECT_VIEW_COLUMN_ID_ACCESSOR =
			new Accessor<ObjectViewColumn, Long>() {

				@Override
				public Long get(ObjectViewColumn objectViewColumn) {
					return objectViewColumn.getObjectViewColumnId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectViewColumn> getTypeClass() {
					return ObjectViewColumn.class;
				}

			};

}