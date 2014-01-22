<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation");

JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = JournalFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<liferay-ui:trash-undo />

<div id="<portlet:namespace />journalContainer">
	<aui:row cssClass="lfr-app-column-view">
		<aui:col cssClass="navigation-pane" width="<%= 25 %>">
			<liferay-util:include page="/html/portlet/journal/view_folders.jsp" />

			<div class="folder-pagination"></div>
		</aui:col>

		<aui:col cssClass="context-pane" last="<%= true %>" width="<%= 75 %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= true %>"
			>
				<liferay-util:include page="/html/portlet/journal/toolbar.jsp" />
			</liferay-ui:app-view-toolbar>

			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/journal/breadcrumb.jsp" />
			</div>

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal/edit_article");
			portletURL.setParameter("folderId", String.valueOf(folderId));
			%>

			<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="articleIds" type="hidden" />
				<aui:input name="newFolderId" type="hidden" />

				<div class="journal-container" id="<portlet:namespace />entriesContainer">
					<liferay-util:include page="/html/portlet/journal/view_entries.jsp" />
				</div>

				<div class="article-entries-pagination"></div>
			</aui:form>
		</aui:col>
	</aui:row>
</div>

<%
int entriesTotal = GetterUtil.getInteger((String)request.getAttribute("view.jsp-total"));
int foldersTotal = GetterUtil.getInteger((String)request.getAttribute("view_folders.jsp-total"));

entryEnd = GetterUtil.getInteger(request.getAttribute("view_entries.jsp-entryEnd"), entryEnd);
entryStart = GetterUtil.getInteger(request.getAttribute("view_entries.jsp-entryStart"), entryStart);

folderEnd = GetterUtil.getInteger(request.getAttribute("view_folders.jsp-folderEnd"), folderEnd);
folderStart = GetterUtil.getInteger(request.getAttribute("view_folders.jsp-folderStart"), folderStart);
%>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			if (actionsButton) {
				actionsButton.toggle(!hide);
			}
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-journal-navigation">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL" />

	new Liferay.Portlet.JournalNavigation(
		{
			advancedSearch: '<%= DisplayTerms.ADVANCED_SEARCH %>',
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			folders: {
				defaultParams: {
					p_p_id: '<%= portletDisplay.getId() %>',
					p_p_lifecycle: 0
				},
				defaultParentFolderId: '<%= JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
				'listViewConfig.useTransition': false,
				mainUrl: '<%= mainURL %>',
				strutsAction: '/journal/view'
			},
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_entry" /></portlet:actionURL>',
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'post',
					node: A.one(document.<portlet:namespace />fm)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/journal/move_entry" /></portlet:renderURL>',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "_" + PortletKeys.CONTROL_PANEL_MENU + "_portlet_" + PortletKeys.TRASH : StringPool.BLANK %>',
				updateable: true
			},
			paginator: {
				entriesTotal: <%= entriesTotal %>,
				entryEnd: <%= entryEnd %>,
				entryRowsPerPage: <%= entryEnd - entryStart %>,
				entryRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
				entryStart: <%= entryStart %>,
				folderEnd: <%= folderEnd %>,
				folderId: <%= folderId %>,
				folderRowsPerPage: <%= folderEnd - folderStart %>,
				folderRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
				folderStart: <%= folderStart %>,
				foldersTotal: <%= foldersTotal %>
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>',
			rowIds: '<%= RowChecker.ROW_IDS %>',
			select: {

				<%
				String[] escapedDisplayViews = new String[displayViews.length];

				for (int i = 0; i < displayViews.length; i++) {
					escapedDisplayViews[i] = HtmlUtil.escapeJS(displayViews[i]);
				}
				%>

				displayViews: ['<%= StringUtil.merge(escapedDisplayViews, "','") %>']
			}
		}
	);
</aui:script>