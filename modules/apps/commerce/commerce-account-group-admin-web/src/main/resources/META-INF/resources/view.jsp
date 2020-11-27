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
CommerceAccountGroupAdminDisplayContext commerceAccountGroupAdminDisplayContext = (CommerceAccountGroupAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceAccountGroupAdminDisplayContext.hasPermission(CommerceAccountActionKeys.MANAGE_ALL_ACCOUNTS) %>">

	<%
	SearchContainer<CommerceAccountGroup> commerceAccountGroupSearchContainer = commerceAccountGroupAdminDisplayContext.getSearchContainer();
	%>

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAccountGroups"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				disabled="<%= true %>"
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commerceAccountGroupAdminDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<c:if test="<%= commerceAccountGroupAdminDisplayContext.hasPermission(CommerceAccountActionKeys.ADD_ACCOUNT) %>">
				<portlet:renderURL var="addCommerceAccountGroupURL">
					<portlet:param name="mvcRenderCommandName" value="editCommerceAccountGroup" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu
					inline="<%= true %>"
				>
					<liferay-frontend:add-menu-item
						title='<%= LanguageUtil.get(request, "add-account-group") %>'
						url="<%= addCommerceAccountGroupURL.toString() %>"
					/>
				</liferay-frontend:add-menu>
			</c:if>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				navigationParam="activeNavigation"
				portletURL="<%= commerceAccountGroupAdminDisplayContext.getPortletURL() %>"
			/>

			<li>
				<liferay-commerce:search-input
					actionURL="<%= commerceAccountGroupAdminDisplayContext.getPortletURL() %>"
					formName="searchFm"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceAccountGroups();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<portlet:actionURL name="editCommerceAccountGroup" var="editCommerceAccountGroupActionURL" />

		<aui:form action="<%= editCommerceAccountGroupActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="deleteCommerceAccountGroupIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceAccountGroups"
				searchContainer="<%= commerceAccountGroupSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.account.model.CommerceAccountGroup"
					keyProperty="commerceAccountGroupId"
					modelVar="commerceAccountGroup"
				>
					<portlet:renderURL var="rowURL">
						<portlet:param name="mvcRenderCommandName" value="editCommerceAccountGroup" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="commerceAccountGroupId" value="<%= String.valueOf(commerceAccountGroup.getCommerceAccountGroupId()) %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-expand"
						href="<%= rowURL %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						property="system"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/account_group_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceAccountGroups() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-account-groups" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form[
					'<portlet:namespace />deleteCommerceAccountGroupIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>
</c:if>