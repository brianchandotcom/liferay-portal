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

JournalEditDDMStructuresDisplayContext journalEditDDMStructuresDisplayContext = new JournalEditDDMStructuresDisplayContext(request);

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
%>

<portlet:actionURL name="/journal/add_ddm_structure" var="addDDMStructureURL">
	<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/journal/update_ddm_structure" var="updateDDMStructureURL">
	<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
</portlet:actionURL>

<aui:form action="<%= (ddmStructure == null) ? addDDMStructureURL : updateDDMStructureURL %>" cssClass="edit-article-form" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveDDMStructure();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.getDDMStructureId() %>" />
	<aui:input name="definition" type="hidden" />
	<aui:input name="indexable" type="hidden" value="<%= journalEditDDMStructuresDisplayContext.isStructureFieldIndexableEnable() %>" />

	<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmForm == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): LocaleUtil.toLanguageId(ddmForm.getDefaultLocale()) %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "structure") %>' wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" type="submit" value="<%= journalEditDDMStructuresDisplayContext.getSaveButtonLabel() %>" />

						<clay:button
							icon="cog"
							id='<%= renderResponse.getNamespace() + "contextualSidebarButton" %>'
							monospaced="<%= true %>"
							size="sm"
							style="borderless"
						/>
					</div>
				</li>
			</ul>
		</div>
	</nav>

	<div class="contextual-sidebar edit-article-sidebar sidebar-light sidebar-sm" id="<portlet:namespace />contextualSidebarContainer">
		<div class="sidebar-header">
			<h4 class="component-title">
				<liferay-ui:message key="properties" />
			</h4>
		</div>

		<div class="sidebar-body">
			<liferay-frontend:form-navigator
				fieldSetCssClass="panel-group-flush"
				formModelBean="<%= ddmStructure %>"
				id="<%= JournalWebConstants.FORM_NAVIGATOR_ID_JOURNAL_DDM_STRUCTURE %>"
				showButtons="<%= false %>"
			/>
		</div>
	</div>

	<div class="contextual-sidebar-content">
		<div class="container-fluid container-fluid-max-xl container-view">
			<liferay-ui:error exception="<%= DDMFormLayoutValidationException.class %>" message="please-enter-a-valid-form-layout" />

			<liferay-ui:error exception="<%= DDMFormLayoutValidationException.MustNotDuplicateFieldName.class %>">

				<%
				DDMFormLayoutValidationException.MustNotDuplicateFieldName mndfn = (DDMFormLayoutValidationException.MustNotDuplicateFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(StringUtil.merge(mndfn.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE)) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.class %>" message="please-enter-a-valid-form-definition" />

			<liferay-ui:error exception="<%= DDMFormValidationException.MustNotDuplicateFieldName.class %>">

				<%
				DDMFormValidationException.MustNotDuplicateFieldName mndfn = (DDMFormValidationException.MustNotDuplicateFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(mndfn.getFieldName()) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetFieldsForForm.class %>" message="please-add-at-least-one-field" />

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetOptionsForField.class %>">

				<%
				DDMFormValidationException.MustSetOptionsForField msoff = (DDMFormValidationException.MustSetOptionsForField)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(msoff.getFieldName()) %>" key="at-least-one-option-should-be-set-for-field-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= DDMFormValidationException.MustSetValidCharactersForFieldName.class %>">

				<%
				DDMFormValidationException.MustSetValidCharactersForFieldName msvcffn = (DDMFormValidationException.MustSetValidCharactersForFieldName)errorException;
				%>

				<liferay-ui:message arguments="<%= HtmlUtil.escape(msvcffn.getFieldName()) %>" key="invalid-characters-were-defined-for-field-name-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= LocaleException.class %>">

				<%
				LocaleException le = (LocaleException)errorException;
				%>

				<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
					<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-definition" />
			<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
			<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

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

			<c:choose>
				<c:when test="<%= journalDisplayContext.useDataEngineEditor() %>">
					<liferay-data-engine:data-layout-builder
						componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
						contentType="journal"
						dataDefinitionId="<%= ddmStructureId %>"
						groupId="<%= groupId %>"
						namespace="<%= renderResponse.getNamespace() %>"
					/>
				</c:when>
				<c:otherwise>
					<div class="sheet">
						<%@ include file="/ddm_form_builder.jspf" %>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />openParentDDMStructureSelector() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				id: '<portlet:namespace />selectDDMStructure',
				title:
					'<%= UnicodeLanguageUtil.get(request, "select-structure") %>',
				uri:
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /><portlet:param name="classPK" value="<%= String.valueOf(journalEditDDMStructuresDisplayContext.getDDMStructureId()) %>" /></portlet:renderURL>'
			},
			function(event) {
				var form = document.<portlet:namespace />fm;

				Liferay.Util.setFormValues(form, {
					parentDDMStructureId: event.ddmstructureid,
					parentDDMStructureName: Liferay.Util.unescape(event.name)
				});

				var removeParentDDMStructureButton = Liferay.Util.getFormElement(
					form,
					'removeParentDDMStructureButton'
				);

				if (removeParentDDMStructureButton) {
					Liferay.Util.toggleDisabled(
						removeParentDDMStructureButton,
						false
					);
				}
			}
		);
	}

	function <portlet:namespace />removeParentDDMStructure() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.setFormValues(form, {
			parentDDMStructureId: '',
			parentDDMStructureName: ''
		});

		var removeParentDDMStructureButton = Liferay.Util.getFormElement(
			form,
			'removeParentDDMStructureButton'
		);

		if (removeParentDDMStructureButton) {
			Liferay.Util.toggleDisabled(removeParentDDMStructureButton, true);
		}
	}

	function <portlet:namespace />saveDDMStructure() {
		<c:choose>
			<c:when test="<%= journalDisplayContext.useDataEngineEditor() %>">
				Liferay.componentReady(
					'<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
				).then(function(dataLayoutBuilder) {
					var name =
						document.<portlet:namespace />fm[
							'<portlet:namespace />name_' + themeDisplay.getLanguageId()
						].value;
					var description =
						document.<portlet:namespace />fm['<portlet:namespace />description']
							.value;

					dataLayoutBuilder
						.save({
							dataDefinition: {
								description: {
									value: description
								},
								name: {
									value: name
								}
							},
							dataLayout: {
								description: {
									value: description
								},
								name: {
									value: name
								}
							}
						})
						.then(function(dataLayout) {
							Liferay.Util.navigate('<%= HtmlUtil.escapeJS(redirect) %>');
						});
				});
			</c:when>
			<c:otherwise>
				Liferay.Util.postForm(document.<portlet:namespace />fm, {
					data: {
						definition: <portlet:namespace />formBuilder.getContentValue()
					}
				});
			</c:otherwise>
		</c:choose>
	}

	var contextualSidebarButton = document.getElementById(
		'<portlet:namespace />contextualSidebarButton'
	);
	var contextualSidebarContainer = document.getElementById(
		'<portlet:namespace />contextualSidebarContainer'
	);

	if (
		contextualSidebarContainer &&
		window.innerWidth > Liferay.BREAKPOINTS.PHONE
	) {
		contextualSidebarContainer.classList.add('contextual-sidebar-visible');
	}

	if (contextualSidebarButton) {
		contextualSidebarButton.addEventListener('click', function(event) {
			if (
				contextualSidebarContainer.classList.contains(
					'contextual-sidebar-visible'
				)
			) {
				contextualSidebarContainer.classList.remove(
					'contextual-sidebar-visible'
				);
			} else {
				contextualSidebarContainer.classList.add(
					'contextual-sidebar-visible'
				);
			}
		});
	}
</aui:script>