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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the DDLRecordSetVersion service. Represents a row in the &quot;DDLRecordSetVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionModel
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl")
@ProviderType
public interface DDLRecordSetVersion extends DDLRecordSetVersionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecordSetVersion, Long> RECORD_SET_VERSION_ID_ACCESSOR =
		new Accessor<DDLRecordSetVersion, Long>() {
			@Override
			public Long get(DDLRecordSetVersion ddlRecordSetVersion) {
				return ddlRecordSetVersion.getRecordSetVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDLRecordSetVersion> getTypeClass() {
				return DDLRecordSetVersion.class;
			}
		};

	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion getDDMStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;
}