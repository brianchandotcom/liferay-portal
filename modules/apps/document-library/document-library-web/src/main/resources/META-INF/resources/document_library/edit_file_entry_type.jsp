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

long dataDefinitionId = BeanParamUtil.getLong(fileEntryType, request, "dataDefinitionId");

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

if (dataDefinitionId != 0) {
	com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(dataDefinitionId);

	defaultLanguageId = ddmStructure.getDefaultLanguageId();
}

String languageId = ParamUtil.getString(request, "languageId", defaultLanguageId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((fileEntryType == null) ? LanguageUtil.get(request, "new-document-type") : fileEntryType.getName(locale));
%>

<portlet:actionURL name="/document_library/edit_file_entry_type" var="editFileEntryTypeURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/edit_file_entry_type" />
</portlet:actionURL>

<aui:form action="<%= editFileEntryTypeURL %>" cssClass="edit-metadata-type-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (fileEntryType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fileEntryTypeId" type="hidden" value="<%= fileEntryTypeId %>" />
	<aui:input name="dataDefinitionId" type="hidden" value="<%= dataDefinitionId %>" />
	<aui:input name="dataDefinition" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />
	<aui:input name="languageId" type="hidden" value="<%= languageId %>" />

	<liferay-ui:error exception="<%= DuplicateFileEntryTypeException.class %>" message="please-enter-a-unique-document-type-name" />
	<liferay-ui:error exception="<%= NoSuchMetadataSetException.class %>" message="please-enter-a-valid-metadata-set-or-enter-a-metadata-field" />
	<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />
	<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-metadata-field-names-(including-field-names-inherited-from-the-parent)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()) %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled", "structure") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item tbar-item-expand"></li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" type="submit" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div class="contextual-sidebar-content">
		<clay:container-fluid
			cssClass="container-view"
		>

			<%
			DLEditFileEntryTypeDataEngineDisplayContext dlEditFileEntryTypeDataEngineDisplayContext = (DLEditFileEntryTypeDataEngineDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_EDIT_FILE_ENTRY_TYPE_DATA_ENGINE_DISPLAY_CONTEXT);
			%>

			<liferay-data-engine:data-layout-builder
				additionalPanels="<%= dlEditFileEntryTypeDataEngineDisplayContext.getAdditionalPanels(npmResolvedPackageName) %>"
				componentId='<%= liferayPortletResponse.getNamespace() + "dataLayoutBuilder" %>'
				contentType="document-library"
				dataDefinitionId="<%= dataDefinitionId %>"
				dataLayoutInputId="dataLayout"
				groupId="<%= scopeGroupId %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
			/>
		</clay:container-fluid>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "LocaleChangedHandlerComponent" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"contentTitle", "name"
		).put(
			"defaultLanguageId", defaultLanguageId
		).build()
	%>'
	module="document_library/js/LocaleChangedHandler.es"
	servletContext="<%= application %>"
/>

<aui:script>
	function <portlet:namespace />getInputLocalizedValues(field) {
		var inputLocalized = Liferay.component('<portlet:namespace />' + field);
		var localizedValues = {};

		if (inputLocalized) {
			var translatedLanguages = inputLocalized
				.get('translatedLanguages')
				.values();

			translatedLanguages.forEach(function (languageId) {
				localizedValues[languageId] = inputLocalized.getValue(languageId);
			});
		}

		return localizedValues;
	}

	function <portlet:namespace />saveStructure() {
		Liferay.componentReady('<portlet:namespace />dataLayoutBuilder').then(
			function (dataLayoutBuilder) {
				var name = <portlet:namespace />getInputLocalizedValues('name');

				var description = <portlet:namespace />getInputLocalizedValues(
					'description'
				);

				var formData = dataLayoutBuilder.getFormData();

				var dataDefinition = formData.definition;

				dataDefinition.description = description;
				dataDefinition.name = name;

				var dataLayout = formData.layout;

				dataLayout.description = description;
				dataLayout.name = name;

				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						dataDefinition: JSON.stringify(dataDefinition),
						dataLayout: JSON.stringify(dataLayout),
					},
				});
			}
		);
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