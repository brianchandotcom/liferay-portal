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

package com.liferay.dynamic.data.lists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDLRecordVersion service. Represents a row in the &quot;DDLRecordVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionModel
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionImpl
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionModelImpl
 * @generated
 */
@ProviderType
public interface DDLRecordVersion extends DDLRecordVersionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecordVersion, Long> RECORD_VERSION_ID_ACCESSOR =
		new Accessor<DDLRecordVersion, Long>() {
			@Override
			public Long get(DDLRecordVersion ddlRecordVersion) {
				return ddlRecordVersion.getRecordVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDLRecordVersion> getTypeClass() {
				return DDLRecordVersion.class;
			}
		};

	public com.liferay.dynamic.data.lists.model.DDLRecord getRecord()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;
}