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
%>

<c:choose>
	<c:when test="<%= (recordSet == null) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="select-an-existing-form-or-add-a-form-to-be-displayed-in-this-application" />
		</div>
	</c:when>
	<c:when test="<%= (ddlDisplayContext.getDisplayDDMTemplateId() > 0) %>">
		<%= ddlDisplayContext.getTemplateContent() %>
	</c:when>
	<c:otherwise>
		<%@ include file="/view_records.jspf" %>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddlDisplayContext.isShowIconsActions() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<liferay-portlet:renderURL varImpl="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/update_redirect.jsp" />
				<portlet:param name="referringPortletResource" value="<%= portletDisplay.getId() %>" />
			</liferay-portlet:renderURL>

			<c:if test="<%= ddlDisplayContext.isShowAddDisplayDDMTemplateIcon() %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="addDisplayTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(recordSet.getDDMStructureId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibAddTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getEditTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(addDisplayTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-plus"
					label="<%= true %>"
					message="add-template"
					url="<%= taglibAddTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowEditDisplayDDMTemplateIcon() %>">
				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editDisplayTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.DYNAMIC_DATA_LISTS %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(ddlDisplayContext.getDisplayDDMTemplateId()) %>" />
					<portlet:param name="resourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)) %>" />
					<portlet:param name="type" value="<%= DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibAddTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + ddlDisplayContext.getEditTemplateTitle() + "', uri:'" + HtmlUtil.escapeJS(editDisplayTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-template"
					url="<%= taglibAddTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= ddlDisplayContext.isShowConfigurationIcon() %>">
				<liferay-ui:icon
					cssClass="lfr-icon-action lfr-icon-action-configuration"
					iconCssClass="icon-cog"
					label="<%= true %>"
					message="select-form"
					method="get"
					onClick="<%= portletDisplay.getURLConfigurationJS() %>"
					url="<%= portletDisplay.getURLConfiguration() %>"
				/>
			</c:if>
		</div>
	</div>
</c:if>