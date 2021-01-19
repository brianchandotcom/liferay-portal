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
CommerceAccountOrganizationRelAdminDisplayContext commerceAccountOrganizationRelAdminDisplayContext = (CommerceAccountOrganizationRelAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceAccount commerceAccount = commerceAccountOrganizationRelAdminDisplayContext.getCommerceAccount();
long commerceAccountId = commerceAccountOrganizationRelAdminDisplayContext.getCommerceAccountId();
SearchContainer<CommerceAccountOrganizationRel> commerceAccountOrganizationRelSearchContainer = commerceAccountOrganizationRelAdminDisplayContext.getSearchContainer();

PortletURL portletURL = commerceAccountOrganizationRelAdminDisplayContext.getPortletURL();
%>

<c:if test="<%= commerceAccountOrganizationRelAdminDisplayContext.hasPermission(commerceAccountId, ActionKeys.UPDATE) %>">
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="commerceAccountOrganizationRels"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="list"
			/>

			<portlet:actionURL name="/commerce_account_admin/edit_commerce_account_organization_rel" var="addCommerceAccountOrganizationRelURL" />

			<aui:form action="<%= addCommerceAccountOrganizationRelURL %>" cssClass="hide" name="addCommerceAccountOrganizationRelFm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceAccountId" type="hidden" value="<%= commerceAccountId %>" />
				<aui:input name="organizationIds" type="hidden" value="" />
			</aui:form>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addCommerceAccountOrganizationRel"
					title='<%= LanguageUtil.get(request, "add-entry") %>'
					url="javascript:;"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= portletURL %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceAccountOrganizationRels();" %>'
				icon="times"
				label="remove"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>

	<div class="container-fluid container-fluid-max-xl">
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceAccountId" type="hidden" value="<%= commerceAccountId %>" />
			<aui:input name="deleteCommerceAccountOrganizationRelIds" type="hidden" />

			<liferay-ui:search-container
				id="commerceAccountOrganizationRels"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceAccountOrganizationRelSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.account.model.CommerceAccountOrganizationRel"
					keyProperty="organizationId"
					modelVar="commerceAccountOrganizationRel"
				>

					<%
					Organization accountOrganization = commerceAccountOrganizationRel.getOrganization();
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="id"
						value="<%= String.valueOf(accountOrganization.getUserId()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="important table-cell-expand"
						href="<%= commerceAccountOrganizationRelAdminDisplayContext.getEditOrganizationURL(accountOrganization.getUserId()) %>"
						name="name"
						value="<%= HtmlUtil.escape(accountOrganization.getName()) %>"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/organization_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>

	<aui:script>
		function <portlet:namespace />deleteCommerceAccountOrganizationRels() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-organizations" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form.setAttribute('method', 'post');
				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.DELETE %>';
				form[
					'<portlet:namespace />deleteCommerceAccountOrganizationRelIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(
					form,
					'<portlet:actionURL name="/commerce_account_admin/edit_commerce_account_organization_rel" />'
				);
			}
		}
	</aui:script>

	<aui:script use="liferay-item-selector-dialog">
		window.document
			.querySelector('#<portlet:namespace />addCommerceAccountOrganizationRel')
			.addEventListener('click', function (event) {
				event.preventDefault();

				var itemSelectorDialog = new A.LiferayItemSelectorDialog({
					eventName: 'organizationsSelectItem',
					on: {
						selectedItemChange: function (event) {
							var <portlet:namespace />addOrganizationIds = [];

							var selectedItems = event.newVal;

							if (selectedItems) {
								A.Array.each(selectedItems, function (
									item,
									index,
									selectedItems
								) {
									<portlet:namespace />addOrganizationIds.push(
										item.id
									);
								});

								window.document.querySelector(
									'#<portlet:namespace />organizationIds'
								).value = <portlet:namespace />addOrganizationIds.join(
									','
								);

								var addCommerceAccountOrganizationRelFm = window.document.querySelector(
									'#<portlet:namespace />addCommerceAccountOrganizationRelFm'
								);

								submitForm(addCommerceAccountOrganizationRelFm);
							}
						},
					},
					title:
						'<liferay-ui:message arguments="<%= HtmlUtil.escape(commerceAccount.getName()) %>" key="add-new-entry-to-x" />',
					url:
						'<%= commerceAccountOrganizationRelAdminDisplayContext.getItemSelectorUrl() %>',
				});

				itemSelectorDialog.open();
			});
	</aui:script>
</c:if>