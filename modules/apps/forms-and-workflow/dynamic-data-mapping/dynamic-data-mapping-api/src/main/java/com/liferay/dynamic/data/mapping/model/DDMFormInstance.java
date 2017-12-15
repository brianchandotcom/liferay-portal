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
 * The extended model interface for the DDMFormInstance service. Represents a row in the &quot;DDMFormInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceImpl")
@ProviderType
public interface DDMFormInstance extends DDMFormInstanceModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstance, Long> FORM_INSTANCE_ID_ACCESSOR =
		new Accessor<DDMFormInstance, Long>() {
			@Override
			public Long get(DDMFormInstance ddmFormInstance) {
				return ddmFormInstance.getFormInstanceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMFormInstance> getTypeClass() {
				return DDMFormInstance.class;
			}
		};

	public java.util.List<DDMFormInstanceRecord> getFormInstanceRecords();

	public DDMFormInstanceVersion getFormInstanceVersion(
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.storage.DDMFormValues getSettingsDDMFormValues()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMFormInstanceSettings getSettingsModel()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException;
}