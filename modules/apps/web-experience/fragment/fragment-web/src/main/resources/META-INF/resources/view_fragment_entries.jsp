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
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "collections"), fragmentDisplayContext.getFragmentCollectionsRedirect());
PortalUtil.addPortletBreadcrumbEntry(request, fragmentDisplayContext.getFragmentCollectionTitle(), null);
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= fragmentDisplayContext.getFragmentCollectionNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= fragmentDisplayContext.isDisabledFragmentEntriesManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="fragmentEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_ENTRY) %>">
			<portlet:actionURL name="/fragment/add_fragment_entry" var="addFragmentEntryURL">
				<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_entry" />
				<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
			</portlet:actionURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="addFragmentEntryMenuItem"
					title='<%= LanguageUtil.get(request, "add-fragment") %>'
					url="<%= addFragmentEntryURL.toString() %>"
				/>
			</liferay-frontend:add-menu>

			<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
				function handleAddFragmentEntryMenuItemClick(event) {
					event.preventDefault();

					modalCommands.openSimpleInputModal(
						{
							dialogTitle: '<liferay-ui:message key="add-fragment" />',
							formSubmitURL: '<%= addFragmentEntryURL %>',
							mainFieldLabel: '<liferay-ui:message key="name" />',
							mainFieldName: 'name',
							mainFieldPlaceholder: '<liferay-ui:message key="name" />',
							namespace: '<portlet:namespace />',
							spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
						}
					);
				}

				var updateFragmentEntryMenuItemClickHandler = dom.delegate(
					document.body,
					'click',
					'.<portlet:namespace />update-fragment-action-option > a',
					function(event) {
						var data = event.delegateTarget.dataset;

						event.preventDefault();

						modalCommands.openSimpleInputModal({
							dialogTitle: '<liferay-ui:message key="rename-fragment" />',
							formSubmitURL: data.formSubmitUrl,
							idFieldName: 'id',
							idFieldValue: data.idFieldValue,
							mainFieldLabel: '<liferay-ui:message key="name" />',
							mainFieldName: 'name',
							mainFieldPlaceholder: '<liferay-ui:message key="name" />',
							mainFieldValue: data.mainFieldValue,
							namespace: '<portlet:namespace />',
							spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
						});
					}
				);

				function handleDestroyPortlet () {
					addFragmentEntryMenuItem.removeEventListener('click', handleAddFragmentEntryMenuItemClick);
					updateFragmentEntryMenuItemClickHandler.removeListener();

					Liferay.detach('destroyPortlet', handleDestroyPortlet);
				}

				var addFragmentEntryMenuItem = document.getElementById('<portlet:namespace />addFragmentEntryMenuItem');

				addFragmentEntryMenuItem.addEventListener('click', handleAddFragmentEntryMenuItemClick);

				Liferay.on('destroyPortlet', handleDestroyPortlet);
			</aui:script>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= fragmentDisplayContext.getOrderByCol() %>"
			orderByType="<%= fragmentDisplayContext.getOrderByType() %>"
			orderColumns="<%= fragmentDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>

		<c:if test="<%= fragmentDisplayContext.isShowFragmentEntriesSearch() %>">
			<portlet:renderURL var="portletURL">
				<portlet:param name="mvcRenderCommandName" value="/fragment/view_fragment_entries" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
				<portlet:param name="displayStyle" value="<%= fragmentDisplayContext.getDisplayStyle() %>" />
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
			icon="import-export"
			id="exportSelectedFragmentEntries"
			label="export"
		/>

		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedFragmentEntries"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="fm">
	<div id="breadcrumb">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</div>

	<liferay-ui:search-container
		id="fragmentEntries"
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>
			<portlet:renderURL var="editFragmentEntryURL">
				<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_entry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentEntry.getFragmentCollectionId()) %>" />
				<portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentEntry.getFragmentEntryId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

			String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
						<liferay-frontend:vertical-card
							actionJsp="/fragment_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							imageCSSClass="aspect-ratio-bg-contain"
							imageUrl="<%= imagePreviewURL %>"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= fragmentEntry.getName() %>"
							url="<%= editFragmentEntryURL %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date statusDate = fragmentEntry.getStatusDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:vertical-card>
					</c:when>
					<c:otherwise>
						<liferay-frontend:icon-vertical-card
							actionJsp="/fragment_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="page"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= fragmentEntry.getName() %>"
							url="<%= editFragmentEntryURL %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date statusDate = fragmentEntry.getStatusDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
							</liferay-frontend:vertical-card-footer>
						</liferay-frontend:icon-vertical-card>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom">
	var deleteSelectedFragmentEntriesHandler = dom.on(
		'#<portlet:namespace />deleteSelectedFragmentEntries',
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:actionURL name="/fragment/delete_fragment_entries"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
			}
		}
	);

	var exportSelectedFragmentEntriesHandler = dom.on(
		'#<portlet:namespace />exportSelectedFragmentEntries',
		'click',
		function() {
			submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_entries" />');
		}
	);

	function handleDestroyPortlet () {
		deleteSelectedFragmentEntriesHandler.removeListener();
		exportSelectedFragmentEntriesHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>