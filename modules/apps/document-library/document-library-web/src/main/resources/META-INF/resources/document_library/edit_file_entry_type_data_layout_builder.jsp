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

String ddmStructureKey = StringPool.BLANK;
String fileEntryTypeUuid = StringPool.BLANK;

if (ddmStructure == null) {
	fileEntryTypeUuid = (fileEntryType != null) ? fileEntryType.getUuid() : PortalUUIDUtil.generate();

	ddmStructureKey = DLUtil.getDDMStructureKey(fileEntryTypeUuid);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((fileEntryType == null) ? LanguageUtil.get(request, "new-document-type") : fileEntryType.getName(locale));
%>

<portlet:actionURL name="/document_library/edit_file_entry_type" var="editFileEntryTypeURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry_type" />
</portlet:actionURL>

<aui:form action="<%= editFileEntryTypeURL %>" cssClass="edit-metadata-type-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (fileEntryType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
	<aui:input name="fileEntryTypeUuid" type="hidden" value="<%= fileEntryTypeUuid %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="definition" type="hidden" />

	<liferay-ui:error exception="<%= DuplicateFileEntryTypeException.class %>" message="please-enter-a-unique-document-type-name" />
	<liferay-ui:error exception="<%= NoSuchMetadataSetException.class %>" message="please-enter-a-valid-metadata-set-or-enter-a-metadata-field" />
	<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />
	<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-metadata-field-names-(including-field-names-inherited-from-the-parent)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand"></li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" type="submit" />
					</div>
				</li>
			</ul>
		</clay:container>
	</nav>

	<clay:container
		className="container-view"
	>

		<%
		DLEditFileEntryTypeDisplayContext dlEditFileEntryTypeDisplayContext = (DLEditFileEntryTypeDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_EDIT_EDIT_FILE_ENTRY_TYPE_DISPLAY_CONTEXT);
		%>

		<liferay-data-engine:data-layout-builder
			additionalPanels="<%= dlEditFileEntryTypeDisplayContext.getAdditionalPanels(npmResolvedPackageName) %>"
			componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
			contentType="document-library"
			dataDefinitionId="<%= ddmStructureId %>"
			dataLayoutInputId="dataLayout"
			groupId="<%= scopeGroupId %>"
			localizable="<%= true %>"
			namespace="<%= renderResponse.getNamespace() %>"
		/>
	</clay:container>
</aui:form>

<aui:script>
	function <portlet:namespace />saveStructure() {
		Liferay.componentReady(
			'<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
		).then(function (dataLayoutBuilder) {
			var name =
				document.<portlet:namespace />fm[
					'<portlet:namespace />name_' + themeDisplay.getLanguageId()
				].value;
			var description =
				document.<portlet:namespace />fm['<portlet:namespace />description']
					.value;

			dataLayoutBuilder
				.save({
					dataDefinition: {
						description: {
							value: description,
						},
						name: {
							value: name,
						},
						dataDefinitionKey: '<%= ddmStructureKey %>',
					},
					dataLayout: {
						description: {
							value: description,
						},
						name: {
							value: name,
						},
					},
				})
				.then(function (dataLayout) {
					document.<portlet:namespace />fm[
						'<portlet:namespace />ddmStructureId'
					].value = dataLayout.id;
					submitForm(document.<portlet:namespace />fm);
				});
		});
	}
</aui:script>

<%
if (fileEntryType == null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-document-type"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit-document-type"), currentURL);
}
%>