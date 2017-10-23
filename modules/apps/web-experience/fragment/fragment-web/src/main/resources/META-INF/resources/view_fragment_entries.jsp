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
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentDisplayContext.getFragmentCollectionsRedirect());

renderResponse.setTitle(fragmentDisplayContext.getFragmentCollectionTitle());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= currentURL %>" label="fragments" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= fragmentDisplayContext.isShowFragmentEntriesSearch() %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_fragment_entries.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
			<portlet:param name="displayStyle" value="<%= fragmentDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

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
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedFragmentEntries" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteFragmentEntries" var="deleteFragmentEntriesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteFragmentEntriesURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="fragmentEntries"
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:icon-vertical-card
					actionJsp="/fragment_entry_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					icon="page"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= fragmentEntry.getName() %>"
				>
					<liferay-frontend:vertical-card-header>
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - fragmentEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-frontend:vertical-card-header>
				</liferay-frontend:icon-vertical-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= fragmentDisplayContext.isShowAddButton(FragmentActionKeys.ADD_FRAGMENT_ENTRY) %>">
	<portlet:actionURL name="addFragmentEntry" var="addFragmentEntryURL">
		<portlet:param name="mvcPath" value="/edit_fragment_entry.jsp" />
		<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
	</portlet:actionURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="addFragmentEntryMenuItem" title='<%= LanguageUtil.get(request, "add-fragment") %>' url="<%= addFragmentEntryURL.toString() %>" />
	</liferay-frontend:add-menu>

	<aui:script require="fragment-web/js/FragmentNameEditor,metal-dom/src/all/dom">
		var fragmentNameEditor;

		var updateFragmentActionOptionQueryClickHandler = metalDomSrcAllDom.default.delegate(
			document.body,
			'click',
			'.<portlet:namespace />update-fragment-action-option > a',
			function(event) {
				var actionElement = event.delegateTarget;

				fragmentNameEditor = new fragmentWebJsFragmentNameEditor.default(
					{
						actionURL: actionElement.dataset.updateUrl,
						editorTitle: '<liferay-ui:message key="rename-fragment" />',
						events: {
							hide: function() {
								fragmentNameEditor.dispose();

								fragmentNameEditor = null;
							}
						},
						fragmentEntryId: actionElement.dataset.fragmentEntryId,
						fragmentEntryName: actionElement.dataset.fragmentEntryName,
						namespace: '<portlet:namespace />',
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
					}
				);
			}
		);

		function handleAddFragmentEntryMenuItemClick(event) {
			event.preventDefault();

			fragmentNameEditor = new fragmentWebJsFragmentNameEditor.default(
				{
					actionURL: '<%= addFragmentEntryURL.toString() %>',
					editorTitle: '<liferay-ui:message key="add-fragment" />',
					events: {
						hide: function() {
							fragmentNameEditor.dispose();

							fragmentNameEditor = null;
						}
					},
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			);
		}

		var addFragmentEntryMenuItem = document.getElementById('<portlet:namespace />addFragmentEntryMenuItem');

		addFragmentEntryMenuItem.addEventListener('click', handleAddFragmentEntryMenuItemClick);

		function handleDestroyPortlet () {
			addFragmentEntryMenuItem.removeEventListener('click', handleAddFragmentEntryMenuItemClick);

			if (fragmentNameEditor) {
				fragmentNameEditor.dispose();
			}

			updateFragmentActionOptionQueryClickHandler.removeListener();

			Liferay.detach('destroyPortlet', handleDestroyPortlet);
		}

		Liferay.on('destroyPortlet', handleDestroyPortlet);
	</aui:script>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedFragmentEntries').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>