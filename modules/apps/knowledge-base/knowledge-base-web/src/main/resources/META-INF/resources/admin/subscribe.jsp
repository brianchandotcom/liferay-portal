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

<%@ include file="/admin/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute("info_panel.jsp-kbArticle");
%>

<c:if test="<%= (kbArticle.isApproved() || !kbArticle.isFirstVersion()) && KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.SUBSCRIBE) %>">
	<c:choose>
		<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), KBArticle.class.getName(), kbArticle.getResourcePrimKey()) %>">
			<liferay-portlet:actionURL name="unsubscribeKBArticle" var="unsubscribeKBArticleURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="star"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="unsubscribe"
				url="<%= unsubscribeKBArticleURL %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-portlet:actionURL name="subscribeKBArticle" var="subscribeKBArticleURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="resourcePrimKey" value="<%= String.valueOf(kbArticle.getResourcePrimKey()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				icon="star-o"
				linkCssClass="icon-monospaced"
				markupView="lexicon"
				message="subscribe"
				url="<%= subscribeKBArticleURL %>"
			/>
		</c:otherwise>
	</c:choose>
</c:if>