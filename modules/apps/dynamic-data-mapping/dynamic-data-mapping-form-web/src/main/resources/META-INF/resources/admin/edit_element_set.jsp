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

<portlet:actionURL name="saveStructure" var="saveStructureURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/edit_element_set" />
</portlet:actionURL>

<div class="portlet-forms" id="<portlet:namespace />formContainer">
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
		<liferay-frontend:translation-manager
			availableLocales="<%= ddmFormAdminDisplayContext.getAvailableLocales() %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			id="translationManager"
		/>
	</div>

	<aui:form action="<%= saveStructureURL %>" cssClass="ddm-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
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

		<div id="<portlet:namespace />-container"></div>
	</aui:form>
</div>

<div class="hide">
	<%= ddmFormAdminDisplayContext.serializeSettingsForm() %>
</div>

<aui:script>
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
							groupId: <%= groupId %>,
							localizedDescription: <%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddmFormAdminDisplayContext.getFormLocalizedName() %>,
							namespace: '<portlet:namespace />',
							redirectURL: '<%= HtmlUtil.escape(redirect) %>',
							spritemap: Liferay.DDM.FormSettings.spritemap,
							strings: Liferay.DDM.FormSettings.strings,
							view: 'fieldSets'
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
	} else {
		Liferay.onceAfter('DMMFieldTypesReady', function() {
			Liferay.Forms.App.start();
		});
	}
</aui:script>