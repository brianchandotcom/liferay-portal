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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDMStructure structure = ddmFormAdminDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

long structureId = ParamUtil.getLong(request, "structureId");

if (structure != null) {
	structureId = structure.getStructureId();
}

String structureKey = BeanParamUtil.getString(structure, request, "structureKey");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((structure == null) ? LanguageUtil.get(request, "new-element-set") : LanguageUtil.get(request, "edit-element-set"));
%>

<portlet:actionURL name="/dynamic_data_mapping_form/save_structure" var="saveStructureURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_element_set" />
</portlet:actionURL>

<div class="portlet-forms" id="<portlet:namespace />formContainer">
	<div class="forms-navigation-bar">
		<clay:navigation-bar
			inverted="<%= true %>"
			navigationItems="<%= ddmFormAdminDisplayContext.getElementSetBuilderNavigationItems() %>"
		/>
	</div>

	<nav class="management-bar management-bar-light navbar navbar-expand-md toolbar-group-field">
		<clay:container-fluid
			cssClass="d-flex justify-content-between toolbar"
		>
			<ul class="navbar-nav toolbar-group-field"></ul>
			<ul class="navbar-nav toolbar-group-field">
				<li class="nav-item">
					<button class="btn btn-primary lfr-ddm-add-field lfr-ddm-plus-button nav-btn nav-btn-monospaced" id="addFieldButton">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddmFormAdminDisplayContext.getLexiconIconsPath() %>plus" />
						</svg>
					</button>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="ddm-translation-manager"
	>
		<liferay-frontend:translation-manager
			availableLocales="<%= ddmFormAdminDisplayContext.getAvailableLocales() %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			id="translationManager"
		/>
	</clay:container-fluid>

	<aui:form action="<%= saveStructureURL %>" cssClass="ddm-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
		<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />
		<aui:input name="name" type="hidden" />
		<aui:input name="description" type="hidden" />
		<aui:input name="serializedFormBuilderContext" type="hidden" value="<%= formBuilderContextJSONObject %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<%@ include file="/admin/exceptions.jspf" %>

		<div id="<portlet:namespace />-container">
			<react:component
				module="admin/js/App.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"context", formBuilderContextJSONObject
					).put(
						"dataProviderInstanceParameterSettingsURL", dataProviderInstanceParameterSettingsURL
					).put(
						"dataProviderInstancesURL", dataProviderInstancesURL
					).put(
						"defaultLanguageId", ddmFormAdminDisplayContext.getDefaultLanguageId()
					).put(
						"elementSets", ddmFormAdminDisplayContext.getFieldSetsJSONArray()
					).put(
						"fieldSetDefinitionURL", ddmFormAdminDisplayContext.getFieldSetDefinitionURL()
					).put(
						"fieldTypes", ddmFormAdminDisplayContext.getDDMFormFieldTypesJSONArray()
					).put(
						"groupId", groupId
					).put(
						"localizedDescription", ddmFormAdminDisplayContext.getFormLocalizedDescriptionJSONObject()
					).put(
						"localizedName", ddmFormAdminDisplayContext.getFormLocalizedNameJSONObject(structure)
					).put(
						"portletNamespace", liferayPortletResponse.getNamespace()
					).put(
						"redirectURL", HtmlUtil.escape(redirect)
					).put(
						"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
					).put(
						"view", "fieldSets"
					).build()
				%>'
			/>
		</div>
	</aui:form>
</div>

<div class="hide">
	<react:component
		module="admin/js/FormView.link.es"
		props="<%= ddmFormAdminDisplayContext.getDDMFormSettingsContext(pageContext) %>"
	/>
</div>

<aui:script>
	Liferay.namespace('DDM').FormSettings = {
		portletNamespace: '<portlet:namespace />',
		showPagination: false,
		spritemap: '<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg',
	};

	var clearPortletHandlers = function (event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			var translationManager = Liferay.component(
				'<portlet:namespace />translationManager'
			);

			Liferay.destroyComponents((component) => {
				var destroy = false;

				if (component === translationManager) {
					destroy = true;
				}

				return destroy;
			});

			Liferay.detach('destroyPortlet', clearPortletHandlers);
		}
	};

	Liferay.on('destroyPortlet', clearPortletHandlers);
</aui:script>