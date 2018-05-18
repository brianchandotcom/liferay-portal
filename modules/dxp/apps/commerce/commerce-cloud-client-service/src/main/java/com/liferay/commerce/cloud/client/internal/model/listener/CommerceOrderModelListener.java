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

package com.liferay.commerce.cloud.client.internal.model.listener;

import com.liferay.commerce.cloud.client.exception.NoSuchCloudForecastOrderException;
import com.liferay.commerce.cloud.client.service.CommerceCloudForecastOrderLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = ModelListener.class)
public class CommerceOrderModelListener
	extends BaseModelListener<CommerceOrder> {

	@Override
	public void onBeforeRemove(CommerceOrder commerceOrder)
		throws ModelListenerException {

		try {
			_commerceCloudForecastOrderLocalService.
				deleteCommerceCloudForecastOrderByCommerceOrderId(
					commerceOrder.getCommerceOrderId());
		}
		catch (NoSuchCloudForecastOrderException nscfoe) {
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Reference
	private CommerceCloudForecastOrderLocalService
		_commerceCloudForecastOrderLocalService;

}