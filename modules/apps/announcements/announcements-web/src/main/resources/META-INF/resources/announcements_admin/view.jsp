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

SearchContainer<AnnouncementsEntry> announcementsEntriesSearchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, currentURLObj, null, "no-entries-were-found");

announcementsEntriesSearchContainer.setRowChecker(new AnnouncementsEntryChecker(liferayPortletRequest, liferayPortletResponse));

announcementsEntriesSearchContainer.setTotal(AnnouncementsEntryLocalServiceUtil.getEntriesCount(themeDisplay.getCompanyId(), classNameId, classPK, navigation.equals("alerts")));
announcementsEntriesSearchContainer.setResults(AnnouncementsEntryLocalServiceUtil.getEntries(themeDisplay.getCompanyId(), classNameId, classPK, navigation.equals("alerts"), announcementsEntriesSearchContainer.getStart(), announcementsEntriesSearchContainer.getEnd()));

AnnouncementsAdminViewManagementToolbarDisplayContext announcementsAdminViewManagementToolbarDisplayContext = new AnnouncementsAdminViewManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, announcementsEntriesSearchContainer);
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("announcements"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "announcements"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("alerts"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "alerts");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "alerts"));
					});
			}
		}
	%>'
/>

<portlet:actionURL name="/announcements/edit_entry" var="deleteEntriesURL" />

<clay:management-toolbar
	actionDropdownItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteEntriesURL", deleteEntriesURL.toString()
		).put(
			"inputId", Constants.CMD
		).put(
			"inputValue", Constants.DELETE
		).build()
	%>'
	clearResultsURL="<%= announcementsAdminViewManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= announcementsAdminViewManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= announcementsAdminViewManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= announcementsAdminViewManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= announcementsAdminViewManagementToolbarDisplayContext.getTotal() %>"
	propsTransformer="announcements_admin/js/AnnouncementsManagementToolbarPropsTransformer"
	searchContainerId="<%= announcementsAdminViewManagementToolbarDisplayContext.getSearchContainerId() %>"
	selectable="<%= true %>"
	showSearch="<%= false %>"
/>

<clay:container-fluid>
	<aui:form action="<%= currentURL %>" method="get" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="<%= announcementsAdminViewManagementToolbarDisplayContext.getSearchContainerId() %>"
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
				row.setData(
					HashMapBuilder.<String, Object>put(
						"actions", StringUtil.merge(announcementsAdminViewManagementToolbarDisplayContext.getAvailableActions(entry))
					).build());
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					href='<%=
						PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/announcements/view_entry"
						).setRedirect(
							currentURL
						).setParameter(
							"entryId", entry.getEntryId()
						).buildPortletURL()
					%>'
					name="title"
					value="<%= HtmlUtil.escape(entry.getTitle()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="type"
					value="<%= HtmlUtil.escape(LanguageUtil.get(resourceBundle, entry.getType())) %>"
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
</clay:container-fluid>