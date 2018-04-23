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

package com.liferay.commerce.cloud.client.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceCloudOrderForecastSync service. Represents a row in the &quot;CCOrderForecastSync&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudOrderForecastSyncModel
 * @see com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncImpl
 * @see com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncImpl")
@ProviderType
public interface CommerceCloudOrderForecastSync
	extends CommerceCloudOrderForecastSyncModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCloudOrderForecastSync, Long> COMMERCE_CLOUD_ORDER_FORECAST_SYNC_ID_ACCESSOR =
		new Accessor<CommerceCloudOrderForecastSync, Long>() {
			@Override
			public Long get(
				CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
				return commerceCloudOrderForecastSync.getCommerceCloudOrderForecastSyncId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCloudOrderForecastSync> getTypeClass() {
				return CommerceCloudOrderForecastSync.class;
			}
		};

	public static final Accessor<CommerceCloudOrderForecastSync, Long> COMMERCE_ORDER_ID_ACCESSOR =
		new Accessor<CommerceCloudOrderForecastSync, Long>() {
			@Override
			public Long get(
				CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
				return commerceCloudOrderForecastSync.getCommerceOrderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCloudOrderForecastSync> getTypeClass() {
				return CommerceCloudOrderForecastSync.class;
			}
		};
}