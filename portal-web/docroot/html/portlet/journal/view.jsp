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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation");
String browseBy = ParamUtil.getString(request, "browseBy");

JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	folder = JournalFolderLocalServiceUtil.fetchFolder(folderId);

	if (folder == null) {
		folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, WorkflowConstants.STATUS_ANY);

boolean showSelectAll = false;

if (total > 0) {
	showSelectAll = true;
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));
%>

<liferay-ui:trash-undo />

<div id="<portlet:namespace />journalContainer">
	<aui:row cssClass="lfr-app-column-view">
		<aui:col cssClass="navigation-pane" width="<%= 25 %>">
			<liferay-util:include page="/html/portlet/journal/view_folders.jsp" />
		</aui:col>

		<aui:col cssClass="context-pane" last="<%= true %>" width="<%= 75 %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= showSelectAll %>"
			>
				<liferay-util:include page="/html/portlet/journal/toolbar.jsp" />
			</liferay-ui:app-view-toolbar>

			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<c:if test='<%= !navigation.equals("recent") && !navigation.equals("mine") && Validator.isNull(browseBy) %>'>
					<liferay-util:include page="/html/portlet/journal/breadcrumb.jsp" />
				</c:if>
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

					<%
					String keywords = ParamUtil.getString(request, "keywords");

					boolean advancedSearch = ParamUtil.getBoolean(liferayPortletRequest, ArticleDisplayTerms.ADVANCED_SEARCH);
					%>

					<c:choose>
						<c:when test="<%= Validator.isNotNull(keywords) || advancedSearch %>">
							<liferay-util:include page="/html/portlet/journal/search_resources.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/journal/view_entries.jsp" />
						</c:otherwise>
					</c:choose>
				</div>
			</aui:form>
		</aui:col>
	</aui:row>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>').length == 0);

			if (actionsButton) {
				actionsButton.toggle(!hide);
			}
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-journal-navigation">
	var journalNavigation = new Liferay.Portlet.JournalNavigation(
		{
			advancedSearch: '<%= DisplayTerms.ADVANCED_SEARCH %>',
			displayStyle: '<%= HtmlUtil.escapeJS(journalDisplayContext.getDisplayStyle()) %>',
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_entry" /></portlet:actionURL>',
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'POST',
					node: A.one(document.<portlet:namespace />fm)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/journal/move_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "_" + PortletKeys.CONTROL_PANEL_MENU + "_portlet_" + PortletKeys.TRASH : StringPool.BLANK %>',
				updateable: true
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>',
			rowIds: '<%= RowChecker.ROW_IDS %>'
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