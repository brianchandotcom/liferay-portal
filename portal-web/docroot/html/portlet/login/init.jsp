<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.AddressCityException" %><%@
page import="com.liferay.portal.AddressStreetException" %><%@
page import="com.liferay.portal.AddressZipException" %><%@
page import="com.liferay.portal.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.ContactFirstNameException" %><%@
page import="com.liferay.portal.ContactFullNameException" %><%@
page import="com.liferay.portal.ContactLastNameException" %><%@
page import="com.liferay.portal.CookieNotSupportedException" %><%@
page import="com.liferay.portal.DuplicateOpenIdException" %><%@
page import="com.liferay.portal.DuplicateUserEmailAddressException" %><%@
page import="com.liferay.portal.DuplicateUserIdException" %><%@
page import="com.liferay.portal.DuplicateUserScreenNameException" %><%@
page import="com.liferay.portal.EmailAddressException" %><%@
page import="com.liferay.portal.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.NoSuchCountryException" %><%@
page import="com.liferay.portal.NoSuchListTypeException" %><%@
page import="com.liferay.portal.NoSuchRegionException" %><%@
page import="com.liferay.portal.PasswordExpiredException" %><%@
page import="com.liferay.portal.PhoneNumberException" %><%@
page import="com.liferay.portal.RequiredFieldException" %><%@
page import="com.liferay.portal.RequiredReminderQueryException" %><%@
page import="com.liferay.portal.ReservedUserEmailAddressException" %><%@
page import="com.liferay.portal.ReservedUserIdException" %><%@
page import="com.liferay.portal.ReservedUserScreenNameException" %><%@
page import="com.liferay.portal.SendPasswordException" %><%@
page import="com.liferay.portal.TermsOfUseException" %><%@
page import="com.liferay.portal.UserActiveException" %><%@
page import="com.liferay.portal.UserEmailAddressException" %><%@
page import="com.liferay.portal.UserIdException" %><%@
page import="com.liferay.portal.UserLockoutException" %><%@
page import="com.liferay.portal.UserPasswordException" %><%@
page import="com.liferay.portal.UserReminderQueryException" %><%@
page import="com.liferay.portal.UserScreenNameException" %><%@
page import="com.liferay.portal.WebsiteURLException" %><%@
page import="com.liferay.portal.kernel.facebook.FacebookConnectUtil" %><%@
page import="com.liferay.portal.security.auth.AuthException" %><%@
page import="com.liferay.portal.util.OpenIdUtil" %><%@
page import="com.liferay.portlet.login.util.LoginUtil" %>

<%@ page import="org.openid4java.association.AssociationException" %><%@
page import="org.openid4java.consumer.ConsumerException" %><%@
page import="org.openid4java.discovery.DiscoveryException" %><%@
page import="org.openid4java.message.MessageException" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String authType = preferences.getValue("authType", StringPool.BLANK);
%>

<%@ include file="/html/portlet/login/init-ext.jsp" %>