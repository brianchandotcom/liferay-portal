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
 * The extended model interface for the DDLRecordSetVersion service. Represents a row in the &quot;DDLRecordSetVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl"
)
@ProviderType
public interface DDLRecordSetVersion
	extends DDLRecordSetVersionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDLRecordSetVersion, Long>
		RECORD_SET_VERSION_ID_ACCESSOR =
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

	public com.liferay.dynamic.data.mapping.model.DDMStructureVersion
			getDDMStructureVersion()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDLRecordSet getRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException;

}