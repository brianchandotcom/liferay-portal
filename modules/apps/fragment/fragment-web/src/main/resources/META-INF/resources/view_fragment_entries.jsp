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

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:management-toolbar
	actionDropdownItems="<%= fragmentDisplayContext.getFragmentEntryActionItemsDropdownItems() %>"
	clearResultsURL="<%= fragmentDisplayContext.getFragmentEntryClearResultsURL() %>"
	componentId="fragmentEntriesManagementToolbar"
	disabled="<%= fragmentDisplayContext.isDisabledFragmentEntriesManagementBar() %>"
	filterDropdownItems="<%= fragmentDisplayContext.getFragmentEntryFilterItemsDropdownItems() %>"
	itemsTotal="<%= fragmentDisplayContext.getFragmentEntryTotalItems() %>"
	searchActionURL="<%= fragmentDisplayContext.getFragmentEntrySearchActionURL() %>"
	searchContainerId="fragmentEntries"
	searchFormName="searchFm"
	showCreationMenu="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_ENTRY) ? true : false %>"
	sortingOrder="<%= fragmentDisplayContext.getOrderByType() %>"
	sortingURL="<%= fragmentDisplayContext.getFragmentEntrySortingURL() %>"
	viewTypeItems="<%= fragmentDisplayContext.getFragmentEntryViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280" name="fm">
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
								<span class="label <%= (fragmentEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(fragmentEntry.getStatus()) %>" />
								</span>
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
								<span class="label <%= (fragmentEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(fragmentEntry.getStatus()) %>" />
								</span>
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

<c:if test="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_ENTRY) %>">
	<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
		function handleAddFragmentEntryMenuItemClick(event) {
			event.preventDefault();

			modalCommands.openSimpleInputModal(
				{
					dialogTitle: '<liferay-ui:message key="add-fragment" />',
					formSubmitURL: '<portlet:actionURL name="/fragment/add_fragment_entry"><portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_entry" /><portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" /></portlet:actionURL>',
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
			updateFragmentEntryMenuItemClickHandler.removeListener();

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		Liferay.componentReady('fragmentEntriesManagementToolbar').then(
			(managementToolbar) => {
				managementToolbar.on('creationButtonClicked', handleAddFragmentEntryMenuItemClick);
			}
		);

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>
</c:if>

<aui:script>
	window.<portlet:namespace />deleteSelectedFragmentEntries = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:actionURL name="/fragment/delete_fragment_entries"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}

	window.<portlet:namespace />exportSelectedFragmentEntries = function() {
		submitForm(document.querySelector('#<portlet:namespace />fm'), '<portlet:resourceURL id="/fragment/export_fragment_entries" />');
	}
</aui:script>