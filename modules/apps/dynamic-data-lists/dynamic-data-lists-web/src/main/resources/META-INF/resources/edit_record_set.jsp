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
String closeRedirect = ParamUtil.getString(request, "closeRedirect");

String portletResource = ParamUtil.getString(request, "portletResource");

DDLRecordSet recordSet = (DDLRecordSet)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");

long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);

Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

if (scopeGroup.isStagingGroup() && !scopeGroup.isInStagingPortlet(DDLPortletKeys.DYNAMIC_DATA_LISTS)) {
	groupId = scopeGroup.getLiveGroupId();
}

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

if (recordSet != null) {
	ddmStructureId = recordSet.getDDMStructureId();
}

String ddmStructureName = StringPool.BLANK;

if (ddmStructureId > 0) {
	DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchDDMStructure(ddmStructureId);

	if (ddmStructure != null) {
		ddmStructureName = HtmlUtil.escape(ddmStructure.getName(locale));
	}
}

String languageId = LanguageUtil.getLanguageId(request);

if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle((recordSet == null) ? LanguageUtil.get(request, "new-list") : recordSet.getName(locale));
}
%>

<portlet:actionURL name="/dynamic_data_lists/add_record_set" var="addRecordSetURL">
	<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/dynamic_data_lists/update_record_set" var="updateRecordSetURL">
	<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
</portlet:actionURL>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<aui:form action="<%= (recordSet == null) ? addRecordSetURL : updateRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveRecordSet();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="scope" type="hidden" value="<%= DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS %>" />

		<liferay-ui:error exception="<%= RecordSetDDMStructureIdException.class %>" message="please-enter-a-valid-definition" />
		<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-name" />

		<liferay-asset:asset-categories-error />

		<liferay-asset:asset-tags-error />

		<aui:model-context bean="<%= recordSet %>" model="<%= DDLRecordSet.class %>" />

		<aui:fieldset-group markupView="lexicon">
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
						cssClass="open-record-set-modal"
						label="<%= true %>"
						linkCssClass="btn btn-secondary"
						message="select"
						url="javascript:void(0);"
					/>
				</div>

				<c:if test="<%= WorkflowEngineManagerUtil.isDeployed() && (WorkflowHandlerRegistryUtil.getWorkflowHandler(DDLRecord.class.getName()) != null) && !scopeGroup.isLayoutSetPrototype() %>">
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

							if ((workflowDefinitionLink != null) && Objects.equals(workflowDefinitionLink.getWorkflowDefinitionName(), workflowDefinition.getName()) && (workflowDefinitionLink.getWorkflowDefinitionVersion() == workflowDefinition.getVersion())) {
								selected = true;
							}
						%>

							<aui:option label="<%= HtmlUtil.escape(workflowDefinition.getTitle(languageId)) %>" selected="<%= selected %>" value="<%= HtmlUtil.escapeAttribute(workflowDefinition.getName()) + StringPool.AT + workflowDefinition.getVersion() %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>
			</aui:fieldset>

			<c:if test="<%= recordSet == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= DDLRecordSet.class.getName() %>"
					/>
				</aui:fieldset>
			</c:if>

			<div class="sheet-footer">
				<aui:button name="saveButton" type="submit" value="save" />

				<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
			</div>
		</aui:fieldset-group>
	</aui:form>
</clay:container-fluid>

<aui:script>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"itemSelectorURL",
				PortletURLBuilder.create(
					PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
				).setMVCPath(
					"/select_structure.jsp"
				).setParameter(
					"classNameId", PortalUtil.getClassNameId(DDMStructure.class)
				).setParameter(
					"classPK", ddmStructureId
				).setParameter(
					"groupId", groupId
				).setParameter(
					"navigationStartsOn", DDMNavigationHelper.SELECT_STRUCTURE
				).setParameter(
					"portletResourceNamespace", liferayPortletResponse.getNamespace()
				).setParameter(
					"refererPortletName", portlet.getPortletName()
				).setParameter(
					"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet)
				).setParameter(
					"showAncestorScopes", true
				).setParameter(
					"showBackURL", false
				).setParameter(
					"showHeader", false
				).setParameter(
					"structureAvailableFields", liferayPortletResponse.getNamespace() + "getAvailableFields"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString()
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).put(
				"selectEventName", "<portlet:namespace />selectDDMStructure"
			).build()
		%>'
		module="js/EditRecordSetStructureSelector"
	/>

	function <portlet:namespace />saveRecordSet() {
		var form = document.<portlet:namespace />fm;

		if (!form) {
			return;
		}

		submitForm(form);
	}
</aui:script>

<%
if (recordSet != null) {
	PortalUtil.addPortletBreadcrumbEntry(
		request, recordSet.getName(locale),
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/edit_record_set.jsp"
		).setParameter(
			"recordSetId", recordSet.getRecordSetId()
		).buildString());

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-list"), currentURL);
}
%>