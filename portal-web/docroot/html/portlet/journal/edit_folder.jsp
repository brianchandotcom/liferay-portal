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
String redirect = ParamUtil.getString(request, "redirect");

JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean mergeWithParentFolderDisabled = ParamUtil.getBoolean(request, "mergeWithParentFolderDisabled");
%>

<portlet:actionURL var="editFolderURL">
	<portlet:param name="struts_action" value="/journal/edit_folder" />
</portlet:actionURL>

<aui:form action="<%= editFolderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveFolder();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (folder == null) %>"
		title='<%= (folder == null) ? "new-folder" : folder.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= folder %>" model="<%= JournalFolder.class %>" />

	<aui:fieldset>
		<c:if test="<%= folder != null %>">
			<aui:field-wrapper label="parent-folder">

				<%
				String parentFolderName = LanguageUtil.get(pageContext, "home");

				try {
					JournalFolder parentFolder = JournalFolderServiceUtil.getFolder(parentFolderId);

					parentFolderName = parentFolder.getName();
				}
				catch (NoSuchFolderException nsfe) {
				}
				%>

				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="struts_action" value="/journal/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
				</portlet:renderURL>

				<aui:a href="<%= viewFolderURL %>" id="parentFolderName"><%= parentFolderName %></aui:a>

				<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/journal/select_folder" />
					<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
				</portlet:renderURL>

				<aui:button name="selecFolderButton" value="select" />

				<aui:script use="aui-base">
					A.one('#<portlet:namespace />selecFolderButton').on(
						'click',
						function(event) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										modal: true,
										zIndex: Liferay.zIndex.WINDOW + 2,
										width: 680
									},
									id: '<portlet:namespace />selectFolder',
									title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "folder") %>',
									uri: '<%= selectFolderURL.toString() %>'
								},
								function(event) {
									var folderData = {
										idString: 'parentFolderId',
										idValue: event.folderid,
										nameString: 'parentFolderName',
										nameValue: event.foldername
									};

									Liferay.Util.selectFolder(folderData, '<portlet:renderURL><portlet:param name="struts_action" value="/journal/view" /></portlet:renderURL>', '<portlet:namespace />');
								}
							);
						}
					);
				</aui:script>

				<%
				String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('parentFolderId', 'parentFolderName', '" + renderResponse.getNamespace() + "');";
				%>

				<aui:button name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />

				<aui:input disabled="<%= mergeWithParentFolderDisabled %>" label="merge-with-parent-folder" name="mergeWithParentFolder" type="checkbox" />
			</aui:field-wrapper>
		</c:if>

		<aui:input autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP)) %>" name="name" />

		<aui:input name="description" />

		<liferay-ui:custom-attributes-available className="<%= JournalFolder.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= JournalFolder.class.getName() %>"
				classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= folder == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= JournalFolder.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveFolder() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (folder == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
if (folder != null) {
	JournalUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	if (parentFolderId > 0) {
		JournalUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);
	}

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-folder"), currentURL);
}
%>