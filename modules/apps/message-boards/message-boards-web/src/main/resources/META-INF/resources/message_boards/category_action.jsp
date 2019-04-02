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

<%@ include file="/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBCategory category = (MBCategory)row.getObject();

Set<Long> categorySubscriptionClassPKs = (Set<Long>)request.getAttribute("view.jsp-categorySubscriptionClassPKs");

boolean defaultParentCategory = false;

if (category.getCategoryId() == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
	defaultParentCategory = true;
}

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (!defaultParentCategory) {
	modelResource = MBCategory.class.getName();
	modelResourceDescription = category.getName();
	resourcePrimKey = String.valueOf(category.getCategoryId());

	showPermissionsURL = MBCategoryPermission.contains(permissionChecker, category, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.message.boards";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = MBResourcePermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= !defaultParentCategory && MBCategoryPermission.contains(permissionChecker, category, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_category" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:renderURL var="moveURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/move_category" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="move"
			url="<%= moveURL %>"
		/>
	</c:if>

	<c:if test="<%= portletName.equals(MBPortletKeys.MESSAGE_BOARDS) && enableRSS %>">
		<liferay-rss:rss
			delta="<%= rssDelta %>"
			displayStyle="<%= rssDisplayStyle %>"
			feedType="<%= rssFeedType %>"
			url="<%= MBRSSUtil.getRSSURL(plid, category.getCategoryId(), 0, 0, themeDisplay) %>"
		/>
	</c:if>

	<%
	long categorySubscriptionClassPK = 0;

	boolean hasSubscriptionPermission = false;

	if (!defaultParentCategory) {
		categorySubscriptionClassPK = category.getCategoryId();

		hasSubscriptionPermission = MBCategoryPermission.contains(permissionChecker, category, ActionKeys.SUBSCRIBE);
	}
	else {
		categorySubscriptionClassPK = scopeGroupId;

		hasSubscriptionPermission = MBResourcePermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE);
	}
	%>

	<c:if test="<%= hasSubscriptionPermission && (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) %>">
		<c:choose>
			<c:when test="<%= (categorySubscriptionClassPKs != null) && categorySubscriptionClassPKs.contains(categorySubscriptionClassPK) %>">
				<portlet:actionURL name="/message_boards/edit_category" var="unsubscribeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="/message_boards/edit_category" var="subscribeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= showPermissionsURL %>">
		<liferay-security:permissionsURL
			modelResource="<%= modelResource %>"
			modelResourceDescription="<%= modelResourceDescription %>"
			resourcePrimKey="<%= resourcePrimKey %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= !defaultParentCategory && MBCategoryPermission.contains(permissionChecker, category, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/message_boards/edit_category" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>