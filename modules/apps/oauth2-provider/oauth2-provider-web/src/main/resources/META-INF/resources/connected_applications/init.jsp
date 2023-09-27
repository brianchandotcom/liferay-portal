<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.oauth2.provider.model.OAuth2Authorization" %><%@
page import="com.liferay.oauth2.provider.service.OAuth2ApplicationLocalServiceUtil" %><%@
page import="com.liferay.oauth2.provider.service.OAuth2AuthorizationServiceUtil" %><%@
page import="com.liferay.oauth2.provider.web.internal.AssignableScopes" %><%@
page import="com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys" %><%@
page import="com.liferay.oauth2.provider.web.internal.display.context.OAuth2ConnectedApplicationsManagementToolbarDisplayContext" %><%@
page import="com.liferay.oauth2.provider.web.internal.display.context.OAuth2ConnectedApplicationsPortletDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.util.Date" %>

<%
OAuth2ConnectedApplicationsPortletDisplayContext oAuth2ConnectedApplicationsPortletDisplayContext = (OAuth2ConnectedApplicationsPortletDisplayContext)request.getAttribute(OAuth2ProviderWebKeys.OAUTH2_CONNECTED_APPLICATIONS_PORTLET_DISPLAY_CONTEXT);
%>