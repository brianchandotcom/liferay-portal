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

package com.liferay.portal.security.sso.openid.internal.portlet.action;

import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.sso.openid.OpenIdProvider;
import com.liferay.portal.security.sso.openid.OpenIdProviderRegistry;
import com.liferay.portal.security.sso.openid.constants.OpenIdWebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.module.configuration.OpenIdConfiguration",
	immediate = true,
	property = {
		"path=/login/open_id", "portlet.login.login=portlet.login.login",
		"portlet.login.open_id=portlet.login.open_id"
	},
	service = StrutsPortletAction.class
)
public class OpenIdAction extends BaseStrutsPortletAction {

	@Override
	public boolean isCheckMethodOnProcessAction() {
		return false;
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_openId.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException.MustBeEnabled(
				themeDisplay.getCompanyId(), OpenId.class.getName());
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.READ)) {
				String redirect = readOpenIdResponse(
					themeDisplay, actionRequest);

				if (Validator.isNull(redirect)) {
					redirect = themeDisplay.getURLSignIn();
				}

				redirect = PortalUtil.escapeRedirect(redirect);

				actionResponse.sendRedirect(redirect);
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
	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_openId.isEnabled(themeDisplay.getCompanyId())) {
			return _forwards.get("portlet.login.login");
		}

		renderResponse.setTitle(themeDisplay.translate("open-id"));

		return _forwards.get("portlet.login.open_id");
	}

	@Reference(unbind = "-")
	public void setOpenIdProviderRegistry(
		OpenIdProviderRegistry openIdProviderRegistry) {

		_openIdProviderRegistry = openIdProviderRegistry;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_forwards.put(
			"/common/referer_jsp.jsp",
			GetterUtil.getString(properties, "/common/referer_jsp.jsp"));
		_forwards.put(
			"portlet.login.login",
			GetterUtil.getString(properties, "portlet.login.login"));
		_forwards.put(
			"portlet.login.open_id",
			GetterUtil.getString(properties, "portlet.login.open_id"));

		try {
			_consumerManager = new ConsumerManager();

			_consumerManager.setAssociations(
				new InMemoryConsumerAssociationStore());
			_consumerManager.setNonceVerifier(new InMemoryNonceVerifier(5000));
		}
		catch (Exception e) {
			throw new IllegalStateException(
				"Unable to start consumer manager", e);
		}
	}

	protected String getFirstValue(List<String> values) {
		if ((values == null) || (values.size() < 1)) {
			return null;
		}

		return values.get(0);
	}

	protected String getScreenName(String openId) {
		String screenName = normalize(openId);

		if (screenName.startsWith(Http.HTTP_WITH_SLASH)) {
			screenName = screenName.substring(Http.HTTP_WITH_SLASH.length());
		}

		if (screenName.startsWith(Http.HTTPS_WITH_SLASH)) {
			screenName = screenName.substring(Http.HTTPS_WITH_SLASH.length());
		}

		screenName = StringUtil.replace(
			screenName, new String[] {StringPool.SLASH, StringPool.UNDERLINE},
			new String[] {StringPool.PERIOD, StringPool.PERIOD});

		return screenName;
	}

	protected String normalize(String identity) {
		if (identity.endsWith(StringPool.SLASH)) {
			return identity.substring(0, identity.length() - 1);
		}

		return identity;
	}

	protected String readOpenIdResponse(
			ThemeDisplay themeDisplay, ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		ParameterList parameterList = new ParameterList(
			request.getParameterMap());

		DiscoveryInformation discoveryInformation =
			(DiscoveryInformation)session.getAttribute(
				OpenIdWebKeys.OPEN_ID_DISCO);

		if (discoveryInformation == null) {
			return null;
		}

		String receivingURL = ParamUtil.getString(request, "openid.return_to");

		VerificationResult verificationResult = _consumerManager.verify(
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

				OpenIdProvider openIdProvider =
					_openIdProviderRegistry.getOpenIdProvider(
						discoveryInformation.getOPEndpoint());

				String[] openIdAXTypes = openIdProvider.getAxSchema();

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

		String openId = normalize(authSuccess.getIdentity());

		User user = _userLocalService.fetchUserByOpenId(
			themeDisplay.getCompanyId(), openId);

		if (user != null) {
			session.setAttribute(WebKeys.OPEN_ID_LOGIN, user.getUserId());

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
		long prefixId = 0;
		long suffixId = 0;
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

		user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		session.setAttribute(WebKeys.OPEN_ID_LOGIN, user.getUserId());

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

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(actionResponse);

		String openId = ParamUtil.getString(actionRequest, "openId");

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter(Constants.CMD, Constants.READ);
		portletURL.setParameter("struts_action", "/login/open_id");

		List<DiscoveryInformation> discoveryInformationList =
			_consumerManager.discover(openId);

		DiscoveryInformation discoveryInformation = _consumerManager.associate(
			discoveryInformationList);

		session.setAttribute(OpenIdWebKeys.OPEN_ID_DISCO, discoveryInformation);

		AuthRequest authRequest = _consumerManager.authenticate(
			discoveryInformation, portletURL.toString(),
			themeDisplay.getPortalURL());

		if (_userLocalService.fetchUserByOpenId(
				themeDisplay.getCompanyId(), openId) != null) {

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		String screenName = getScreenName(openId);

		User user = _userLocalService.fetchUserByScreenName(
			themeDisplay.getCompanyId(), screenName);

		if (user != null) {
			_userLocalService.updateOpenId(user.getUserId(), openId);

			response.sendRedirect(authRequest.getDestinationUrl(true));

			return;
		}

		FetchRequest fetchRequest = FetchRequest.createFetchRequest();

		OpenIdProvider openIdProvider =
			_openIdProviderRegistry.getOpenIdProvider(
				discoveryInformation.getOPEndpoint());

		Map<String, String> openIdAXTypes = openIdProvider.getAxTypes();

		for (String openIdAXType : openIdAXTypes.keySet()) {
			fetchRequest.addAttribute(
				openIdAXType, openIdAXTypes.get(openIdAXType), true);
		}

		authRequest.addExtension(fetchRequest);

		SRegRequest sRegRequest = SRegRequest.createFetchRequest();

		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_EMAIL, true);
		sRegRequest.addAttribute(_OPEN_ID_SREG_ATTR_FULLNAME, true);

		authRequest.addExtension(sRegRequest);

		response.sendRedirect(authRequest.getDestinationUrl(true));
	}

	@Reference(unbind = "-")
	protected void setOpenId(OpenId openId) {
		_openId = openId;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
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

	private static final String _OPEN_ID_AX_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_AX_ATTR_FIRST_NAME = "firstname";

	private static final String _OPEN_ID_AX_ATTR_FULL_NAME = "fullname";

	private static final String _OPEN_ID_AX_ATTR_LAST_NAME = "lastname";

	private static final String _OPEN_ID_SREG_ATTR_EMAIL = "email";

	private static final String _OPEN_ID_SREG_ATTR_FULLNAME = "fullname";

	private static final Log _log = LogFactoryUtil.getLog(OpenIdAction.class);

	private ConsumerManager _consumerManager;
	private final Map<String, String> _forwards = new HashMap<>();
	private volatile OpenId _openId;
	private volatile OpenIdProviderRegistry _openIdProviderRegistry;
	private volatile UserLocalService _userLocalService;

}