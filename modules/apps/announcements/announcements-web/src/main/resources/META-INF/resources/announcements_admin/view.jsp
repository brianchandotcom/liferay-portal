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

<%@ include file="/announcements_admin/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "announcements");

String distributionScope = ParamUtil.getString(request, "distributionScope");

long classNameId = 0;
long classPK = 0;

String[] distributionScopeArray = StringUtil.split(distributionScope);

if (distributionScopeArray.length == 2) {
	classNameId = GetterUtil.getLong(distributionScopeArray[0]);
	classPK = GetterUtil.getLong(distributionScopeArray[1]);
}

if ((classNameId == 0) && (classPK == 0) && !PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS)) {
	throw new PrincipalException.MustHavePermission(permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS);
}

SearchContainer<AnnouncementsEntry> announcementsEntriesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, "no-entries-were-found");

announcementsEntriesSearchContainer.setRowChecker(new AnnouncementsEntryChecker(liferayPortletRequest, liferayPortletResponse));

announcementsEntriesSearchContainer.setTotal(AnnouncementsEntryLocalServiceUtil.getEntriesCount(themeDisplay.getCompanyId(), classNameId, classPK, navigation.equals("alerts")));
announcementsEntriesSearchContainer.setResults(AnnouncementsEntryLocalServiceUtil.getEntries(themeDisplay.getCompanyId(), classNameId, classPK, navigation.equals("alerts"), announcementsEntriesSearchContainer.getStart(), announcementsEntriesSearchContainer.getEnd()));

AnnouncementsAdminViewManagementToolbarDisplayContext announcementsAdminViewManagementToolbarDisplayContext = new AnnouncementsAdminViewManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, announcementsEntriesSearchContainer);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("announcements"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(request, "announcements"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("alerts"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "alerts");
						navigationItem.setLabel(LanguageUtil.get(request, "alerts"));
					});

			}
		}
	%>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= announcementsAdminViewManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="announcementsAdminViewManagementToolbar"
	creationMenu="<%= announcementsAdminViewManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= announcementsAdminViewManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= announcementsAdminViewManagementToolbarDisplayContext.getTotal() %>"
	searchContainerId="announcementsEntries"
	selectable="<%= true %>"
	showSearch="false"
/>

<div class="container-fluid-1280">
	<aui:form action="<%= currentURL %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="announcementsEntries"
			searchContainer="<%= announcementsEntriesSearchContainer %>"
			total="<%= announcementsEntriesSearchContainer.getTotal() %>"
		>
			<liferay-ui:search-container-results
				results="<%= announcementsEntriesSearchContainer.getResults() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.announcements.kernel.model.AnnouncementsEntry"
				keyProperty="entryId"
				modelVar="entry"
			>

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("actions", StringUtil.merge(announcementsAdminViewManagementToolbarDisplayContext.getAvailableActions(entry)));

				row.setData(rowData);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/announcements/view_entry");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(entry.getTitle()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= LanguageUtil.get(resourceBundle, entry.getType()) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= entry.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-date
					name="display-date"
					value="<%= entry.getDisplayDate() %>"
				/>

				<liferay-ui:search-container-column-date
					name="expiration-date"
					value="<%= entry.getExpirationDate() %>"
				/>

				<liferay-ui:search-container-column-jsp
					path="/announcements_admin/entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchContainer="<%= announcementsEntriesSearchContainer %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	var deleteEntries = function() {
		if (
			confirm(
				'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />'
			)
		) {
			var form = document.getElementById('<portlet:namespace />fm');

			if (form) {
				form.setAttribute('method', 'post');

				var cmd = form.querySelector(
					'#<portlet:namespace /><%= Constants.CMD %>'
				);

				if (cmd) {
					cmd.setAttribute('value', '<%= Constants.DELETE %>');
				}

				submitForm(
					form,
					'<portlet:actionURL name="/announcements/edit_entry" />'
				);
			}
		}
	};

	var ACTIONS = {
		deleteEntries: deleteEntries
	};

	Liferay.componentReady('announcementsAdminViewManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>