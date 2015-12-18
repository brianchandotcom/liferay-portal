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

<%
String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId");

Folder parentFolder = null;

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String parentFolderName = LanguageUtil.get(request, "home");

try {
	if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		parentFolder = DLAppLocalServiceUtil.getFolder(parentFolderId);

		parentFolderName = parentFolder.getName();
	}
}
catch (NoSuchFolderException nsfe) {
}

boolean rootFolder = ParamUtil.getBoolean(request, "rootFolder");

boolean workflowEnabled = WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(DLFileEntry.class.getName()) != null) && DLFolderPermission.contains(permissionChecker, themeDisplay.getScopeGroupId(), folderId, ActionKeys.UPDATE);

List<WorkflowDefinition> workflowDefinitions = null;

if (workflowEnabled) {
	workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
}

String headerTitle = (folder == null) ? (rootFolder ? LanguageUtil.get(request, "home") : LanguageUtil.get(request, "new-folder")) : folder.getName();

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<liferay-util:buffer var="removeFileEntryTypeIcon">
		<liferay-ui:icon
			iconCssClass="icon-remove"
			label="<%= true %>"
			message="remove"
		/>
	</liferay-util:buffer>

	<portlet:actionURL name="/document_library/edit_folder" var="editFolderURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
	</portlet:actionURL>

	<aui:form action="<%= editFolderURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "savePage();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value='<%= rootFolder ? "updateWorkflowDefinitions" : ((folder == null) ? Constants.ADD : Constants.UPDATE) %>' />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
		<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
		<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				localizeTitle="<%= false %>"
				title="<%= headerTitle %>"
			/>
		</c:if>

		<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="please-enter-a-unique-folder-name" />
		<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />

		<liferay-ui:error exception="<%= FolderNameException.class %>">
			<p>
				<liferay-ui:message arguments="<%= new String[] {DLFolderConstants.NAME_LABEL, DLFolderConstants.NAME_GENERAL_RESTRICTIONS, DLFolderConstants.getNameReservedWords(PropsValues.DL_NAME_BLACKLIST)} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
			</p>

			<p>
				<liferay-ui:message arguments="<%= new String[] {DLFolderConstants.getNameInvalidEndCharacters(PropsValues.DL_CHAR_LAST_BLACKLIST)} %>" key="the-folder-name-cannot-end-with-the-following-characters-x" />
			</p>

			<p>
				<liferay-ui:message arguments="<%= new String[] {DLFolderConstants.NAME_LABEL, DLFolderConstants.getNameInvalidCharacters(PropsValues.DL_CHAR_BLACKLIST)} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
			</p>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RequiredFileEntryTypeException.class %>" message="please-select-a-document-type" />

		<aui:model-context bean="<%= folder %>" model="<%= DLFolder.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<c:if test="<%= !rootFolder %>">
					<c:if test="<%= folder != null %>">
						<aui:input name="parentFolder" type="resource" value="<%= parentFolderName %>" />
					</c:if>

					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

					<c:if test="<%= (parentFolder == null) || parentFolder.isSupportsMetadata() %>">
						<aui:input name="description" />
					</c:if>
				</c:if>
			</aui:fieldset>

			<c:if test="<%= rootFolder || ((folder != null) && (folder.getModel() instanceof DLFolder)) %>">

				<%
				DLFolder dlFolder = null;

				long defaultFileEntryTypeId = 0;

				if (!rootFolder) {
					dlFolder = (DLFolder)folder.getModel();

					defaultFileEntryTypeId = dlFolder.getDefaultFileEntryTypeId();
				}

				List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, false);

				String headerNames = null;

				if (workflowEnabled) {
					headerNames = "name,workflow,null";
				}
				else {
					headerNames = "name,null";
				}
				%>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" helpMessage='<%= rootFolder ? "" : "document-type-restrictions-help" %>' label='<%= rootFolder ? "" : (workflowEnabled ? "document-type-restrictions-and-workflow" : "document-type-restrictions") %>'>
					<c:if test="<%= !rootFolder %>">
						<aui:input checked="<%= dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_INHERIT %>" id="restrictionTypeInherit" label='<%= workflowEnabled ? LanguageUtil.format(request, "use-document-type-restrictions-and-workflow-of-the-parent-folder-x", parentFolderName, false) : LanguageUtil.format(request, "use-document-type-restrictions-of-the-parent-folder-x", parentFolderName, false) %>' name="restrictionType" type="radio" value="<%= DLFolderConstants.RESTRICTION_TYPE_INHERIT %>" />

						<aui:input checked="<%= dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW %>" id="restrictionTypeDefined" label='<%= workflowEnabled ? LanguageUtil.format(request, "define-specific-document-type-restrictions-and-workflow-for-this-folder-x", folder.getName(), false) : LanguageUtil.format(request, "define-specific-document-type-restrictions-for-this-folder-x", folder.getName(), false) %>' name="restrictionType" type="radio" value="<%= DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW %>" />

						<div class='<%= (dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace />restrictionTypeDefinedDiv">
							<liferay-ui:search-container
								headerNames="<%= headerNames %>"
								total="<%= fileEntryTypes.size() %>"
							>
								<liferay-ui:search-container-results
									results="<%= fileEntryTypes %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
									escapedModel="<%= true %>"
									keyProperty="fileEntryTypeId"
									modelVar="dlFileEntryType"
								>
									<liferay-ui:search-container-column-text
										name="name"
										value="<%= dlFileEntryType.getName(locale) %>"
									/>

									<c:if test="<%= workflowEnabled %>">
										<liferay-ui:search-container-column-text name="workflow">
											<aui:select label="" name='<%= "workflowDefinition" + dlFileEntryType.getFileEntryTypeId() %>' title="workflow-definition">
												<aui:option label="no-workflow" value="" />

												<%
												WorkflowDefinitionLink workflowDefinitionLink = null;

												try {
													workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), repositoryId, DLFolderConstants.getClassName(), folderId, dlFileEntryType.getFileEntryTypeId(), true);
												}
												catch (NoSuchWorkflowDefinitionLinkException nswdle) {
												}

												for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
													boolean selected = false;

													if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
														selected = true;
													}
												%>

													<aui:option label='<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

												<%
												}
												%>

											</aui:select>
										</liferay-ui:search-container-column-text>
									</c:if>

									<liferay-ui:search-container-column-text>
										<a class="modify-link" data-rowId="<%= dlFileEntryType.getFileEntryTypeId() %>" href="javascript:;"><%= removeFileEntryTypeIcon %></a>
									</liferay-ui:search-container-column-text>
								</liferay-ui:search-container-row>

								<liferay-ui:search-iterator paginate="<%= false %>" />
							</liferay-ui:search-container>

							<liferay-ui:icon
								cssClass="modify-link select-file-entry-type"
								iconCssClass="icon-search"
								label="<%= true %>"
								linkCssClass="btn btn-default"
								message="select-document-type"
								url='<%= "javascript:" + renderResponse.getNamespace() + "openFileEntryTypeSelector();" %>'
							/>

							<aui:select cssClass='<%= !fileEntryTypes.isEmpty() ? "default-document-type" : "default-document-type hide" %>' helpMessage="default-document-type-help" label="default-document-type" name="defaultFileEntryTypeId">

								<%
								for (DLFileEntryType fileEntryType : fileEntryTypes) {
								%>

									<aui:option id='<%= renderResponse.getNamespace() + "defaultFileEntryTypeId-" + fileEntryType.getFileEntryTypeId() %>' label="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>" selected="<%= (fileEntryType.getFileEntryTypeId() == defaultFileEntryTypeId) %>" value="<%= fileEntryType.getFileEntryTypeId() %>" />

								<%
								}
								%>

							</aui:select>
						</div>
					</c:if>

					<c:if test="<%= workflowEnabled %>">
						<c:choose>
							<c:when test="<%= !rootFolder %>">
								<aui:input checked="<%= dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" id="restrictionTypeWorkflow" label='<%= LanguageUtil.format(locale, "default-workflow-for-this-folder-x", folder.getName(), false) %>' name="restrictionType" type="radio" value="<%= DLFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
							</c:when>
							<c:otherwise>
								<aui:input name="restrictionType" type="hidden" value="<%= DLFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
							</c:otherwise>
						</c:choose>

						<div class='<%= (rootFolder || (dlFolder.getRestrictionType() == DLFolderConstants.RESTRICTION_TYPE_WORKFLOW)) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace />restrictionTypeWorkflowDiv">
							<aui:select label="default-workflow-for-all-document-types" name='<%= "workflowDefinition" + DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>'>
								<aui:option label="no-workflow" value="" />

								<%
								WorkflowDefinitionLink workflowDefinitionLink = null;

								try {
									workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), repositoryId, DLFolderConstants.getClassName(), folderId, DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, true);
								}
								catch (NoSuchWorkflowDefinitionLinkException nswdle) {
								}

								for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
									boolean selected = false;

									if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
										selected = true;
									}
								%>

									<aui:option label='<%= workflowDefinition.getName() + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

								<%
								}
								%>

							</aui:select>
						</div>
					</c:if>
				</aui:fieldset>
			</c:if>

			<c:if test="<%= (parentFolder == null) || parentFolder.isSupportsMetadata() %>">
				<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
						<liferay-ui:custom-attribute-list
							className="<%= DLFolderConstants.getClassName() %>"
							classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</liferay-ui:custom-attributes-available>
			</c:if>

			<c:if test="<%= !rootFolder && (folder == null) %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= DLFolderConstants.getClassName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<liferay-util:buffer var="workflowDefinitionsBuffer">
	<c:if test="<%= workflowEnabled %>">
		<aui:select label="" name="LIFERAY_WORKFLOW_DEFINITION_FILE_ENTRY_TYPE" title="workflow-definition">
			<aui:option label="no-workflow" value="" />

			<%
			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			%>

				<aui:option label='<%= workflowDefinition.getName() + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<% selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

			<%
			}
			%>

		</aui:select>
	</c:if>
