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
MDRActionDisplayContext mdrActionDisplayContext = new MDRActionDisplayContext(renderRequest, renderResponse, resourceBundle);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= mdrActionDisplayContext.getActionNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="actionActions"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= mdrActionDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= mdrActionDisplayContext.getOrderByCol() %>"
			orderByType="<%= mdrActionDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= mdrActionDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= mdrActionDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= mdrActionDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteActions"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/mobile_device_rules/edit_action" var="deleteURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
</portlet:actionURL>

<%
PortletURL portletURL = mdrActionDisplayContext.getPortletURL();
%>

<aui:form action="<%= deleteURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<c:if test="<%= MDRPermission.contains(permissionChecker, groupId, ActionKeys.ADD_RULE_GROUP) %>">
		<liferay-portlet:renderURL var="addURL">
			<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(mdrActionDisplayContext.getRuleGroupInstanceId()) %>" />
		</liferay-portlet:renderURL>

		<div class="button-holder text-center">
			<aui:button href="<%= addURL.toString() %>" value="add-action" />
		</div>
	</c:if>

	<liferay-ui:search-container
		id="actionActions"
		searchContainer="<%= mdrActionDisplayContext.getActionSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.mobile.device.rules.model.MDRAction"
			escapedModel="<%= true %>"
			keyProperty="actionId"
			modelVar="action"
		>

			<%
			Group group = GroupLocalServiceUtil.getGroup(action.getGroupId());
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(mdrActionDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="mobile-portrait"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h6 class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - action.getModifiedDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</h6>

						<h5>
							<%= action.getName(locale) %>
						</h5>

						<h6 class="text-default">
							<%= action.getDescription(locale) %>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="type" /></strong>: <%= LanguageUtil.get(resourceBundle, action.getType()) %>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="scope" /></strong>: <%= LanguageUtil.get(resourceBundle, group.getScopeLabel(themeDisplay)) %>
						</h6>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						path="/action_actions.jsp"
					/>
				</c:when>
				<c:when test='<%= Objects.equals(mdrActionDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							actionJsp="/action_actions.jsp"
							actionJspServletContext="<%= application %>"
							icon="mobile-portrait"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							subtitle="<%= LanguageUtil.get(resourceBundle, group.getScopeLabel(themeDisplay)) %>"
							title="<%= action.getName(locale) %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - action.getModifiedDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<h6 class="text-default">
									<%= LanguageUtil.get(resourceBundle, action.getType()) %>
								</h6>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(mdrActionDisplayContext.getDisplayStyle(), "list") %>'>
					<%@ include file="/action_columns.jspf" %>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= mdrActionDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<script>
	(function() {
		var deleteActionsButton = document.getElementById(
			'<portlet:namespace />deleteActions'
		);

		if (deleteActionsButton) {
			deleteActionsButton.addEventListener('click', function() {
				if (
					confirm(
						'<%= UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-delete-this") %>'
					)
				) {
					submitForm(document.<portlet:namespace />fm);
				}
			});
		}
	})();
</script>