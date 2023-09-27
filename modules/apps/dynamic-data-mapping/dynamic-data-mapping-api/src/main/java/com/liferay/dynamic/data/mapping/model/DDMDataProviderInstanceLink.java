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
 * The extended model interface for the DDMDataProviderInstanceLink service. Represents a row in the &quot;DDMDataProviderInstanceLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl"
)
@ProviderType
public interface DDMDataProviderInstanceLink
	extends DDMDataProviderInstanceLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMDataProviderInstanceLink, Long>
		DATA_PROVIDER_INSTANCE_LINK_ID_ACCESSOR =
			new Accessor<DDMDataProviderInstanceLink, Long>() {

				@Override
				public Long get(
					DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

					return ddmDataProviderInstanceLink.
						getDataProviderInstanceLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DDMDataProviderInstanceLink> getTypeClass() {
					return DDMDataProviderInstanceLink.class;
				}

			};

}