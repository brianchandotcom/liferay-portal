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
String redirect = ParamUtil.getString(request, "redirect");

JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.JOURNAL_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean mergeWithParentFolderDisabled = ParamUtil.getBoolean(request, "mergeWithParentFolderDisabled");

boolean rootFolder = ParamUtil.getBoolean(request, "rootFolder");
%>

<portlet:actionURL var="editFolderURL">
	<portlet:param name="struts_action" value="/journal/edit_folder" />
</portlet:actionURL>

<liferay-util:buffer var="removeDDMStructureIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

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

				<div class="input-append">
					<liferay-ui:input-resource id="parentFolderName" url="<%= parentFolderName %>" />

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
											width: 680
										},
										id: '<portlet:namespace />selectFolder',
										title: '<liferay-ui:message arguments="folder" key="select-x" />',
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
					String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('parentFolderId', 'parentFolderName', '" + renderResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= (parentFolderId <= 0) %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
				</div>

				<aui:input disabled="<%= mergeWithParentFolderDisabled %>" label="merge-with-parent-folder" name="mergeWithParentFolder" type="checkbox" />
			</aui:field-wrapper>
		</c:if>

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

		<c:if test="<%= rootFolder || (folder != null) %>">

			<%
			List<DDMStructure> ddmStructures = DDMStructureLocalServiceUtil.getJournalFolderStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), folderId, false);

			String headerNames = "name,null";
			%>

			<aui:field-wrapper helpMessage='<%= rootFolder ? "" : "structure-restrictions-help" %>' label='<%= rootFolder ? "" : "structure-restrictions" %>'>
				<c:if test="<%= !rootFolder %>">
					<aui:input checked="<%= !folder.isOverrideDDMStructures() %>" id="useDDMStructures" label="use-structure-restrictions-of-the-parent-folder" name="overrideDDMStructures" type="radio" value="<%= false %>" />

					<aui:input checked="<%= folder.isOverrideDDMStructures() %>" id="overrideDDMStructures" label="define-specific-structure-restrictions-for-this-folder" name="overrideDDMStructures" type="radio" value="<%= true %>" />
				</c:if>

				<div id="<portlet:namespace />overrideParentSettings">
					<c:if test="<%= !rootFolder %>">
						<liferay-ui:search-container
							headerNames="<%= headerNames %>"
							total="<%= ddmStructures.size() %>"
						>
							<liferay-ui:search-container-results
								results="<%= ddmStructures %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
								escapedModel="<%= true %>"
								keyProperty="structureId"
								modelVar="ddmStructure"
							>
								<liferay-ui:search-container-column-text
									name="name"
									value="<%= ddmStructure.getName(locale) %>"
								/>

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
							linkCssClass="btn"
							message="choose-structure"
							url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
						/>
					</c:if>
				</div>
			</aui:field-wrapper>
		</c:if>

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

	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectStructure',
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= PortletKeys.JOURNAL_CONTENT %>',
				showGlobalScope: true,
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
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
			var A = AUI();

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

			var ddmStructureLink = '<a class="modify-link" data-rowId="' + ddmStructureId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeDDMStructureIcon) %></a>';

			searchContainer.addRow([ddmStructureName, ddmStructureLink], ddmStructureId);

			searchContainer.updateDataStore();
		},
		['liferay-search-container']
	);

	Liferay.Util.toggleRadio('<portlet:namespace />overrideDDMStructures', '<portlet:namespace />overrideParentSettings', '');
	Liferay.Util.toggleRadio('<portlet:namespace />useDDMStructures', '', '<portlet:namespace />overrideParentSettings');
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var A = AUI();

			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
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