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

String portletResource = ParamUtil.getString(request, "portletResource");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

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
if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle((recordSet == null) ? LanguageUtil.get(request, "new-list") : recordSet.getName(locale));
}
%>

<portlet:actionURL name="addRecordSet" var="addRecordSetURL">
	<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
</portlet:actionURL>

<portlet:actionURL name="updateRecordSet" var="updateRecordSetURL">
	<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
</portlet:actionURL>

<aui:form action="<%= (recordSet == null) ? addRecordSetURL : updateRecordSetURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
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
		<c:if test="<%= (recordSet != null) && (DDMStorageLinkLocalServiceUtil.getStructureStorageLinksCount(recordSet.getDDMStructureId()) > 0) %>">
			<div class="alert alert-warning">
				<liferay-ui:message key="updating-the-data-definition-may-cause-data-loss" />
			</div>
		</c:if>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" />

		<aui:input name="description" />

		<div class="form-group">
			<aui:input label="data-definition" name="ddmStructureNameDisplay" required="<%= true %>" type="resource" value="<%= ddmStructureName %>" />

			<liferay-ui:icon
				iconCssClass="icon-search"
				label="<%= true %>"
				linkCssClass="btn btn-default"
				message="select"
				url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
			/>
		</div>

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

				<aui:option><liferay-ui:message key="no-workflow" /></aui:option>

				<%
				List<WorkflowDefinition> workflowDefinitions = WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

				for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
					boolean selected = false;

					if ((workflowDefinitionLink != null) && workflowDefinitionLink.getWorkflowDefinitionName().equals(workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
						selected = true;
					}
				%>

					<aui:option label='<%= HtmlUtil.escape(workflowDefinition.getName()) + " (" + LanguageUtil.format(locale, "version-x", workflowDefinition.getVersion(), false) + ")" %>' selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

				<%
				}
				%>

			</aui:select>
		</c:if>

		<c:if test="<%= recordSet == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DDLRecordSet.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />openDDMStructureSelector() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classPK: <%= ddmStructureId %>,
				dialog: {
					destroyOnHide: true
				},
				eventName: '<portlet:namespace />selectDDMStructure',
				groupId: <%= groupId %>,
				mvcPath: '/select_structure.jsp',

				<%
				Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
				%>

				refererPortletName: '<%= portlet.getPortletName() %>',
				refererWebDAVToken: '<%= WebDAVUtil.getStorageToken(portlet) %>',
				showAncestorScopes: true,
				title: '<%= UnicodeLanguageUtil.get(request, "data-definitions") %>'
			},
			function(event) {
				AUI.$('#<portlet:namespace />ddmStructureId').val(event.ddmstructureid);

				AUI.$('#<portlet:namespace />ddmStructureNameDisplay').val(_.unescape(event.name));
			}
		);
	}

	function <portlet:namespace />saveRecordSet() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
if (recordSet != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/edit_record_set.jsp");
	portletURL.setParameter("recordSetId", String.valueOf(recordSet.getRecordSetId()));

	PortalUtil.addPortletBreadcrumbEntry(request, recordSet.getName(locale), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-list"), currentURL);
}
%>