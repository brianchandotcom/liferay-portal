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

<%@ include file="/init.jsp" %>

<%
String notificationNavigationItem = ParamUtil.getString(request, "notificationNavigationItem", "view-all-notification-templates");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceNotificationTemplatesDisplayContext commerceNotificationTemplatesDisplayContext = (CommerceNotificationTemplatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

PortletURL portletURL = commerceNotificationTemplatesDisplayContext.getPortletURL();

portletURL.setParameter("notificationNavigationItem", notificationNavigationItem);
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/navbar.jspf" %>

<liferay-frontend:management-bar
	searchContainerId="commerceNotificationTemplates"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "enabled", "disabled"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceNotificationTemplatesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceNotificationTemplatesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date", "name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceNotificationTemplatesDisplayContext.isShowAddButton() %>">
			<portlet:renderURL var="addCommerceNotificationTemplateURL">
				<portlet:param name="mvcRenderCommandName" value="editCommerceNotificationTemplate" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(resourceBundle, "add-notification-template") %>'
					url="<%= addCommerceNotificationTemplateURL %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceNotificationTemplates"
		searchContainer="<%= commerceNotificationTemplatesDisplayContext.getSearchContainer() %>"
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

			<%
			CommerceNotificationType commerceNotificationType = commerceNotificationTemplatesDisplayContext.getCommerceNotificationType(commerceNotificationTemplate.getType());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="type"
				value="<%= (commerceNotificationType == null) ? StringPool.BLANK : HtmlUtil.escape(commerceNotificationType.getLabel(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="enabled"
			>
				<c:choose>
					<c:when test="<%= commerceNotificationTemplate.isEnabled() %>">
						<liferay-ui:icon
							cssClass="commerce-admin-icon-check"
							icon="check"
							markupView="lexicon"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							cssClass="commerce-admin-icon-times"
							icon="times"
							markupView="lexicon"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

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