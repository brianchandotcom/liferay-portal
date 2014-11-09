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

package com.liferay.portlet.login.action;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;

import java.net.URL;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class OpenIdAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException();
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.READ)) {
				String redirect = readOpenIdResponse(
					themeDisplay, actionRequest, actionResponse);

				if (Validator.isNull(redirect)) {
					redirect = themeDisplay.getURLSignIn();
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				sendOpenIdRequest(themeDisplay, actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof OpenIDException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Error communicating with OpenID provider: " +
							e.getMessage());
				}

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				_log.error("Error processing the OpenID login", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			return actionMapping.findForward("portlet.login.login");
		}

		renderResponse.setTitle(themeDisplay.translate("open-id"));

		return actionMapping.findForward("portlet.login.open_id");
	}

	protected String getFirstValue(List<String> values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return values.get(0);
	}

	protected String getOpenIdProvider(URL endpointURL) {
		String hostName = endpointURL.getHost();

		for (String openIdProvider : PropsValues.OPEN_ID_PROVIDERS) {
			String openIdURLString = PropsUtil.get(
				PropsKeys.OPEN_ID_URL, new Filter(openIdProvider));

			if (hostName.equals(openIdURLString)) {
				return openIdProvider;
			}
		}

		return _OPEN_ID_PROVIDER_DEFAULT;
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected String readOpenIdResponse(
			ThemeDisplay themeDisplay, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		ConsumerManager consumerManager = OpenIdUtil.getConsumerManager();

		ParameterList parameterList = new ParameterList(
			request.getParameterMap());

		DiscoveryInformation discoveryInformation =
			(DiscoveryInformation)session.getAttribute(WebKeys.OPEN_ID_DISCO);

		if (discoveryInformation == null) {
			return null;
		}

		String receivingURL = ParamUtil.getString(request, "openid.return_to");

		VerificationResult verificationResult = consumerManager.verify(
			receivingURL, parameterList, discoveryInformation);

		Identifier identifier = verificationResult.getVerifiedId();

		if (identifier == null) {
			return null;
		}

		AuthSuccess authSuccess =
			(AuthSuccess)verificationResult.getAuthResponse();

		String firstName = null;
		String lastName = null;
		String emailAddress = null;

		if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension messageExtension = authSuccess.getExtension(
				SRegMessage.OPENID_NS_SREG);

			if (messageExtension instanceof SRegResponse) {
				SRegResponse sregResp = (SRegResponse)messageExtension;

				String fullName = GetterUtil.getString(
					sregResp.getAttributeValue(_OPEN_ID_SREG_ATTR_FULLNAME));

				String[] names = splitFullName(fullName);

				if (names != null) {
					firstName = names[0];
					lastName = names[1];
				}

				emailAddress = sregResp.getAttributeValue(
					_OPEN_ID_SREG_ATTR_EMAIL);
			}
		}

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension messageExtension = authSuccess.getExtension(
				AxMessage.OPENID_NS_AX);

			if (messageExtension instanceof FetchResponse) {
				FetchResponse fetchResponse = (FetchResponse)messageExtension;

				String openIdProvider = getOpenIdProvider(
					discoveryInformation.getOPEndpoint());

				String[] openIdAXTypes = PropsUtil.getArray(
					PropsKeys.OPEN_ID_AX_SCHEMA, new Filter(openIdProvider));

				for (String openIdAXType : openIdAXTypes) {
					if (openIdAXType.equals(_OPEN_ID_AX_ATTR_EMAIL)) {
						if (Validator.isNull(emailAddress)) {
							emailAddress = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_EMAIL));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_FIRST_NAME)) {
						if (Validator.isNull(firstName)) {
							firstName = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_FIRST_NAME));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_FULL_NAME)) {
						String fullName = fetchResponse.getAttributeValue(
							_OPEN_ID_AX_ATTR_FULL_NAME);

						String[] names = splitFullName(fullName);

						if (names != null) {
							if (Validator.isNull(firstName)) {
								firstName = names[0];
							}

							if (Validator.isNull(lastName)) {
								lastName = names[1];
							}
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_AX_ATTR_LAST_NAME)) {
						if (Validator.isNull(lastName)) {
							lastName = getFirstValue(
								fetchResponse.getAttributeValues(
									_OPEN_ID_AX_ATTR_LAST_NAME));
						}
					}
				}
			}
		}

		String openId = OpenIdUtil.normalize(authSuccess.getIdentity());

		User user = UserLocalServiceUtil.fetchUserByOpenId(
			themeDisplay.getCompanyId(), openId);

		if (user != null) {
			session.setAttribute(
				WebKeys.OPEN_ID_LOGIN, new Long(user.getUserId()));

			return null;
		}

		if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(emailAddress)) {

			SessionMessages.add(request, "openIdUserInformationMissing");

			if (_log.isInfoEnabled()) {
				_log.info(
					"The OpenID provider did not send the required " +
						"attributes to create an account");
			}

			String createAccountURL = PortalUtil.getCreateAccountURL(
				request, themeDisplay);

			createAccountURL = HttpUtil.setParameter(
				createAccountURL, "openId", openId);

			session.setAttribute(WebKeys.OPEN_ID_LOGIN_PENDING, Boolean.TRUE);

			return createAccountURL;
		}

		long creatorUserId = 0;
		long companyId = themeDisplay.getCompanyId();
		boolean autoPassword = false;
		String password1 = PwdGenerator.getPassword();
		String password2 = password1;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		Locale locale = themeDisplay.getLocale();
		String middleName = StringPool.BLANK;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		user = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		session.setAttribute(WebKeys.OPEN_ID_LOGIN, new Long(user.getUserId()));

		return null;
	}

	protected void sendOpenIdRequest(
			ThemeDisplay themeDisplay, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);
		HttpSession session = request.getSession();

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		String openId = ParamUtil.getString(actionRequest, "openId");

		PortletURL portletURL = actionResponseImpl.createActionURL();

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter(Constants.CMD, Constants.READ);
		portletURL.setParameter("struts_action", "/login/open_id");

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		List<DiscoveryInformation> discoveries = manager.discover(openId);

		DiscoveryInformation discovered = manager.associate(discoveries);

		session.setAttribute(WebKeys.OPEN_ID_DISCO, discovered);

		AuthRequest authRequest = manager.authenticate(
			discovered, portletURL.toString(), themeDisplay.getPortalURL());

		if (UserLocalServiceUtil.fetchUserByOpenId(
				themeDisplay.getCompanyId(), openId) != null) {

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		String screenName = OpenIdUtil.getScreenName(openId);

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			themeDisplay.getCompanyId(), screenName);

		if (user != null) {
			UserLocalServiceUtil.updateOpenId(user.getUserId(), openId);

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		FetchRequest fetchRequest = FetchRequest.createFetchRequest();

		String openIdProvider = getOpenIdProvider(discovered.getOPEndpoint());

		String[] openIdAXTypes = PropsUtil.getArray(
			PropsKeys.OPEN_ID_AX_SCHEMA, new Filter(openIdProvider));

		for (String openIdAXType : openIdAXTypes) {
			fetchRequest.addAttribute(
				openIdAXType,
				PropsUtil.get(
					_OPEN_ID_AX_TYPE.concat(openIdAXType),
					new Filter(openIdProvider)),
				true);
		}

		authRequest.addExtension(fetchRequest);

		SRegRequest sRegRequest = SRegRequest.createFetchRequest();

		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_EMAIL, true);
		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_FULLNAME, true);

		authRequest.addExtension(sRegRequest);

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	protected String[] splitFullName(String fullName) {
		if (Validator.isNull(fullName)) {
			return null;
		}

		int pos = fullName.indexOf(CharPool.SPACE);

		if ((pos != -1) && ((pos + 1) < fullName.length())) {
			String[] names = new String[2];

			names[0] = fullName.substring(0, pos);
			names[1] = fullName.substring(pos + 1);

			return names;
		}

		return null;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static final String _OPEN_ID_AX_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_AX_ATTR_FIRST_NAME = "firstname";

	private static final String _OPEN_ID_AX_ATTR_FULL_NAME = "fullname";

	private static final String _OPEN_ID_AX_ATTR_LAST_NAME = "lastname";

	private static final String _OPEN_ID_AX_TYPE = "open.id.ax.type.";

	private static final String _OPEN_ID_PROVIDER_DEFAULT = "default";

	private static final String _OPEN_ID_SREG_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_SREG_ATTR_FULLNAME = "fullname";

	private static final Log _log = LogFactoryUtil.getLog(OpenIdAction.class);

}