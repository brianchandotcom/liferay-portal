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

package com.liferay.commerce.cloud.client.internal.util;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = CommerceCloudClientConstants.CONFIGURATION_PID,
	immediate = true
)
public class CommerceCloudClientImpl implements CommerceCloudClient {

	@Override
	public JSONObject getOrderForecastConfiguration()
		throws CommerceCloudClientException {

		try {
			return doGetOrderForecastConfiguration();
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public void syncOrders(
			List<CommerceCloudOrderForecastSync>
				commerceCloudOrderForecastSyncs)
		throws CommerceCloudClientException {

		try {
			doSyncOrders(commerceCloudOrderForecastSyncs);
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public void updateOrderForecastConfiguration(JSONObject jsonObject)
		throws CommerceCloudClientException {

		String location = getLocation("/forecast/configuration/");

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			jsonObject.toJSONString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(location);
		options.setPut(true);

		executeRequest(options);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceCloudClientConfiguration = ConfigurableUtil.createConfigurable(
			CommerceCloudClientConfiguration.class, properties);
	}

	protected JSONObject doGetOrderForecastConfiguration() throws Exception {
		String location = getLocation("/forecast/configuration/");

		Http.Options options = new Http.Options();

		options.setLocation(location);

		String json = executeRequest(options);

		return _jsonFactory.createJSONObject(json);
	}

	protected void doSyncOrders(
			List<CommerceCloudOrderForecastSync>
				commerceCloudOrderForecastSyncs)
		throws Exception {

		String location = getLocation("/forecast/orders/");

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (CommerceCloudOrderForecastSync commerceCloudOrderForecastSync :
				commerceCloudOrderForecastSyncs) {

			JSONObject jsonObject = getJSONObject(
				commerceCloudOrderForecastSync);

			jsonArray.put(jsonObject);
		}

		options.setBody(
			jsonArray.toJSONString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		options.setLocation(location);
		options.setPost(true);

		executeRequest(options);
	}

	protected String executeRequest(Http.Options options)
		throws CommerceCloudClientException {

		String responseBody = null;

		try {
			responseBody = _http.URLtoString(options);
		}
		catch (IOException ioe) {
			throw new CommerceCloudClientException.ServerError(
				options.getLocation(), ioe);
		}

		Http.Response response = options.getResponse();

		int responseCode = response.getResponseCode();

		if (responseCode != HttpServletResponse.SC_OK) {
			throw new CommerceCloudClientException.ServerResponseError(
				options.getLocation(), responseCode, responseBody);
		}

		return responseBody;
	}

	protected JSONObject getJSONObject(
			CommerceCloudOrderForecastSync commerceCloudOrderForecastSync)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceCloudOrderForecastSync.getCommerceOrderId());

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("companyId", commerceOrder.getCompanyId());

		Date createDate = commerceCloudOrderForecastSync.getCreateDate();

		jsonObject.put("createTime", createDate.getTime());

		jsonObject.put("customerId", commerceOrder.getClassPK());
		jsonObject.put("orderId", commerceOrder.getCommerceOrderId());

		JSONArray orderItemsJSONArray = _jsonFactory.createJSONArray();

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			JSONObject orderItemJSONObject = _jsonFactory.createJSONObject();

			BigDecimal price = commerceOrderItem.getPrice();
			int quantity = commerceOrderItem.getQuantity();

			BigDecimal unitPrice = price.divide(BigDecimal.valueOf(quantity));

			orderItemJSONObject.put("price", unitPrice.toString());

			orderItemJSONObject.put("quantity", quantity);
			orderItemJSONObject.put("sku", commerceOrderItem.getSku());

			orderItemsJSONArray.put(orderItemJSONObject);
		}

		jsonObject.put("orderItems", orderItemsJSONArray);

		return jsonObject;
	}

	protected String getLocation(String path)
		throws CommerceCloudClientException.MustBeConfigured {

		String projectId = _commerceCloudClientConfiguration.projectId();

		if (Validator.isNull(projectId)) {
			throw new CommerceCloudClientException.MustBeConfigured(
				"Project ID is not configured properly");
		}

		String serverUrl = _commerceCloudClientConfiguration.serverUrl();

		if (Validator.isNull(serverUrl)) {
			throw new CommerceCloudClientException.MustBeConfigured(
				"Server URL is not configured properly");
		}

		StringBundler sb = new StringBundler(5);

		sb.append(serverUrl);

		if (serverUrl.charAt(serverUrl.length() - 1) != CharPool.SLASH) {
			sb.append(CharPool.SLASH);
		}

		sb.append("projects/");
		sb.append(projectId);
		sb.append(path);

		return sb.toString();
	}

	private volatile CommerceCloudClientConfiguration
		_commerceCloudClientConfiguration;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}