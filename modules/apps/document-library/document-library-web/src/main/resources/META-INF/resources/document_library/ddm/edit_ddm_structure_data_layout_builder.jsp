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

DLEditDDMStructureDisplayContext dlEditDDMStructureDisplayContext = new DLEditDDMStructureDisplayContext(request, liferayPortletResponse);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = dlEditDDMStructureDisplayContext.getDDMStructure();

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

String title = LanguageUtil.format(request, "new-x", LanguageUtil.get(resourceBundle, "metadata-set"), false);

if (ddmStructure != null) {
	title = LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<portlet:actionURL name="/document_library/add_data_definition" var="addDataDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/edit_ddm_structure" />
</portlet:actionURL>

<portlet:actionURL name="/document_library/update_data_definition" var="updateDataDefinitionURL">
	<portlet:param name="mvcRenderCommandName" value="/document_library/edit_ddm_structure" />
</portlet:actionURL>

<aui:form action="<%= (ddmStructure == null) ? addDataDefinitionURL : updateDataDefinitionURL %>" cssClass="edit-metadata-type-form" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveDDMStructure();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="dataDefinitionId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="dataDefinition" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />
	<aui:input name="languageId" type="hidden" value="<%= dlEditDDMStructureDisplayContext.getSelectedLanguageId() %>" />
	<aui:input name="status" type="hidden" />

	<aui:model-context bean="<%= ddmStructure %>" model="<%= com.liferay.dynamic.data.mapping.model.DDMStructure.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-metadata-type">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="form-control-inline" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled", "metadata-set") %>' wrapperCssClass="mb-0" />
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div class="contextual-sidebar-content">
		<clay:container-fluid
			cssClass="container-view"
		>
			<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(ddmStructure.getPrimaryKey()) > 0) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
				</div>
			</c:if>

			<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
				</div>
			</c:if>

			<liferay-data-engine:data-layout-builder
				additionalPanels="<%= dlEditDDMStructureDisplayContext.getAdditionalPanels(npmResolvedPackageName) %>"
				componentId='<%= liferayPortletResponse.getNamespace() + "dataLayoutBuilder" %>'
				contentType="document-library"
				dataDefinitionId="<%= ddmStructureId %>"
				dataLayoutInputId="dataLayout"
				groupId="<%= groupId %>"
				localizable="<%= true %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
			/>
		</clay:container-fluid>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "LocaleChangedHandlerComponent" %>'
	context="<%= dlEditDDMStructureDisplayContext.getComponentContext() %>"
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

	function <portlet:namespace />saveDDMStructure() {
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