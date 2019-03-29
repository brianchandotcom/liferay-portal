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

<%@ include file="/metal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDMFormInstance formInstance = ddmFormAdminDisplayContext.getDDMFormInstance();
DDMStructure structure = ddmFormAdminDisplayContext.getDDMStructure();

long formInstanceId = BeanParamUtil.getLong(formInstance, request, "formInstanceId");
long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);
long structureId = ParamUtil.getLong(request, "structureId");

if (structure != null) {
	structureId = structure.getStructureId();
}

String structureKey = BeanParamUtil.getString(structure, request, "structureKey");

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Locale[] availableLocales = ddmFormAdminDisplayContext.getAvailableLocales();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((structure == null) ? LanguageUtil.get(request, "new-element-set") : LanguageUtil.get(request, "edit-element-set"));
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, "/o/dynamic-data-mapping-form-builder/metal/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<portlet:actionURL name="saveStructure" var="saveStructureURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_element_set" />
</portlet:actionURL>

<div class=" portlet-forms" id="<portlet:namespace />formContainer">
	<clay:navigation-bar
		inverted="<%= true %>"
		navigationItems="<%= ddmFormAdminDisplayContext.getElementSetBuilderNavigationItems() %>"
	/>

	<nav class="management-bar management-bar-light navbar navbar-expand-md toolbar-group-field">
		<div class="container toolbar">
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
		</div>
	</nav>

	<div class="container-fluid-1280 ddm-translation-manager">
		<aui:translation-manager availableLocales="<%= availableLocales %>" changeableDefaultLanguage="<%= false %>" defaultLanguageId="<%= defaultLanguageId %>" id="translationManager" />
	</div>

	<aui:form action="<%= saveStructureURL %>" cssClass="ddm-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
		<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />
		<aui:input name="serializedFormBuilderContext" type="hidden" value="<%= serializedFormBuilderContext %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<%@ include file="/admin/exceptions.jspf" %>

		<div class="ddm-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddmFormAdminDisplayContext.getFormName()) %>"
						cssClass="ddm-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-element-set"
						showSource="<%= false %>"
					/>
				</h1>

				<aui:input name="name" type="hidden" />

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddmFormAdminDisplayContext.getFormDescription()) %>"
						cssClass="ddm-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-element-set"
						showSource="<%= false %>"
					/>
				</h5>

				<aui:input name="description" type="hidden" />
			</div>
		</div>

		<div id="<portlet:namespace />-container">
		</div>

		<div class="container-fluid-1280 ddm-form-builder-app">
			<aui:input name="serializedFormBuilderContext" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
		</div>

		<%-- <div class="container-fluid-1280">
			<aui:button-row cssClass="ddm-form-builder-buttons">
				<aui:button id="save" type="submit" value="save" />
				<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</div> --%>

		<liferay-form:ddm-form-builder
			ddmStructureId="<%= ddmFormAdminDisplayContext.getDDMStructureId() %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			editingLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			fieldSetClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
			refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
			showPagination="<%= false %>"
		/>
	</aui:form>

	<div class="container-fluid-1280 ddm-form-instance-settings hide" id="<portlet:namespace />settings">
		<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
	</div>
</div>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="saveFormInstance" var="autoSaveFormInstanceURL" />

