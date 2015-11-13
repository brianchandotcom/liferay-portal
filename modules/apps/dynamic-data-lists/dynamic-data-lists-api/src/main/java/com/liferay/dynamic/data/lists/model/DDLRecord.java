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
 * The extended model interface for the DDLRecord service. Represents a row in the &quot;DDLRecord&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordModel
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl
 * @generated
 */
@ProviderType
public interface DDLRecord extends DDLRecordModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecord, Long> RECORD_ID_ACCESSOR = new Accessor<DDLRecord, Long>() {
			@Override
			public Long get(DDLRecord ddlRecord) {
				return ddlRecord.getRecordId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDLRecord> getTypeClass() {
				return DDLRecord.class;
			}
		};

	public java.util.List<com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue> getDDMFormFieldValues(
		java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldDataType(java.lang.String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldType(java.lang.String fieldName)
		throws java.lang.Exception;

	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException;
}