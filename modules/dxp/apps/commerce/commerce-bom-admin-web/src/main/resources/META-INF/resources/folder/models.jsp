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
CommerceBOMAdminDisplayContext commerceBOMAdminDisplayContext = (CommerceBOMAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceBOMFolder commerceBOMFolder = commerceBOMAdminDisplayContext.getCommerceBOMFolder();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceBOMFolderApplicationRels"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= commerceBOMAdminDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceBOMAdminDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceBOMAdminDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "year"} %>'
			portletURL="<%= commerceBOMAdminDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceBOMAdminDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= commerceBOMAdminDisplayContext.hasCommerceBOMFolderPermissions(commerceBOMAdminDisplayContext.getCommerceBOMFolderId(), ActionKeys.UPDATE) %>">
			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addCommerceBOMFolderApplicationRel"
					title='<%= LanguageUtil.get(request, "add-model") %>'
					url="javascript:;"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteCommerceBOMFolderApplicationRels();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/commerce_bom_admin/edit_commerce_bom_folder_application_rel" var="editCommerceBOMFolderApplicationRelActionURL" />

<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />commerceBOMFolderApplicationRelContainer">
	<aui:form action="<%= editCommerceBOMFolderApplicationRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceBOMFolderId" type="hidden" value="<%= commerceBOMAdminDisplayContext.getCommerceBOMFolderId() %>" />
		<aui:input name="commerceApplicationModelIds" type="hidden" />
		<aui:input name="deleteCommerceBOMFolderApplicationRelIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceBOMFolderApplicationRels"
			searchContainer="<%= commerceBOMAdminDisplayContext.getCommerceBOMFolderApplicationRelSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel"
				keyProperty="commerceBOMFolderApplicationRelId"
				modelVar="commerceBOMFolderApplicationRel"
			>

				<%
				CommerceApplicationModel commerceApplicationModel = commerceBOMFolderApplicationRel.getCommerceApplicationModel();
				%>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-expand"
					name="name"
					value="<%= HtmlUtil.escape(commerceApplicationModel.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="year"
					value="<%= HtmlUtil.escape(commerceApplicationModel.getYear()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/bom_folder_application_rel_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="list"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= commerceBOMAdminDisplayContext.hasCommerceBOMFolderPermissions(commerceBOMAdminDisplayContext.getCommerceBOMFolderId(), ActionKeys.UPDATE) %>">
	<aui:script>
		function <portlet:namespace />deleteCommerceBOMFolderApplicationRels() {
			if (
				confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-models" />'
				)
			) {
				var form = window.document['<portlet:namespace />fm'];

				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.DELETE %>';
				form[
					'<portlet:namespace />deleteCommerceBOMFolderApplicationRelIds'
				].value = Liferay.Util.listCheckedExcept(
					form,
					'<portlet:namespace />allRowIds'
				);

				submitForm(form);
			}
		}
	</aui:script>

	<aui:script use="liferay-item-selector-dialog">
		window.document
			.querySelector('#<portlet:namespace />addCommerceBOMFolderApplicationRel')
			.addEventListener('click', function (event) {
				event.preventDefault();

				var itemSelectorDialog = new A.LiferayItemSelectorDialog({
					eventName: 'commerceApplicationModelsSelectItem',
					on: {
						selectedItemChange: function (event) {
							var <portlet:namespace />addCommerceApplicationModelIds = [];

							var selectedItems = event.newVal;

							if (selectedItems) {
								A.Array.each(selectedItems, function (
									item,
									index,
									selectedItems
								) {
									<portlet:namespace />addCommerceApplicationModelIds.push(
										item.commerceApplicationModelId
									);
								});

								window.document.querySelector(
									'#<portlet:namespace />commerceApplicationModelIds'
								).value = <portlet:namespace />addCommerceApplicationModelIds.join(
									','
								);

								var fm = window.document.querySelector(
									'#<portlet:namespace />fm'
								);

								submitForm(fm);
							}
						},
					},
					title:
						'<liferay-ui:message arguments="<%= HtmlUtil.escape(commerceBOMFolder.getName()) %>" key="add-new-entry-to-x" />',
					url:
						'<%= commerceBOMAdminDisplayContext.getCommerceApplicationModelItemSelectorUrl() %>',
				});

				itemSelectorDialog.open();
			});
	</aui:script>
</c:if>