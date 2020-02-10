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

DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

long fileEntryTypeId = BeanParamUtil.getLong(fileEntryType, request, "fileEntryTypeId");

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = (com.liferay.dynamic.data.mapping.model.DDMStructure)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

List<DDMStructure> ddmStructures = null;

if (fileEntryType != null) {
	ddmStructures = fileEntryType.getDDMStructures();

	if (ddmStructure != null) {
		ddmStructures = new ArrayList<>(ddmStructures);

		ddmStructures.remove(ddmStructure);
	}
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((fileEntryType == null) ? LanguageUtil.get(request, "new-document-type") : fileEntryType.getName(locale));

DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext = (DLEditFileEntryTypeDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_EDIT_EDIT_FILE_ENTRY_TYPE_DISPLAY_CONTEXT);
%>

<div class="container-fluid-1280">
	<liferay-util:buffer
		var="removeStructureIcon"
	>
		<clay:icon
			symbol="times-circle"
		/>
	</liferay-util:buffer>

	<portlet:actionURL name="/document_library/edit_file_entry_type" var="editFileEntryTypeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry_type" />
	</portlet:actionURL>

	<aui:form action="<%= dlEditFileEntryTypeDisplayContext.useDataEngineEditor() ? StringPool.BLANK : editFileEntryTypeURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (fileEntryType == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="definition" type="hidden" />

		<liferay-ui:error exception="<%= DuplicateFileEntryTypeException.class %>" message="please-enter-a-unique-document-type-name" />
		<liferay-ui:error exception="<%= NoSuchMetadataSetException.class %>" message="please-enter-a-valid-metadata-set-or-enter-a-metadata-field" />
		<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />
		<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
		<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-metadata-field-names-(including-field-names-inherited-from-the-parent)" />
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

		<aui:fieldset-group cssClass="edit-file-entry-type" markupView="lexicon">
			<aui:fieldset collapsible="<%= true %>" extended="<%= false %>" id="detailsMetadataFields" persistState="<%= true %>" title="details">
				<aui:field-wrapper>
					<c:if test="<%= (ddmStructure != null) && (ddmStructure.getGroupId() != scopeGroupId) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-document-type-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-document-type" />
						</div>
					</c:if>
				</aui:field-wrapper>

				<aui:input name="name" />

				<aui:input name="description" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" id="mainMetadataFields" label="main-metadata-fields">
				<c:choose>
					<c:when test="<%= dlEditFileEntryTypeDisplayContext.useDataEngineEditor() %>">
						<liferay-data-engine:data-layout-builder
							componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
							contentType="document-library"
							dataDefinitionId="<%= ddmStructureId %>"
							dataLayoutInputId="dataLayout"
							groupId="<%= scopeGroupId %>"
							localizable="<%= true %>"
							namespace="<%= renderResponse.getNamespace() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/document_library/ddm/ddm_form_builder.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" id="additionalMetadataFields" label="additional-metadata-fields">
				<liferay-ui:search-container
					headerNames="name,null"
					total="<%= (ddmStructures != null) ? ddmStructures.size() : 0 %>"
				>
					<liferay-ui:search-container-results
						results="<%= ddmStructures %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.dynamic.data.mapping.kernel.DDMStructure"
						escapedModel="<%= true %>"
						keyProperty="structureId"
						modelVar="curDDMStructure"
					>
						<liferay-ui:search-container-column-text
							name="name"
							value="<%= HtmlUtil.escape(curDDMStructure.getName(locale)) %>"
						/>

						<liferay-ui:search-container-column-text>
							<a class="modify-link" data-rowId="<%= curDDMStructure.getStructureId() %>" href="javascript:;" title="<%= LanguageUtil.get(request, "remove") %>"><%= removeStructureIcon %></a>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
						paginate="<%= false %>"
					/>
				</liferay-ui:search-container>

				<liferay-ui:icon
					cssClass="modify-link select-metadata"
					label="<%= true %>"
					linkCssClass="btn btn-secondary"
					message="select"
					url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
				/>
			</aui:fieldset>

			<c:if test="<%= fileEntryType == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions" markupView="lexicon">
					<liferay-ui:input-permissions
						modelName="<%= DLFileEntryType.class.getName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
function <portlet:namespace />openDDMStructureSelector() {
	Liferay.Util.selectEntity(
		{
			dialog: {
				constrain: true,
				modal: true
			},
			eventName: '<portlet:namespace />selectDDMStructure',
			id: '<portlet:namespace />selectDDMStructure',
			title:
				'<%= UnicodeLanguageUtil.get(request, "select-structure") %>',
			uri:
				'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/ddm/select_ddm_structure.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" /></portlet:renderURL>'
		},
		function(event) {
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />ddmStructuresSearchContainer'
			);

			var data = searchContainer.getData(false);

			if (!data.includes(event.ddmstructureid)) {
				var ddmStructureLink =
					'<a class="modify-link" data-rowId="' +
					event.ddmstructureid +
					'" href="javascript:;" title="<%= LanguageUtil.get(request, "remove") %>"><%= UnicodeFormatter.toString(removeStructureIcon) %></a>';

				searchContainer.addRow(
					[event.name, ddmStructureLink],
					event.ddmstructureid
				);

				searchContainer.updateDataStore();
			}
		}
	);
}

function <portlet:namespace />saveStructure() {
	<c:choose>
		<c:when test="<%= dlEditFileEntryTypeDisplayContext.useDataEngineEditor() %>">

		</c:when>
		<c:otherwise>
			document.<portlet:namespace />fm.<portlet:namespace />definition.value = window.<portlet:namespace />formBuilder.getContentValue();

			submitForm(document.<portlet:namespace />fm);
		</c:otherwise>
	</c:choose>
}
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />ddmStructuresSearchContainer'
	);

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
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-document-type"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit-document-type"), currentURL);
}
%>