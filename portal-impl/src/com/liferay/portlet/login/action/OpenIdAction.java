/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.NoSuchUserException;
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
import com.liferay.util.PwdGenerator;

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
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
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
					redirect =
						PortalUtil.getPortalURL(actionRequest) +
							themeDisplay.getURLSignIn();
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				sendOpenIdRequest(themeDisplay, actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateUserEmailAddressException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof OpenIDException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Error communicating with OpenID provider: " +
							e.getMessage());
				}

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
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			return mapping.findForward("portlet.login.login");
		}

		renderResponse.setTitle(themeDisplay.translate("open-id"));

		return mapping.findForward("portlet.login.open_id");
	}

	protected String getFirstValue(List<String> values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return values.get(0);
	}

	protected String getOpenIdHostType(URL endpoint) {
		String hostName = endpoint.getHost();

		String[] openIdHostTypes = PropsValues.OPEN_ID_HOST_TYPES;

		for (String openIdHostType : openIdHostTypes) {
			String openIdHost = PropsUtil.get(
				PropsKeys.OPEN_ID_HOST, new Filter(openIdHostType));

			if (hostName.equals(openIdHost)) {
				return openIdHostType;
			}
		}

		return "default";
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
		HttpSession session = request.getSession();

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		ParameterList params = new ParameterList(
			actionRequest.getParameterMap());

		DiscoveryInformation discovered =
			(DiscoveryInformation)session.getAttribute(WebKeys.OPEN_ID_DISCO);

		if (discovered == null) {
			return null;
		}

		String receivingUrl = ParamUtil.getString(
			actionRequest, "openid.return_to");

		VerificationResult verification = manager.verify(
			receivingUrl, params, discovered);

		Identifier verified = verification.getVerifiedId();

		if (verified == null) {
			return null;
		}

		AuthSuccess authSuccess = (AuthSuccess)verification.getAuthResponse();

		String firstName = null;
		String lastName = null;
		String emailAddress = null;

		if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
			MessageExtension ext = authSuccess.getExtension(
				SRegMessage.OPENID_NS_SREG);

			if (ext instanceof SRegResponse) {
				SRegResponse sregResp = (SRegResponse)ext;

				String fullName = GetterUtil.getString(
					sregResp.getAttributeValue(_OPEN_ID_ATTR_FULLNAME));

				String[] names = splitName(fullName);

				if (names != null) {
					firstName = names[0];
					lastName = names[1];
				}

				emailAddress = sregResp.getAttributeValue(_OPEN_ID_ATTR_EMAIL);
			}
		}

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension ext = authSuccess.getExtension(
				AxMessage.OPENID_NS_AX);

			if (ext instanceof FetchResponse) {
				FetchResponse fetchResp = (FetchResponse)ext;

				URL endpoint = discovered.getOPEndpoint();

				String openIdHost = getOpenIdHostType(endpoint);

				String[] openIdAXTypes = PropsUtil.getArray(
					PropsKeys.OPEN_ID_AX_TYPES, new Filter(openIdHost));

				for (String openIdAXType : openIdAXTypes) {
					if (openIdAXType.equals(_OPEN_ID_ATTR_EMAIL)) {
						if (Validator.isNull(emailAddress)) {
							emailAddress = getFirstValue(
								fetchResp.getAttributeValues(
									_OPEN_ID_ATTR_EMAIL));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_FIRSTNAME)) {
						if (Validator.isNull(firstName)) {
							firstName = getFirstValue(
								fetchResp.getAttributeValues(
									_OPEN_ID_ATTR_FIRSTNAME));
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_FULLNAME)) {
						String fullName = fetchResp.getAttributeValue(
							_OPEN_ID_ATTR_FULLNAME);

						String[] names = splitName(fullName);

						if (names != null) {
							if (Validator.isNull(firstName)) {
								firstName = names[0];
							}

							if (Validator.isNull(lastName)) {
								lastName = names[1];
							}
						}
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_LASTNAME)) {
						if (Validator.isNull(lastName)) {
							lastName = getFirstValue(
								fetchResp.getAttributeValues(
									_OPEN_ID_ATTR_LASTNAME));
						}
					}
				}
			}
		}

		String openId = OpenIdUtil.normalize(authSuccess.getIdentity());

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserByOpenId(
				themeDisplay.getCompanyId(), openId);
		}
		catch (NoSuchUserException nsue) {
			if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
				Validator.isNull(emailAddress)) {

				SessionMessages.add(request, "missingOpenIdUserInformation");

				if (_log.isInfoEnabled()) {
					_log.info(
						"The OpenID provider did not send the required " +
							"attributes to create an account");
				}

				String createAccountURL = PortalUtil.getCreateAccountURL(
					request, themeDisplay);

				createAccountURL = HttpUtil.setParameter(
					createAccountURL, "openId", openId);

				session.setAttribute(
					WebKeys.OPEN_ID_LOGIN_PENDING, Boolean.TRUE);

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
				locale, firstName, middleName, lastName, prefixId, suffixId,
				male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
				serviceContext);
		}

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

		portletURL.setParameter("struts_action", "/login/open_id");
		portletURL.setParameter(Constants.CMD, Constants.READ);
		portletURL.setParameter("saveLastPath", "0");

		ConsumerManager manager = OpenIdUtil.getConsumerManager();

		List<DiscoveryInformation> discoveries = manager.discover(openId);

		DiscoveryInformation discovered = manager.associate(discoveries);

		session.setAttribute(WebKeys.OPEN_ID_DISCO, discovered);

		AuthRequest authRequest = manager.authenticate(
			discovered, portletURL.toString(), themeDisplay.getPortalURL());

		try {
			UserLocalServiceUtil.getUserByOpenId(
				themeDisplay.getCompanyId(), openId);
		}
		catch (NoSuchUserException nsue) {
			String screenName = OpenIdUtil.getScreenName(openId);

			try {
				User user = UserLocalServiceUtil.getUserByScreenName(
					themeDisplay.getCompanyId(), screenName);

				UserLocalServiceUtil.updateOpenId(user.getUserId(), openId);
			}
			catch (NoSuchUserException nsue2) {
				FetchRequest fetch = FetchRequest.createFetchRequest();

				URL endpoint = discovered.getOPEndpoint();

				String openIdHost = getOpenIdHostType(endpoint);

				String[] openIdAXTypes = PropsUtil.getArray
					(PropsKeys.OPEN_ID_AX_TYPES, new Filter(openIdHost));

				for (String openIdAXType : openIdAXTypes) {
					if (openIdAXType.equals(_OPEN_ID_ATTR_EMAIL)) {
						fetch.addAttribute(
							_OPEN_ID_ATTR_EMAIL,
							PropsUtil.get(
								PropsKeys.OPEN_ID_AX_TYPE_EMAIL,
								new Filter(openIdHost)), true);
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_FIRSTNAME)) {
						fetch.addAttribute(
							_OPEN_ID_ATTR_FIRSTNAME,
							PropsUtil.get(
								PropsKeys.OPEN_ID_AX_TYPE_FIRST_NAME,
								new Filter(openIdHost)), true);
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_FULLNAME)) {
						fetch.addAttribute(
							_OPEN_ID_ATTR_FULLNAME,
							PropsUtil.get(
								PropsKeys.OPEN_ID_AX_TYPE_FULL_NAME,
									new Filter(openIdHost)), true);
					}
					else if (openIdAXType.equals(_OPEN_ID_ATTR_LASTNAME)) {
						fetch.addAttribute(
							_OPEN_ID_ATTR_LASTNAME,
							PropsUtil.get(
								PropsKeys.OPEN_ID_AX_TYPE_LAST_NAME,
								new Filter(openIdHost)), true);
					}
				}

				authRequest.addExtension(fetch);

				SRegRequest sregRequest = SRegRequest.createFetchRequest();

				sregRequest.addAttribute(_OPEN_ID_ATTR_FULLNAME, true);
				sregRequest.addAttribute(_OPEN_ID_ATTR_EMAIL, true);

				authRequest.addExtension(sregRequest);
			}
		}

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	protected String[] splitName(String fullName) {
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

	private static final String _OPEN_ID_ATTR_EMAIL = "email";
	private static final String _OPEN_ID_ATTR_FIRSTNAME = "firstName";
	private static final String _OPEN_ID_ATTR_FULLNAME = "fullname";
	private static final String _OPEN_ID_ATTR_LASTNAME = "lastName";

	private static Log _log = LogFactoryUtil.getLog(OpenIdAction.class);

}