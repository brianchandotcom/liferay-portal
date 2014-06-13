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

<%@ include file="/html/portlet/dynamic_data_list_display/init.jsp" %>

<%
DDLRecordSet recordSet = null;

try {
	if (Validator.isNotNull(recordSetId)) {
		recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);

		if (recordSet.getGroupId() != scopeGroupId) {
			recordSet = null;
		}
	}
%>

	<c:choose>
		<c:when test="<%= (recordSet != null) %>">
			<c:choose>
				<c:when test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSetId, ActionKeys.VIEW) %>">

					<%
					renderRequest.setAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD_SET, recordSet);
					%>

					<liferay-util:include page="/html/portlet/dynamic_data_lists/view_record_set.jsp">
						<liferay-util:param name="displayDDMTemplateId" value="<%= String.valueOf(displayDDMTemplateId) %>" />
						<liferay-util:param name="formDDMTemplateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
						<liferay-util:param name="editable" value="<%= String.valueOf(editable) %>" />
						<liferay-util:param name="spreadsheet" value="<%= String.valueOf(spreadsheet) %>" />
					</liferay-util:include>
				</c:when>
				<c:otherwise>

					<%
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
					%>

					<div class="alert alert-danger">
						<liferay-ui:message key="you-do-not-have-the-roles-required-to-access-this-dynamic-data-list-record-set" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>

			<%
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
			%>

			<br />

			<div class="alert alert-info">
				<liferay-ui:message key="select-an-existing-list-or-add-a-list-to-be-displayed-in-this-portlet" />
			</div>
		</c:otherwise>
	</c:choose>

<%
}
catch (NoSuchRecordSetException nsrse) {
%>

	<div class="alert alert-danger">
		<%= LanguageUtil.get(pageContext, "the-selected-list-no-longer-exists") %>
	</div>

<%
}

boolean hasConfigurationPermission = PortletPermissionUtil.contains(permissionChecker, layout, portletDisplay.getId(), ActionKeys.CONFIGURATION);

boolean showAddListIcon = hasConfigurationPermission && DDLPermission.contains(permissionChecker, scopeGroupId, portletDisplay.getId(), ActionKeys.ADD_RECORD_SET);
boolean showAddTemplateIcon = (recordSet != null) && DDMPermission.contains(permissionChecker, scopeGroupId, ddmDisplay.getResourceName(), ddmDisplay.getAddTemplateActionId());
boolean showEditDisplayTemplateIcon = (displayDDMTemplateId != 0) && DDMTemplatePermission.contains(permissionChecker, scopeGroupId, displayDDMTemplateId, PortletKeys.DYNAMIC_DATA_LISTS, ActionKeys.UPDATE);
boolean showEditFormTemplateIcon = (formDDMTemplateId != 0) && DDMTemplatePermission.contains(permissionChecker, scopeGroupId, formDDMTemplateId, PortletKeys.DYNAMIC_DATA_LISTS, ActionKeys.UPDATE);
%>

<c:if test="<%= themeDisplay.isSignedIn() && !layout.isLayoutPrototypeLinkActive() && (showAddListIcon || showAddTemplateIcon || showEditDisplayTemplateIcon || showEditFormTemplateIcon || hasConfigurationPermission ) %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<c:if test="<%= showAddTemplateIcon %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="addFormTemplateURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="portletResourceNamespace" value="<%= renderResponse.getNamespace() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-form-template"
					url="<%= addFormTemplateURL %>"
				/>

				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="addDisplayTemplateURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-display-template"
					url="<%= addDisplayTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= showEditFormTemplateIcon %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editFormTemplateURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="portletResourceNamespace" value="<%= renderResponse.getNamespace() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(formDDMTemplateId) %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-form-template"
					url="<%= editFormTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= showEditDisplayTemplateIcon %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editDisplayTemplateURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(displayDDMTemplateId) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-display-template"
					url="<%= editDisplayTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= hasConfigurationPermission %>">
				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-cog"
					label="<%= true %>"
					message="select-list"
					method="get"
					onClick="<%= portletDisplay.getURLConfigurationJS() %>"
					url="<%= portletDisplay.getURLConfiguration() %>"
				/>
			</c:if>

			<c:if test="<%= showAddListIcon %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" var="addListURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-list"
					url="<%= addListURL %>"
				/>
			</c:if>
		</div>
	</div>
</c:if>