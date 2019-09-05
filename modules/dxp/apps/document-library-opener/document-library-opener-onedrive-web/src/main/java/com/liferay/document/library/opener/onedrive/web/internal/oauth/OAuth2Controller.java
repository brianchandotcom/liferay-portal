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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Cristina González
 */
public class OAuth2Controller {

	public OAuth2Controller(
		Language language, OAuth2Manager oAuth2Manager, Portal portal,
		PortletURLFactory portletURLFactory,
		ResourceBundleLoader resourceBundleLoader) {

		_language = language;
		_oAuth2Manager = oAuth2Manager;
		_portal = portal;
		_portletURLFactory = portletURLFactory;
		_resourceBundleLoader = resourceBundleLoader;
	}

	public <T extends PortletRequest, R extends PortletResponse> void execute(
			T t, R r,
			UnsafeFunction<T, JSONObject, PortalException> unsafeFunction)
		throws PortalException {

		boolean redirect = ParamUtil.getBoolean(t, "redirect");

		try {
			OAuth2Result oAuth2Result = _executeWithOAuth2(t, unsafeFunction);

			if (redirect) {
				JSONObject jsonObject = oAuth2Result.getResponse();

				for (String fieldName : jsonObject.keySet()) {
					t.setAttribute(fieldName, jsonObject.getString(fieldName));
				}

				t.setAttribute(
					WebKeys.REDIRECT,
					Optional.ofNullable(
						oAuth2Result.getRedirectURL()
					).orElseGet(
						() -> _getRenderURL(t)
					));
			}
			else {
				JSONPortletResponseUtil.writeJSON(
					t, r, oAuth2Result.getResponse());
			}
		}
		catch (PortalException pe) {
			try {
				if (!redirect) {
					JSONPortletResponseUtil.writeJSON(
						t, r,
						JSONUtil.put(
							"error",
							_translateKey(
								_portal.getLocale(t),
								"your-request-failed-to-complete")));
				}
			}
			catch (IOException ioe) {
				throw new PortalException(ioe);
			}

			_log.error(pe.getMessage(), pe);

			throw pe;
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}
	}

	protected static class OAuth2Result {

		public OAuth2Result(JSONObject response) {
			_response = response;
			_redirectURL = null;
		}

		public OAuth2Result(String redirectURL) {
			_redirectURL = redirectURL;
			_response = null;
		}

		public String getRedirectURL() {
			return _redirectURL;
		}

		public JSONObject getResponse() {
			if (_redirectURL != null) {
				return JSONUtil.put("redirectURL", _redirectURL);
			}

			return Optional.ofNullable(
				_response
			).orElseGet(
				() -> JSONFactoryUtil.createJSONObject()
			);
		}

		private final String _redirectURL;
		private final JSONObject _response;

	}

	private <T extends PortletRequest> OAuth2Result _executeWithOAuth2(
			T t, UnsafeFunction<T, JSONObject, PortalException> unsafeFunction)
		throws PortalException {

		long companyId = _portal.getCompanyId(t);
		long userId = _portal.getUserId(t);

		if (_oAuth2Manager.hasAccessToken(companyId, userId)) {
			return new OAuth2Result(unsafeFunction.apply(t));
		}

		String state = PwdGenerator.getPassword(5);

		OAuth2StateUtil.save(
			_portal.getOriginalServletRequest(_portal.getHttpServletRequest(t)),
			new OAuth2State(
				userId, _getSuccessURL(t), _getFailureURL(t), state));

		return new OAuth2Result(
			_oAuth2Manager.getAuthorizationURL(
				companyId, _portal.getPortalURL(t), state));
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getRenderURL(PortletRequest portletRequest) {
		PortletURL portletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"folderId", ParamUtil.getString(portletRequest, "folderId"));
		portletURL.setParameter(
			"repositoryId",
			ParamUtil.getString(portletRequest, "repositoryId"));

		return portletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameters(portletRequest.getParameterMap());
		liferayPortletURL.setParameter(
			"redirect", String.valueOf(Boolean.TRUE));

		return liferayPortletURL.toString();
	}

	private String _translateKey(Locale locale, String key) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.get(resourceBundle, key);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2Controller.class);

	private final Language _language;
	private final OAuth2Manager _oAuth2Manager;
	private final Portal _portal;
	private final PortletURLFactory _portletURLFactory;
	private final ResourceBundleLoader _resourceBundleLoader;

}