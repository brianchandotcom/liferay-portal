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
 * The extended model interface for the DDMStructureVersion service. Represents a row in the &quot;DDMStructureVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl"
)
@ProviderType
public interface DDMStructureVersion
	extends DDMStructureVersionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMStructureVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMStructureVersion, Long>
		STRUCTURE_VERSION_ID_ACCESSOR =
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

	public DDMForm getDDMForm();

	public DDMFormLayout getDDMFormLayout()
		throws com.liferay.portal.kernel.exception.PortalException;

	public DDMStructure getStructure()
		throws com.liferay.portal.kernel.exception.PortalException;

	public void setDDMForm(DDMForm ddmForm);

}