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

JournalFolder folder = journalDisplayContext.getFolder();

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean rootFolder = ParamUtil.getBoolean(request, "rootFolder");

boolean workflowEnabled = WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(JournalArticle.class.getName()) != null);

List<WorkflowDefinition> workflowDefinitions = null;

if (workflowEnabled) {
	workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
}

String languageId = LocaleUtil.toLanguageId(locale);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

String title = StringPool.BLANK;

if (rootFolder) {
	title = LanguageUtil.get(request, "home");
}
else {
	if (folder == null) {
		title = LanguageUtil.get(request, "new-folder");
	}
	else {
		title = folder.getName();
	}
}

renderResponse.setTitle(title);
%>

<portlet:actionURL name='<%= rootFolder ? "/journal/update_workflow_definitions" : ((folder == null) ? "/journal/add_folder" : "/journal/update_folder") %>' var="editFolderURL">
	<portlet:param name="mvcPath" value="/edit_folder.jsp" />
</portlet:actionURL>

<liferay-util:buffer
	var="removeDDMStructureIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-frontend:edit-form
	action="<%= editFolderURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />

		<liferay-ui:error exception="<%= FolderNameException.class %>">
			<p>
				<liferay-ui:message arguments="<%= new String[] {JournalFolderConstants.NAME_RESERVED_WORDS} %>" key="the-folder-name-cannot-be-blank-or-a-reserved-word-such-as-x" />
			</p>

			<p>
				<liferay-ui:message arguments="<%= new String[] {JournalFolderConstants.getNameInvalidCharacters(journalDisplayContext.getCharactersBlacklist())} %>" key="the-folder-name-cannot-contain-the-following-invalid-characters-x" />
			</p>
		</liferay-ui:error>

		<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="you-cannot-apply-the-selected-structure-restrictions-for-this-folder.-at-least-one-web-content-references-another-structure" />

		<aui:model-context bean="<%= folder %>" model="<%= JournalFolder.class %>" />

		<liferay-frontend:fieldset-group>
			<c:if test="<%= !rootFolder %>">
				<liferay-frontend:fieldset
					collapsed="<%= false %>"
					collapsible="<%= true %>"
					label="details"
				>
					<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

					<aui:input name="description" />
				</liferay-frontend:fieldset>

				<liferay-expando:custom-attributes-available
					className="<%= JournalFolder.class.getName() %>"
				>
					<liferay-frontend:fieldset
						collapsed="<%= true %>"
						collapsible="<%= true %>"
						label="custom-fields"
					>
						<liferay-expando:custom-attribute-list
							className="<%= JournalFolder.class.getName() %>"
							classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</liferay-frontend:fieldset>
				</liferay-expando:custom-attributes-available>
			</c:if>

			<c:if test="<%= !rootFolder && (folder != null) %>">
				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="parent-folder"
				>

					<%
					String parentFolderName = LanguageUtil.get(request, "home");

					JournalFolder parentFolder = JournalFolderServiceUtil.fetchFolder(parentFolderId);

					if (parentFolder != null) {
						parentFolderName = parentFolder.getName();
					}
					%>

					<div class="form-group">
						<aui:input name="parentFolderName" type="resource" value="<%= parentFolderName %>" />

						<aui:button name="selectFolderButton" value="select" />

						<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
							var selectFolderButton = document.getElementById(
								'<portlet:namespace />selectFolderButton'
							);

							if (selectFolderButton) {
								selectFolderButton.addEventListener('click', function(event) {
									event.preventDefault();

									var itemSelectorDialog = new ItemSelectorDialog.default({
										eventName: '<portlet:namespace />selectFolder',
										singleSelect: true,
										title: '<liferay-ui:message arguments="folder" key="select-x" />',

										<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
											<portlet:param name="mvcPath" value="/select_folder.jsp" />
											<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
											<portlet:param name="parentFolderId" value="<%= String.valueOf(parentFolderId) %>" />
										</portlet:renderURL>

										url: '<%= selectFolderURL.toString() %>'
									});

									itemSelectorDialog.open();

									itemSelectorDialog.on('selectedItemChange', function(event) {
										var selectedItem = event.selectedItem;

										if (selectedItem) {
											var folderData = {
												idString: 'parentFolderId',
												idValue: selectedItem.folderId,
												nameString: 'parentFolderName',
												nameValue: selectedItem.folderName
											};

											Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
										}
									});
								});
							}
						</aui:script>

						<%
						String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('parentFolderId', 'parentFolderName', this, '" + renderResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= parentFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
					</div>
				</liferay-frontend:fieldset>
			</c:if>

			<c:if test="<%= rootFolder || (folder != null) %>">

				<%
				List<DDMStructure> ddmStructures = journalDisplayContext.getDDMStructures(JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

				String headerNames = null;

				if (workflowEnabled) {
					headerNames = "name,workflow,null";
				}
				else {
					headerNames = "name,null";
				}
				%>

				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					cssClass="structure-restrictions"
					helpMessage='<%= rootFolder ? "" : "structure-restrictions-help" %>'
					label='<%= rootFolder ? "" : (workflowEnabled ? "structure-restrictions-and-workflow" : "structure-restrictions") %>'
				>
					<c:if test="<%= !rootFolder %>">

						<%
						JournalFolder parentFolder = JournalFolderLocalServiceUtil.fetchFolder(folder.getParentFolderId());

						String parentFolderName = LanguageUtil.get(request, "home");

						if (parentFolder != null) {
							parentFolderName = parentFolder.getName();
						}
						%>

						<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_INHERIT %>" id="restrictionTypeInherit" label='<%= workflowEnabled ? LanguageUtil.format(request, "use-structure-restrictions-and-workflow-of-the-parent-folder-x", HtmlUtil.escape(parentFolderName)) : LanguageUtil.format(request, "use-structure-restrictions-of-the-parent-folder-x", HtmlUtil.escape(parentFolderName)) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_INHERIT %>" />

						<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW %>" id="restrictionTypeDefined" label='<%= workflowEnabled ? LanguageUtil.format(request, "define-specific-structure-restrictions-and-workflow-for-this-folder-x", HtmlUtil.escape(folder.getName())) : LanguageUtil.format(request, "define-specific-structure-restrictions-for-this-folder-x", HtmlUtil.escape(folder.getName())) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW %>" />

						<div class="<%= (folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />restrictionTypeDefinedDiv">
							<liferay-ui:search-container
								headerNames="<%= headerNames %>"
								total="<%= ddmStructures.size() %>"
							>
								<liferay-ui:search-container-results
									results="<%= ddmStructures %>"
								/>

								<liferay-ui:search-container-row
									className="com.liferay.dynamic.data.mapping.model.DDMStructure"
									keyProperty="structureId"
									modelVar="ddmStructure"
								>
									<liferay-ui:search-container-column-text
										cssClass="table-cell-expand table-cell-minw-200 table-title"
										name="name"
										value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
									/>

									<c:if test="<%= workflowEnabled %>">
										<liferay-ui:search-container-column-text
											cssClass="table-cell-expand table-cell-minw-200"
											name="workflow"
										>
											<aui:select label="" name='<%= "workflowDefinition" + ddmStructure.getStructureId() %>' wrapperCssClass="mb-0">
												<aui:option label="no-workflow" value="" />

												<%
												WorkflowDefinitionLink workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.fetchWorkflowDefinitionLink(company.getCompanyId(), scopeGroupId, JournalFolder.class.getName(), folderId, ddmStructure.getStructureId(), true);

												for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
													boolean selected = false;

													if ((workflowDefinitionLink != null) && Objects.equals(workflowDefinitionLink.getWorkflowDefinitionName(), workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
														selected = true;
													}
												%>

													<aui:option label="<%= HtmlUtil.escape(workflowDefinition.getTitle(languageId)) %>" selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

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

								<liferay-ui:search-iterator
									markupView="lexicon"
									paginate="<%= false %>"
								/>
							</liferay-ui:search-container>

							<aui:button id="selectDDMStructure" value="choose-structure" />
						</div>
					</c:if>

					<c:if test="<%= workflowEnabled %>">
						<c:choose>
							<c:when test="<%= !rootFolder %>">
								<aui:input checked="<%= folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" id="restrictionTypeWorkflow" label='<%= LanguageUtil.format(request, "default-workflow-for-this-folder-x", HtmlUtil.escape(folder.getName())) %>' name="restrictionType" type="radio" value="<%= JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
							</c:when>
							<c:otherwise>
								<aui:input name="restrictionType" type="hidden" value="<%= JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW %>" />
							</c:otherwise>
						</c:choose>

						<div class="<%= (rootFolder || (folder.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_WORKFLOW)) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />restrictionTypeWorkflowDiv">
							<aui:select label='<%= rootFolder ? "default-workflow-for-all-structures" : StringPool.BLANK %>' name='<%= "workflowDefinition" + JournalArticleConstants.DDM_STRUCTURE_ID_ALL %>'>
								<aui:option label="no-workflow" value="" />

								<%
								WorkflowDefinitionLink workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.fetchWorkflowDefinitionLink(company.getCompanyId(), scopeGroupId, JournalFolder.class.getName(), folderId, JournalArticleConstants.DDM_STRUCTURE_ID_ALL, true);

								for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
									boolean selected = false;

									if ((workflowDefinitionLink != null) && Objects.equals(workflowDefinitionLink.getWorkflowDefinitionName(), workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
										selected = true;
									}
								%>

									<aui:option label="<%= HtmlUtil.escape(workflowDefinition.getTitle(languageId)) %>" selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

								<%
								}
								%>

							</aui:select>
						</div>
					</c:if>
				</liferay-frontend:fieldset>
			</c:if>

			<c:if test="<%= !rootFolder && (folder == null) %>">
				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="permissions"
				>
					<liferay-ui:input-permissions
						modelName="<%= JournalFolder.class.getName() %>"
					/>
				</liferay-frontend:fieldset>
			</c:if>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<liferay-util:buffer
	var="workflowDefinitionsBuffer"
>
	<c:if test="<%= workflowEnabled %>">
		<aui:select label="" name="LIFERAY_WORKFLOW_DEFINITION_DDM_STRUCTURE" title="workflow-definition">
			<aui:option label="no-workflow" value="" />

			<%
			for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			%>

				<aui:option label="<%= HtmlUtil.escape(workflowDefinition.getTitle(languageId)) %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

			<%
			}
			%>

		</aui:select>
	</c:if>
</liferay-util:buffer>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />ddmStructuresSearchContainer'
	);

	var selectDDMStructureButton = document.getElementById(
		'<portlet:namespace />selectDDMStructure'
	);

	if (selectDDMStructureButton) {
		selectDDMStructureButton.addEventListener('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectDDMStructure',
					title: '<%= UnicodeLanguageUtil.get(request, "structures") %>',
					uri:
						'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /></portlet:renderURL>'
				},
				function(event) {
					var ddmStructureLink =
						'<a class="modify-link" data-rowId="' +
						event.ddmstructureid +
						'" href="javascript:;"><%= UnicodeFormatter.toString(removeDDMStructureIcon) %></a>';

					<c:choose>
						<c:when test="<%= workflowEnabled %>">
							var workflowDefinitions =
								'<%= UnicodeFormatter.toString(workflowDefinitionsBuffer) %>';

							workflowDefinitions = workflowDefinitions.replace(
								/LIFERAY_WORKFLOW_DEFINITION_DDM_STRUCTURE/g,
								'workflowDefinition' + event.ddmstructureid
							);

							searchContainer.addRow(
								[event.name, workflowDefinitions, ddmStructureLink],
								event.ddmstructureid
							);
						</c:when>
						<c:otherwise>
							searchContainer.addRow(
								[event.name, ddmStructureLink],
								event.ddmstructureid
							);
						</c:otherwise>
					</c:choose>

					searchContainer.updateDataStore();
				}
			);
		});
	}

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

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />restrictionTypeInherit', '', [
		'<portlet:namespace />restrictionTypeDefinedDiv',
		'<portlet:namespace />restrictionTypeWorkflowDiv'
	]);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />restrictionTypeDefined',
		'<portlet:namespace />restrictionTypeDefinedDiv',
		'<portlet:namespace />restrictionTypeWorkflowDiv'
	);

	<c:if test="<%= !rootFolder %>">
		Liferay.Util.toggleRadio(
			'<portlet:namespace />restrictionTypeWorkflow',
			'<portlet:namespace />restrictionTypeWorkflowDiv',
			'<portlet:namespace />restrictionTypeDefinedDiv'
		);
	</c:if>
</aui:script>