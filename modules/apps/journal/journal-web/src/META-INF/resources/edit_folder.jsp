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
String redirect = ParamUtil.getString(request, "redirect");

JournalFolder folder = ActionUtil.getFolder(request);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean mergeWithParentFolderDisabled = ParamUtil.getBoolean(request, "mergeWithParentFolderDisabled");

boolean rootFolder = ParamUtil.getBoolean(request, "rootFolder");

boolean workflowEnabled = WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(JournalArticle.class.getName()) != null);

List<WorkflowDefinition> workflowDefinitions = null;

if (workflowEnabled) {
	workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), 0, 100, null);
}
%>

<portlet:actionURL name='<%= rootFolder ? "updateWorkflowDefinitions" : ((folder == null) ? "addFolder" : "updateFolder") %>' var="editFolderURL" />

<liferay-util:buffer var="removeDDMStructureIcon">
	<liferay-ui:icon
		iconCssClass="icon-remove"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:form action="<%= editFolderURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (folder == null) %>"
		title='<%= rootFolder ? "home" : ((folder == null) ? "new-folder" : folder.getName()) %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="you-cannot-apply-the-selected-structure-restrictions-for-this-folder.-at-least-one-web-content-references-another-structure" />

	<aui:model-context bean="<%= folder %>" model="<%= JournalFolder.class %>" />

	<aui:fieldset>
		<c:if test="<%= !rootFolder && (folder != null) %>">
			<aui:field-wrapper label="parent-folder">

				<%
				String parentFolderName = LanguageUtil.get(request, "home");

				JournalFolder parentFolder = JournalFolderServiceUtil.fetchFolder(parentFolderId);

				if (parentFolder != null) {
					parentFolderName = parentFolder.getName();
				}
				%>

				<div class="form-group">
					<aui:input name="parentFolderName" type="resource" value="<%= parentFolderName %>" />

					<aui:button name="selecFolderButton" value="select" />

					<aui:script>
						AUI.$('#<portlet:namespace />selecFolderButton').on(
							'click',
							function(event) {
								Liferay.Util.selectEntity(
									{
										dialog: {
											constrain: true,
											modal: true,
											width: 680
										},
										id: '<portlet:namespace />selectFolder',
										title: '<liferay-ui:message arguments="folder" key="select-x" />',

										<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
											<portlet:param name="mvcPath" value="/html/portletjournal/select_folder.jsp" />
											<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
										</portlet:renderURL>

										uri: '<%= selectFolderURL.toString() %>'
									},
									function(event) {
										var folderData = {
											idString: 'parentFolderId',
											idValue: event.folderid,
											nameString: 'parentFolderName',
											nameValue: event.foldername
										};

										Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
									}
								);
							}
						);
					</aui:script>

					<%
					String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentFolderId', 'parentFolderName', this, '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= (parentFolderId <= 0) %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:input disabled="<%= mergeWithParentFolderDisabled %>" label="merge-with-parent-folder" name="mergeWithParentFolder" type="checkbox" />
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= !rootFolder %>">
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

			<aui:input name="description" />

			<liferay-ui:custom-attributes-available className="<%= JournalFolder.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= JournalFolder.class.getName() %>"
					classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</c:if>

		<c:if test="<%= rootFolder || (folder != null) %>">

			<%
			List<DDMStructure> ddmStructures = JournalFolderLocalServiceUtil.getDDMStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

			String headerNames = null;

			if (workflowEnabled) {
				headerNames = "name,workflow,null";
			}
			else {
				headerNames = "name,null";
			}
			%>

			<aui:field-wrapper helpMessage='<%= rootFolder ? "" : "structure-restrictions-help" %>' label='<%= rootFolder ? "" : (workflowEnabled ? "structure-restrictions-and-workflow" : "structure-restrictions") %>'>
				<c:if test="<%= !rootFolder %>">

					<%
					JournalFolder parentFolder = JournalFolderLocalServiceUtil.fetchFolder(folder.getParentFolderId());

					String parentFolderName = LanguageUtil.get(locale, "home");

					if (parentFolder != null) {
						parentFolderName = parentFolder.getName();
					}
					%>

					<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_INHERIT %>" id="restrictionTypeInherit" label='<%= workflowEnabled ? LanguageUtil.format(locale, "use-structure-restrictions-and-workflow-of-the-parent-folder-x", HtmlUtil.escape(parentFolderName)) : LanguageUtil.format(locale, "use-structure-restrictions-of-the-parent-folder-x", HtmlUtil.escape(parentFolderName)) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_INHERIT %>" />

					<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW %>" id="restrictionTypeDefined" label='<%= workflowEnabled ? LanguageUtil.format(locale, "define-specific-structure-restrictions-and-workflow-for-this-folder-x", HtmlUtil.escape(folder.getName())) : LanguageUtil.format(locale, "define-specific-structure-restrictions-for-this-folder-x", HtmlUtil.escape(folder.getName())) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW %>" />

					<div class='<%= (folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace />restrictionTypeDefinedDiv">
						<liferay-ui:search-container
							headerNames="<%= headerNames %>"
							total="<%= ddmStructures.size() %>"
						>
							<liferay-ui:search-container-results
								results="<%= ddmStructures %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
								keyProperty="structureId"
								modelVar="ddmStructure"
							>
								<liferay-ui:search-container-column-text
									name="name"
									value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
								/>

								<c:if test="<%= workflowEnabled %>">
									<liferay-ui:search-container-column-text name="workflow">
										<aui:select label="" name='<%= "workflowDefinition" + ddmStructure.getStructureId() %>'>
											<aui:option label="no-workflow" value="" />

											<%
											WorkflowDefinitionLink workflowDefinitionLink = null;

											try {
												workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), scopeGroupId, JournalFolder.class.getName(), folderId, ddmStructure.getStructureId(), true);
											}
											catch (NoSuchWorkflowDefinitionLinkException nswdle) {
											}

											for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
												boolean selected = false;

												if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
													selected = true;
												}
											%>

												<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

											<%
											}
											%>

										</aui:select>
									</liferay-ui:search-container-column-text>
								</c:if>

								<liferay-ui:search-container-column-text>
									<a class="modify-link" data-rowId="<%= ddmStructure.getStructureId() %>" href="javascript:;"><%= removeDDMStructureIcon %></a>
								</liferay-ui:search-container-column-text>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator paginate="<%= false %>" />
						</liferay-ui:search-container>

						<liferay-ui:icon
							cssClass="modify-link select-structure"
							iconCssClass="icon-search"
							label="<%= true %>"
							linkCssClass="btn btn-default"
							message="choose-structure"
							url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
						/>
					</div>
				</c:if>

				<c:if test="<%= workflowEnabled %>">
					<c:choose>
						<c:when test="<%= !rootFolder %>">
							<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" id="restrictionTypeWorkflow" label='<%= LanguageUtil.format(locale, "default-workflow-for-this-folder-x", HtmlUtil.escape(folder.getName())) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
						</c:when>
						<c:otherwise>
							<aui:input name="restrictionType" type="hidden" value="<%= JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
						</c:otherwise>
					</c:choose>

					<div class='<%= (rootFolder || (folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW)) ? StringPool.BLANK : "hide" %>' id="<portlet:namespace />restrictionTypeWorkflowDiv">
						<aui:select label='<%= rootFolder ? "default-workflow-for-all-structures" : StringPool.BLANK %>' name='<%= "workflowDefinition" + JournalArticleConstants.DDM_STRUCTURE_ID_ALL %>'>
							<aui:option label="no-workflow" value="" />

							<%
							WorkflowDefinitionLink workflowDefinitionLink = null;

							try {
								workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), scopeGroupId, JournalFolder.class.getName(), folderId, JournalArticleConstants.DDM_STRUCTURE_ID_ALL, true);
							}
							catch (NoSuchWorkflowDefinitionLinkException nswdle) {
							}

							for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
								boolean selected = false;

								if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
									selected = true;
								}
							%>

								<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</c:if>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= !rootFolder && (folder == null) %>">
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

