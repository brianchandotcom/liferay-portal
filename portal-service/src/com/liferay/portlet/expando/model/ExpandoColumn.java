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
 * The extended model interface for the ExpandoColumn service. Represents a row in the &quot;ExpandoColumn&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoColumnModel
 * @see com.liferay.portlet.expando.model.impl.ExpandoColumnImpl
 * @see com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl
 * @generated
 */
@ProviderType
public interface ExpandoColumn extends ExpandoColumnModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.expando.model.impl.ExpandoColumnImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoColumn, Long> COLUMN_ID_ACCESSOR = new Accessor<ExpandoColumn, Long>() {
			@Override
			public Long get(ExpandoColumn expandoColumn) {
				return expandoColumn.getColumnId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoColumn> getTypeClass() {
				return ExpandoColumn.class;
			}
		};

	public java.io.Serializable getDefaultValue();

	public java.lang.String getDisplayName(java.util.Locale locale);

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}