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

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the DDMDataProviderInstance service. Represents a row in the &quot;DDMDataProviderInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl
 * @generated
 */
@ProviderType
public interface DDMDataProviderInstance extends DDMDataProviderInstanceModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMDataProviderInstance, Long> DATA_PROVIDER_INSTANCE_ID_ACCESSOR =
		new Accessor<DDMDataProviderInstance, Long>() {
			@Override
			public Long get(DDMDataProviderInstance ddmDataProviderInstance) {
				return ddmDataProviderInstance.getDataProviderInstanceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMDataProviderInstance> getTypeClass() {
				return DDMDataProviderInstance.class;
			}
		};

	public java.util.List<com.liferay.portal.kernel.util.KeyValuePair> getData()
		throws com.liferay.portal.kernel.exception.PortalException;
}