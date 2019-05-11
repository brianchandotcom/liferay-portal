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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDLRecordVersion service. Represents a row in the &quot;DDLRecordVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionImpl"
)
@ProviderType
public interface DDLRecordVersion
	extends DDLRecordVersionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecordVersion, Long>
		RECORD_VERSION_ID_ACCESSOR = new Accessor<DDLRecordVersion, Long>() {

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

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.dynamic.data.mapping.exception.StorageException;

	public DDLRecord getRecord()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;

}