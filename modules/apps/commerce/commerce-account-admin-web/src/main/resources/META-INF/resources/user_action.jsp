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
CommerceAccountUserRelAdminDisplayContext commerceAccountUserRelAdminDisplayContext = (CommerceAccountUserRelAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceAccountUserRel commerceAccountUserRel = (CommerceAccountUserRel)row.getObject();

User commerceAccountUser = commerceAccountUserRel.getUser();

String editUserRoleId = "editUserRoles" + commerceAccountUser.getUserId();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= commerceAccountUserRelAdminDisplayContext.hasPermission(commerceAccountUserRel.getCommerceAccountId(), ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			id="<%= editUserRoleId %>"
			message="edit-roles"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= commerceAccountUserRelAdminDisplayContext.hasPermission(commerceAccountUserRel.getCommerceAccountId(), ActionKeys.DELETE) %>">
		<portlet:actionURL name="editCommerceAccountUserRel" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="commerceAccountId" value="<%= String.valueOf(commerceAccountUserRel.getCommerceAccountId()) %>" />
			<portlet:param name="commerceAccountUserId" value="<%= String.valueOf(commerceAccountUserRel.getCommerceAccountUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="remove"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:script use="liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace /><%= editUserRoleId %>')
		.addEventListener('click', function (event) {
			event.preventDefault();

			var form = window.document['<portlet:namespace />fm'];

			form['<portlet:namespace />originalRoleIds'].value =
				'<%= commerceAccountUserRelAdminDisplayContext.getUserRoleIds(commerceAccountUserRel) %>';

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'userRoleItemSelector',
				on: {
					selectedItemChange: function (event) {
						var <portlet:namespace />addUserRolesIds = [];

						var selectedItems = event.newVal;

						if (selectedItems) {
							A.Array.each(selectedItems, function (
								item,
								index,
								selectedItems
							) {
								<portlet:namespace />addUserRolesIds.push(item.id);
							});

							form[
								'<portlet:namespace /><%= Constants.CMD %>'
							].value = '<%= Constants.UPDATE %>';
							form[
								'<portlet:namespace />commerceAccountUserId'
							].value =
								'<%= String.valueOf(commerceAccountUser.getUserId()) %>';
							form[
								'<portlet:namespace />roleIds'
							].value = <portlet:namespace />addUserRolesIds.join(
								','
							);

							submitForm(
								form,
								'<portlet:actionURL name="editCommerceAccountUserRel" />'
							);
						}
					},
				},
				title: '<liferay-ui:message key="edit-roles" />',
				url:
					'<%= commerceAccountUserRelAdminDisplayContext.getUserRoleItemSelectorUrl(commerceAccountUserRel) %>',
			});

			itemSelectorDialog.open();
		});
</aui:script>