</liferay-util:buffer>

<aui:script>
	var <portlet:namespace />documentTypesChanged = false;

	function <portlet:namespace />openFileEntryTypeSelector() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true,
					width: 1024
				},
				eventName: '<portlet:namespace />selectFileEntryType',
				id: '<portlet:namespace />fileEntryTypeSelector',
				title: '<%= UnicodeLanguageUtil.get(request, "document-types") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/select_restricted_file_entry_type.jsp" /><portlet:param name="includeBasicFileEntryType" value="<%= Boolean.TRUE.toString() %>" /></portlet:renderURL>'
			},
			function(event) {
				<portlet:namespace />selectFileEntryType(event.fileentrytypeid, event.fileentrytypename);
			}
		);
	}

	function <portlet:namespace />savePage() {
		var message = '<%= UnicodeLanguageUtil.get(request, workflowEnabled ? "change-document-types-and-workflow-message" : "change-document-types-message") %>';

		var submit = true;

		if (<portlet:namespace />documentTypesChanged) {
			if (!confirm(message)) {
				submit = false;
			}
		}

		if (submit) {
			submitForm(document.<portlet:namespace />fm);
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectFileEntryType',
		function(fileEntryTypeId, fileEntryTypeName) {
			var A = AUI();

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />dlFileEntryTypesSearchContainer');

			var fileEntryTypeLink = '<a class="modify-link" data-rowId="' + fileEntryTypeId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeFileEntryTypeIcon) %></a>';

			<c:choose>
				<c:when test="<%= workflowEnabled %>">
					var restrictionTypeWorkflow = A.one('#<portlet:namespace />restrictionTypeWorkflow');

					restrictionTypeWorkflow.hide();

					var workflowDefinitions = '<%= UnicodeFormatter.toString(workflowDefinitionsBuffer) %>';

					workflowDefinitions = workflowDefinitions.replace(/LIFERAY_WORKFLOW_DEFINITION_FILE_ENTRY_TYPE/g, 'workflowDefinition' + fileEntryTypeId);

					<portlet:namespace />documentTypesChanged = true;

					searchContainer.addRow([fileEntryTypeName, workflowDefinitions, fileEntryTypeLink], fileEntryTypeId);
				</c:when>
				<c:otherwise>
					searchContainer.addRow([fileEntryTypeName, fileEntryTypeLink], fileEntryTypeId);
				</c:otherwise>
			</c:choose>

			searchContainer.updateDataStore();

			var select = A.one('#<portlet:namespace />defaultFileEntryTypeId');

			var selectContainer = A.one('#<portlet:namespace />restrictionTypeDefinedDiv .default-document-type');

			selectContainer.show();

			var option = A.Node.create('<option id="<portlet:namespace />defaultFileEntryTypeId-' + fileEntryTypeId + '" value="' + fileEntryTypeId + '">' + fileEntryTypeName + '</option>');

			select.show();

			select.append(option);
		},
		['liferay-search-container']
	);

	Liferay.Util.toggleRadio('<portlet:namespace />restrictionTypeInherit', '', ['<portlet:namespace />restrictionTypeDefinedDiv', '<portlet:namespace />restrictionTypeWorkflowDiv']);
	Liferay.Util.toggleRadio('<portlet:namespace />restrictionTypeDefined', '<portlet:namespace />restrictionTypeDefinedDiv', '<portlet:namespace />restrictionTypeWorkflowDiv');

	<c:if test="<%= !rootFolder %>">
		Liferay.Util.toggleRadio('<portlet:namespace />restrictionTypeWorkflow', '<portlet:namespace />restrictionTypeWorkflowDiv', '<portlet:namespace />restrictionTypeDefinedDiv');
	</c:if>
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />dlFileEntryTypesSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var A = AUI();

			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			A.one('#<portlet:namespace />defaultFileEntryTypeId-' + link.getAttribute('data-rowId')).remove();

			<portlet:namespace />documentTypesChanged = true;

			var select = A.one('#<%= liferayPortletResponse.getNamespace() + "workflowDefinition" + DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>');

			var selectContainer = A.one('#<portlet:namespace />restrictionTypeWorkflow');

			var fileEntryTypesCount = select.get('children').size();

			if (fileEntryTypesCount == 0) {
				selectContainer.hide();

				var restrictionTypeWorkflow = A.one('#<portlet:namespace />restrictionTypeWorkflow');

				restrictionTypeWorkflow.show();
			}
			else {
				selectContainer.show();
			}

		},
		'.modify-link'
	);
</aui:script>

<%
if (!rootFolder && (folder == null)) {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-folder"), currentURL);
}
else {
	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
%>