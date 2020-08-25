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

request.setAttribute(LayoutAdminWebKeys.LAYOUT_PAGE_TEMPLATE_DISPLAY_CONTEXT, layoutPageTemplateDisplayContext);
%>

<clay:management-toolbar
	actionDropdownItems="<%= layoutPageTemplateDisplayContext.geLayoutPageTemplateEntriesActionDropdownItems() %>"
	clearResultsURL="<%= layoutPageTemplateDisplayContext.getClearResultsURL() %>"
	componentId="layoutPageTemplateEntriesManagementToolbar"
	creationMenu="<%= layoutPageTemplateDisplayContext.getCreationMenu() %>"
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
/>

<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateEntryURL %>" name="fm">
	<liferay-ui:error key="<%= PortalException.class.getName() %>" message="one-or-more-entries-could-not-be-deleted" />

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

			String editLayoutPageTemplateURL = layoutPageTemplateDisplayContext.getEditLayoutPageTemplateEntryURL(layoutPageTemplateEntry);
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
							url="<%= editLayoutPageTemplateURL %>"
						>
							<liferay-frontend:vertical-card-footer>
								<div class="card-subtitle row">
									<div class="col text-truncate">
										<c:choose>
											<c:when test="<%= Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) %>">
												<liferay-ui:message key="widget-page-template" />
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="content-page-template" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="card-subtitle row">
									<div class="col text-truncate">
										<span class="label <%= (layoutPageTemplateEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
											<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(layoutPageTemplateEntry.getStatus()) %>" />
										</span>
									</div>
								</div>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:vertical-card>
					</c:when>
					<c:otherwise>
						<liferay-frontend:icon-vertical-card
							actionJsp="/layout_page_template_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon='<%= Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) ? "page-template" : "page" %>'
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= layoutPageTemplateEntry.getName() %>"
							url="<%= editLayoutPageTemplateURL %>"
						>
							<liferay-frontend:vertical-card-footer>
								<div class="card-subtitle row">
									<div class="col text-truncate">
										<c:choose>
											<c:when test="<%= Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) %>">
												<liferay-ui:message key="widget-page-template" />
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="content-page-template" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="card-subtitle row">
									<div class="col text-truncate">
										<span class="label <%= (layoutPageTemplateEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
											<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(layoutPageTemplateEntry.getStatus()) %>" />
										</span>
									</div>
								</div>
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/layout/update_layout_page_template_entry_preview" var="updateLayoutPageTemplateEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateLayoutPageTemplateEntryPreviewURL %>" name="layoutPageTemplateEntryPreviewFm">
	<aui:input name="layoutPageTemplateEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	function addLayoutPageTemplateEntry(event) {
		event.preventDefault();

		var itemData = event.data.data || event.data.item.data;

		modalCommands.openSimpleInputModal(
			{
				dialogTitle: '<liferay-ui:message key="add-page-template" />',
				formSubmitURL: itemData.addPageTemplateURL,
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
					idFieldName: data.idFieldName,
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

	var deleteLayoutPageTemplateEntries = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm($(document.<portlet:namespace />fm));
		}
	}

	var ACTIONS = {
		'addLayoutPageTemplateEntry': addLayoutPageTemplateEntry,
		'deleteLayoutPageTemplateEntries': deleteLayoutPageTemplateEntries
	};

	Liferay.componentReady('layoutPageTemplateEntriesManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				['actionItemClicked', 'creationButtonClicked', 'creationMenuItemClicked', 'filterItemClicked'],
				function(event) {
					var itemData = event.data.data || event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action](event);
					}
				}
			);
		}
	);

	var updateLayoutPageTemplateEntryPreviewMenuItemClickHandler = dom.delegate(
		document.body,
		'click',
		'.update-layout-page-template-entry-preview > a',
		function(event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			AUI().use(
				'liferay-item-selector-dialog',
				function(A) {
					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<portlet:namespace />changePreview',
							on: {
								selectedItemChange: function(event) {
									var selectedItem = event.newVal;

									if (selectedItem) {
										var itemValue = JSON.parse(selectedItem.value);

										document.<portlet:namespace />layoutPageTemplateEntryPreviewFm.<portlet:namespace />layoutPageTemplateEntryId.value = data.layoutPageTemplateEntryId;
										document.<portlet:namespace />layoutPageTemplateEntryPreviewFm.<portlet:namespace />fileEntryId.value = itemValue.fileEntryId;

										submitForm(document.<portlet:namespace />layoutPageTemplateEntryPreviewFm);
									}
								}
							},
							'strings.add': '<liferay-ui:message key="ok" />',
							title: '<liferay-ui:message key="page-template-thumbnail" />',
							url: data.itemSelectorUrl
						}
					);

					itemSelectorDialog.open();
				}
			);
		}
	);

	function handleDestroyPortlet() {
		updateLayoutPageTemplateEntryMenuItemClickHandler.removeListener();
		updateLayoutPageTemplateEntryPreviewMenuItemClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>