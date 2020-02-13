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
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

String refererWebDAVToken = WebDAVUtil.getStorageToken(portlet);

String redirect = ParamUtil.getString(request, "redirect");

DLEditDDMStructureDisplayContext dlEditDDMStructureDisplayContext = new DLEditDDMStructureDisplayContext(request);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = dlEditDDMStructureDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);

boolean localizeTitle = true;
String title = LanguageUtil.format(request, "new-x", LanguageUtil.get(resourceBundle, "metadata-set"), false);

long ddmStructureId = BeanParamUtil.getLong(ddmStructure, request, "structureId");

if (ddmStructure != null) {
	localizeTitle = false;
	title = LanguageUtil.format(request, "edit-x", ddmStructure.getName(locale), false);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(title);
%>

<portlet:actionURL name="/document_library/ddm/add_ddm_structure" var="addDDMStructureURL" />

<portlet:actionURL name="/document_library/ddm/update_ddm_structure" var="updateDDMStructureURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= (ddmStructure == null) ? addDDMStructureURL : updateDDMStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveDDMStructure();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="definition" type="hidden" />
		<aui:input name="status" type="hidden" />

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

		<aui:model-context bean="<%= ddmStructure %>" model="<%= com.liferay.dynamic.data.mapping.model.DDMStructure.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper>
					<c:if test="<%= (ddmStructure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(ddmStructure.getPrimaryKey()) > 0) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
						</div>
					</c:if>

					<c:if test="<%= (ddmStructure != null) && (groupId != scopeGroupId) %>">
						<div class="alert alert-warning">
							<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
						</div>
					</c:if>
				</aui:field-wrapper>

				<aui:input autoFocus="<%= windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

				<liferay-ui:panel-container
					cssClass="lfr-structure-entry-details-container"
					extended="<%= false %>"
					id="structureDetailsPanelContainer"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						defaultState="closed"
						extended="<%= false %>"
						id="structureDetailsSectionPanel"
						markupView="lexicon"
						persistState="<%= true %>"
						title='<%= LanguageUtil.get(request, "details") %>'
					>
						<aui:row cssClass="lfr-ddm-types-form-column">
							<aui:input name="storageType" type="hidden" value="<%= StorageType.JSON.getValue() %>" />
						</aui:row>

						<aui:input name="description" />

						<aui:field-wrapper label='<%= LanguageUtil.format(request, "parent-x", HtmlUtil.escape(LanguageUtil.get(resourceBundle, "metadata-set")), false) %>'>
							<aui:input name="parentDDMStructureId" type="hidden" value="<%= dlEditDDMStructureDisplayContext.getParentDDMStructureId() %>" />

							<aui:input cssClass="lfr-input-text" disabled="<%= true %>" label="" name="parentDDMStructureName" type="text" value="<%= dlEditDDMStructureDisplayContext.getParentDDMStructureName() %>" />

							<aui:button onClick='<%= renderResponse.getNamespace() + "openParentDDMStructureSelector();" %>' value="select" />

							<aui:button disabled="<%= Validator.isNull(dlEditDDMStructureDisplayContext.getParentDDMStructureName()) %>" name="removeParentDDMStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentDDMStructure();" %>' value="remove" />
						</aui:field-wrapper>

						<c:if test="<%= ddmStructure != null %>">
							<portlet:resourceURL id="getStructure" var="getStructureURL">
								<portlet:param name="structureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
							</portlet:resourceURL>

							<aui:input name="url" type="resource" value="<%= getStructureURL.toString() %>" />

							<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
								<aui:input name="webDavURL" type="resource" value="<%= ddmStructure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
							</c:if>
						</c:if>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<c:choose>
					<c:when test="<%= FFDocumentLibraryDDMEditorConfigurationUtil.useDataEngineEditor() %>">
						<liferay-data-engine:data-layout-builder
							componentId='<%= renderResponse.getNamespace() + "dataLayoutBuilder" %>'
							contentType="document-library"
							dataDefinitionId="<%= ddmStructureId %>"
							dataLayoutInputId="dataLayout"
							groupId="<%= groupId %>"
							localizable="<%= true %>"
							namespace="<%= renderResponse.getNamespace() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/document_library/ddm/ddm_form_builder.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</aui:fieldset>
		</aui:fieldset-group>
	</aui:form>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "saveDDMStructure();" %>' primary="<%= true %>" value='<%= LanguageUtil.get(request, "save") %>' />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>

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
					'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/ddm/select_ddm_structure.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(dlEditDDMStructureDisplayContext.getDDMStructureId()) %>" /></portlet:renderURL>'
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
			<c:when test="<%= FFDocumentLibraryDDMEditorConfigurationUtil.useDataEngineEditor() %>">
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
							document.<portlet:namespace />fm[
								'<portlet:namespace />ddmStructureId'
							].value = dataLayout.id;
							submitForm(document.<portlet:namespace />fm);
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
</aui:script>