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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBCategory category = (MBCategory)row.getObject();

Set<Long> categorySubscriptionClassPKs = (Set<Long>)row.getParameter("categorySubscriptionClassPKs");
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/message_boards/edit_category" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>

		<portlet:renderURL var="moveURL">
			<portlet:param name="struts_action" value="/message_boards/move_category" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="submit"
			message="move"
			url="<%= moveURL %>"
		/>
	</c:if>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= MBCategory.class.getName() %>"
			modelResourceDescription="<%= category.getName() %>"
			resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.MESSAGE_BOARDS) %>">

		<c:if test="<%= enableRSS %>">

			<%
			if (category.getCategoryId() > 0) {
				rssURL.setParameter("mbCategoryId", String.valueOf(category.getCategoryId()));
			}
			else {
				rssURL.setParameter("groupId", String.valueOf(scopeGroupId));
			}
			%>

			<liferay-ui:rss
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				feedType="<%= rssFeedType %>"
				resourceURL="<%= rssURL %>"
			/>
		</c:if>

		<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= (categorySubscriptionClassPKs != null) && categorySubscriptionClassPKs.contains(category.getCategoryId()) %>">
					<portlet:actionURL var="unsubscribeURL">
						<portlet:param name="struts_action" value="/message_boards/edit_category" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						image="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL var="subscribeURL">
						<portlet:param name="struts_action" value="/message_boards/edit_category" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						image="subscribe"
						url="<%= subscribeURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/message_boards/edit_category" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>