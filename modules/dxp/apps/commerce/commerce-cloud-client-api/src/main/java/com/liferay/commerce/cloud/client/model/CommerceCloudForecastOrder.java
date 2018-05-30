/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.cloud.client.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceCloudForecastOrder service. Represents a row in the &quot;CommerceCloudForecastOrder&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrderModel
 * @see com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderImpl
 * @see com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderImpl")
@ProviderType
public interface CommerceCloudForecastOrder
	extends CommerceCloudForecastOrderModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCloudForecastOrder, Long> COMMERCE_CLOUD_FORECAST_ORDER_ID_ACCESSOR =
		new Accessor<CommerceCloudForecastOrder, Long>() {
			@Override
			public Long get(
				CommerceCloudForecastOrder commerceCloudForecastOrder) {
				return commerceCloudForecastOrder.getCommerceCloudForecastOrderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCloudForecastOrder> getTypeClass() {
				return CommerceCloudForecastOrder.class;
			}
		};

	public static final Accessor<CommerceCloudForecastOrder, Long> COMMERCE_ORDER_ID_ACCESSOR =
		new Accessor<CommerceCloudForecastOrder, Long>() {
			@Override
			public Long get(
				CommerceCloudForecastOrder commerceCloudForecastOrder) {
				return commerceCloudForecastOrder.getCommerceOrderId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCloudForecastOrder> getTypeClass() {
				return CommerceCloudForecastOrder.class;
			}
		};
}