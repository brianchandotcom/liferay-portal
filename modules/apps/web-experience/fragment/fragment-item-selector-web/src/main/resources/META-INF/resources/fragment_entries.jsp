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
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "collections"), fragmentItemSelectorViewDisplayContext.getFragmentCollectionsRedirect());
PortalUtil.addPortletBreadcrumbEntry(request, fragmentItemSelectorViewDisplayContext.getFragmentCollectionTitle(), null);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
	searchContainerId="fragmentEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= fragmentItemSelectorViewDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= fragmentItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= fragmentItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns="<%= fragmentItemSelectorViewDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>

		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcRenderCommandName" value="/fragment/view_fragment_entries" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentItemSelectorViewDisplayContext.getFragmentCollectionId()) %>" />
			<portlet:param name="displayStyle" value="<%= fragmentItemSelectorViewDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<li>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="import-export" id="exportSelectedFragmentEntries" label="export" />

		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedFragmentEntries" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="fm">
	<div id="breadcrumb">
		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</div>

	<liferay-ui:search-container
		id="fragmentEntries"
		searchContainer="<%= fragmentItemSelectorViewDisplayContext.getFragmentEntriesSearchContainer() %>"
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

		<liferay-ui:search-iterator displayStyle="<%= fragmentItemSelectorViewDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>