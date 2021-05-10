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

if (!ddmFormAdminDisplayContext.isFormPublished()) {
	disableCopyButton = true;
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((formInstance == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));
%>

<portlet:actionURL name="/dynamic_data_mapping_form/save_form_instance" var="saveFormInstanceURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
</portlet:actionURL>

<portlet:actionURL name="/dynamic_data_mapping_form/publish_form_instance" var="publishFormInstanceURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
</portlet:actionURL>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_mapping_form/save_form_instance" var="autoSaveFormInstanceURL" />

<div class="lfr-alert-container">
	<clay:container-fluid cssClass="lfr-alert-wrapper"></clay:container-fluid>
</div>

<div class="portlet-forms" id="<portlet:namespace />formContainer">
	<div class="forms-navigation-bar">
		<clay:navigation-bar
			id="formsNavigationBar"
			inverted="<%= true %>"
			navigationItems="<%= ddmFormAdminDisplayContext.getFormBuilderNavigationItems() %>"
		/>
	</div>

	<nav class="hide management-bar management-bar-light navbar navbar-expand-md toolbar-group-field" id="<portlet:namespace />managementToolbar">
		<clay:container-fluid
			cssClass="autosave-bar d-flex justify-content-between toolbar"
		>
			<div class="autosave-feedback-container navbar-form navbar-form-autofit navbar-overlay toolbar-group-content">
				<span class="autosave-feedback management-bar-text" id="<portlet:namespace />autosaveMessage"></span>
			</div>

			<ul class="navbar-nav toolbar-group-field">
				<li class="nav-item pr-3">
					<clay:button
						borderless="<%= true %>"
						cssClass="lfr-ddm-button"
						displayType="secondary"
						icon="cog"
						id='<%= liferayPortletResponse.getNamespace() + "ddmFormInstanceSettingsIcon" %>'
						onClick="javascript:Liferay.DDM.openSettings()"
						small="<%= true %>"
						title='<%= LanguageUtil.get(request, "settings") %>'
					/>
				</li>
				<li class="nav-item pr-2">
					<c:choose>
						<c:when test="<%= disableCopyButton %>">
							<button class="btn btn-secondary btn-sm disabled lfr-ddm-button lfr-ddm-share-url-button share-form-icon" data-original-title="<liferay-ui:message key="share" />" id="<portlet:namespace />publishIcon" title="<%= disableCopyButton ? LanguageUtil.get(request, "publish-the-form-to-get-its-shareable-link") : "" %>" type="button">
								<%= LanguageUtil.get(request, "share") %>
							</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-secondary btn-sm lfr-ddm-button lfr-ddm-share-url-button share-form-icon" id="<portlet:namespace />publishIcon" type="button">
								<%= LanguageUtil.get(request, "share") %>
							</button>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="nav-item pr-2">
					<button class="btn btn-secondary btn-sm lfr-ddm-button lfr-ddm-preview-button">
						<%= LanguageUtil.get(request, "preview") %>
					</button>
				</li>
				<li class="nav-item pl-2 pr-2">
					<button class="btn btn-secondary btn-sm lfr-ddm-button lfr-ddm-save-button">
						<%= LanguageUtil.get(request, "save") %>
					</button>
				</li>
				<li class="nav-item pr-2">
					<button class="btn <%= ddmFormAdminDisplayContext.isFormPublished() ? "btn-secondary" : "btn-primary" %> btn-sm lfr-ddm-button lfr-ddm-publish-button">
						<%= ddmFormAdminDisplayContext.isFormPublished() ? LanguageUtil.get(request, "unpublish") : LanguageUtil.get(request, "publish") %>
					</button>
				</li>
				<li class="nav-item">
					<button class="btn btn-primary btn-sm lfr-ddm-add-field lfr-ddm-plus-button nav-btn nav-btn-monospaced" id="addFieldButton" title="<%= LanguageUtil.get(request, "add-elements") %>">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddmFormAdminDisplayContext.getLexiconIconsPath() %>plus" />
						</svg>
					</button>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="ddm-translation-manager hide"
	>
		<liferay-frontend:translation-manager
			availableLocales="<%= ddmFormAdminDisplayContext.getAvailableLocales() %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			id="translationManager"
		/>
	</clay:container-fluid>

	<aui:form action="<%= saveFormInstanceURL %>" cssClass="ddm-form-builder-form" enctype="multipart/form-data" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="name" type="hidden" value="<%= ddmFormAdminDisplayContext.getFormLocalizedNameJSONObject(formInstance) %>" />
		<aui:input name="description" type="hidden" value="<%= ddmFormAdminDisplayContext.getFormLocalizedDescriptionJSONObject() %>" />
		<aui:input name="serializedFormBuilderContext" type="hidden" value="<%= formBuilderContextJSONObject %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<clay:container-fluid>
			<div class="exception-container">
				<%@ include file="/admin/exceptions.jspf" %>
			</div>
		</clay:container-fluid>

		<div id="<portlet:namespace />-container">
			<react:component
				module="admin/js/App.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"autocompleteUserURL", ddmFormAdminDisplayContext.getAutocompleteUserURL()
					).put(
						"autosaveInterval", ddmFormAdminDisplayContext.getAutosaveInterval()
					).put(
						"autosaveURL", autoSaveFormInstanceURL.toString()
					).put(
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
						"formInstanceId", formInstanceId
					).put(
						"functionsMetadata", functionsMetadataJSONObject
					).put(
						"functionsURL", functionsURL
					).put(
						"groupId", groupId
					).put(
						"localizedDescription", ddmFormAdminDisplayContext.getFormLocalizedDescriptionJSONObject()
					).put(
						"localizedName", ddmFormAdminDisplayContext.getFormLocalizedNameJSONObject(formInstance)
					).put(
						"portletNamespace", liferayPortletResponse.getNamespace()
					).put(
						"published", ddmFormAdminDisplayContext.isFormPublished()
					).put(
						"publishFormInstanceURL", publishFormInstanceURL.toString()
					).put(
						"restrictedFormURL", ddmFormAdminDisplayContext.getRestrictedFormURL()
					).put(
						"rolesURL", rolesURL
					).put(
						"rules", ddmFormRulesJSONArray
					).put(
						"saved", formInstance != null
					).put(
						"sharedFormURL", ddmFormAdminDisplayContext.getSharedFormURL()
					).put(
						"shareFormInstanceURL", ddmFormAdminDisplayContext.getShareFormInstanceURL(formInstance)
					).put(
						"showPublishAlert", ddmFormAdminDisplayContext.isShowPublishAlert()
					).put(
						"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
					).put(
						"view", "formBuilder"
					).build()
				%>'
			/>
		</div>
	</aui:form>

	<clay:container-fluid
		cssClass="ddm-form-instance-settings hide"
		id='<%= liferayPortletResponse.getNamespace() + "settings" %>'
	>
		<react:component
			module="admin/js/FormView.link.es"
			props="<%= ddmFormAdminDisplayContext.getDDMFormSettingsContext(pageContext) %>"
		/>
	</clay:container-fluid>
