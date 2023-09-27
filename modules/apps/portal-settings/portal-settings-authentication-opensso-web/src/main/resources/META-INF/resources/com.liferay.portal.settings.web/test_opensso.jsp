<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/com.liferay.portal.settings.web/init.jsp" %>

<%
String openSsoLoginURL = ParamUtil.getString(request, "openSsoLoginURL");
String openSsoLogoutURL = ParamUtil.getString(request, "openSsoLogoutURL");
String openSsoServiceURL = ParamUtil.getString(request, "openSsoServiceURL");
String openSsoScreenNameAttr = ParamUtil.getString(request, "openSsoScreenNameAttr");
String openSsoEmailAddressAttr = ParamUtil.getString(request, "openSsoEmailAddressAttr");
String openSsoFirstNameAttr = ParamUtil.getString(request, "openSsoFirstNameAttr");
String openSsoLastNameAttr = ParamUtil.getString(request, "openSsoLastNameAttr");

List<String> urls = new ArrayList<String>();

urls.add(openSsoLoginURL);
urls.add(openSsoLogoutURL);
urls.add(openSsoServiceURL);
%>

<c:choose>
	<c:when test="<%= !OpenSSOUtil.isValidUrls(urls.toArray(new String[0])) %>">
		<liferay-ui:message key="liferay-has-failed-to-connect-to-the-opensso-server" />
	</c:when>
	<c:when test="<%= !OpenSSOUtil.isValidServiceUrl(openSsoServiceURL) %>">
		<liferay-ui:message key="liferay-has-failed-to-connect-to-the-opensso-services" />
	</c:when>
	<c:when test="<%= Validator.isNull(openSsoScreenNameAttr) || Validator.isNull(openSsoEmailAddressAttr) || Validator.isNull(openSsoFirstNameAttr) || Validator.isNull(openSsoLastNameAttr) %>">
		<liferay-ui:message key="please-map-each-of-the-user-properties-screen-name,-email-address,-first-name,-and-last-name-to-an-opensso-attribute" />
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="liferay-has-successfully-connected-to-the-opensso-server" />
	</c:otherwise>
</c:choose>