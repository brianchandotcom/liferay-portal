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

long formInstanceId = BeanParamUtil.getLong(formInstance, request, "formInstanceId");
long groupId = BeanParamUtil.getLong(formInstance, request, "groupId", scopeGroupId);
long ddmStructureId = BeanParamUtil.getLong(formInstance, request, "structureId");

String defaultLanguageId = ddmFormAdminDisplayContext.getDefaultLanguageId();

Locale[] availableLocales = ddmFormAdminDisplayContext.getAvailableLocales();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((formInstance == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));

boolean isFormPublished = ddmFormAdminDisplayContext.isFormPublished();
boolean isFormSaved = formInstance != null ? true : false;
String disableCopyBtnClass = "";

if (!isFormPublished && isFormSaved) {
	disableCopyBtnClass = "ddm-btn-disabled";
}
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, "/o/dynamic-data-mapping-form-builder/metal/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<portlet:actionURL name="saveFormInstance" var="saveFormInstanceURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
</portlet:actionURL>

<div class="lfr-alert-container">
	<div class="container-fluid-1280 lfr-alert-wrapper"></div>
</div>

<div class="portlet-forms" id="<portlet:namespace />formContainer">
	<clay:navigation-bar
		componentId="formsNavigationBar"
		elementClasses="forms-management-bar"
		inverted="<%= true %>"
		navigationItems="<%= ddmFormAdminDisplayContext.getFormBuilderNavigationItems() %>"
	/>

	<nav class="management-bar management-bar-light navbar navbar-expand-md toolbar-group-field">
		<div class="autosave-bar container toolbar">
			<div class="navbar-form navbar-form-autofit navbar-overlay toolbar-group-content">
				<span class="autosave-feedback management-bar-text" id="<portlet:namespace />autosaveMessage"></span>
			</div>

			<ul class="navbar-nav toolbar-group-field">
				<li class="nav-item">
					<button class="btn btn-secondary lfr-ddm-share-url-button nav-btn nav-btn-monospaced share-form-icon <%= disableCopyBtnClass %> <%= (!isFormPublished && !isFormSaved) ? "hide" : "" %>" data-original-title="<liferay-ui:message key="copy-url" />" id="<portlet:namespace />publishIcon" title="<%= (disableCopyBtnClass == "") ? LanguageUtil.get(request, "copy-url") : LanguageUtil.get(request, "publish-the-form-to-get-its-shareable-link") %>" type="button">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddmFormAdminDisplayContext.getLexiconIconsPath() %>link" />
						</svg>
					</button>
				</li>
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

	<aui:form action="<%= saveFormInstanceURL %>" cssClass="ddm-form-builder-form" enctype="multipart/form-data" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="name" type="hidden" value="<%= ddmFormAdminDisplayContext.getFormLocalizedName() %>" />
		<aui:input name="description" type="hidden" value="<%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>" />
		<aui:input name="serializedFormBuilderContext" type="hidden" value="<%= serializedFormBuilderContext %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<%@ include file="/admin/exceptions.jspf" %>

		<div class="ddm-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(HtmlUtil.unescape(ddmFormAdminDisplayContext.getFormName())) %>"
						cssClass="ddm-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-form"
						showSource="<%= false %>"
					/>
				</h1>

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(HtmlUtil.unescape(ddmFormAdminDisplayContext.getFormDescription())) %>"
						cssClass="ddm-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-form"
						showSource="<%= false %>"
					/>
				</h5>
			</div>
		</div>

		<div id="<portlet:namespace />-container"></div>
	</aui:form>

	<div class="container-fluid-1280 ddm-form-instance-settings hide" id="<portlet:namespace />settings">
		<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
	</div>
</div>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishFormInstance" var="publishFormInstanceURL" />

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="saveFormInstance" var="autoSaveFormInstanceURL" />

<aui:script>
	var rawModuleName = '<%= mainRequire %>'.split(' ')[0];

	Liferay.namespace('DDM').FormSettings = {
		autosaveInterval: <%= ddmFormAdminDisplayContext.getAutosaveInterval() %>,
		autosaveURL: '<%= autoSaveFormInstanceURL.toString() %>',
		portletNamespace: '<portlet:namespace />',
		publishFormInstanceURL: '<%= publishFormInstanceURL.toString() %>',
		restrictedFormURL: '<%= ddmFormAdminDisplayContext.getRestrictedFormURL() %>',
		sharedFormURL: '<%= ddmFormAdminDisplayContext.getSharedFormURL() %>',
		showPagination: true,
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
		start: function(sessionPages) {
			Liferay.Loader.require(
				rawModuleName,
				function(packageName) {
					var context = <%= serializedFormBuilderContext %>;

					if (context.pages.length === 0 && sessionPages) {
						context.pages = sessionPages;
					}

					packageName.DDMForm(
						{
							context: context,
							dataProviderInstanceParameterSettingsURL: '<%= dataProviderInstanceParameterSettingsURL %>',
							dataProviderInstancesURL: '<%= dataProviderInstancesURL %>',
							defaultLanguageId: '<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>',
							dependencies: ['dynamic-data-mapping-form-field-type/metal'],
							fieldTypes: <%= ddmFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>,
							formInstanceId: '<%= formInstanceId %>',
							functionsMetadata: <%= functionsMetadata %>,
							functionsURL: '<%= functionsURL %>',
							localizedDescription: <%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddmFormAdminDisplayContext.getFormLocalizedName() %>,
							modules: Liferay.MODULES,
							namespace: '<portlet:namespace />',
							published: !!<%= ddmFormAdminDisplayContext.isFormPublished() %>,
							rolesURL: '<%= rolesURL %>',
							rules: <%= serializedDDMFormRules %>,
							saved: <%= formInstance != null %>,
							spritemap: Liferay.DDM.FormSettings.spritemap,
							strings: Liferay.DDM.FormSettings.strings
						},
						'#<portlet:namespace />-container',
						function(ddmForm) {
							Liferay.Forms.instance = ddmForm;
						}
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

	Liferay.Forms.App.start();
</aui:script>