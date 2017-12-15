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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the DDMFormInstanceRecord service. Represents a row in the &quot;DDMFormInstanceRecord&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl")
@ProviderType
public interface DDMFormInstanceRecord extends DDMFormInstanceRecordModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstanceRecord, Long> FORM_INSTANCE_RECORD_ID_ACCESSOR =
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

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getStatus()
		throws com.liferay.portal.kernel.exception.PortalException;
}