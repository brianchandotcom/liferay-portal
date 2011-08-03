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
String redirect = ParamUtil.getString(request, "redirect");

DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

long fileEntryTypeId = BeanParamUtil.getLong(fileEntryType, request, "fileEntryTypeId");

String headerNames = "name,null";

String scopeAvailableFields = ParamUtil.getString(request, "scopeAvailableFields");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long structureId = BeanParamUtil.getLong(structure, request, "structureId");

String script = BeanParamUtil.getString(structure, request, "xsd");

List<DDMStructure> ddmStructures = null;

if (fileEntryType != null) {
	headerNames = "name";

	ddmStructures = fileEntryType.getDDMStructures();

	ddmStructures.remove(structure);
}
%>

<liferay-util:buffer var="removeStructureIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL var="editFileEntryTypeURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry_type" />
</portlet:actionURL>

<aui:form action="<%= editFileEntryTypeURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (fileEntryType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	<aui:input name="xsd" type="hidden" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (fileEntryType == null) %>"
		title='<%= (fileEntryType == null) ? "new-document-type" : fileEntryType.getName() %>'
	/>

	<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

	<aui:fieldset cssClass="edit-file-entry-type">
		<aui:input name="name" />

		<aui:input name="description" />

		<h3><liferay-ui:message key="metadata-sets" /></h3>

		<liferay-ui:search-container
			id='<%= renderResponse.getNamespace() + "structuresSearchContainer" %>'
			headerNames="<%= headerNames %>"
		>
			<liferay-ui:search-container-results
				results="<%= ddmStructures %>"
				total="<%= ddmStructures != null ? ddmStructures.size() : 0 %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
				escapedModel="<%= true %>"
				keyProperty="structureId"
				modelVar="curStructure"
			>
				<liferay-ui:search-container-column-text
					name="name"
					value="<%= curStructure.getName(locale) %>"
				/>

				<liferay-ui:search-container-column-text>
					<a class="modify-link" data-rowId="<%= curStructure.getStructureId() %>" href="javascript:;"><%= removeStructureIcon %></a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" />
		</liferay-ui:search-container>

		<liferay-ui:icon
			cssClass="modify-link select-metadata"
			image="add"
			label="<%= true %>"
			message="select"
			url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
		/>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' type="submit" />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				dialog: {
					stack: false,
					width:680
				},
				showManageTemplates: 'false',
				showToolbar: 'true',
				storageType: 'xml',
				struts_action: '/dynamic_data_mapping/select_structure',
				structureName: '<liferay-ui:message key="metadata-sets" />',
				structureType: 'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata',
				title: '<liferay-ui:message key="metadata-sets" />',
				saveCallback: '<%= renderResponse.getNamespace() + "selectDDMStructure" %>'
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectDDMStructure',
		function(ddmStructureId, ddmStructureName, dialog) {
			var A = AUI();

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />structuresSearchContainer');

			var ddmStructureLink = '<a class="modify-link" data-rowId="' + ddmStructureId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeStructureIcon) %></a>';

			searchContainer.addRow([ddmStructureName, ddmStructureLink], ddmStructureId);

			searchContainer.updateDataStore();

			if (dialog) {
				dialog.close();
			}
		},
		['liferay-search-container']
	);
</aui:script>

<aui:script use="liferay-portlet-dynamic-data-mapping">
	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = window.<portlet:namespace />formBuilder.getXSD();

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />structuresSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
		},
		'.modify-link'
	);
</aui:script>

<%
if (fileEntryType == null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-document-type"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit-document-type"), currentURL);
}
%>