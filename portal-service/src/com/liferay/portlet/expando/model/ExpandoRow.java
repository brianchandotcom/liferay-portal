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

package com.liferay.portlet.expando.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the ExpandoRow service. Represents a row in the &quot;ExpandoRow&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowModel
 * @see com.liferay.portlet.expando.model.impl.ExpandoRowImpl
 * @see com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl
 * @generated
 */
@ProviderType
public interface ExpandoRow extends ExpandoRowModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.expando.model.impl.ExpandoRowImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoRow, Long> ROW_ID_ACCESSOR = new Accessor<ExpandoRow, Long>() {
			@Override
			public Long get(ExpandoRow expandoRow) {
				return expandoRow.getRowId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoRow> getTypeClass() {
				return ExpandoRow.class;
			}
		};
}