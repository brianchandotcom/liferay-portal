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

package com.liferay.object.internal.action.executor;

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.configuration.FunctionObjectActionExecutorImplConfiguration;
import com.liferay.osgi.util.factory.FactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.object.internal.configuration.FunctionObjectActionExecutorImplConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ObjectActionExecutor.class
)
public class FunctionObjectActionExecutorImpl implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			payloadJSONObject.toString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(_location);
		options.setMethod(Http.Method.POST);
		options.setTimeout(_timeout);

		// TODO

		//_authorize(companyId, options, userId);

		_http.URLtoByteArray(options);
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		_key = StringBundler.concat(
			ObjectActionExecutorConstants.KEY_FUNCTION, StringPool.POUND,
			FactoryUtil.getExternalReferenceCode(properties));

		FunctionObjectActionExecutorImplConfiguration
			functionObjectActionExecutorImplConfiguration =
				ConfigurableUtil.createConfigurable(
					FunctionObjectActionExecutorImplConfiguration.class,
					properties);

		_oAuth2Application =
			_oAuth2ApplicationLocalService.
				getOAuth2ApplicationByExternalReferenceCode(
					FactoryUtil.getCompanyId(properties, _companyLocalService),
					functionObjectActionExecutorImplConfiguration.
						oAuth2ApplicationExternalReferenceCode());

		_location = _getLocation(
			functionObjectActionExecutorImplConfiguration, _oAuth2Application);

		_timeout = functionObjectActionExecutorImplConfiguration.timeout();
	}

	private String _getLocation(
		FunctionObjectActionExecutorImplConfiguration
			functionObjectActionExecutorImplConfiguration,
		OAuth2Application oAuth2Application) {

		String resourcePath =
			functionObjectActionExecutorImplConfiguration.resourcePath();

		if (resourcePath.contains(Http.PROTOCOL_DELIMITER)) {
			return resourcePath;
		}

		String homePageURL = oAuth2Application.getHomePageURL();

		if (homePageURL.endsWith(StringPool.SLASH)) {
			homePageURL = homePageURL.substring(0, homePageURL.length() - 1);
		}

		if (resourcePath.startsWith(StringPool.SLASH)) {
			resourcePath = resourcePath.substring(1);
		}

		return StringBundler.concat(
			homePageURL, StringPool.SLASH, resourcePath);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Http _http;

	private String _key;
	private String _location;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private int _timeout;

}