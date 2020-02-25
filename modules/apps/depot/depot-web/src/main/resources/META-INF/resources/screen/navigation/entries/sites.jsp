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
DepotAdminSitesDisplayContext depotAdminSitesDisplayContext = new DepotAdminSitesDisplayContext(liferayPortletRequest, liferayPortletResponse);

List<DepotEntryGroupRel> depotEntryGroupRels = depotAdminSitesDisplayContext.getDepotEntryGroupRels();
%>

<div class="sheet-section">
	<h3 class="autofit-row sheet-subtitle">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="connected-sites" /></span>
		</span>
		<span class="autofit-col">
			<span class="heading-end">
				<clay:button
					elementClasses="btn-secondary"
					id='<%= renderResponse.getNamespace() + "addConnectedSiteButton" %>'
					label='<%= LanguageUtil.get(request, "add") %>'
					size="sm"
					style="secondary"
					title='<%= LanguageUtil.get(request, "connect-to-a-site") %>'
				/>
			</span>
		</span>
	</h3>

	<aui:input name="toGroupId" type="hidden" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		cssClass="lfr-search-container-connected-sites"
		emptyResultsMessage="no-sites-are-connected-yet"
		headerNames="title,null"
		id="connectedSitesSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= depotEntryGroupRels.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= depotEntryGroupRels.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.depot.model.DepotEntryGroupRel"
			keyProperty="depotEntryGroupRelId"
			modelVar="depotEntryGroupRel"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<%= HtmlUtil.escape(depotAdminSitesDisplayContext.getSiteName(depotEntryGroupRel)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				helpMessage="shows-the-asset-library-content-in-search-results"
				name="searchable-content"
			>
				<liferay-ui:message key='<%= depotEntryGroupRel.isSearchable() ? "yes" : "no" %>' />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<clay:dropdown-menu
					defaultEventHandler="<%= DepotAdminWebKeys.CONNECTED_SITE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
					dropdownItems="<%= depotAdminSitesDisplayContext.getConnectedSiteDropdownItems(depotEntryGroupRel) %>"
					icon="ellipsis-v"
					style="secondary"
					triggerCssClasses="btn-monospaced btn-outline-borderless btn-secondary btn-sm"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<liferay-frontend:component
		componentId="<%= DepotAdminWebKeys.CONNECTED_SITE_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
		module="js/ConnectedSiteDropdownDefaultEventHandler.es"
	/>

	<aui:script require="metal-dom/src/all/dom as dom">
		var addConnectedSiteButton = document.querySelector(
			'#<portlet:namespace />addConnectedSiteButton'
		);

		addConnectedSiteButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
					},
					eventName:
						'<%= liferayPortletResponse.getNamespace() + "selectSite" %>',
					id: '<portlet:namespace />selectSite',
					title: '<liferay-ui:message key="select-site" />',
					uri:
						'<%= String.valueOf(depotAdminSitesDisplayContext.getItemSelectorURL()) %>',
				},
				function(event) {
					var toGroupIdInput = document.querySelector(
						'#<portlet:namespace />toGroupId'
					);

					toGroupIdInput.value = event.groupid;

					var redirectInput = document.querySelector(
						'#<portlet:namespace />redirect'
					);

					redirectInput.value = '<%= currentURL %>';

					submitForm(toGroupIdInput.form);
				}
			);
		});
	</aui:script>
</div>