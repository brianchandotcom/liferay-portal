/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDLRecord service. Represents a row in the &quot;DDLRecord&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl"
)
@ProviderType
public interface DDLRecord extends DDLRecordModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecord, Long> RECORD_ID_ACCESSOR =
		new Accessor<DDLRecord, Long>() {

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

	public java.util.List
		<com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue>
				getDDMFormFieldValues(String fieldName)
			throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldDataType(String fieldName)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.io.Serializable getFieldType(String fieldName) throws Exception;

	public DDLRecordVersion getLatestRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordVersion getRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordVersion getRecordVersion(String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException;

}