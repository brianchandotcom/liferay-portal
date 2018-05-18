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

package com.liferay.commerce.cloud.client.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
@ProviderType
public interface CommerceCloudClient {

	public void addCommerceForecastEntries(String json)
		throws CommerceCloudClientException;

	public void checkCommerceForecastEntries()
		throws CommerceCloudClientException;

	public JSONObject getForecastingConfiguration()
		throws CommerceCloudClientException;

	public JSONObject getProjectConfiguration()
		throws CommerceCloudClientException;

	public void sync(
			List<CommerceCloudForecastOrder> commerceCloudForecastOrders)
		throws CommerceCloudClientException;

	public void updateForecastingConfiguration(JSONObject jsonObject)
		throws CommerceCloudClientException;

	public void updateProjectConfiguration(String callbackHost)
		throws CommerceCloudClientException;

}