<liferay-util:buffer var="workflowDefinitionsBuffer">
	<c:if test="<%= workflowEnabled %>">
		<aui:select label="" name="LIFERAY_WORKFLOW_DEFINITION_DDM_STRUCTURE" title="workflow-definition">
			<aui:option label="no-workflow" value="" />

			<%
			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			%>

				<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<% selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

			<%
			}
			%>

		</aui:select>
	</c:if>
</liferay-util:buffer>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
				showAncestorScopes: true,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(request, "structures") %>'
			},
			function(event) {
				<portlet:namespace />selectStructure(event.ddmstructureid, event.name);
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectStructure',
		function(ddmStructureId, ddmStructureName) {
			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

			var ddmStructureLink = '<a class="modify-link" data-rowId="' + ddmStructureId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeDDMStructureIcon) %></a>';

			<c:choose>
				<c:when test="<%= workflowEnabled %>">
					var workflowDefinitions = '<%= UnicodeFormatter.toString(workflowDefinitionsBuffer) %>';

					workflowDefinitions = workflowDefinitions.replace(/LIFERAY_WORKFLOW_DEFINITION_DDM_STRUCTURE/g, 'workflowDefinition' + ddmStructureId);

					searchContainer.addRow([ddmStructureName, workflowDefinitions, ddmStructureLink], ddmStructureId);
				</c:when>
				<c:otherwise>
					searchContainer.addRow([ddmStructureName, ddmStructureLink], ddmStructureId);
				</c:otherwise>
			</c:choose>

			searchContainer.updateDataStore();
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
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.attr('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>

<%
if (folder != null) {
	JournalPortletUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	if (parentFolderId > 0) {
		JournalPortletUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);
	}

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-folder"), currentURL);
}
%>