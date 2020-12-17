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

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="connected-sites" /></span>
		</clay:content-col>

		<c:if test="<%= !depotAdminSitesDisplayContext.isLiveDepotEntry() %>">
			<clay:content-col>
				<span class="heading-end">
					<clay:button
						displayType="secondary"
						id='<%= liferayPortletResponse.getNamespace() + "addConnectedSiteButton" %>'
						label="add"
						small="<%= true %>"
						title="connect-to-a-site"
					/>
				</span>
			</clay:content-col>
		</c:if>
	</clay:content-row>

	<liferay-ui:error exception="<%= DepotEntryGroupRelToGroupException.MustBeLocallyStaged.class %>">
		<liferay-ui:message key="a-locally-staged-asset-library-cannot-be-connected-to-a-remotely-staged-site" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DepotEntryGroupRelToGroupException.MustBeRemotelyStaged.class %>">
		<liferay-ui:message key="a-remotely-staged-asset-library-cannot-be-connected-to-a-locally-staged-site" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DepotEntryGroupRelToGroupException.MustBeStaged.class %>">
		<liferay-ui:message key="a-staged-asset-library-cannot-be-connected-to-an-unstaged-site" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= DepotEntryGroupRelToGroupException.MustNotBeStaged.class %>">
		<liferay-ui:message key="an-unstaged-asset-library-cannot-be-connected-to-a-staged-site" />
	</liferay-ui:error>

	<c:if test="<%= depotAdminSitesDisplayContext.isLiveDepotEntry() %>">
		<clay:alert
			displayType="info"
			message='<%= LanguageUtil.get(request, "this-is-a-live-asset-library.-site-connections-must-be-managed-from-the-staging-one") %>'
		/>
	</c:if>

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
				cssClass="table-cell-expand"
				name="name"
			>
				<%= HtmlUtil.escape(depotAdminSitesDisplayContext.getSiteName(depotEntryGroupRel)) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				helpMessage="shows-the-asset-library-content-in-search-results"
				name="searchable-content"
			>
				<liferay-ui:message key='<%= depotEntryGroupRel.isSearchable() ? "yes" : "no" %>' />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				helpMessage="makes-the-asset-library-web-content-structures-and-document-types-available-in-the-site"
				name="structures-and-types"
			>
				<liferay-ui:message key='<%= depotEntryGroupRel.isDdmStructuresAvailable() ? "yes" : "no" %>' />
			</liferay-ui:search-container-column-text>

			<c:if test="<%= !depotAdminSitesDisplayContext.isLiveDepotEntry() %>">
				<liferay-ui:search-container-column-text>
					<clay:dropdown-menu
						borderless="<%= true %>"
						displayType="secondary"
						dropdownItems="<%= depotAdminSitesDisplayContext.getConnectedSiteDropdownItems(depotEntryGroupRel) %>"
						icon="ellipsis-v"
						monospaced="<%= true %>"
						propsTransformer="js/ConnectedSiteDropdownPropsTransformer"
						small="<%= true %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<aui:script require="metal-dom/src/all/dom as dom">
		var addConnectedSiteButton = document.querySelector(
			'#<portlet:namespace />addConnectedSiteButton'
		);

		if (addConnectedSiteButton) {
			addConnectedSiteButton.addEventListener('click', function (event) {
				Liferay.Util.openSelectionModal({
					customSelectEvent: true,
					id: '<portlet:namespace />selectSite',
					onSelect: function (event) {
						var toGroupIdInput = document.querySelector(
							'#<portlet:namespace />toGroupId'
						);

						toGroupIdInput.value = event.groupid;

						var redirectInput = document.querySelector(
							'#<portlet:namespace />redirect'
						);

						redirectInput.value = '<%= currentURL %>';

						submitForm(toGroupIdInput.form);
					},
					selectEventName:
						'<%= liferayPortletResponse.getNamespace() + "selectSite" %>',
					title: '<liferay-ui:message key="select-site" />',
					url:
						'<%= String.valueOf(depotAdminSitesDisplayContext.getItemSelectorURL()) %>',
				});
			});
		}
	</aui:script>
</clay:sheet-section>