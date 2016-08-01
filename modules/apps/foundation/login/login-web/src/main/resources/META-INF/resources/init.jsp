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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.login.web.internal.portlet.util.LoginUtil" %><%@
page import="com.liferay.petra.content.ContentUtil" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaConfigurationException" %><%@
page import="com.liferay.portal.kernel.captcha.CaptchaTextException" %><%@
page import="com.liferay.portal.kernel.exception.AddressCityException" %><%@
page import="com.liferay.portal.kernel.exception.AddressStreetException" %><%@
page import="com.liferay.portal.kernel.exception.AddressZipException" %><%@
page import="com.liferay.portal.kernel.exception.CompanyMaxUsersException" %><%@
page import="com.liferay.portal.kernel.exception.ContactBirthdayException" %><%@
page import="com.liferay.portal.kernel.exception.ContactNameException" %><%@
page import="com.liferay.portal.kernel.exception.CookieNotSupportedException" %><%@
page import="com.liferay.portal.kernel.exception.DuplicateOpenIdException" %><%@
page import="com.liferay.portal.kernel.exception.EmailAddressException" %><%@
page import="com.liferay.portal.kernel.exception.GroupFriendlyURLException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchCountryException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchListTypeException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchRegionException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchUserException" %><%@
page import="com.liferay.portal.kernel.exception.PasswordExpiredException" %><%@
page import="com.liferay.portal.kernel.exception.PhoneNumberException" %><%@
page import="com.liferay.portal.kernel.exception.PhoneNumberExtensionException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredFieldException" %><%@
page import="com.liferay.portal.kernel.exception.RequiredReminderQueryException" %><%@
page import="com.liferay.portal.kernel.exception.SendPasswordException" %><%@
page import="com.liferay.portal.kernel.exception.TermsOfUseException" %><%@
page import="com.liferay.portal.kernel.exception.UserActiveException" %><%@
page import="com.liferay.portal.kernel.exception.UserEmailAddressException" %><%@
page import="com.liferay.portal.kernel.exception.UserIdException" %><%@
page import="com.liferay.portal.kernel.exception.UserLockoutException" %><%@
page import="com.liferay.portal.kernel.exception.UserPasswordException" %><%@
page import="com.liferay.portal.kernel.exception.UserReminderQueryException" %><%@
page import="com.liferay.portal.kernel.exception.UserScreenNameException" %><%@
page import="com.liferay.portal.kernel.exception.WebsiteURLException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.CompanyConstants" %><%@
page import="com.liferay.portal.kernel.model.Contact" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.security.auth.AuthException" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinition" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinitionFactory" %><%@
page import="com.liferay.portal.kernel.security.auth.PrincipalException" %><%@
page import="com.liferay.portal.kernel.security.auth.ScreenNameValidator" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.ClassLoaderUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PortletKeys" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.security.auth.ScreenNameValidatorFactory" %><%@
page import="com.liferay.portal.util.PropsValues" %>

<%@ page import="java.util.Calendar" %><%@
page import="java.util.Date" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String authType = portletPreferences.getValue("authType", StringPool.BLANK);
%>

<%@ include file="/init-ext.jsp" %>