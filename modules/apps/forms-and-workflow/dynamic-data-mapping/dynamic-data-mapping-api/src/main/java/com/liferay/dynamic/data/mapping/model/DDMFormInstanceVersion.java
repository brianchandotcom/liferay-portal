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
 * The extended model interface for the DDMFormInstanceVersion service. Represents a row in the &quot;DDMFormInstanceVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl")
@ProviderType
public interface DDMFormInstanceVersion extends DDMFormInstanceVersionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstanceVersion, Long> FORM_INSTANCE_VERSION_ID_ACCESSOR =
		new Accessor<DDMFormInstanceVersion, Long>() {
			@Override
			public Long get(DDMFormInstanceVersion ddmFormInstanceVersion) {
				return ddmFormInstanceVersion.getFormInstanceVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMFormInstanceVersion> getTypeClass() {
				return DDMFormInstanceVersion.class;
			}
		};

	public DDMFormInstance getFormInstance()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMStructureVersion getStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException;
}