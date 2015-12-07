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
 * The extended model interface for the DDLRecordSet service. Represents a row in the &quot;DDLRecordSet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetModel
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetImpl
 * @see com.liferay.dynamic.data.lists.model.impl.DDLRecordSetModelImpl
 * @generated
 */
@ProviderType
public interface DDLRecordSet extends DDLRecordSetModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecordSet, Long> RECORD_SET_ID_ACCESSOR = new Accessor<DDLRecordSet, Long>() {
			@Override
			public Long get(DDLRecordSet ddlRecordSet) {
				return ddlRecordSet.getRecordSetId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDLRecordSet> getTypeClass() {
				return DDLRecordSet.class;
			}
		};

	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.dynamic.data.mapping.model.DDMStructure getDDMStructure(
		long formDDMTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord> getRecords();

	public com.liferay.portal.kernel.util.UnicodeProperties getSettingsProperties();

	public java.lang.String getSettingsProperty(java.lang.String key);

	public java.lang.String getSettingsProperty(java.lang.String key,
		java.lang.String defaultValue);
}