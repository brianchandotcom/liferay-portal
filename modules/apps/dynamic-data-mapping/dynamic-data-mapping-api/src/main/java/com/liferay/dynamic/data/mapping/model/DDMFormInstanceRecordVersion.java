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
 * The extended model interface for the DDMFormInstanceRecordVersion service. Represents a row in the &quot;DDMFormInstanceRecordVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersionModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionImpl")
@ProviderType
public interface DDMFormInstanceRecordVersion
	extends DDMFormInstanceRecordVersionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceRecordVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstanceRecordVersion, Long> FORM_INSTANCE_RECORD_VERSION_ID_ACCESSOR =
		new Accessor<DDMFormInstanceRecordVersion, Long>() {
			@Override
			public Long get(
				DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion) {
				return ddmFormInstanceRecordVersion.getFormInstanceRecordVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMFormInstanceRecordVersion> getTypeClass() {
				return DDMFormInstanceRecordVersion.class;
			}
		};

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getDDMFormValues()
		throws com.liferay.dynamic.data.mapping.exception.StorageException;

	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceRecord getFormInstanceRecord()
		throws com.liferay.portal.kernel.exception.PortalException;
}