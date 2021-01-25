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

JournalEditDDMStructuresDisplayContext journalEditDDMStructuresDisplayContext = new JournalEditDDMStructuresDisplayContext(request, liferayPortletResponse);

DDMStructure ddmStructure = journalEditDDMStructuresDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((ddmStructure != null) ? LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false) : LanguageUtil.get(request, "new-structure"));

DDMForm ddmForm = null;
long ddmStructureId = 0L;

if (ddmStructure != null) {
	ddmForm = ddmStructure.getDDMForm();
	ddmStructureId = ddmStructure.getStructureId();
}

PortletURL editDDMStructureURL = renderResponse.createActionURL();

if (ddmStructure == null) {
	editDDMStructureURL.setParameter(ActionRequest.ACTION_NAME, "/journal/add_data_definition");
}
else {
	editDDMStructureURL.setParameter(ActionRequest.ACTION_NAME, "/journal/update_data_definition");
}

editDDMStructureURL.setParameter("mvcPath", "/edit_data_definition.jsp");
%>

<aui:form action="<%= editDDMStructureURL.toString() %>" cssClass="edit-article-form" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="dataDefinition" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />
	<aui:input name="dataDefinitionId" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getDDMStructureId() %>" />
	<aui:input name="languageId" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getDefaultLanguageId() %>" />

	<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmForm == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()) %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "structure") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" type="submit" value="<%= journalEditDDMStructuresDisplayContext.getSaveButtonLabel() %>" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div class="contextual-sidebar-content">
		<clay:container-fluid
			cssClass="container-view"
		>
			<div class="contextual-sidebar-mr">
				<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(journalEditDDMStructuresDisplayContext.getDDMStructureId()) > 0) %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
					</div>
				</c:if>

				<c:if test="<%= (journalEditDDMStructuresDisplayContext.getDDMStructureId() > 0) && (DDMTemplateLocalServiceUtil.getTemplatesCount(null, PortalUtil.getClassNameId(DDMStructure.class), journalEditDDMStructuresDisplayContext.getDDMStructureId()) > 0) %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-are-template-references-to-this-structure.-please-update-them-if-a-field-name-is-renamed-or-removed" />
					</div>
				</c:if>

				<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
					</div>
				</c:if>

				<div class="contextual-sidebar-mr-n">
					<liferay-data-engine:data-layout-builder
						additionalPanels="<%= journalEditDDMStructuresDisplayContext.getAdditionalPanels(npmResolvedPackageName) %>"
						componentId='<%= liferayPortletResponse.getNamespace() + "dataLayoutBuilder" %>'
						contentType="journal"
						dataDefinitionId="<%= ddmStructureId %>"
						groupId="<%= groupId %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
						singlePage="<%= true %>"
					/>
				</div>
			</div>
		</clay:container-fluid>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "LocaleChangedHandlerComponent" %>'
	context="<%= journalEditDDMStructuresDisplayContext.getComponentContext() %>"
	module="js/LocaleChangedHandler.es"
	servletContext="<%= application %>"
/>

<aui:script sandbox="<%= true %>">
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
				const nameInput = document.getElementById(
					'<portlet:namespace />name'
				);

				var name = <portlet:namespace />getInputLocalizedValues('name');

				if (
					!nameInput.value ||
					!name[
						'<%= journalEditDDMStructuresDisplayContext.getDefaultLanguageId() %>'
					]
				) {
					Liferay.Util.openToast({
						message:
							'<liferay-ui:message key="please-enter-a-valid-name" />',
						title: '<liferay-ui:message key="error" />',
						type: 'danger',
					});

					nameInput.focus();

					return;
				}

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

	const form = document.getElementById('<portlet:namespace />fm');

	if (form) {
		form.addEventListener('submit', <portlet:namespace />saveDDMStructure);

		Liferay.once('destroyPortlet', function () {
			form.removeEventListener(
				'submit',
				<portlet:namespace />saveDDMStructure
			);
		});
	}
</aui:script>