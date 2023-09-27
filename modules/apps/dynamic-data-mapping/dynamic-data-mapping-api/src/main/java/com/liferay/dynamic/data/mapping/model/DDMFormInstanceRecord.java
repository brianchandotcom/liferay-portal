/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DDMFormInstanceRecord service. Represents a row in the &quot;DDMFormInstanceRecord&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl"
)
@ProviderType
public interface DDMFormInstanceRecord
	extends DDMFormInstanceRecordModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstanceRecord, Long>
		FORM_INSTANCE_RECORD_ID_ACCESSOR =
			new Accessor<DDMFormInstanceRecord, Long>() {

				@Override
				public Long get(DDMFormInstanceRecord ddmFormInstanceRecord) {
					return ddmFormInstanceRecord.getFormInstanceRecordId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DDMFormInstanceRecord> getTypeClass() {
					return DDMFormInstanceRecord.class;
				}

			};

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getStorageType()
		throws com.liferay.portal.kernel.exception.PortalException;

}