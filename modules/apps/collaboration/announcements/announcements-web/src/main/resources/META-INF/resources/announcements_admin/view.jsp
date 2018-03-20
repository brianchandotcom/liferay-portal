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

announcementsEntriesSearchContainer.setTotal(AnnouncementsEntryLocalServiceUtil.getEntriesCount(classNameId, classPK, navigation.equals("alerts")));
announcementsEntriesSearchContainer.setResults(AnnouncementsEntryLocalServiceUtil.getEntries(classNameId, classPK, navigation.equals("alerts"), announcementsEntriesSearchContainer.getStart(), announcementsEntriesSearchContainer.getEnd()));

List<AnnouncementsEntry> announcementsEntries = announcementsEntriesSearchContainer.getResults();
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewEntriesURL" />

		<aui:nav-item href="<%= viewEntriesURL %>" label="announcements" selected='<%= navigation.equals("announcements") %>' />

		<portlet:renderURL var="viewAlertsURL">
			<portlet:param name="navigation" value="alerts" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewAlertsURL %>" label="alerts" selected='<%= navigation.equals("alerts") %>' />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= announcementsEntries.isEmpty() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="announcementsEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle='<%= "list" %>'
		/>

		<portlet:renderURL var="addEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/announcements/edit_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="distributionScope" value="<%= distributionScope %>" />
			<portlet:param name="alert" value='<%= String.valueOf(navigation.equals("alerts")) %>' />
		</portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= navigation.equals("alerts") ? LanguageUtil.get(resourceBundle, "add-alert") : LanguageUtil.get(resourceBundle, "add-announcement") %>'
				url="<%= addEntryURL %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		AnnouncementsAdminViewDisplayContext announcementsAdminViewDisplayContext = new DefaultAnnouncementsAdminViewDisplayContext(request);
		%>

		<liferay-frontend:management-bar-navigation
			disabled="<%= false %>"
			label="<%= announcementsAdminViewDisplayContext.getCurrentDistributionScopeLabel() %>"
			navigationKeys="<%= announcementsAdminViewDisplayContext.getDistributionScopes() %>"
			navigationParam="distributionScope"
			portletURL="<%= PortletURLUtil.clone(currentURLObj, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
	function <portlet:namespace />deleteEntries() {
		if (confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/announcements/edit_entry" />');
		}
	}
</aui:script>