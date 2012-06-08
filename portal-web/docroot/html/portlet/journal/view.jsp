<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

int entryRowsPerPage = entryEnd - entryStart;

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

int folderRowsPerPage = folderEnd - folderStart;

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<div id="<portlet:namespace />journalContainer">
	<aui:layout cssClass="lfr-app-column-view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/journal/view_folders.jsp" />

			<div class="folder-paginator"></div>
		</aui:column>

		<aui:column columnWidth="80" cssClass="context-pane" last="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content">
					<liferay-util:include page="/html/portlet/journal/article_toolbar_search.jsp" />

					<div class="toolbar">
						<liferay-util:include page="/html/portlet/journal/toolbar.jsp" />
					</div>

					<div class="display-style">
						<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>
					</div>
				</div>
			</div>

			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/journal/breadcrumb.jsp" />
			</div>

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal/edit_article");
			portletURL.setParameter("folderId", String.valueOf(folderId));
			%>

			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

				<aui:input name="folderIds" type="hidden" />
				<aui:input name="articleIds" type="hidden" />
				<aui:input name="newFolderId" type="hidden" />

				<div class="journal-container" id="<portlet:namespace />entriesContainer">
					<liferay-util:include page="/html/portlet/journal/article_search.jsp" />

					<c:choose>
						<c:when test='<%= navigation.equals("recent") %>'>
							<liferay-util:include page="/html/portlet/journal/view_recent.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/journal/view_entries.jsp" />
						</c:otherwise>
					</c:choose>
				</div>

				<div class="article-entries-paginator"></div>
			</aui:form>
		</aui:column>
	</aui:layout>
</div>

<%
int entriesTotal = GetterUtil.getInteger((String)request.getAttribute("view.jsp-total"));
int foldersTotal = GetterUtil.getInteger((String)request.getAttribute("view_folders.jsp-total"));
%>

<span id="<portlet:namespace />displayStyleButtonsContainer">
	<liferay-util:include page="/html/portlet/journal/display_style_buttons.jsp" />
</span>

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
			actions: {
				DELETE: '<%= Constants.DELETE %>',
				DELETE_VERSIONS: '<%= Constants.DELETE_VERSIONS %>',
				EXPIRE: '<%= Constants.EXPIRE %>',
				MOVE: '<%= Constants.MOVE %>'
			},
			advancedSearch: '<%= DisplayTerms.ADVANCED_SEARCH %>',
			allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
			defaultParams: {
				p_p_id: <%= portletId %>,
				p_p_lifecycle: 0
			},
			defaultParentFolderId: '<%= JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			displayViews: ['<%= StringUtil.merge(displayViews, "','") %>'],
			editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_entry" /></portlet:actionURL>',
			entriesTotal: <%= entriesTotal %>,
			entryEnd: <%= entryEnd %>,
			entryRowsPerPage: <%= entryRowsPerPage %>,
			entryRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
			entryStart: <%= entryStart %>,
			folderEnd: <%= folderEnd %>,
			folderId: <%= folderId %>,
			folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
			folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
			folderRowsPerPage: <%= folderRowsPerPage %>,
			folderRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
			folderStart: <%= folderStart %>,
			foldersTotal: <%= foldersTotal %>,
			form: {
				method: 'post',
				node: A.one(document.<portlet:namespace />fm)
			},
			mainUrl: '<%= mainURL %>',
			moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/journal/move_entry" /></portlet:renderURL>',
			namespace: '<portlet:namespace />',
			portletId: '<%= portletId %>',
			rowIds: '<%= RowChecker.ROW_IDS %>',
			strutsAction: '/journal/view'
		}
	);
</aui:script>