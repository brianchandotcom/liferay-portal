<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute("info_panel.jsp-kbArticle");
%>

<c:if test="<%= (kbArticle.isApproved() || !kbArticle.isFirstVersion()) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.SUBSCRIBE) %>">
	<c:choose>
		<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), KBArticle.class.getName(), kbArticle.getResourcePrimKey()) %>">
			<liferay-portlet:actionURL name="/knowledge_base/unsubscribe_kb_article" var="unsubscribeKBArticleURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="bell-off"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="unsubscribe"
				url="<%= unsubscribeKBArticleURL %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-portlet:actionURL name="/knowledge_base/subscribe_kb_article" var="subscribeKBArticleURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="bell-on"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="subscribe"
				url="<%= subscribeKBArticleURL %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>