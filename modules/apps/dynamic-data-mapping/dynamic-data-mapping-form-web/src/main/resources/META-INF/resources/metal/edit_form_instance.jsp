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
%>

<portlet:actionURL name="saveFormInstance" var="saveFormInstanceURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_form_instance" />
</portlet:actionURL>

<div class="portlet-forms" id="<portlet:namespace />formContainer">
	<clay:navigation-bar
		componentId="formsNavigationBar"
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
					<button class="btn btn-secondary nav-btn nav-btn-monospaced publish-icon <%= (formInstance == null) ? "hide" : "" %>" data-original-title="<liferay-ui:message key="copy-url" />" id="<portlet:namespace />publishIcon" title="<liferay-ui:message key="copy-url" />" type="button">
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

	<div class="container-fluid-1280">
		<aui:translation-manager availableLocales="<%= availableLocales %>" changeableDefaultLanguage="<%= false %>" defaultLanguageId="<%= defaultLanguageId %>" id="translationManager" />
	</div>

	<aui:form action="<%= saveFormInstanceURL %>" cssClass="ddm-form-builder-form" enctype="multipart/form-data" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<%@ include file="/admin/exceptions.jspf" %>

		<div class="ddm-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= true %>"
						contents="<%= HtmlUtil.escape(HtmlUtil.unescape(ddmFormAdminDisplayContext.getFormName())) %>"
						cssClass="ddm-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-form"
						showSource="<%= false %>"
					/>
				</h1>

				<aui:input name="name" type="hidden" />

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= true %>"
						contents="<%= HtmlUtil.escape(HtmlUtil.unescape(ddmFormAdminDisplayContext.getFormDescription())) %>"
						cssClass="ddm-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-form"
						showSource="<%= false %>"
					/>
				</h5>

				<aui:input name="description" type="hidden" />
			</div>
		</div>

		<div class="container-fluid-1280">
			<aui:input name="serializedFormBuilderContext" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
			<div id="<portlet:namespace />ruleBuilder"></div>
		</div>

		<div id="<portlet:namespace />-container"></div>

		<div class="container-fluid-1280">
			<aui:button-row cssClass="ddm-form-builder-buttons">
				<aui:button cssClass="btn-primary ddm-button" id="publish" value='<%= ddmFormAdminDisplayContext.isFormPublished() ? "unpublish-form": "publish-form" %>' />

				<aui:button cssClass="ddm-button" id="save" value="save-form" />

				<aui:button cssClass="btn-link" id="preview" value="preview-form" />
			</aui:button-row>
		</div>
	</aui:form>

	<div class="container-fluid-1280 ddm-form-instance-settings hide" id="<portlet:namespace />settings">
		<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
	</div>
</div>

<aui:script require="<%= mainRequire %>">
	const spritemap = '<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg';

	const fieldsList = [
		{
			icon: 'calendar',
			name: '<%= LanguageUtil.get(request, "date") %>',
			type: 'date'
		},
		{
			icon: 'text',
			name: '<%= LanguageUtil.get(request, "text-field") %>',
			type: 'text'
		},
		{
			icon: 'radio-button',
			name: '<%= LanguageUtil.get(request, "radio-field-type-label") %>',
			type: 'radio'
		},
		{
			icon: 'list',
			name: '<%= LanguageUtil.get(request, "select-field-type-label") %>',
			type: 'select'
		},
		{
			icon: 'grid',
			name: '<%= LanguageUtil.get(request, "grid-field-type-label") %>',
			type: 'grid'
		},
		{
			icon: 'select-from-list',
			name: '<%= LanguageUtil.get(request, "checkbox-field-type-label") %>',
			type: 'checkbox'
		}
	];

	const fieldContext = [
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									key: 'label',
									label: '<%= LanguageUtil.get(request, "label") %>',
									required: false,
									spritemap: spritemap,
									type: 'text'
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									key: 'helpText',
									label: '<%= LanguageUtil.get(request, "help-text") %>',
									required: false,
									spritemap: spritemap,
									type: 'text'
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									items: [
										{label: '￿0￿'}
									],
									key: 'required',
									required: false,
									showAsSwitcher: true,
									spritemap: spritemap,
									type: 'checkbox'
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									items: [
										{
											disabled: false,
											label: '<%= LanguageUtil.get(request, "option") %>'
										}
									],
									key: 'items',
									label: '<%= LanguageUtil.get(request, "options") %>',
									placeholder: '<%= LanguageUtil.get(request, "enter-an-option") %>',
									required: true,
									spritemap: spritemap,
									type: 'options'
								}
							],
							size: 12
						}
					]
				}
			]
		},
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									key: 'value',
									label: '<%= LanguageUtil.get(request, "predefined-value") %>',
									required: false,
									spritemap: spritemap,
									type: 'select'
								}
							],
							size: 12
						}
					]
				}
			]
		}
	];

	const serializedFormBuilderContext = <%= serializedFormBuilderContext %>;

	main.DDMForm(
		{
			context: serializedFormBuilderContext.pages,
			dependencies: ['dynamic-data-mapping-form-field-type/metal'],
			fieldContext,
			fieldsList,
			modules: Liferay.MODULES,
			spritemap
		},
		'#<portlet:namespace />-container',
		function() {
		}
	);
</aui:script>