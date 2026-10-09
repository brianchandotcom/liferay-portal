/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.catapult.internal;

import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.catapult.PortalCatapultHeaderContributor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@Component(service = PortalCatapult.class)
public class PortalCatapultImpl implements PortalCatapult {

	@Override
	public Future<byte[]> launch(
			long companyId, Http.Method method,
			String oAuth2ApplicationExternalReferenceCode,
			JSONObject payloadJSONObject, String resourcePath, long userId)
		throws PortalException {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);

		if (payloadJSONObject != null) {
			options.setBody(
				payloadJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setFollowRedirects(false);

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.
				getOAuth2ApplicationByExternalReferenceCode(
					oAuth2ApplicationExternalReferenceCode, companyId);

		String location = _getLocation(oAuth2Application, resourcePath);

		options.setLocation(location);

		options.setMethod(method);

		_addRequestHeaders(
			companyId, location, oAuth2Application, options, userId);

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_localOAuthClient.consumeAccessToken(
						accessToken -> options.addHeader(
							HttpHeaders.AUTHORIZATION, "Bearer " + accessToken),
						oAuth2Application, userId);

					return null;
				});
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				PortalCatapultImpl.class.getName());

		return executorService.submit(
			() -> {
				try {
					byte[] bytes = _http.URLtoByteArray(options);

					Http.Response response = options.getResponse();

					for (int i = 0; i < _MAX_REDIRECTS; i++) {
						String redirectLocation = _getRedirectLocation(
							location, options, response);

						if (redirectLocation == null) {
							break;
						}

						options.setLocation(redirectLocation);
						options.setResponse(new Http.Response());

						bytes = _http.URLtoByteArray(options);

						response = options.getResponse();
					}

					if ((response.getResponseCode() >=
							HttpURLConnection.HTTP_MULT_CHOICE) ||
						(response.getResponseCode() <
							HttpURLConnection.HTTP_OK)) {

						throw new PortalException(new String(bytes));
					}

					return bytes;
				}
				catch (IOException ioException) {
					_log.error(ioException);

					return ReflectionUtil.throwException(ioException);
				}
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, PortalCatapultHeaderContributor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private void _addRequestHeaders(
		long companyId, String location, OAuth2Application oAuth2Application,
		Http.Options options, long userId) {

		Map<String, String> headers = new HashMap<>();

		for (PortalCatapultHeaderContributor portalCatapultHeaderContributor :
				_serviceTrackerList) {

			portalCatapultHeaderContributor.contribute(
				companyId, headers, oAuth2Application.getHomePageURL(),
				location, oAuth2Application.getFeaturesList(), userId);
		}

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String name = entry.getKey();

			if (StringUtil.equalsIgnoreCase(name, HttpHeaders.AUTHORIZATION) ||
				StringUtil.equalsIgnoreCase(name, HttpHeaders.CONTENT_TYPE)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add the reserved header \"" + name + "\"");
				}

				continue;
			}

			options.addHeader(name, entry.getValue());
		}
	}

	private String _getLocation(
		OAuth2Application oAuth2Application, String resourcePath) {

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

	private String _getOrigin(URI uri) {
		int port = uri.getPort();

		if (port == -1) {
			if (StringUtil.equalsIgnoreCase(uri.getScheme(), Http.HTTPS)) {
				port = Http.HTTPS_PORT;
			}
			else {
				port = Http.HTTP_PORT;
			}
		}

		return StringBundler.concat(
			uri.getScheme(), StringPool.COLON,
			StringUtil.toLowerCase(uri.getHost()), StringPool.COLON, port);
	}

	private String _getRedirectLocation(
		String location, Http.Options options, Http.Response response) {

		String redirect = response.getRedirect();

		if ((options.getMethod() != Http.Method.GET) ||
			(response.getResponseCode() >=
				HttpURLConnection.HTTP_BAD_REQUEST) ||
			(response.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) ||
			Validator.isNull(redirect)) {

			return null;
		}

		try {
			URI uri = new URI(options.getLocation());

			URI redirectURI = uri.resolve(new URI(redirect));

			if (Objects.equals(
					_getOrigin(new URI(location)), _getOrigin(redirectURI))) {

				return redirectURI.toString();
			}

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to follow the redirect to ", redirectURI,
						" because it leaves the origin of ", location));
			}
		}
		catch (URISyntaxException uriSyntaxException) {
			if (_log.isDebugEnabled()) {
				_log.debug(uriSyntaxException);
			}
		}

		return null;
	}

	private static final int _MAX_REDIRECTS = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCatapultImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	@Reference
	private Http _http;

	@Reference
	private LocalOAuthClient _localOAuthClient;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	private ServiceTrackerList<PortalCatapultHeaderContributor>
		_serviceTrackerList;

}