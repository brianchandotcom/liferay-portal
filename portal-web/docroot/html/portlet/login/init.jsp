<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.AddressCityException" %><%@
page import="com.liferay.portal.AddressStreetException" %><%@
page import="com.liferay.portal.AddressZipException" %><%@
page import="com.liferay.portal.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.ContactBirthdayException" %><%@
page import="com.liferay.portal.ContactFirstNameException" %><%@
page import="com.liferay.portal.ContactFullNameException" %><%@
page import="com.liferay.portal.ContactLastNameException" %><%@
page import="com.liferay.portal.CookieNotSupportedException" %><%@
page import="com.liferay.portal.DuplicateOpenIdException" %><%@
page import="com.liferay.portal.EmailAddressException" %><%@
page import="com.liferay.portal.NoSuchCountryException" %><%@
page import="com.liferay.portal.NoSuchListTypeException" %><%@
page import="com.liferay.portal.NoSuchRegionException" %><%@
page import="com.liferay.portal.PasswordExpiredException" %><%@
page import="com.liferay.portal.PhoneNumberException" %><%@
page import="com.liferay.portal.RequiredFieldException" %><%@
page import="com.liferay.portal.RequiredReminderQueryException" %><%@
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
page import="com.liferay.portal.kernel.util.CamelCaseUtil" %><%@
page import="com.liferay.portal.security.auth.FullNameDefinition" %><%@
page import="com.liferay.portal.security.auth.FullNameDefinitionFactory" %><%@
page import="com.liferay.portal.security.auth.FullNameField" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidator" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidatorFactory" %><%@
page import="com.liferay.portlet.login.util.LoginUtil" %>

<%
String authType = portletPreferences.getValue("authType", StringPool.BLANK);
%>

<%@ include file="/html/portlet/login/init-ext.jsp" %>