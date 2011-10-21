/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.OpenIdUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.util.PwdGenerator;

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
				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof OpenIDException) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Error communicating with OpenID provider: " +
							e.getMessage());
				}

				SessionErrors.add(actionRequest, e.getClass().getName());
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

		renderResponse.setTitle(themeDisplay.translate("open-id"));

		return mapping.findForward("portlet.login.open_id");
	}

	protected String getFirstValue(List<String> values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return values.get(0);
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
					sregResp.getAttributeValue("fullname"));

				int pos = fullName.indexOf(CharPool.SPACE);

				if ((pos != -1) && ((pos + 1) < fullName.length())) {
					firstName = fullName.substring(0, pos);
					lastName = fullName.substring(pos + 1);
				}

				emailAddress = sregResp.getAttributeValue("email");
			}
		}

		if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
			MessageExtension ext = authSuccess.getExtension(
				AxMessage.OPENID_NS_AX);

			if (ext instanceof FetchResponse) {
				FetchResponse fetchResp = (FetchResponse)ext;

				if (Validator.isNull(firstName)) {
					firstName = getFirstValue(
						fetchResp.getAttributeValues("firstName"));
				}

				if (Validator.isNull(lastName)) {
					lastName = getFirstValue(
						fetchResp.getAttributeValues("lastName"));
				}

				if (Validator.isNull(emailAddress)) {
					emailAddress = getFirstValue(
						fetchResp.getAttributeValues("email"));
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

		if (!OpenIdUtil.isEnabled(themeDisplay.getCompanyId())) {
			return;
		}

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

				fetch.addAttribute(
					"email", "http://schema.openid.net/contact/email", true);
				fetch.addAttribute(
					"firstName", "http://schema.openid.net/namePerson/first",
					true);
				fetch.addAttribute(
					"lastName", "http://schema.openid.net/namePerson/last",
					true);

				authRequest.addExtension(fetch);

				SRegRequest sregRequest = SRegRequest.createFetchRequest();

				sregRequest.addAttribute("fullname", true);
				sregRequest.addAttribute("email", true);

				authRequest.addExtension(sregRequest);
			}
		}

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(OpenIdAction.class);

}