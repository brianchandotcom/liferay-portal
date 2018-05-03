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

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiNode node = null;
WikiPage wikiPage = null;

if (row != null) {
	Object rowObject = row.getObject();

	if (rowObject instanceof WikiNode) {
		node = (WikiNode)rowObject;
	}

	if (rowObject instanceof WikiPage) {
		wikiPage = (WikiPage)rowObject;
	}
}
else {
	node = (WikiNode)request.getAttribute("node_info_panel.jsp-wikiNode");
	wikiPage = (WikiPage)request.getAttribute("page_info_panel.jsp-wikiPage");
}
%>

<c:if test="<%= wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled() || wikiGroupServiceOverriddenConfiguration.emailPageUpdatedEnabled() %>">
	<c:choose>
		<c:when test="<%= (node != null) && WikiNodePermission.contains(permissionChecker, node, ActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiNode.class.getName(), node.getNodeId()) %>">
					<portlet:actionURL name="/wiki/edit_node" var="unsubscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						icon='<%= (row == null) ? "star" : StringPool.BLANK %>'
						linkCssClass='<%= (row == null) ? "icon-monospaced" : StringPool.BLANK %>'
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="/wiki/edit_node" var="subscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						icon='<%= (row == null) ? "star-o" : StringPool.BLANK %>'
						linkCssClass='<%= (row == null) ? "icon-monospaced" : StringPool.BLANK %>'
						markupView="lexicon"
						message="subscribe"
						url="<%= subscribeURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="<%= (wikiPage != null) && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiPage.class.getName(), wikiPage.getResourcePrimKey()) %>">
					<portlet:actionURL name="/wiki/edit_page" var="unsubscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						icon="star"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL name="/wiki/edit_page" var="subscribeURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
						<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						icon="star-o"
						linkCssClass="icon-monospaced"
						markupView="lexicon"
						message="subscribe"
						url="<%= subscribeURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</c:if>