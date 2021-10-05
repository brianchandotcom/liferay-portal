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
editDDMStructureURL.setParameter("ddmStructureId", String.valueOf(ddmStructureId));
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/journal-web/css/ddm_form.css") %>" rel="stylesheet" />
</liferay-util:html-top>

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
					<c:choose>
						<c:when test="<%= journalEditDDMStructuresDisplayContext.isFFTranslationManagerAdminModeEnabled() %>">
							<aui:input activeLanguageIds="<%= journalEditDDMStructuresDisplayContext.getAvailableLanguageIds() %>" adminMode="<%= true %>" cssClass="form-control-inline" defaultLanguageId="<%= (ddmForm == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()) %>" label="" localized="<%= true %>" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "structure") %>' type="text" wrapperCssClass="article-content-title mb-0" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmForm == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()) %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "structure") %>' wrapperCssClass="article-content-title mb-0" />
						</c:otherwise>
					</c:choose>
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" id="submitButton" type="submit" value="save" />
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
						scopes='<%= SetUtil.fromCollection(Arrays.asList("journal")) %>'
						searchableFieldsDisabled="<%= !journalEditDDMStructuresDisplayContext.isStructureFieldIndexableEnable() %>"
						singlePage="<%= true %>"
						submitButtonId='<%= liferayPortletResponse.getNamespace() + "submitButton" %>'
					/>
				</div>
			</div>
		</clay:container-fluid>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "DataEngineLayoutBuilderHandler" %>'
	context="<%= journalEditDDMStructuresDisplayContext.getDataEngineLayoutBuilderHandlerContext() %>"
	module="js/DataEngineLayoutBuilderHandler.es"
	servletContext="<%= application %>"
/>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "LocaleChangedHandlerComponent" %>'
	context="<%= journalEditDDMStructuresDisplayContext.getLocaleChangedHandlerContext() %>"
	module="js/LocaleChangedHandler.es"
	servletContext="<%= application %>"
/>