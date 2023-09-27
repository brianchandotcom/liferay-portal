<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean mergeWithParentFolderDisabled = ParamUtil.getBoolean(request, "mergeWithParentFolderDisabled");

if (folder != null) {
	BookmarksUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
}
else {
	if (parentFolderId > 0) {
		BookmarksUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);

		if (!layout.isTypeControlPanel()) {
			PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-subfolder"), currentURL);
		}
	}
	else if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-folder"), currentURL);
	}
}

String headerTitle = (folder == null) ? ((parentFolderId > 0) ? LanguageUtil.get(request, "add-subfolder") : LanguageUtil.get(request, "add-folder")) : LanguageUtil.format(request, "edit-x", folder.getName(), false);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(headerTitle);
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<portlet:actionURL name="/bookmarks/edit_folder" var="editFolderURL">
		<portlet:param name="mvcRenderCommandName" value="/bookmarks/edit_folder" />
	</portlet:actionURL>

	<aui:form action="<%= editFolderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveFolder();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
		<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

		<liferay-ui:error exception="<%= FolderNameException.class %>">
			<p>
				<liferay-ui:message arguments="<%= BookmarksFolderConstants.NAME_RESERVED_WORDS %>" key="the-folder-name-cannot-be-blank-or-a-reserved-word-such-as-x" />
			</p>

			<p>
				<liferay-ui:message arguments="<%= BookmarksFolderConstants.NAME_INVALID_CHARACTERS %>" key="the-folder-name-cannot-contain-the-following-invalid-characters-x" />
			</p>
		</liferay-ui:error>

		<aui:model-context bean="<%= folder %>" model="<%= BookmarksFolder.class %>" />

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<aui:input name="name" />

					<aui:input name="description" />
				</aui:fieldset>

				<c:if test="<%= folder != null %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="parent-folder">

						<%
						String parentFolderName = LanguageUtil.get(request, "home");

						try {
							BookmarksFolder parentFolder = BookmarksFolderServiceUtil.getFolder(parentFolderId);

							parentFolderName = parentFolder.getName();
						}
						catch (NoSuchFolderException nsfe) {
						}
						%>

						<div class="form-group">
							<aui:input label="parent-folder" name="parentFolderName" type="resource" value="<%= parentFolderName %>" />

							<aui:button name="selectFolderButton" value="select" />

							<aui:script>
								var <portlet:namespace />selectFolderButton = document.getElementById(
									'<portlet:namespace />selectFolderButton'
								);

								if (<portlet:namespace />selectFolderButton) {
									<portlet:namespace />selectFolderButton.addEventListener(
										'click',
										(event) => {
											Liferay.Util.openSelectionModal({
												onSelect: function (event) {
													var folderData = {
														idString: 'parentFolderId',
														idValue: event.entityid,
														nameString: 'parentFolderName',
														nameValue: event.entityname,
													};

													Liferay.Util.selectFolder(
														folderData,
														'<portlet:namespace />'
													);
												},
												selectEventName: '<portlet:namespace />selectFolder',
												title:
													'<liferay-ui:message arguments="folder" key="select-x" />',
												url:
													'<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" /></liferay-portlet:renderURL>',
											});
										}
									);
								}
							</aui:script>

							<%
							String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentFolderId', 'parentFolderName', this, '" + liferayPortletResponse.getNamespace() + "');";
							%>

							<aui:button disabled="<%= parentFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
						</div>

						<aui:input disabled="<%= mergeWithParentFolderDisabled %>" inlineLabel="right" label="merge-with-parent-folder" labelCssClass="simple-toggle-switch" name="mergeWithParentFolder" type="toggle-switch" />
					</aui:fieldset>
				</c:if>

				<liferay-expando:custom-attributes-available
					className="<%= BookmarksFolder.class.getName() %>"
				>
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
						<liferay-expando:custom-attribute-list
							className="<%= BookmarksFolder.class.getName() %>"
							classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</liferay-expando:custom-attributes-available>

				<c:if test="<%= folder == null %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
						<aui:field-wrapper label="permissions">
							<liferay-ui:input-permissions
								modelName="<%= BookmarksFolder.class.getName() %>"
							/>
						</aui:field-wrapper>
					</aui:fieldset>
				</c:if>

				<div class="sheet-footer">
					<aui:button type="submit" />

					<aui:button href="<%= redirect %>" type="cancel" />
				</div>
			</div>
		</div>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveFolder() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var cmd = form.querySelector(
				'#<portlet:namespace /><%= Constants.CMD %>'
			);

			if (cmd) {
				cmd.setAttribute(
					'value',
					'<%= (folder == null) ? Constants.ADD : Constants.UPDATE %>'
				);

				submitForm(form);
			}
		}
	}
</aui:script>