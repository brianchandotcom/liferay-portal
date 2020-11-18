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

<%@ include file="/document_library/init.jsp" %>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view_file_entry.jsp#pre" />

<%
DLViewFileEntryDisplayContext dlViewFileEntryDisplayContext = (DLViewFileEntryDisplayContext)request.getAttribute(DLViewFileEntryDisplayContext.class.getName());

FileEntry fileEntry = dlViewFileEntryDisplayContext.getFileEntry();

FileVersion fileVersion = dlViewFileEntryDisplayContext.getFileVersion();

boolean addPortletBreadcrumbEntries = ParamUtil.getBoolean(request, "addPortletBreadcrumbEntries", true);

if (addPortletBreadcrumbEntries) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(dlViewFileEntryDisplayContext.getFileEntry(), request, renderResponse);
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(dlViewFileEntryDisplayContext.getRedirect());

	renderResponse.setTitle(fileVersion.getTitle());
}
%>

<div class="<%= portletTitleBasedNavigation ? StringPool.BLANK : "closed sidenav-container sidenav-right" %>" id="<%= liferayPortletResponse.getNamespace() + (portletTitleBasedNavigation ? "FileEntry" : "infoPanelId") %>">
	<c:if test="<%= portletTitleBasedNavigation %>">
		<liferay-util:include page="/document_library/file_entry_upper_tbar.jsp" servletContext="<%= application %>" />
	</c:if>

	<portlet:actionURL name="/document_library/edit_file_entry" var="editFileEntry" />

	<aui:form action="<%= editFileEntry %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="fileEntryId" type="hidden" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
		<aui:input name="newFolderId" type="hidden" />
		<aui:input name="rowIdsDLFileShortcut" type="hidden" />
		<aui:input name="rowIdsFileEntry" type="hidden" />
		<aui:input name="rowIdsFolder" type="hidden" />
	</aui:form>

	<c:if test="<%= !portletTitleBasedNavigation && dlViewFileEntryDisplayContext.isShowHeader() %>">
		<liferay-ui:header
			backURL="<%= dlViewFileEntryDisplayContext.getRedirect() %>"
			localizeTitle="<%= false %>"
			title="<%= dlViewFileEntryDisplayContext.getDocumentTitle() %>"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= portletTitleBasedNavigation %>">
			<div class="contextual-sidebar sidebar-light sidebar-preview" id="<%= liferayPortletResponse.getNamespace() + "ContextualSidebar" %>">

				<%
				request.setAttribute("info_panel.jsp-fileEntry", dlViewFileEntryDisplayContext.getFileEntry());
				request.setAttribute("info_panel.jsp-fileVersion", dlViewFileEntryDisplayContext.getFileVersion());
				request.setAttribute("info_panel_file_entry.jsp-hideActions", true);
				%>

				<liferay-util:include page="/document_library/info_panel_file_entry.jsp" servletContext="<%= application %>" />
			</div>
		</c:when>
		<c:otherwise>
			<liferay-frontend:sidebar-panel>

				<%
				request.setAttribute("info_panel.jsp-fileEntry", dlViewFileEntryDisplayContext.getFileEntry());
				request.setAttribute("info_panel.jsp-fileVersion", dlViewFileEntryDisplayContext.getFileVersion());
				%>

				<liferay-util:include page="/document_library/info_panel_file_entry.jsp" servletContext="<%= application %>" />
			</liferay-frontend:sidebar-panel>
		</c:otherwise>
	</c:choose>

	<div class="<%= portletTitleBasedNavigation ? "contextual-sidebar-content" : "sidenav-content" %>">
		<clay:container-fluid>
			<div class="alert alert-danger hide" id="<portlet:namespace />openMSOfficeError"></div>

			<c:if test="<%= !portletTitleBasedNavigation %>">
				<div class="file-entry-actions">
					<liferay-frontend:management-bar-sidenav-toggler-button
						label="info"
					/>

					<%
					for (ToolbarItem toolbarItem : dlViewFileEntryDisplayContext.getToolbarItems()) {
					%>

						<liferay-ui:toolbar-item
							toolbarItem="<%= toolbarItem %>"
						/>

					<%
					}
					%>

				</div>
			</c:if>

			<c:if test="<%= dlViewFileEntryDisplayContext.isShowLockInfo() %>">
				<div class="alert <%= dlViewFileEntryDisplayContext.getLockInfoCssClass() %>">
					<%= dlViewFileEntryDisplayContext.getLockInfoMessage(locale) %>
				</div>
			</c:if>

			<div class="body-row">
				<c:if test="<%= PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED %>">

					<%
					dlViewFileEntryDisplayContext.renderPreview(pageContext);
					%>

				</c:if>

				<c:if test="<%= dlViewFileEntryDisplayContext.isShowComments() %>">
					<liferay-comment:discussion
						className="<%= dlViewFileEntryDisplayContext.getDiscussionClassName() %>"
						classPK="<%= dlViewFileEntryDisplayContext.getDiscussionClassPK() %>"
						formName="fm2"
						ratingsEnabled="<%= dlViewFileEntryDisplayContext.isEnableDiscussionRatings() %>"
						redirect="<%= currentURL %>"
						userId="<%= dlViewFileEntryDisplayContext.getDiscussionUserId() %>"
					/>
				</c:if>
			</div>
		</clay:container-fluid>
	</div>
</div>

<c:if test="<%= dlViewFileEntryDisplayContext.isShowVersionDetails() %>">

	<%
	request.setAttribute("edit_file_entry.jsp-checkedOut", fileEntry.isCheckedOut());
	%>

	<liferay-util:include page="/document_library/version_details.jsp" servletContext="<%= application %>" />
</c:if>

<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
	<portlet:param name="folderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
	<portlet:param name="originFolderId" value="<%= String.valueOf(fileEntry.getFolderId()) %>" />
</portlet:renderURL>

<portlet:actionURL name="/document_library/edit_entry" var="editEntryURL" />

<aui:script>
	function <portlet:namespace />move(
		selectedItems,
		parameterName,
		parameterValue
	) {
		var namespace = '<portlet:namespace />';

		Liferay.Util.openSelectionModal({
			id: namespace + 'selectFolder',
			onSelect: function (selectedItem) {
				var form = document.getElementById(namespace + 'fm');

				if (parameterName && parameterValue) {
					form.elements[namespace + parameterName].value = parameterValue;
				}

				var actionUrl = '<%= editEntryURL.toString() %>';

				form.setAttribute('action', actionUrl);
				form.setAttribute('enctype', 'multipart/form-data');

				form.elements[namespace + 'cmd'].value = 'move';
				form.elements[namespace + 'newFolderId'].value =
					selectedItem.folderid;

				submitForm(form, actionUrl, false);
			},
			selectEventName: namespace + 'selectFolder',
			title:
				'<liferay-ui:message arguments="<%= 1 %>" key="select-destination-folder-for-x-items" translateArguments="<%= false %>" />',
			url: '<%= selectFolderURL.toString() %>',
		});
	}
</aui:script>

<c:if test="<%= portletTitleBasedNavigation %>">
	<aui:script>
		var openContextualSidebarButton = document.getElementById(
			'<portlet:namespace />OpenContextualSidebar'
		);

		if (openContextualSidebarButton) {
			openContextualSidebarButton.addEventListener('click', function (event) {
				event.currentTarget.classList.toggle('active');

				document
					.getElementById('<portlet:namespace />ContextualSidebar')
					.classList.toggle('contextual-sidebar-visible');
			});
		}
	</aui:script>
</c:if>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view_file_entry.jsp#post" />