<aui:script>
	var rawModuleName = '<%= mainRequire %>'.split(' ')[0];

	Liferay.namespace('DDM').FormSettings = {
		portletNamespace: '<portlet:namespace />',
		showPagination: false,
		spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
	};

	Liferay.Forms.App = {
		dispose: function() {
			if (Liferay.Forms.instance) {
				Liferay.Forms.instance.dispose();
				Liferay.Forms.instance = null;
			}
		},
		reset: function() {
			var pages;

			if (Liferay.Forms.instance) {
				pages = Liferay.Forms.instance.state.pages;
			}

			this.dispose();
			this.start(pages);
		},
		start: function(initialPages) {
			Liferay.Loader.require(
				rawModuleName,
				function(packageName) {
					var context = <%= serializedFormBuilderContext %>;

					if (context.pages.length === 0 && initialPages) {
						context.pages = initialPages;
					}

					Liferay.Forms.instance = new packageName.Form(
						{
							context: context,
							dataProviderInstanceParameterSettingsURL: '<%= dataProviderInstanceParameterSettingsURL %>',
							dataProviderInstancesURL: '<%= dataProviderInstancesURL %>',
							defaultLanguageId: '<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>',
							fieldSetDefinitionURL: '<%= ddmFormAdminDisplayContext.getFieldSetDefinitionURL() %>',
							fieldSets: <%= ddmFormAdminDisplayContext.getFieldSets() %>,
							fieldTypes: <%= ddmFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>,
							formInstanceId: '<%= formInstanceId %>',
							functionsMetadata: <%= functionsMetadata %>,
							functionsURL: '<%= functionsURL %>',
							groupId: <%= groupId %>,
							localizedDescription: <%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddmFormAdminDisplayContext.getFormLocalizedName() %>,
							namespace: '<portlet:namespace />',
							redirectURL: '<%= HtmlUtil.escape(redirect) %>',
							rolesURL: '<%= rolesURL %>',
							rules: <%= serializedDDMFormRules %>,
							saved: <%= formInstance != null %>,
							spritemap: Liferay.DDM.FormSettings.spritemap,
							strings: Liferay.DDM.FormSettings.strings,
							view: 'fieldSets'
						},
						'#<portlet:namespace />-container'
					);
				},
				function(error) {
				}
			);
		}
	};

	var clearPortletHandlers = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.Forms.App.dispose();

			var translationManager = Liferay.component('<portlet:namespace />translationManager');

			Liferay.destroyComponents(
				function(component) {
					var destroy = false;

					if (component === translationManager) {
						destroy = true;
					}

					return destroy;
				}
			);

			Liferay.detach('destroyPortlet', clearPortletHandlers);
		}
	};

	Liferay.on('destroyPortlet', clearPortletHandlers);

	if (Liferay.DMMFieldTypesReady) {
		Liferay.Forms.App.start();
	}
	else {
		Liferay.onceAfter(
			'DMMFieldTypesReady',
			function() {
				Liferay.Forms.App.start();
			}
		);
	}
</aui:script>

<aui:script use="aui-base">
	Liferay.namespace('FormPortlet').destroySettings = function() {
		var settingsNode = A.one('#<portlet:namespace />settingsModal');

		if (settingsNode) {
			Liferay.Util.getWindow('<portlet:namespace />settingsModal').destroy();
		}
	};

	Liferay.namespace('DDM').openSettings = function() {
		Liferay.Util.openWindow(
			{
				dialog: {
					cssClass: 'ddm-form-settings-modal',
					height: 700,
					resizable: false,
					'toolbars.footer': [
						{
							cssClass: 'btn-link',
							label: '<liferay-ui:message key="cancel" />',
							on: {
								click: function() {
									Liferay.Util.getWindow('<portlet:namespace />settingsModal').hide();
								}
							}
						},
						{
							cssClass: 'btn-primary',
							label: '<liferay-ui:message key="done" />',
							on: {
								click: function() {
									var ddmForm = Liferay.component('settingsDDMForm');

									ddmForm.validate(
										function(hasErrors) {
											if (!hasErrors) {
												Liferay.Util.getWindow('<portlet:namespace />settingsModal').hide();
											}
										}
									);
								}
							}
						}
					],
					width: 720
				},
				id: '<portlet:namespace />settingsModal',
				title: '<liferay-ui:message key="form-settings" />'
			},
			function(dialogWindow) {
				var bodyNode = dialogWindow.bodyNode;

				var settingsNode = A.one('#<portlet:namespace />settings');

				settingsNode.show();

				bodyNode.append(settingsNode);
			}
		);
	};

	var clearPortletHandlers = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.namespace('FormPortlet').destroySettings();

			Liferay.detach('destroyPortlet', clearPortletHandlers);
		}
	};

	Liferay.on('destroyPortlet', clearPortletHandlers);
</aui:script>