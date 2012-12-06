<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
	scriptJSONArray = DDMXSDUtil.getJSONArray(script);
}

if (scriptJSONArray != null) {
	scriptJSONArray = _addStructureFieldAttributes(structure, scriptJSONArray);
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
	<aui:input name="saveCallback" type="hidden" value="<%= saveCallback %>" />
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
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
				<aui:layout cssClass="lfr-ddm-types-form-column">
					<c:choose>
						<c:when test="<%= scopeClassNameId == 0 %>">
							<aui:column first="<%= true %>">
								<aui:field-wrapper>
									<aui:select disabled="<%= structure != null %>" label="type" name="scopeClassNameId">
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DDLRecordSet.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DDLRecordSet.class.getName()) %>" />
										<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, DLFileEntryMetadata.class.getName()) %>" value="<%= PortalUtil.getClassNameId(DLFileEntryMetadata.class.getName()) %>" />
									</aui:select>
								</aui:field-wrapper>
							</aui:column>
						</c:when>
						<c:otherwise>
							<aui:input name="scopeClassNameId" type="hidden" value="<%= scopeClassNameId %>" />
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= Validator.isNull(storageTypeValue) %>">
							<aui:column>
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
							</aui:column>
						</c:when>
						<c:otherwise>
							<aui:input name="storageType" type="hidden" value="<%= storageTypeValue %>" />
						</c:otherwise>
					</c:choose>
				</aui:layout>

				<aui:input name="description" />

				<aui:field-wrapper label="parent-data-definition">
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

					<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
						<aui:field-wrapper label="webdav-url">

							<%
							Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);
							%>

							<liferay-ui:input-resource url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/webdav" + scopeGroup.getFriendlyURL() + "/dynamic_data_mapping/ddmStructures/" + classPK %>' />
						</aui:field-wrapper>
					</c:if>
				</c:if>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</aui:form>

<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />openParentStructureSelector() {
		Liferay.Util.openDDMPortlet(
		{
			ddmResource: '<%= ddmResource %>',
			dialog: {
				width: 820
			},
			saveCallback: '<%= renderResponse.getNamespace() + "selectParentStructure" %>',
			showGlobalScope: true,
			showManageTemplates: false,
			storageType: '<%= PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE %>',
			structureName: 'data-definition',
			structureType: 'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet',
			struts_action: '/dynamic_data_mapping/select_structure',
			title: '<%= UnicodeLanguageUtil.get(pageContext, "data-definitions") %>'
		}
		);
	}

	function <portlet:namespace />removeParentStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />parentStructureId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

		nameEl.href = "#";
		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = true;
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectParentStructure',
		function(ddmStructureId, ddmStructureName, dialog) {
			document.<portlet:namespace />fm.<portlet:namespace />parentStructureId.value = ddmStructureId;

			var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

			nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" /><portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" /></portlet:renderURL>&<portlet:namespace />classPK=" + ddmStructureId;
			nameEl.innerHTML = ddmStructureName + "&nbsp;";

			document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = false;

			if (dialog) {
				dialog.close();
			}
		}
	);
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			if (window.<portlet:namespace />formBuilder) {
				document.<portlet:namespace />fm.<portlet:namespace />xsd.value = window.<portlet:namespace />formBuilder.getXSD();
			}

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	<c:if test="<%= Validator.isNotNull(saveCallback) && (classPK != 0) %>">
		window.parent['<%= HtmlUtil.escapeJS(saveCallback) %>']('<%= classPK %>', '<%= HtmlUtil.escape(structure.getName(locale)) %>');
	</c:if>
</aui:script>

<%!
public JSONArray _addStructureFieldAttributes(DDMStructure structure, JSONArray scriptJSONArray) throws Exception {
	for (int i = 0; i < scriptJSONArray.length(); i++) {
		JSONObject jsonObject = scriptJSONArray.getJSONObject(i);

		String fieldName = jsonObject.getString("name");

		try {
			jsonObject.put("readOnlyAttributes", _getFieldReadOnlyAttributes(structure, fieldName));
		}
		catch (StructureFieldException sfe) {
		}
	}

	return scriptJSONArray;
}

public JSONArray _getFieldReadOnlyAttributes(DDMStructure structure, String fieldName) throws Exception {
	JSONArray readOnlyAttributesJSONArray = JSONFactoryUtil.createJSONArray();

	try {
		if (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(structure.getStructureId()) > 0) {
			readOnlyAttributesJSONArray.put("name");
		}
	}
	catch (Exception e) {
	}

	return readOnlyAttributesJSONArray;
}
%>