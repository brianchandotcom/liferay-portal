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
 * The extended model interface for the DDMFormInstanceVersion service. Represents a row in the &quot;DDMFormInstanceVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl"
)
@ProviderType
public interface DDMFormInstanceVersion
	extends DDMFormInstanceVersionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMFormInstanceVersion, Long>
		FORM_INSTANCE_VERSION_ID_ACCESSOR =
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