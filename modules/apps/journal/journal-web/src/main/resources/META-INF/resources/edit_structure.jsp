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
JournalEditStructuresDisplayContext journalEditStructuresDisplayContext = new JournalEditStructuresDisplayContext(request);

String redirect = ParamUtil.getString(request, "redirect");

long structureId = ParamUtil.getLong(request, "structureId");

DDMStructure structure = DDMStructureLocalServiceUtil.fetchStructure(structureId);

DDMStructureVersion structureVersion = null;

if (structure != null) {
	structureVersion = structure.getLatestStructureVersion();
}

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

long parentStructureId = BeanParamUtil.getLong(structure, request, "parentStructureId", DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);

DDMStructure parentStructure = DDMStructureLocalServiceUtil.fetchStructure(parentStructureId);

String parentStructureName = StringPool.BLANK;

if (parentStructure != null) {
	parentStructureName = parentStructure.getName(locale);
}

String script = null;

if (structure != null) {
	script = BeanParamUtil.getString(structureVersion, request, "definition");
}
else {
	script = BeanParamUtil.getString(structure, request, "definition");
}

JSONArray fieldsJSONArray = null;

if (structure != null) {
	fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(structureVersion, script);
}
else {
	fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(structure, script);
}

String fieldsJSONArrayString = StringPool.BLANK;

if (fieldsJSONArray != null) {
	fieldsJSONArrayString = fieldsJSONArray.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(PortalUtil.escapeRedirect(redirect));

renderResponse.setTitle((structure != null) ? LanguageUtil.format(request, "edit-x", structure.getName(locale), false) : LanguageUtil.get(request, "new-structure"));
%>

<portlet:actionURL name="/journal/add_structure" var="addStructureURL">
	<portlet:param name="mvcPath" value="/edit_structure.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/journal/update_structure" var="updateStructureURL">
	<portlet:param name="mvcPath" value="/edit_structure.jsp" />
</portlet:actionURL>

<aui:form action="<%= (structure == null) ? addStructureURL : updateStructureURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	<aui:input name="definition" type="hidden" />

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

	<c:if test="<%= (structure != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(structureId) > 0) %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="there-are-content-references-to-this-structure.-you-may-lose-data-if-a-field-name-is-renamed-or-removed" />
		</div>
	</c:if>

	<c:if test="<%= (structureId > 0) && (DDMTemplateLocalServiceUtil.getTemplatesCount(null, PortalUtil.getClassNameId(DDMStructure.class), structureId) > 0) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-template-references-to-this-structure.-please-update-them-if-a-field-name-is-renamed-or-removed" />
		</div>
	</c:if>

	<c:if test="<%= (structure != null) && (groupId != scopeGroupId) %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="this-structure-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-structure" />
		</div>
	</c:if>

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="name" />

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
					<aui:input name="storageType" type="hidden" value="<%= journalEditStructuresDisplayContext.getStorageType() %>" />

					<c:if test="<%= !journalEditStructuresDisplayContext.autogenerateStructureKey() %>">
						<aui:input disabled="<%= structure != null %>" name="structureKey" />
					</c:if>

					<aui:input name="description" />

					<aui:field-wrapper label="parent-strucutre">
						<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />

						<aui:input cssClass="lfr-input-text" disabled="<%= true %>" label="" name="parentStructureName" type="text" value="<%= parentStructureName %>" />

						<aui:button onClick='<%= renderResponse.getNamespace() + "openParentStructureSelector();" %>' value="select" />

						<aui:button disabled="<%= Validator.isNull(parentStructureName) %>" name="removeParentStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentStructure();" %>' value="remove" />
					</aui:field-wrapper>

					<c:if test="<%= structure != null %>">
						<portlet:resourceURL id="/journal/get_structure" var="getStructureURL">
							<portlet:param name="structureId" value="<%= String.valueOf(structureId) %>" />
						</portlet:resourceURL>

						<aui:input name="url" type="resource" value="<%= getStructureURL %>" />

						<%
						Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
						%>

						<aui:input name="webDavURL" type="resource" value="<%= structure.getWebDavURL(themeDisplay, WebDAVUtil.getStorageToken(portlet)) %>" />
					</c:if>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<%@ include file="/form_builder.jspf" %>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />openParentStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, DDMPortletKeys.DYNAMIC_DATA_MAPPING, PortletRequest.RENDER_PHASE) %>',
				classPK: <%= (structure != null) ? structure.getPrimaryKey() : 0 %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectParentStructure',
				mvcPath: '/select_structure.jsp',
				showAncestorScopes: true,
				showManageTemplates: false,
				title: '<liferay-ui:message key="select-structure" />'
			},
			function(event) {
				var form = AUI.$('#<portlet:namespace />fm');

				form.fm('parentStructureId').val(event.ddmstructureid);

				form.fm('parentStructureName').val(AUI._.unescape(event.name));

				form.fm('removeParentStructureButton').attr('disabled', false).removeClass('disabled');
			}
		);
	}

	function <portlet:namespace />removeParentStructure() {
		var form = AUI.$('#<portlet:namespace />fm');

		form.fm('parentStructureId').val('');
		form.fm('parentStructureName').val('');

		form.fm('removeParentStructureButton').attr('disabled', true).addClass('disabled');
	}

	function <portlet:namespace />saveStructure() {
		var form = AUI.$('#<portlet:namespace />fm');

		form.fm('definition').val(<portlet:namespace />formBuilder.getContentValue());

		submitForm(form);
	}
</aui:script>