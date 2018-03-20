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

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "page-templates"), layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionRedirect());
PortalUtil.addPortletBreadcrumbEntry(request, layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionTitle(), null);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= layoutsAdminDisplayContext.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= layoutPageTemplateDisplayContext.isDisabledLayoutPageTemplateEntriesManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="layoutPageTemplateEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= layoutPageTemplateDisplayContext.isShowAddButton(LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE_ENTRY) %>">
			<portlet:renderURL var="addLayoutPageTemplateEntryURL">
				<portlet:param name="mvcPath" value="/edit_layout_page_template_entry.jsp" />
				<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addLayoutPageTemplateEntryMenuItem"
					title='<%= LanguageUtil.get(request, "add-page-template") %>'
					url="<%= addLayoutPageTemplateEntryURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= layoutPageTemplateDisplayContext.getOrderByCol() %>"
			orderByType="<%= layoutPageTemplateDisplayContext.getOrderByType() %>"
			orderColumns="<%= layoutPageTemplateDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>

		<c:if test="<%= layoutPageTemplateDisplayContext.isShowLayoutPageTemplateEntriesSearch() %>">
			<portlet:renderURL var="portletURL">
				<portlet:param name="mvcPath" value="/view_layout_page_template_entries.jsp" />
				<portlet:param name="tabs1" value="page-templates" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId()) %>" />
				<portlet:param name="displayStyle" value="<%= layoutPageTemplateDisplayContext.getDisplayStyle() %>" />
			</portlet:renderURL>

			<li>
				<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedLayoutPageTemplateEntries"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteLayoutPageTemplateEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutPageTemplateEntryURL %>" cssClass="container-fluid-1280" name="fm">
	<div id="breadcrumb">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</div>

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
	<portlet:param name="mvcPath" value="/edit_layout_page_template_entry.jsp" />
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

	var deleteSelectedLayoutPageTemplateEntriesClickHandler = dom.on(
		'#<portlet:namespace />deleteSelectedLayoutPageTemplateEntries',
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);

	var addLayoutPageTemplateEntryMenuItem = document.getElementById('<portlet:namespace />addLayoutPageTemplateEntryMenuItem');

	addLayoutPageTemplateEntryMenuItem.addEventListener('click', handleAddLayoutPageTemplateEntryMenuItemClick);

	function handleDestroyPortlet() {
		addLayoutPageTemplateEntryMenuItem.removeEventListener('click', handleAddLayoutPageTemplateEntryMenuItemClick);
		deleteSelectedLayoutPageTemplateEntriesClickHandler.removeListener();
		updateLayoutPageTemplateEntryMenuItemClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>