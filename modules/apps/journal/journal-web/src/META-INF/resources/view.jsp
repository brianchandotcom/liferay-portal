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
String navigation = ParamUtil.getString(request, "navigation");
String browseBy = ParamUtil.getString(request, "browseBy");

JournalFolder folder = ActionUtil.getFolder(request);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	folder = JournalFolderLocalServiceUtil.fetchFolder(folderId);

	if (folder == null) {
		folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

String keywords = ParamUtil.getString(request, "keywords");

boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, ArticleDisplayTerms.ADVANCED_SEARCH);

boolean search = Validator.isNotNull(keywords) || advancedSearch;

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<portlet:actionURL name="restoreTrashEntries" var="restoreTrashEntriesURL" />

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/navigation.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<div id="<portlet:namespace />journalContainer">
	<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<div class="sidenav-menu-slider">
			<div class="sidebar sidebar-default sidenav-menu">
				<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
			</div>
		</div>

		<div class="sidenav-content">
			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") && Validator.isNull(browseBy) %>'>
					<liferay-util:include page="/breadcrumb.jsp" servletContext="<%= application %>" />
				</c:if>
			</div>

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("folderId", String.valueOf(folderId));
			%>

			<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
				<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="articleIds" type="hidden" />
				<aui:input name="newFolderId" type="hidden" />

				<div class="journal-container" id="<portlet:namespace />entriesContainer">
					<c:choose>
						<c:when test="<%= search %>">
							<liferay-util:include page="/search_resources.jsp" servletContext="<%= application %>" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
						</c:otherwise>
					</c:choose>
				</div>
			</aui:form>
		</div>
	</div>
</div>

<c:if test="<%= !search %>">
	<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />
</c:if>

<aui:script>
	$('#<portlet:namespace />infoPanelId').sideNavigation(
		{
			gutter: 15,
			position: 'right',
			toggler: '.infoPanelToggler',
			type: 'relative',
			typeMobile: 'fixed',
			width: 320
		}
	);

	function <portlet:namespace />toggleActionsButton() {
		var form = AUI.$(document.<portlet:namespace />fm);

		var hide = Liferay.Util.listCheckedExcept(form, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0;

		AUI.$('#<portlet:namespace />actionButtons').toggleClass('on', !hide);
	}

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-journal-navigation">
	var journalNavigation = new Liferay.Portlet.JournalNavigation(
		{
			advancedSearch: '<%= DisplayTerms.ADVANCED_SEARCH %>',
			displayStyle: '<%= HtmlUtil.escapeJS(journalDisplayContext.getDisplayStyle()) %>',
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL />',
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'POST',
					node: A.one(document.<portlet:namespace />fm)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="mvcPath" value="/move_entries.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
				selectedCSSClass: 'active',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? ("_" + PortletProviderUtil.getPortletId(PortalProductMenuApplicationType.ProductMenu.CLASS_NAME, PortletProvider.Action.VIEW) + "_portlet_" + PortletProviderUtil.getPortletId(TrashEntry.class.getName(), PortletProvider.Action.VIEW)) : StringPool.BLANK %>',
				updateable: true
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>',
			rowIds: '<portlet:namespace /><%= RowChecker.ROW_IDS %>',
			select: {
				displayStyleCSSClass: 'entry-display-style',
				selectAllCheckbox: '.select-all-checkboxes',
				selectedCSSClass: 'active'
			}
		}
	);

	var clearJournalNavigationHandles = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			journalNavigation.destroy();

			Liferay.detach('destroyPortlet', clearJournalNavigationHandles);
		}
	};

	Liferay.on('destroyPortlet', clearJournalNavigationHandles);
</aui:script>