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

DDMFormInstance formInstance = ddmFormAdminDisplayContext.getDDMFormInstance();

long formInstanceId = BeanParamUtil.getLong(formInstance, request, "formInstanceId");
long groupId = BeanParamUtil.getLong(formInstance, request, "groupId", scopeGroupId);
long ddmStructureId = BeanParamUtil.getLong(formInstance, request, "structureId");

boolean disableCopyButton = false;

if (!ddmFormAdminDisplayContext.isFormPublished() && (formInstance != null)) {
	disableCopyButton = true;
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((formInstance == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));
%>

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
					<button class="btn btn-secondary <%= disableCopyButton ? "ddm-btn-disabled" : "" %> <%= (!ddmFormAdminDisplayContext.isFormPublished() && (formInstance == null)) ? "hide" : "" %> lfr-ddm-share-url-button nav-btn nav-btn-monospaced share-form-icon" data-original-title="<liferay-ui:message key="copy-url" />" id="<portlet:namespace />publishIcon" title="<%= disableCopyButton ? LanguageUtil.get(request, "publish-the-form-to-get-its-shareable-link") : LanguageUtil.get(request, "copy-url") %>" type="button">
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
		<liferay-frontend:translation-manager
			availableLocales="<%= ddmFormAdminDisplayContext.getAvailableLocales() %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			id="translationManager"
		/>
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
						contents="<%= HtmlUtil.escapeAttribute(ddmFormAdminDisplayContext.getFormName()) %>"
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
						contents="<%= HtmlUtil.escapeAttribute(ddmFormAdminDisplayContext.getFormDescription()) %>"
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
		<%= ddmFormAdminDisplayContext.serializeSettingsForm() %>
	</div>
</div>

<portlet:actionURL name="publishFormInstance" var="publishFormInstanceURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
</portlet:actionURL>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="saveFormInstance" var="autoSaveFormInstanceURL" />

<aui:script>
	Liferay.namespace('DDM').FormSettings = {
		autosaveInterval: <%= ddmFormAdminDisplayContext.getAutosaveInterval() %>,
		autosaveURL: '<%= autoSaveFormInstanceURL.toString() %>',
		portletNamespace: '<portlet:namespace />',
		publishFormInstanceURL: '<%= publishFormInstanceURL.toString() %>',
		restrictedFormURL:
			'<%= ddmFormAdminDisplayContext.getRestrictedFormURL() %>',
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
		start: function(initialPages) {
			Liferay.Loader.require(
				'<%= mainRequire %>',
				function(packageName) {
					var context = <%= serializedFormBuilderContext %>;

					if (context.pages.length === 0 && initialPages) {
						context.pages = initialPages;
					}

					Liferay.Forms.instance = new packageName.Form(
						{
							context: context,
							dataProviderInstanceParameterSettingsURL:
								'<%= dataProviderInstanceParameterSettingsURL %>',
							dataProviderInstancesURL:
								'<%= dataProviderInstancesURL %>',
							defaultLanguageId:
								'<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>',
							fieldSetDefinitionURL:
								'<%= ddmFormAdminDisplayContext.getFieldSetDefinitionURL() %>',
							fieldSets: <%= ddmFormAdminDisplayContext.getFieldSetsJSONArray() %>,
							fieldTypes: <%= ddmFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>,
							formInstanceId: <%= formInstanceId %>,
							functionsMetadata: <%= functionsMetadata %>,
							functionsURL: '<%= functionsURL %>',
							groupId: <%= groupId %>,
							localizedDescription: <%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddmFormAdminDisplayContext.getFormLocalizedName() %>,
							namespace: '<portlet:namespace />',
							published: <%= ddmFormAdminDisplayContext.isFormPublished() %>,
							rolesURL: '<%= rolesURL %>',
							rules: <%= serializedDDMFormRules %>,
							saved: <%= formInstance != null %>,
							showPublishAlert: <%= ddmFormAdminDisplayContext.isShowPublishAlert() %>,
							spritemap: Liferay.DDM.FormSettings.spritemap,
							strings: Liferay.DDM.FormSettings.strings,
							view: 'formBuilder'
						},
						'#<portlet:namespace />-container'
					);
				},
				function(error) {
					throw error;
				}
			);
		}
	};

	var clearPortletHandlers = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.Forms.App.dispose();

			var translationManager = Liferay.component(
				'<portlet:namespace />translationManager'
			);

			Liferay.destroyComponents(function(component) {
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

	if (Liferay.DMMFieldTypesReady) {
		Liferay.Forms.App.start();
	}
	else {
		Liferay.onceAfter('DMMFieldTypesReady', function() {
			Liferay.Forms.App.start();
		});
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
									Liferay.Util.getWindow(
										'<portlet:namespace />settingsModal'
									).hide();
								}
							}
						},
						{
							cssClass: 'btn-primary',
							label: '<liferay-ui:message key="done" />',
							on: {
								click: function() {
									Liferay.Util.getWindow(
										'<portlet:namespace />settingsModal'
									).hide();
								}
							}
						}
					],
					width: 720
				},
				id: '<portlet:namespace />settingsModal',
				stack: false,
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