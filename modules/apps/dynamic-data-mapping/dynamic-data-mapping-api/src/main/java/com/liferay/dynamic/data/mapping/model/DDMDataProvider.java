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
 * The extended model interface for the DDMDataProvider service. Represents a row in the &quot;DDMDataProvider&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderModel
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderImpl
 * @see com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl
 * @generated
 */
@ProviderType
public interface DDMDataProvider extends DDMDataProviderModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DDMDataProvider, Long> DATA_PROVIDER_ID_ACCESSOR =
		new Accessor<DDMDataProvider, Long>() {
			@Override
			public Long get(DDMDataProvider ddmDataProvider) {
				return ddmDataProvider.getDataProviderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DDMDataProvider> getTypeClass() {
				return DDMDataProvider.class;
			}
		};
}