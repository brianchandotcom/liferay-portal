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

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = OAuth2ControllerFactory.class)
public class OAuth2ControllerFactory {

	public OAuth2Controller getJSONOAuth2Controller(
		Function<PortletRequest, String> function) {

		return new JSONOAuth2Controller(function);
	}

	public OAuth2Controller getRedirectingOAuth2Controller() {
		return new RedirectingOAuth2Controller();
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private OAuth2Result _getOAuth2Result(
		PortletRequest portletRequest,
		UnsafeFunction<PortletRequest, JSONObject, PortalException>
			unsafeFunction,
		Function<PortletRequest, String> function) {

		try {
			long companyId = _portal.getCompanyId(portletRequest);
			long userId = _portal.getUserId(portletRequest);

			if (_oAuth2Manager.hasAccessToken(companyId, userId)) {
				return new OAuth2Result(unsafeFunction.apply(portletRequest));
			}

			String state = PwdGenerator.getPassword(5);

			OAuth2StateUtil.save(
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(portletRequest)),
				new OAuth2State(
					userId, function.apply(portletRequest),
					_getFailureURL(portletRequest), state));

			return new OAuth2Result(
				_oAuth2Manager.getAuthorizationURL(
					companyId, _portal.getPortalURL(portletRequest), state));
		}
		catch (PortalException portalException) {
			return new OAuth2Result(portalException);
		}
	}

	private String _getRenderURL(PortletRequest portletRequest) {
		return PortletURLBuilder.create(
			_portletURLFactory.create(
				portletRequest, _portal.getPortletId(portletRequest),
				PortletRequest.RENDER_PHASE)
		).setParameter(
			"folderId", ParamUtil.getString(portletRequest, "folderId")
		).setParameter(
			"repositoryId", ParamUtil.getString(portletRequest, "repositoryId")
		).buildString();
	}

	private String _translate(Locale locale, String key) {
		return _language.get(locale, key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ControllerFactory.class);

	@Reference
	private Language _language;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	private static class OAuth2Result {

		public OAuth2Result(JSONObject responseJSONObject) {
			_responseJSONObject = responseJSONObject;

			_portalException = null;
			_redirectURL = null;
		}

		public OAuth2Result(PortalException portalException) {
			_portalException = portalException;

			_responseJSONObject = null;
			_redirectURL = null;
		}

		public OAuth2Result(String redirectURL) {
			_redirectURL = redirectURL;

			_portalException = null;
			_responseJSONObject = null;
		}

		public PortalException getPortalException() {
			return _portalException;
		}

		public String getRedirectURL() {
			return _redirectURL;
		}

		public JSONObject getResponseJSONObject() {
			if (_redirectURL != null) {
				return JSONUtil.put("redirectURL", _redirectURL);
			}

			return Optional.ofNullable(
				_responseJSONObject
			).orElseGet(
				JSONFactoryUtil::createJSONObject
			);
		}

		private final PortalException _portalException;
		private final String _redirectURL;
		private final JSONObject _responseJSONObject;

	}

	private class JSONOAuth2Controller implements OAuth2Controller {

		public JSONOAuth2Controller(Function<PortletRequest, String> function) {
			_function = function;
		}

		@Override
		public void execute(
				PortletRequest portletRequest, PortletResponse portletResponse,
				UnsafeFunction<PortletRequest, JSONObject, PortalException>
					unsafeFunction)
			throws PortalException {

			try {
				OAuth2Result oAuth2Result = _getOAuth2Result(
					portletRequest, unsafeFunction, _function);

				PortalException portalException =
					oAuth2Result.getPortalException();

				if (Objects.nonNull(portalException)) {
					_log.error(portalException);

					JSONPortletResponseUtil.writeJSON(
						portletRequest, portletResponse,
						JSONUtil.put(
							"error",
							_translate(
								_portal.getLocale(portletRequest),
								"your-request-failed-to-complete")));
				}
				else {
					JSONPortletResponseUtil.writeJSON(
						portletRequest, portletResponse,
						oAuth2Result.getResponseJSONObject());
				}
			}
			catch (IOException ioException) {
				throw new PortalException(ioException);
			}
		}

		private final Function<PortletRequest, String> _function;

	}

	private class RedirectingOAuth2Controller implements OAuth2Controller {

		public RedirectingOAuth2Controller() {
			_function = portletRequest -> _portal.getCurrentURL(
				_portal.getHttpServletRequest(portletRequest));
		}

		@Override
		public void execute(
				PortletRequest portletRequest, PortletResponse portletResponse,
				UnsafeFunction<PortletRequest, JSONObject, PortalException>
					unsafeFunction)
			throws PortalException {

			OAuth2Result oAuth2Result = _getOAuth2Result(
				portletRequest, unsafeFunction, _function);

			PortalException portalException = oAuth2Result.getPortalException();

			if (Objects.nonNull(portalException)) {
				_log.error(portalException);

				throw portalException;
			}

			JSONObject jsonObject = oAuth2Result.getResponseJSONObject();

			for (String fieldName : jsonObject.keySet()) {
				portletRequest.setAttribute(
					fieldName, jsonObject.getString(fieldName));
			}

			portletRequest.setAttribute(
				WebKeys.REDIRECT,
				Optional.ofNullable(
					oAuth2Result.getRedirectURL()
				).orElseGet(
					() -> _getRenderURL(portletRequest)
				));
		}

		private final Function<PortletRequest, String> _function;

	}

}