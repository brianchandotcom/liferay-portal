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
String redirect = ParamUtil.getString(request, "redirect", currentURL);

DDLRecordSet recordSet = ddlDisplayContext.getRecordSet();

if (recordSet != null) {
	renderResponse.setTitle(recordSet.getName(locale));
}
%>

<c:choose>
	<c:when test="<%= recordSet == null %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-list-or-add-a-list-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_record_set.jsp" servletContext="<%= application %>">
			<liferay-util:param name="mvcPath" value="/view_selected_record_set.jsp" />
			<liferay-util:param name="displayDDMTemplateId" value="<%= String.valueOf(ddlDisplayContext.getDisplayDDMTemplateId()) %>" />
			<liferay-util:param name="formDDMTemplateId" value="<%= String.valueOf(ddlDisplayContext.getFormDDMTemplateId()) %>" />
			<liferay-util:param name="editable" value="<%= String.valueOf(ddlDisplayContext.isEditable()) %>" />
			<liferay-util:param name="spreadsheet" value="<%= String.valueOf(ddlDisplayContext.isSpreadsheet()) %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddlDisplayContext.isShowIconsActions() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<liferay-portlet:renderURL varImpl="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/update_redirect.jsp" />
				<portlet:param name="referringPortletResource" value="<%= portletDisplay.getId() %>" />
			</liferay-portlet:renderURL>

			<c:if test="<%= ddlDisplayContext.isShowAddDDMTemplateIcon() %>">
				<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="addFormTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="portletResourceNamespace" value="<%= renderResponse.getNamespace() %>" />
					<portlet:param name="refererPortletName" value="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
				</liferay-portlet:renderURL>

				<%
				String taglibAddFormTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getAddDDMTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(addFormTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-form-template"
					url="<%= taglibAddFormTemplateURL %>"
				/>

				<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="addDisplayTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibAddDisplayTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getAddDDMTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(addDisplayTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-display-template"
					url="<%= taglibAddDisplayTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowEditDisplayDDMTemplateIcon() %>">
				<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="editDisplayTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(ddlDisplayContext.getDisplayDDMTemplateId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibEditDisplayDDMTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getEditDisplayDDMTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(editDisplayTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-display-template"
					url="<%= taglibEditDisplayDDMTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowEditFormDDMTemplateIcon() %>">
				<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="editFormTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="portletResourceNamespace" value="<%= renderResponse.getNamespace() %>" />
					<portlet:param name="refererPortletName" value="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(ddlDisplayContext.getFormDDMTemplateId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_FORM %>" />
					<portlet:param name="structureAvailableFields" value='<%= renderResponse.getNamespace() + "getAvailableFields" %>' />
				</liferay-portlet:renderURL>

				<%
				String taglibEditFormDDMTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getEditFormDDMTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(editFormTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-form-template"
					url="<%= taglibEditFormDDMTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowConfigurationIcon() %>">
				<liferay-ui:icon
					cssClass="lfr-icon-action lfr-icon-action-configuration"
					iconCssClass="icon-cog"
					label="<%= true %>"
					message="select-list"
					method="get"
					onClick="<%= portletDisplay.getURLConfigurationJS() %>"
					url="<%= portletDisplay.getURLConfiguration() %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowAddRecordSetIcon() %>">
				<liferay-portlet:renderURL portletName="<%= DDLPortletKeys.DYNAMIC_DATA_LISTS %>" var="addRecordSetURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibAddRecordSetURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getEditFormDDMTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(addRecordSetURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-list"
					url="<%= taglibAddRecordSetURL %>"
				/>
			</c:if>
		</div>
	</div>
</c:if>