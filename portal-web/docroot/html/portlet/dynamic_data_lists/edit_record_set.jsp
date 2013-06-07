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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String portletResource = ParamUtil.getString(request, "portletResource");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");

long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

if (recordSet != null) {
	ddmStructureId = recordSet.getDDMStructureId();
}

String ddmStructureName = StringPool.BLANK;

if (ddmStructureId > 0) {
	try {
		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(ddmStructureId);

		ddmStructureName = HtmlUtil.escape(ddmStructure.getName(locale));
	}
	catch (NoSuchStructureException nsse) {
	}
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= (recordSet == null) %>"
	title='<%= (recordSet == null) ? "new-list" : recordSet.getName(locale) %>'
/>

<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
	<aui:input name="scope" type="hidden" value="<%= DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS %>" />

	<liferay-ui:error exception="<%= RecordSetDDMStructureIdException.class %>" message="please-enter-a-valid-definition" />
	<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= recordSet %>" model="<%= DDLRecordSet.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<aui:field-wrapper label="data-definition" required="<%= true %>">
			<span id="<portlet:namespace />ddmStructureNameDisplay">
				<%= ddmStructureName %>
			</span>

			<liferay-ui:icon
				image="add"
				label="<%= true %>"
				message="select"
				url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
			/>
		</aui:field-wrapper>

		<c:if test="<%= WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(DDLRecord.class.getName()) != null) %>">
			<aui:select label="workflow" name="workflowDefinition">

				<%
				WorkflowDefinitionLink workflowDefinitionLink = null;

				try {
					workflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.getWorkflowDefinitionLink(company.getCompanyId(), themeDisplay.getScopeGroupId(), DDLRecordSet.class.getName(), recordSetId, 0, true);
				}
				catch (NoSuchWorkflowDefinitionLinkException nswdle) {
				}
				%>

				<aui:option><%= LanguageUtil.get(pageContext, "no-workflow") %></aui:option>

				<%
				List<WorkflowDefinition> workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), 0, 100, null);

				for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
					boolean selected = false;

					if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
						selected = true;
					}
				%>

					<aui:option label='<%= workflowDefinition.getName() + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion()) + ")" %>' selected="<%= selected %>" value="<%= workflowDefinition.getName() + StringPool.AT + workflowDefinition.getVersion() %>" />

				<%
				}
				%>

			</aui:select>
		</c:if>

		<aui:button-row>
			<aui:button name="saveButton" type="submit" value="save" />

			<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= ddmStructureId %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				groupId: <%= groupId %>,

				<%
				Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
				%>

				refererPortletName: '<%= portlet.getPortletName() %>',
				refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
				struts_action: '/dynamic_data_mapping/select_structure',
				title: '<%= UnicodeLanguageUtil.get(pageContext, "data-definitions") %>'
			},
			function(event) {
				var A = AUI();

				A.one('#<portlet:namespace />ddmStructureId').val(event.ddmstructureid);

				A.one('#<portlet:namespace />ddmStructureNameDisplay').setContent(event.name);
			}
		);
	}

	function <portlet:namespace />saveRecordSet() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (recordSet == null) ? Constants.ADD : Constants.UPDATE %>";

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
if (recordSet != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/dynamic_data_lists/edit_record_set");
	portletURL.setParameter("recordSetId", String.valueOf(recordSet.getRecordSetId()));

	PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-list"), currentURL);
}
%>