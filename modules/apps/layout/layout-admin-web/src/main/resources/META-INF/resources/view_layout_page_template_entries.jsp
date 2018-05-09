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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);
%>

<clay:management-toolbar
	actionDropdownItems="<%= layoutPageTemplateDisplayContext.geLayoutPageTemplateEntriesActionDropdownItems() %>"
	clearResultsURL="<%= layoutPageTemplateDisplayContext.getClearResultsURL() %>"
	componentId="layoutPageTemplateEntriesManagementToolbar"
	disabled="<%= layoutPageTemplateDisplayContext.isDisabledLayoutPageTemplateEntriesManagementBar() %>"
	filterDropdownItems="<%= layoutPageTemplateDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= layoutPageTemplateDisplayContext.getTotalItems() %>"
	searchActionURL="<%= layoutPageTemplateDisplayContext.getSearchActionURL() %>"
	searchContainerId="layoutPageTemplateEntries"
	searchFormName="searchFm"
	showCreationMenu="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY) %>"
	showSearch="<%= layoutPageTemplateDisplayContext.isShowLayoutPageTemplateEntriesSearch() %>"
	sortingOrder="<%= layoutPageTemplateDisplayContext.getOrderByType() %>"
	sortingURL="<%= layoutPageTemplateDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= layoutPageTemplateDisplayContext.getViewTypeItems() %>"
/>

<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateEntryURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="layoutPageTemplateEntries"
		searchContainer="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.page.template.model.LayoutPageTemplateEntry"
			keyProperty="layoutPageTemplateEntryId"
			modelVar="layoutPageTemplateEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

			String imagePreviewURL = layoutPageTemplateEntry.getImagePreviewURL(themeDisplay);
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
						<liferay-frontend:vertical-card
							actionJsp="/layout_page_template_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							imageCSSClass="aspect-ratio-bg-contain"
							imageUrl="<%= imagePreviewURL %>"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutPageTemplateEntry.getName() %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - layoutPageTemplateEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<span class="label <%= (layoutPageTemplateEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(layoutPageTemplateEntry.getStatus()) %>" />
								</span>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:vertical-card>
					</c:when>
					<c:otherwise>
						<liferay-frontend:icon-vertical-card
							actionJsp="/layout_page_template_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="page"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutPageTemplateEntry.getName() %>"
						>
							<liferay-frontend:vertical-card-header>
								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - layoutPageTemplateEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<span class="label <%= (layoutPageTemplateEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(layoutPageTemplateEntry.getStatus()) %>" />
								</span>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout/add_layout_page_template_entry" var="addLayoutPageTemplateEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/layout/edit_layout_page_template_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId()) %>" />
</portlet:actionURL>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	function handleAddLayoutPageTemplateEntryMenuItemClick(event) {
		event.preventDefault();

		modalCommands.openSimpleInputModal(
			{
				dialogTitle: '<liferay-ui:message key="add-page-template" />',
				formSubmitURL: '<%= addLayoutPageTemplateEntryURL %>',
				mainFieldLabel: '<liferay-ui:message key="name" />',
				mainFieldName: 'name',
				mainFieldPlaceholder: '<liferay-ui:message key="name" />',
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		);
	}

	var updateLayoutPageTemplateEntryMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.<portlet:namespace />update-layout-page-template-action-option > a',
		function(event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="rename-layout-page-template" />',
					formSubmitURL: data.formSubmitUrl,
					idFieldName: 'layoutPageTemplateEntryId',
					idFieldValue: data.idFieldValue,
					mainFieldLabel: '<liferay-ui:message key="name" />',
					mainFieldName: 'name',
					mainFieldPlaceholder: '<liferay-ui:message key="name" />',
					mainFieldValue: data.mainFieldValue,
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}
	);

	window.<portlet:namespace />deleteLayoutPageTemplateEntries = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}

	Liferay.componentReady('layoutPageTemplateEntriesManagementToolbar').then(
		(managementToolbar) => {
			managementToolbar.on('creationButtonClicked', handleAddLayoutPageTemplateEntryMenuItemClick);
		}
	);

	function handleDestroyPortlet() {
		updateLayoutPageTemplateEntryMenuItemClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>