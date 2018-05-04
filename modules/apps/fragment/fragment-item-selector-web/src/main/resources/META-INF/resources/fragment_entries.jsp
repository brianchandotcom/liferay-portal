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

<clay:management-toolbar
	clearResultsURL="<%= fragmentItemSelectorViewDisplayContext.getClearResultsURL() %>"
	componentId="fragmentItemSelectorFragmentEntriesManagementToolbar"
	filterDropdownItems="<%= fragmentItemSelectorViewDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= fragmentItemSelectorViewDisplayContext.getFragmentEntriesTotalItems() %>"
	searchActionURL="<%= fragmentItemSelectorViewDisplayContext.getSearchActionURL() %>"
	searchContainerId="fragmentEntries"
	searchFormName="searchFm"
	selectable="<%= false %>"
	sortingOrder="<%= fragmentItemSelectorViewDisplayContext.getOrderByType() %>"
	sortingURL="<%= fragmentItemSelectorViewDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= fragmentItemSelectorViewDisplayContext.getViewTypeItems() %>"
/>

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
		searchContainer="<%= fragmentItemSelectorViewDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>

			<%
			row.setCssClass("entry-card form-check-card lfr-asset-item " + row.getCssClass());

			String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);

			Map<String, Object> data = new HashMap<String, Object>();

			data.put("fragment-entry-id", fragmentEntry.getFragmentEntryId());
			data.put("name", fragmentEntry.getName());
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
						<liferay-frontend:vertical-card
							cssClass="entry-display-style fragment-entry"
							data="<%= data %>"
							imageCSSClass="aspect-ratio-bg-contain"
							imageUrl="<%= imagePreviewURL %>"
							resultRow="<%= row %>"
							title="<%= fragmentEntry.getName() %>"
							url="javascript:;"
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
							cssClass="entry-display-style fragment-entry"
							data="<%= data %>"
							icon="page"
							resultRow="<%= row %>"
							title="<%= fragmentEntry.getName() %>"
							url="javascript:;"
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
			displayStyle="<%= fragmentItemSelectorViewDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom">
	var selectFragmentEntryHandler = dom.delegate(
		document.querySelector('#<portlet:namespace/>fm'),
		'click',
		'.fragment-entry',
		function(event) {
			dom.removeClasses(document.querySelectorAll('.form-check-card.active'), 'active');
			dom.addClasses(dom.closest(event.delegateTarget, '.form-check-card'), 'active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= fragmentItemSelectorViewDisplayContext.getItemSelectedEventName() %>',
				{
					data: event.delegateTarget.dataset
				}
			);
		}
	);

	function removeListener() {
		selectFragmentEntryHandler.removeListener();

		Liferay.detach('destroyPortlet', removeListener);
	}

	Liferay.on('destroyPortlet', removeListener);
</aui:script>