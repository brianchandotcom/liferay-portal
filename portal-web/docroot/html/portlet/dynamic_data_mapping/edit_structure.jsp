<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

long parentStructureId = BeanParamUtil.getLong(structure, request, "parentStructureId", DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID);

String parentStructureName = StringPool.BLANK;

try {
	DDMStructure parentStructure = DDMStructureServiceUtil.getStructure(parentStructureId);

	parentStructureName = parentStructure.getName(locale);
}
catch (NoSuchStructureException nsee) {
}

long classNameId = PortalUtil.getClassNameId(DDMStructure.class);
long classPK = BeanParamUtil.getLong(structure, request, "structureId");

String script = BeanParamUtil.getString(structure, request, "xsd");

JSONArray scriptJSONArray = null;

if (Validator.isNotNull(script)) {
	if (structure != null) {
		try {
			scriptJSONArray = DDMXSDUtil.getJSONArray(structure, script);
		}
		catch (Exception e) {
			scriptJSONArray = DDMXSDUtil.getJSONArray(structure.getDocument());
		}
	}
	else {
		scriptJSONArray = DDMXSDUtil.getJSONArray(script);
	}
}
%>

<portlet:actionURL var="editStructureURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
</portlet:actionURL>

<aui:form action="<%= editStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (structure != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="classNameId" type="hidden" value="<%= String.valueOf(classNameId) %>" />
	<aui:input name="classPK" type="hidden" value="<%= String.valueOf(classPK) %>" />
	<aui:input name="xsd" type="hidden" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

	<%
	boolean localizeTitle = true;
	String title = "new-structure";

	if (structure != null) {
		localizeTitle = false;
		title = structure.getName(locale);
	}
	else if (Validator.isNotNull(scopeStructureName)) {
		title = LanguageUtil.format(pageContext, "new-x", scopeStructureName);
	}
	%>

	<portlet:renderURL var="viewRecordsURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/view" />
	</portlet:renderURL>

	<liferay-ui:header
		backURL="<%= viewRecordsURL %>"
		localizeTitle="<%= localizeTitle %>"
		title="<%= title %>"
	/>

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
				<aui:row cssClass="lfr-ddm-types-form-column">
					<c:choose>
						<c:when test="<%= scopeClassNameId == 0 %>">
							<aui:col width="<%= 50 %>">
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" label="type" name="scopeClassNameId">
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DLFileEntryMetadata.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()) %>" />
									</aui:select>
								</aui:field-wrapper>
							</aui:col>
						</c:when>
						<c:otherwise>
							<aui:input name="scopeClassNameId" type="hidden" value="<%= scopeClassNameId %>" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= Validator.isNull(storageTypeValue) %>">
							<aui:col width="<%= 50 %>">
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" name="storageType">

									<%
									for (StorageType storageType : StorageType.values()) {
									%>

										<aui:option label="<%= storageType %>" value="<%= storageType %>" />

									<%
									}
									%>

									</aui:select>
								</aui:field-wrapper>
							</aui:col>
						</c:when>
						<c:otherwise>
							<aui:input name="storageType" type="hidden" value="<%= storageTypeValue %>" />
						</c:otherwise>
					</c:choose>
				</aui:row>

				<aui:input name="description" />

				<aui:field-wrapper label='<%= LanguageUtil.format(pageContext, "parent-x", HtmlUtil.escape(scopeStructureName)) %>'>
					<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />

					<c:choose>
						<c:when test="<%= (structure == null) || Validator.isNotNull(parentStructureId) %>">
							<portlet:renderURL var="parentStructureURL">
								<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
								<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
								<portlet:param name="classPK" value="<%= String.valueOf(parentStructureId) %>" />
							</portlet:renderURL>

							<aui:a href="<%= parentStructureURL %>" id="parentStructureName" label="<%= HtmlUtil.escape(parentStructureName) %>" />
						</c:when>
						<c:otherwise>
							<aui:a href="" id="parentStructureName" />
						</c:otherwise>
					</c:choose>

					<aui:button onClick='<%= renderResponse.getNamespace() + "openParentStructureSelector();" %>' value="select" />

					<aui:button name="removeParentStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentStructure();" %>' value="remove" />
				</aui:field-wrapper>

				<c:if test="<%= structure != null %>">
					<aui:field-wrapper label="url">
						<liferay-ui:input-resource url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/dynamic_data_mapping/get_structure?structureId=" + classPK %>' />
					</aui:field-wrapper>

					<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
						<aui:field-wrapper label="webdav-url">
							<liferay-ui:input-resource url="<%= structure.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
						</aui:field-wrapper>
					</c:if>
				</c:if>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' primary="<%= true %>" value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />openParentStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				availableFields: 'Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE',
				classPK: <%= (structure != null) ? structure.getPrimaryKey() : 0 %>,
				ddmResource: '<%= HtmlUtil.escapeJS(ddmResource) %>',
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectParentStructure',
				showGlobalScope: true,
				showManageTemplates: false,
				storageType: '<%= HtmlUtil.escapeJS(scopeStorageType) %>',
				structureName: '<%= HtmlUtil.escapeJS(scopeStructureName) %>',
				structureType: '<%= HtmlUtil.escapeJS(scopeStructureType) %>',
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= HtmlUtil.escapeJS(scopeTitle) %>'
			},
			function(event){
				document.<portlet:namespace />fm.<portlet:namespace />parentStructureId.value = event.ddmstructureid;

				var nameEl = document.getElementById('<portlet:namespace />parentStructureName');

				nameEl.href = '<portlet:renderURL><portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" /><portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" /></portlet:renderURL>&<portlet:namespace />classPK=' + event.ddmstructureid;
				nameEl.innerHTML = event.name + '&nbsp;';

				document.getElementById('<portlet:namespace />removeParentStructureButton').disabled = false;
			}
		);
	}

	function <portlet:namespace />removeParentStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />parentStructureId.value = '';

		var nameEl = document.getElementById('<portlet:namespace />parentStructureName');

		nameEl.href = '#';
		nameEl.innerHTML = '';

		document.getElementById('<portlet:namespace />removeParentStructureButton').disabled = true;
	}
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = window.<portlet:namespace />formBuilder.getContentXSD();

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base', 'liferay-portlet-dynamic-data-mapping']
	);
</aui:script>