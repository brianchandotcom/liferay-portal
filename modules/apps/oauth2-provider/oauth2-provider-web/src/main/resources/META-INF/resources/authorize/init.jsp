<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.oauth2.provider.web.internal.AssignableScopes" %><%@
page import="com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderWebKeys" %><%@
page import="com.liferay.oauth2.provider.web.internal.display.context.OAuth2AuthorizePortletDisplayContext" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%
OAuth2AuthorizePortletDisplayContext oAuth2AuthorizePortletDisplayContext = (OAuth2AuthorizePortletDisplayContext)request.getAttribute(OAuth2ProviderWebKeys.OAUTH2_AUTHORIZE_PORTLET_DISPLAY_CONTEXT);
%>