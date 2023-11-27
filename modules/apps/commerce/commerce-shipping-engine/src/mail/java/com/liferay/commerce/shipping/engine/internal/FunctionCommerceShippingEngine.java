/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.internal;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceShippingEngineException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.engine.internal.configuration.FunctionCommerceShippingEngineConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	configurationPid = "com.liferay.commerce.shipping.engine.internal.configuration.FunctionCommerceShippingEngineConfiguration",
	service = CommerceShippingEngine.class
)
public class FunctionCommerceShippingEngine implements CommerceShippingEngine {

	@Override
	public String getCommerceShippingOptionLabel(String name, Locale locale) {
		try {
			User currentUser = _userService.getCurrentUser();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						currentUser.getCompanyId(), Http.Method.POST,
						_functionCommerceShippingEngineConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						JSONUtil.put(
							"locale", locale
						).put(
							"name", name
						),
						_functionCommerceShippingEngineConfiguration.
							shippingEngineOptionLabelPath(),
						currentUser.getUserId()
					).get()));

			return jsonObject.getString(name);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	@Override
	public List<CommerceShippingOption> getCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		try {
			User currentUser = _userService.getCurrentUser();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						commerceOrder.getCompanyId(), Http.Method.POST,
						_functionCommerceShippingEngineConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						_getPayloadJSONObject(commerceContext, commerceOrder),
						_functionCommerceShippingEngineConfiguration.
							shippingEngineOptionsPath(),
						currentUser.getUserId()
					).get()));

			return _getCommerceShippingOptions(jsonObject);
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new CommerceShippingEngineException(exception.getMessage());
		}
	}

	@Override
	public String getDescription(Locale locale) {
		try {
			User currentUser = _userService.getCurrentUser();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						currentUser.getCompanyId(), Http.Method.POST,
						_functionCommerceShippingEngineConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						JSONUtil.put("locale", locale),
						_functionCommerceShippingEngineConfiguration.
							shippingEngineLocalizedDescriptionPath(),
						currentUser.getUserId()
					).get()));

			return jsonObject.getString("description");
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	@Override
	public List<CommerceShippingOption> getEnabledCommerceShippingOptions(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			Locale locale)
		throws CommerceShippingEngineException {

		try {
			User currentUser = _userService.getCurrentUser();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						commerceOrder.getCompanyId(), Http.Method.POST,
						_functionCommerceShippingEngineConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						_getPayloadJSONObject(commerceContext, commerceOrder),
						_functionCommerceShippingEngineConfiguration.
							enabledShippingEngineOptionsPath(),
						currentUser.getUserId()
					).get()));

			return _getCommerceShippingOptions(jsonObject);
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new CommerceShippingEngineException(exception.getMessage());
		}
	}

	@Override
	public String getKey() {
		return _functionCommerceShippingEngineConfiguration.key();
	}

	@Override
	public String getName(Locale locale) {
		try {
			User currentUser = _userService.getCurrentUser();

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				new String(
					_portalCatapult.launch(
						currentUser.getCompanyId(), Http.Method.POST,
						_functionCommerceShippingEngineConfiguration.
							oAuth2ApplicationExternalReferenceCode(),
						JSONUtil.put("locale", locale),
						_functionCommerceShippingEngineConfiguration.
							shippingEngineLocalizedNamePath(),
						currentUser.getUserId()
					).get()));

			return jsonObject.getString("name");
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_functionCommerceShippingEngineConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionCommerceShippingEngineConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		String key = getKey();

		if (key == null) {
			return;
		}

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			if (key.equals(commerceShippingMethod.getEngineKey())) {
				_commerceShippingMethodLocalService.
					deleteCommerceShippingMethod(
						commerceShippingMethod.getCommerceShippingMethodId());
			}
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties)
		throws PortalException {

		_functionCommerceShippingEngineConfiguration =
			ConfigurableUtil.createConfigurable(
				FunctionCommerceShippingEngineConfiguration.class, properties);

		List<CommerceShippingMethod> commerceShippingMethods =
			_commerceShippingMethodLocalService.getCommerceShippingMethods(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			String key = (String)properties.get("key");

			if (key.equals(commerceShippingMethod.getEngineKey())) {
				_commerceShippingMethodLocalService.
					deleteCommerceShippingMethod(
						commerceShippingMethod.getCommerceShippingMethodId());
			}
		}
	}

	private List<CommerceShippingOption> _getCommerceShippingOptions(
		JSONObject jsonObject) {

		List<CommerceShippingOption> commerceShippingOptions =
			new ArrayList<>();

		JSONArray shippingOptionJSONArray = jsonObject.getJSONArray(
			"shippingOptions");

		shippingOptionJSONArray.forEach(
			object -> {
				JSONObject shippingOptionJSONObject = (JSONObject)object;

				commerceShippingOptions.add(
					new CommerceShippingOption(
						(BigDecimal)GetterUtil.getNumber(
							shippingOptionJSONObject.get("amount")),
						shippingOptionJSONObject.getString("shippingMethodKey"),
						shippingOptionJSONObject.getString("key"),
						shippingOptionJSONObject.getString("name"),
						shippingOptionJSONObject.getDouble("priority")));
			});

		return commerceShippingOptions;
	}

	private JSONObject _getPayloadJSONObject(
			CommerceContext commerceContext, CommerceOrder commerceOrder)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				commerceContext.getCommerceChannelId());

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(), getKey());

		UnicodeProperties typeSettingsUnicodeProperties =
			commerceShippingMethod.getTypeSettingsUnicodeProperties();

		JSONObject typeSettingsJSONObject = _jsonFactory.createJSONObject();

		typeSettingsUnicodeProperties.forEach(
			(key, value) -> typeSettingsJSONObject.put(key, value));

		return JSONUtil.put(
			"commerceContext", _jsonFactory.looseSerializeDeep(commerceContext)
		).put(
			"commerceOrder", _jsonFactory.looseSerializeDeep(commerceOrder)
		).put(
			"typeSettings", typeSettingsJSONObject
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FunctionCommerceShippingEngine.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	private volatile FunctionCommerceShippingEngineConfiguration
		_functionCommerceShippingEngineConfiguration;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortalCatapult _portalCatapult;

	@Reference
	private UserService _userService;

}