<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceNotificationsDisplayContext commerceNotificationsDisplayContext = (CommerceNotificationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	searchContainerId="commerceNotificationTemplates"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceNotificationsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceNotificationsDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceNotificationsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= commerceNotificationsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceNotificationsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceNotificationsDisplayContext.isShowAddButton() %>">
			<portlet:renderURL var="addCommerceNotificationTemplateURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceNotificationTemplate" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(resourceBundle, "add-notification-template") %>'
					url="<%= addCommerceNotificationTemplateURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceNotificationTemplates"
		searchContainer="<%= commerceNotificationsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.notification.model.CommerceNotificationTemplate"
			keyProperty="commerceNotificationTemplateId"
			modelVar="commerceNotificationTemplate"
		>
			<c:choose>
				<c:when test="<%= CommerceNotificationTemplatePermission.contains(permissionChecker, commerceNotificationTemplate, ActionKeys.UPDATE) %>">

					<%
					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter("mvcRenderCommandName", "editCommerceNotificationTemplate");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("commerceNotificationTemplateId", String.valueOf(commerceNotificationTemplate.getCommerceNotificationTemplateId()));
					%>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-content"
						href="<%= rowURL %>"
						property="name"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="name"
					/>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="enabled"
				value='<%= LanguageUtil.get(request, commerceNotificationTemplate.isEnabled() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-content"
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/notification_template_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>