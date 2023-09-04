<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);

	String backURL = request.getHeader(HttpHeaders.REFERER);

	if (Validator.isNull(backURL)) {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, KBPortletKeys.KNOWLEDGE_BASE_ADMIN, PortletRequest.RENDER_PHASE);

		backURL = portletURL.toString();
	}

	portletDisplay.setURLBack(backURL);
	portletDisplay.setURLBackTitle(portletDisplay.getTitle());

	renderResponse.setTitle(LanguageUtil.get(resourceBundle, "error"));
}
%>

<c:if test="<%= !rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) && !portletTitleBasedNavigation %>">
	<liferay-ui:error-header />
</c:if>

<c:choose>
	<c:when test="<%= rootPortletId.equals(KBPortletKeys.KNOWLEDGE_BASE_ARTICLE) %>">
		<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-selected-article-no-longer-exists" />
	</c:when>
	<c:otherwise>
		<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-article-could-not-be-found" />
	</c:otherwise>
</c:choose>

<liferay-ui:error exception="<%= NoSuchCommentException.class %>" message="the-comment-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchSubscriptionException.class %>" message="the-subscription-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchTemplateException.class %>" message="the-template-could-not-be-found" />

<liferay-ui:error-principal />