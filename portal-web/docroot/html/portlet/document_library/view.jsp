<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

long start = ParamUtil.getLong(request, "start");
long end = ParamUtil.getLong(request, "end", SearchContainer.DEFAULT_DELTA);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:layout cssClass="view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />
		</aui:column>

		<aui:column columnWidth="<%= showFolderMenu ? 80 : 100 %>" cssClass="context-pane" last="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content">
					<liferay-util:include page="/html/portlet/document_library/file_entry_search.jsp" />

					<div class="toolbar">
						<liferay-util:include page="/html/portlet/document_library/toolbar.jsp" />
					</div>

					<div class="display-style">
						<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>
					</div>
				</div>
			</div>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
			</div>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= editFileEntryURL.toString() %>" method="get" name="fm2">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />
				<aui:input name="fileShortcutIds" type="hidden" />

				<div class="document-container" id="<portlet:namespace />documentContainer">
					<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
				</div>

				<div class="document-entries-paginator"></div>
			</aui:form>
		</aui:column>
	</aui:layout>
</div>

<%
if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
		PortalUtil.setPageSubtitle(folder.getName(), request);
		PortalUtil.setPageDescription(folder.getDescription(), request);
	}
}
%>

<aui:script>
	function <portlet:namespace />editFileEntry(action) {
		if (action == '<%= Constants.DELETE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
		else if (action == '<%= Constants.MOVE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /></portlet:renderURL>');
		}
		else {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />doFileEntryAction',
		function(action, url) {
			document.<portlet:namespace />fm2.method = "post";
			document.<portlet:namespace />fm2.<portlet:namespace /><%= Constants.CMD %>.value = action;
			document.<portlet:namespace />fm2.<portlet:namespace />folderIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileShortcutIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox');

			submitForm(document.<portlet:namespace />fm2, url);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var disabled = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			actionsButton.toggleClass('disabled', disabled);
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var markSelected = function(node) {
		var documentThumbnail = node.ancestor('.document-display-style.selectable');

		documentThumbnail.toggleClass('selected');
	};

	var documentContainer = A.one('#<portlet:namespace />documentContainer');

	documentContainer.delegate(
		'change',
		function(event) {
			markSelected(event.currentTarget);

			<portlet:namespace />toggleActionsButton();

			Liferay.Util.checkAllBox(documentContainer, ['<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox'], '#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox');
		},
		'.document-selector'
	);

	<c:if test='<%= (!displayStyle.equals("list")) %>'>
		var toggleHoverClass = function(event) {
			var documentDisplayStyle = event.currentTarget.ancestor('.document-display-style');

			if (documentDisplayStyle) {
				documentDisplayStyle.toggleClass('hover', (event.type == 'focus'));
			}
		};

		documentContainer.delegate('focus', toggleHoverClass, '*');

		documentContainer.delegate('blur', toggleHoverClass, '*');
	</c:if>
</aui:script>

<span id="<portlet:namespace />displayStyleButtonsContainer">
	<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
</span>

<aui:script use="liferay-document-library">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL" />

	new Liferay.Portlet.DocumentLibrary(
		{
			defaultParentFolderId: '<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			end: <%= SearchContainer.DEFAULT_DELTA %>,
			folderId: '<%= folderId %>',
			mainUrl: '<%= mainURL %>',
			portletId: '<%= portletDisplay.getId() %>',
			rowsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			rowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
			showSiblings: true,
			start: 0,
			strutsAction: '/document_library/view',
			viewBreadcrumb: true,
			viewEntries: true,
			viewFolders: true
		}
	);
</aui:script>