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

package com.liferay.analytics.connector.web.internal.security.auth.verifier;

import com.liferay.analytics.connector.web.internal.configuration.AnalyticsConnectorConfiguration;
import com.liferay.analytics.connector.web.internal.constants.AnalyticsConnectorConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifier;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.service.access.policy.ServiceAccessPolicy;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	enabled = false,
	property = "auth.verifier.AnalyticsConnectorAuthVerifier.urls.includes=/api/jsonws/*",
	service = AuthVerifier.class
)
public class AnalyticsConnectorAuthVerifier implements AuthVerifier {

	@Override
	public String getAuthType() {
		Class<?> clazz = getClass();

		return clazz.getSimpleName();
	}

	@Override
	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException {

		AuthVerifierResult authVerifierResult = new AuthVerifierResult();

		HttpServletRequest httpServletRequest =
			accessControlContext.getRequest();

		String signature = httpServletRequest.getHeader(
			"Liferay-Analytics-Cloud-Security-Signature");

		if (signature == null) {
			return authVerifierResult;
		}

		try {
			JSONObject jsonObject = _getConfigurationJSONObject(
				_portal.getCompanyId(httpServletRequest));

			if (jsonObject == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Missing security configuration");
				}

				return authVerifierResult;
			}

			Set<String> hostsAllowed = JSONUtil.toStringSet(
				jsonObject.getJSONArray("hostsAllowed"));

			if (!hostsAllowed.isEmpty() &&
				!hostsAllowed.contains(httpServletRequest.getRemoteAddr())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Access denied for " +
							httpServletRequest.getRemoteAddr());
				}

				return authVerifierResult;
			}

			String timestamp = httpServletRequest.getHeader(
				"Liferay-Analytics-Cloud-Security-Timestamp");

			if ((System.currentTimeMillis() - GetterUtil.getLong(timestamp)) >
					_EXPIRATION) {

				if (_log.isDebugEnabled()) {
					_log.debug("Signature timestamp expired " + timestamp);
				}

				return authVerifierResult;
			}

			if (!_validateSignature(
					httpServletRequest, jsonObject.getString("publicKey"),
					signature, timestamp)) {

				if (_log.isDebugEnabled()) {
					_log.debug("Invalid signature " + signature);
				}

				return authVerifierResult;
			}

			Map<String, Object> settings = authVerifierResult.getSettings();

			List<String> serviceAccessPolicyNames =
				(List<String>)settings.computeIfAbsent(
					ServiceAccessPolicy.SERVICE_ACCESS_POLICY_NAMES,
					value -> new ArrayList<>());

			serviceAccessPolicyNames.add(
				AnalyticsConnectorConstants.SERVICE_ACCESS_POLICY_NAME);

			authVerifierResult.setState(AuthVerifierResult.State.SUCCESS);
			authVerifierResult.setUserId(
				_getAnalyticsAdminUserId(
					_portal.getCompanyId(httpServletRequest)));

			return authVerifierResult;
		}
		catch (Exception e) {
			throw new AuthException(e);
		}
	}

	private long _getAnalyticsAdminUserId(long companyId) {
		User user = _userLocalService.fetchUserByScreenName(
			companyId, AnalyticsConnectorConstants.SCREEN_NAME_ANALYTICS_ADMIN);

		return user.getUserId();
	}

	private JSONObject _getConfigurationJSONObject(long companyId)
		throws Exception {

		AnalyticsConnectorConfiguration analyticsConnectorConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConnectorConfiguration.class, companyId);

		String code = analyticsConnectorConfiguration.code();

		if (Validator.isNotNull(code)) {
			return JSONFactoryUtil.createJSONObject(
				new String(Base64.decode(code)));
		}

		return null;
	}

	private boolean _validateSignature(
			HttpServletRequest httpServletRequest, String publicKey,
			String signatureString, String timestamp)
		throws Exception {

		Signature signature = Signature.getInstance("DSA");

		KeyFactory keyFactory = KeyFactory.getInstance("DSA");

		signature.initVerify(
			keyFactory.generatePublic(
				new X509EncodedKeySpec(Base64.decode(publicKey))));

		Map<String, String> sortedParameters = new TreeMap<>();

		Map<String, String[]> parameters = httpServletRequest.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			sortedParameters.put(entry.getKey(), entry.getValue()[0]);
		}

		sortedParameters.put(
			"Liferay-Analytics-Cloud-Security-Timestamp", timestamp);

		StringBundler sb = new StringBundler((2 * sortedParameters.size()) + 2);

		sb.append(httpServletRequest.getServletPath());
		sb.append(httpServletRequest.getPathInfo());

		for (Map.Entry<String, String> entry : sortedParameters.entrySet()) {
			sb.append(entry.getKey());
			sb.append(entry.getValue());
		}

		String requestContent = sb.toString();

		signature.update(requestContent.getBytes());

		return signature.verify(Base64.decode(signatureString));
	}

	private static final long _EXPIRATION = 10 * 60 * 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsConnectorAuthVerifier.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}