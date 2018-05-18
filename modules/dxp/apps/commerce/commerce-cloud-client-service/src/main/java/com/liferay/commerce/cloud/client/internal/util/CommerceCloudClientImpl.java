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

package com.liferay.commerce.cloud.client.internal.util;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.forecast.model.CommerceForecastEntry;
import com.liferay.commerce.forecast.model.CommerceForecastEntryConstants;
import com.liferay.commerce.forecast.service.CommerceForecastEntryLocalService;
import com.liferay.commerce.forecast.service.CommerceForecastValueLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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
	public void addCommerceForecastEntries(String json)
		throws CommerceCloudClientException {

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				addCommerceForecastEntry(jsonObject);
			}
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public void checkCommerceForecastEntries()
		throws CommerceCloudClientException {

		if (_lockManager.isLocked(
				_LOCK_CLASS_NAME, _CHECK_COMMERCE_FORECAST_ENTRIES_LOCK_KEY)) {

			if (_log.isInfoEnabled()) {
				_log.info("Skipping check, another job is still in progress");
			}

			return;
		}

		try {
			_lockManager.lock(
				_LOCK_CLASS_NAME, _CHECK_COMMERCE_FORECAST_ENTRIES_LOCK_KEY,
				null);

			doCheckCommerceForecastEntries();
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
		finally {
			_lockManager.unlock(
				_LOCK_CLASS_NAME, _CHECK_COMMERCE_FORECAST_ENTRIES_LOCK_KEY);
		}
	}

	@Override
	public JSONObject getForecastingConfiguration()
		throws CommerceCloudClientException {

		try {
			return doGetForecastingConfiguration();
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public JSONObject getProjectConfiguration()
		throws CommerceCloudClientException {

		try {
			return doGetProjectConfiguration();
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public void sync(
			List<CommerceCloudForecastOrder> commerceCloudForecastOrders)
		throws CommerceCloudClientException {

		try {
			doSync(commerceCloudForecastOrders);
		}
		catch (CommerceCloudClientException ccce) {
			throw ccce;
		}
		catch (Exception e) {
			throw new CommerceCloudClientException(e);
		}
	}

	@Override
	public void updateForecastingConfiguration(JSONObject jsonObject)
		throws CommerceCloudClientException {

		String location = getLocation("/forecast");

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			jsonObject.toJSONString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(location);
		options.setPost(true);

		executeRequest(options);
	}

	@Override
	public void updateProjectConfiguration(String callbackHost)
		throws CommerceCloudClientException {

		String location = getLocation(StringPool.BLANK);

		Http.Options options = new Http.Options();

		options.addPart("callbackHost", callbackHost);
		options.setLocation(location);
		options.setPost(true);

		executeRequest(options);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceCloudClientConfiguration = ConfigurableUtil.createConfigurable(
			CommerceCloudClientConfiguration.class, properties);
	}

	protected void addCommerceForecastEntry(JSONObject jsonObject)
		throws PortalException {

		long companyId = jsonObject.getLong("companyId");
		Date date = new Date(jsonObject.getLong("time"));
		String periodString = jsonObject.getString("period");
		String targetString = jsonObject.getString("target");
		long customerId = jsonObject.getLong("customerId");
		String sku = jsonObject.getString("sku");
		BigDecimal assertivity = _getBigDecimal(jsonObject, "assertivity");

		long userId = getCompanyAdminUserId(companyId);

		int period = CommerceForecastEntryConstants.getLabelPeriod(
			StringUtil.toLowerCase(periodString));
		int target = CommerceForecastEntryConstants.getLabelTarget(
			StringUtil.toLowerCase(targetString));

		CommerceForecastEntry commerceForecastEntry =
			_commerceForecastEntryLocalService.addCommerceForecastEntry(
				companyId, userId, date, period, target, customerId, sku,
				assertivity);

		JSONArray valuesJSONArray = jsonObject.getJSONArray("values");

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

			addCommerceForecastValue(
				userId, commerceForecastEntry, valueJSONObject);
		}
	}

	protected void addCommerceForecastValue(
			long userId, CommerceForecastEntry commerceForecastEntry,
			JSONObject jsonObject)
		throws PortalException {

		Date date = new Date(jsonObject.getLong("time"));
		BigDecimal lowerValue = _getBigDecimal(jsonObject, "lowerValue");
		BigDecimal value = _getBigDecimal(jsonObject, "value");
		BigDecimal upperValue = _getBigDecimal(jsonObject, "upperValue");

		_commerceForecastValueLocalService.addCommerceForecastValue(
			userId, commerceForecastEntry.getCommerceForecastEntryId(), date,
			lowerValue, value, upperValue);
	}

	protected void doCheckCommerceForecastEntries() throws Exception {
		DynamicQuery dynamicQuery =
			_commerceForecastEntryLocalService.dynamicQuery();

		dynamicQuery.setProjection(ProjectionFactoryUtil.max("date"));

		List<?> results = _commerceForecastEntryLocalService.dynamicQuery(
			dynamicQuery);

		Date date = null;

		if (ListUtil.isNotEmpty(results)) {
			date = (Date)results.get(0);
		}

		long time = Long.MIN_VALUE;

		if (date != null) {
			time = date.getTime();
		}

		Http.Options options = new Http.Options();

		options.setLocation(getLocation("/forecasts/" + time));

		String json = executeRequest(options);

		addCommerceForecastEntries(json);
	}

	protected JSONObject doGetForecastingConfiguration() throws Exception {
		String location = getLocation("/forecast");

		Http.Options options = new Http.Options();

		options.setLocation(location);

		String json = executeRequest(options);

		return _jsonFactory.createJSONObject(json);
	}

	protected JSONObject doGetProjectConfiguration() throws Exception {
		String location = getLocation(StringPool.BLANK);

		Http.Options options = new Http.Options();

		options.setLocation(location);

		String json = executeRequest(options);

		return _jsonFactory.createJSONObject(json);
	}

	protected void doSync(
			List<CommerceCloudForecastOrder> commerceCloudForecastOrders)
		throws Exception {

		String location = getLocation("/forecast/orders");

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (CommerceCloudForecastOrder commerceCloudForecastOrder :
				commerceCloudForecastOrders) {

			JSONObject jsonObject = getJSONObject(commerceCloudForecastOrder);

			jsonArray.put(jsonObject);
		}

		options.setBody(
			jsonArray.toJSONString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);

		options.setLocation(location);
		options.setPut(true);

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

	protected long getCompanyAdminUserId(long companyId)
		throws PortalException {

		if (_companyLocalService.fetchCompany(companyId) == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Company " + companyId +
						" does not exist, using default company instead");
			}

			companyId = _portal.getDefaultCompanyId();
		}

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

		if (ArrayUtil.isNotEmpty(userIds)) {
			return userIds[0];
		}

		throw new PortalException(
			"Unable to find an administrator user in company " + companyId);
	}

	protected JSONObject getJSONObject(
			CommerceCloudForecastOrder commerceCloudForecastOrder)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(
				commerceCloudForecastOrder.getCommerceOrderId());

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put("companyId", Long.valueOf(commerceOrder.getCompanyId()));

		Date createDate = commerceCloudForecastOrder.getCreateDate();

		jsonObject.put("createTime", Long.valueOf(createDate.getTime()));

		jsonObject.put("customerId", Long.valueOf(commerceOrder.getClassPK()));
		jsonObject.put(
			"orderId", Long.valueOf(commerceOrder.getCommerceOrderId()));

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

		String serverHost = _commerceCloudClientConfiguration.serverHost();

		if (Validator.isNull(serverHost)) {
			throw new CommerceCloudClientException.MustBeConfigured(
				"Server URL is not configured properly");
		}

		StringBundler sb = new StringBundler(5);

		sb.append("https://");
		sb.append(serverHost);
		sb.append("/projects/");
		sb.append(projectId);
		sb.append(path);

		return sb.toString();
	}

	private static BigDecimal _getBigDecimal(
		JSONObject jsonObject, String key) {

		String value = jsonObject.getString(key);

		if (Validator.isNull(value)) {
			return null;
		}

		return new BigDecimal(value);
	}

	private static final String _CHECK_COMMERCE_FORECAST_ENTRIES_LOCK_KEY =
		"checkCommerceForecastEntries";

	private static final String _LOCK_CLASS_NAME =
		CommerceCloudClientImpl.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCloudClientImpl.class);

	private volatile CommerceCloudClientConfiguration
		_commerceCloudClientConfiguration;

	@Reference
	private CommerceForecastEntryLocalService
		_commerceForecastEntryLocalService;

	@Reference
	private CommerceForecastValueLocalService
		_commerceForecastValueLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LockManager _lockManager;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}