</div>

<liferay-portlet:runtime
	portletName="<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_REPORT %>"
	queryString='<%= "formInstanceId=" + formInstanceId %>'
/>

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

<aui:script use="aui-base">
	Liferay.namespace('FormPortlet').destroySettings = function () {
		var settingsNode = A.one('#<portlet:namespace />settingsModal');

		if (settingsNode) {
			Liferay.Util.getWindow('<portlet:namespace />settingsModal').destroy();
		}
	};

	Liferay.namespace('DDM').openSettings = function () {
		Liferay.Util.openWindow(
			{
				dialog: {
					cssClass: 'ddm-form-settings-modal modal-full-screen',
					height: 600,
					resizable: false,
					'toolbars.footer': [
						{
							cssClass: 'btn-secondary mr-3',
							label: '<liferay-ui:message key="cancel" />',
							on: {
								click: function () {
									Liferay.Util.getWindow(
										'<portlet:namespace />settingsModal'
									).hide();
								},
							},
						},
						{
							cssClass: 'btn-primary',
							label: '<liferay-ui:message key="done" />',
							on: {
								click: function () {
									Liferay.Util.getWindow(
										'<portlet:namespace />settingsModal'
									).hide();
								},
							},
						},
					],
					width: 600,
				},
				id: '<portlet:namespace />settingsModal',
				stack: false,
				title: '<liferay-ui:message key="settings" />',
			},
			(dialogWindow) => {
				var bodyNode = dialogWindow.bodyNode;

				var settingsNode = A.one('#<portlet:namespace />settings');

				settingsNode.show();

				bodyNode.append(settingsNode);
			}
		);
	};

	var clearPortletHandlers = function (event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.namespace('FormPortlet').destroySettings();

			Liferay.detach('destroyPortlet', clearPortletHandlers);
		}
	};

	Liferay.on('destroyPortlet', clearPortletHandlers);
</aui:script>