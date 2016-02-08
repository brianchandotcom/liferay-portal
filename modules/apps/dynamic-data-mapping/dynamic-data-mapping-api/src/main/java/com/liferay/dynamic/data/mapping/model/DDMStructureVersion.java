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
 * The extended model interface for the DDMStructureVersion service. Represents a row in the &quot;DDMStructureVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl")
@ProviderType
public interface DDMStructureVersion extends DDMStructureVersionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMStructureVersion, Long> STRUCTURE_VERSION_ID_ACCESSOR =
		new Accessor<DDMStructureVersion, Long>() {
			@Override
			public Long get(DDMStructureVersion ddmStructureVersion) {
				return ddmStructureVersion.getStructureVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMStructureVersion> getTypeClass() {
				return DDMStructureVersion.class;
			}
		};

	public com.liferay.dynamic.data.mapping.model.DDMForm getDDMForm();

	public com.liferay.dynamic.data.mapping.model.DDMFormLayout getDDMFormLayout()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDDMForm(
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm);
}