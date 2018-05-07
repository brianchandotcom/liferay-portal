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
 * The extended model interface for the DDMDataProviderInstanceLink service. Represents a row in the &quot;DDMDataProviderInstanceLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLinkModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl")
@ProviderType
public interface DDMDataProviderInstanceLink
	extends DDMDataProviderInstanceLinkModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceLinkImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMDataProviderInstanceLink, Long> DATA_PROVIDER_INSTANCE_LINK_ID_ACCESSOR =
		new Accessor<DDMDataProviderInstanceLink, Long>() {
			@Override
			public Long get(
				DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {
				return ddmDataProviderInstanceLink.getDataProviderInstanceLinkId();
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