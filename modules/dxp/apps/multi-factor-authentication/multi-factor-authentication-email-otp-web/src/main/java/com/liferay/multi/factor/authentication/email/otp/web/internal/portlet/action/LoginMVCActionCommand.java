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

package com.liferay.multi.factor.authentication.email.otp.web.internal.portlet.action;

import com.liferay.multi.factor.authentication.email.otp.web.internal.checker.MFAEmailOTPChecker;
import com.liferay.multi.factor.authentication.email.otp.web.internal.configuration.MFAEmailOTPConfiguration;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.LoginPortletKeys;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPPortletKeys;
import com.liferay.multi.factor.authentication.email.otp.web.internal.constants.MFAEmailOTPWebKeys;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.security.Key;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.filter.ActionRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = {
		"javax.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + LoginPortletKeys.LOGIN,
		"mvc.command.name=/login/login", "service.ranking:Integer=1"
	},
	service = MVCActionCommand.class
)
public class LoginMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		MFAEmailOTPConfiguration mfaEmailOTPConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				MFAEmailOTPConfiguration.class,
				_portal.getCompanyId(actionRequest));

		if (!mfaEmailOTPConfiguration.enabled()) {
			_loginMVCActionCommand.processAction(actionRequest, actionResponse);

			return;
		}

		String state = ParamUtil.getString(actionRequest, "state");

		if (!Validator.isBlank(state)) {
			actionRequest = _getActionRequestFromState(actionRequest, state);
		}

		String login = ParamUtil.getString(actionRequest, "login");
		String password = ParamUtil.getString(actionRequest, "password");

		if (!Validator.isBlank(login) && !Validator.isBlank(password)) {
			HttpServletRequest httpServletRequest =
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(actionRequest));

			long userId =
				AuthenticatedSessionManagerUtil.getAuthenticatedUserId(
					httpServletRequest, login, password, null);

			if ((userId > 0) &&
				!_mfaEmailOTPChecker.isBrowserVerified(
					httpServletRequest, userId)) {

				_redirectToVerify(userId, actionRequest, actionResponse);

				return;
			}
		}

		_loginMVCActionCommand.processAction(actionRequest, actionResponse);
	}

	private ActionRequest _getActionRequestFromState(
			ActionRequest actionRequest, String encryptedStateMap)
		throws Exception {

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(actionRequest));

		HttpSession httpSession = httpServletRequest.getSession();

		String mfaEmailOTPDigest = (String)httpSession.getAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_DIGEST);

		if (!StringUtil.equals(
				DigesterUtil.digest(encryptedStateMap), mfaEmailOTPDigest)) {

			throw new PrincipalException("User sent unverified data");
		}

		Map<String, Object> stateMap = _jsonFactory.looseDeserialize(
			Encryptor.decrypt(
				(Key)httpSession.getAttribute(
					MFAEmailOTPWebKeys.MFA_EMAIL_OTP_KEY),
				encryptedStateMap),
			Map.class);

		Map<String, Object> requestParameters =
			(Map<String, Object>)stateMap.get("requestParameters");

		for (Map.Entry<String, Object> entry : requestParameters.entrySet()) {
			if (entry.getValue() instanceof List) {
				entry.setValue(
					ListUtil.toArray(
						(List<?>)entry.getValue(), _STRING_ACCESSOR));
			}
		}

		return new ActionRequestWrapper(actionRequest) {

			@Override
			public String getParameter(String name) {
				return MapUtil.getString(requestParameters, name, null);
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return new HashMap(requestParameters);
			}

			@Override
			public Enumeration<String> getParameterNames() {
				return Collections.enumeration(requestParameters.keySet());
			}

			@Override
			public String[] getParameterValues(String name) {
				return (String[])requestParameters.get(name);
			}

		};
	}

	private LiferayPortletURL _getLiferayPortletURL(
		HttpServletRequest httpServletRequest, String redirectURL) {

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		long plid = 0;
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			plid = themeDisplay.getPlid();
		}

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			httpServletRequest,
			MFAEmailOTPPortletKeys.MFA_EMAIL_OTP_VERIFY_PORTLET, plid,
			PortletRequest.RENDER_PHASE);

		/*This seems tobe the most common ordering for these parameters across
		* the portal:
		* - saveLastPath
		* - mvc*
		* - redirect
		* */
		liferayPortletURL.setParameter(
			"saveLastPath", Boolean.FALSE.toString());
		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "/mfa_email_otp_verify/verify");
		liferayPortletURL.setParameter("redirect", redirectURL);

		return liferayPortletURL;
	}

	private void _redirectToVerify(
			long userId, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		ActionURL actionURL = liferayPortletResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, "/login/login");

		Key key = Encryptor.generateKey();

		Map<String, Object> stateMap = HashMapBuilder.<String, Object>put(
			"requestParameters", actionRequest.getParameterMap()
		).build();

		String encryptedStateMapJSON = Encryptor.encrypt(
			key, _jsonFactory.looseSerializeDeep(stateMap));

		actionURL.setParameter("state", encryptedStateMapJSON);

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(actionRequest));

		LiferayPortletURL liferayPortletURL = _getLiferayPortletURL(
			httpServletRequest, actionURL.toString());

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		if (LoginPortletKeys.FAST_LOGIN.equals(portletId)) {
			liferayPortletURL.setWindowState(actionRequest.getWindowState());
		}
		else {
			liferayPortletURL.setWindowState(WindowState.MAXIMIZED);
		}

		actionRequest.setAttribute(
			WebKeys.REDIRECT, liferayPortletURL.toString());

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(
			MFAEmailOTPWebKeys.MFA_EMAIL_OTP_DIGEST,
			DigesterUtil.digest(encryptedStateMapJSON));
		httpSession.setAttribute(MFAEmailOTPWebKeys.MFA_EMAIL_OTP_KEY, key);
		httpSession.setAttribute(WebKeys.USER_ID, userId);
	}

	private static final Accessor<Object, String> _STRING_ACCESSOR =
		new Accessor<Object, String>() {

			@Override
			public String get(Object object) {
				return String.valueOf(object);
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<Object> getTypeClass() {
				return Object.class;
			}

		};

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(component.name=com.liferay.login.web.internal.portlet.action.LoginMVCActionCommand)"
	)
	private MVCActionCommand _loginMVCActionCommand;

	@Reference
	private MFAEmailOTPChecker _mfaEmailOTPChecker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}