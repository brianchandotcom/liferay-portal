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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

long fileEntryTypeId = BeanParamUtil.getLong(fileEntryType, request, "fileEntryTypeId");

DDMStructure ddmStructure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

String script = BeanParamUtil.getString(ddmStructure, request, "xsd");

JSONArray scriptJSONArray = null;

if (Validator.isNotNull(script)) {
	scriptJSONArray = DDMXSDUtil.getJSONArray(script);
}

List<DDMStructure> ddmStructures = null;

if (fileEntryType != null) {
	ddmStructures = fileEntryType.getDDMStructures();

	if (ddmStructure != null) {
		ddmStructures = new ArrayList<DDMStructure>(ddmStructures);

		ddmStructures.remove(ddmStructure);
	}
}

String scopeAvailableFields = ParamUtil.getString(request, "scopeAvailableFields", "Liferay.FormBuilder.AVAILABLE_FIELDS.DDM_STRUCTURE");
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
	<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="xsd" type="hidden" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (fileEntryType == null) %>"
		title='<%= (fileEntryType == null) ? "new-document-type" : fileEntryType.getName() %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFileEntryTypeException.class %>" message="please-enter-a-unique-document-type-name" />
	<liferay-ui:error exception="<%= NoSuchMetadataSetException.class %>" message="please-enter-a-valid-metadata-set-or-enter-a-metadata-field" />
	<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />
	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-metadata-field-names-(including-field-names-inherited-from-the-parent)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

	<aui:fieldset cssClass="edit-file-entry-type">
		<aui:input name="name" />

		<aui:input name="description" />

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="mainMetadataFields" persistState="<%= true %>" title="main-metadata-fields">
			<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="additionalMetadataFields" persistState="<%= true %>" title="additional-metadata-fields">
			<liferay-ui:search-container
				headerNames='<%= (fileEntryType == null) ? "name,null" : "name" %>'
				total="<%= (ddmStructures != null) ? ddmStructures.size() : 0 %>"
			>
				<liferay-ui:search-container-results
					results="<%= ddmStructures %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
					escapedModel="<%= true %>"
					keyProperty="structureId"
					modelVar="curDDMStructure"
				>
					<liferay-ui:search-container-column-text
						name="name"
						value="<%= curDDMStructure.getName(locale) %>"
					/>

					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-rowId="<%= curDDMStructure.getStructureId() %>" href="javascript:;"><%= removeStructureIcon %></a>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" />
			</liferay-ui:search-container>

			<liferay-ui:icon
				cssClass="modify-link select-metadata"
				image="add"
				label="<%= true %>"
				message="select-metadata-set"
				url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
			/>
		</liferay-ui:panel>
	</aui:fieldset>
</aui:form>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' type="submit" />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				classPK: '<%= ddmStructureId %>',
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				refererPortletName: '<%= PortletKeys.DOCUMENT_LIBRARY %>',
				showGlobalScope: true,
				showManageTemplates: false,
				showToolbar: true,
				storageType: 'xml',
				structureName: '<%= UnicodeLanguageUtil.get(pageContext, "metadata-sets") %>',
				structureType: 'com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata',
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "metadata-sets") %>'
			},
			function(event){
				var A = AUI();

				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

				var ddmStructureLink = '<a class="modify-link" data-rowId="' + event.ddmstructureid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeStructureIcon) %></a>';

				searchContainer.addRow([event.name, ddmStructureLink], event.ddmstructureid);

				searchContainer.updateDataStore();
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = window.<portlet:namespace />formBuilder.getContentXSD();

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-portlet-dynamic-data-mapping']
	);
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />ddmStructuresSearchContainer');

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