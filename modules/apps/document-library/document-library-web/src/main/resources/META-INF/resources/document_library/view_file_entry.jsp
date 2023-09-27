<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-ui:success key='<%= portletDisplay.getId() + "requestProcessed" %>' message="your-request-completed-successfully" />

<div class="<%= portletTitleBasedNavigation ? StringPool.BLANK : "closed sidenav-container sidenav-right" %>" id="<%= liferayPortletResponse.getNamespace() + (portletTitleBasedNavigation ? "FileEntry" : ("infoPanelId_" + fileEntry.getFileEntryId())) %>">
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

	<c:choose>
		<c:when test="<%= portletTitleBasedNavigation %>">
			<div class="contextual-sidebar sidebar-light sidebar-preview" id="<portlet:namespace />ContextualSidebar">

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
				<div class="file-entry-actions management-bar management-bar-light navbar navbar-expand-md">
					<ul class="navbar-nav navbar-nav-expand">
						<li class="nav-item nav-item-expand">
							<clay:link
								aria-label='<%= LanguageUtil.get(request, "back") %>'
								borderless="<%= true %>"
								displayType="secondary"
								href="<%= dlViewFileEntryDisplayContext.getRedirect() %>"
								icon="angle-left"
								monospaced="<%= true %>"
								type="button"
							/>

							<h3 class="mb-1 text-secondary"><%= dlViewFileEntryDisplayContext.getDocumentTitle() %></h3>
						</li>
						<li class="nav-item">
							<liferay-frontend:sidebar-toggler-button
								cssClass="btn btn-monospaced btn-secondary btn-sm btn-unstyled"
								icon="info-circle-open"
								sidenavId='<%= liferayPortletResponse.getNamespace() + "infoPanelId_" + fileEntry.getFileEntryId() %>'
							/>
						</li>
						<li class="nav-item">
							<clay:dropdown-actions
								aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
								dropdownItems="<%= dlViewFileEntryDisplayContext.getActionDropdownItems() %>"
								propsTransformer="document_library/js/DLFileEntryDropdownPropsTransformer"
							/>
						</li>
					</ul>
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

<div>
	<portlet:actionURL name="/document_library/edit_file_entry_image_editor" var="editImageURL" />

	<react:component
		module="document_library/js/image-editor/EditImageWithImageEditor"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"editImageURL", editImageURL
			).put(
				"redirectURL", currentURL
			).build()
		%>'
	/>
</div>

<%
ItemSelector itemSelector = (ItemSelector)request.getAttribute(ItemSelector.class.getName());

FolderItemSelectorCriterion folderItemSelectorCriterion = new FolderItemSelectorCriterion();

folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new FolderItemSelectorReturnType());
folderItemSelectorCriterion.setFolderId(fileEntry.getFolderId());
folderItemSelectorCriterion.setRepositoryId(fileEntry.getRepositoryId());
folderItemSelectorCriterion.setSelectedFolderId(fileEntry.getFolderId());
folderItemSelectorCriterion.setSelectedRepositoryId(fileEntry.getRepositoryId());

PortletURL selectFolderURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(request), portletDisplay.getNamespace() + "folderSelected", folderItemSelectorCriterion);
%>

<portlet:actionURL name="/document_library/edit_entry" var="editEntryURL" />

<aui:script>
	function <portlet:namespace />move(
		selectedItems,
		parameterName,
		parameterValue
	) {
		var namespace = '<portlet:namespace />';

		Liferay.Util.openSelectionModal({
			selectEventName: '<portlet:namespace />folderSelected',
			multiple: false,
			onSelect: function (selectedItem) {
				if (!selectedItem) {
					return;
				}

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
			title:
				'<liferay-ui:message arguments="<%= 1 %>" key="select-destination-folder-for-x-items" translateArguments="<%= false %>" />',
			url: '<%= HtmlUtil.escapeJS(selectFolderURL.toString()) %>',
		});
	}
</aui:script>

<c:if test="<%= portletTitleBasedNavigation %>">
	<aui:script>
		var openContextualSidebarButton = document.getElementById(
			'<portlet:namespace />OpenContextualSidebar'
		);

		if (openContextualSidebarButton) {
			openContextualSidebarButton.addEventListener('click', (event) => {
				event.currentTarget.classList.toggle('active');

				document
					.getElementById('<portlet:namespace />ContextualSidebar')
					.classList.toggle('contextual-sidebar-visible');
			});
		}
	</aui:script>
</c:if>

<liferay-util:dynamic-include key="com.liferay.document.library.web#/document_library/view_file_entry.jsp#post" />

<%@ include file="/document_library/friendly_url_changed_message.jspf" %>