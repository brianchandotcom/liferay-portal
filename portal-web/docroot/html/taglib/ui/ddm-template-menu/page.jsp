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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %>
<%@ page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-menu:classNameId"));
List<String> displayStyles = (List<String>)request.getAttribute("liferay-ui:ddm-template-menu:displayStyles");
String label = (String)request.getAttribute("liferay-ui:ddm-template-menu:label");
String preferenceName = (String)request.getAttribute("liferay-ui:ddm-template-menu:preferenceName");
String preferenceValue = (String)request.getAttribute("liferay-ui:ddm-template-menu:preferenceValue");
boolean showEmptyOption = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ddm-template-menu:showEmptyOption"));

DDMTemplate ddmTemplate = null;

long ddmTemplateGroupId = PortletDisplayTemplateUtil.getDDMTemplateGroupId(themeDisplay);

if (preferenceValue.startsWith("ddmTemplate_")) {
	ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(ddmTemplateGroupId, preferenceValue);
}

List<DDMTemplate> companyPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getCompanyGroupId(), classNameId, 0);
List<DDMTemplate> groupPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(ddmTemplateGroupId, classNameId, 0);
%>

<aui:select id="displayStyle" label="<%= label %>" name='<%= "preferences--" + preferenceName + "--" %>'>
	<c:if test="<%= showEmptyOption %>">
		<aui:option label="default" selected="<%= Validator.isNull(preferenceValue) %>" />
	</c:if>

	<c:if test="<%= (displayStyles != null) && !displayStyles.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="default" />">

			<%
			for (String displayStyle : displayStyles) {
			%>

				<aui:option label="<%= HtmlUtil.escape(displayStyle) %>" selected="<%= preferenceValue.equals(displayStyle) %>" />

			<%
			}
			%>

		</optgroup>
	</c:if>

	<c:if test="<%= (companyPortletDDMTemplates != null) && !companyPortletDDMTemplates.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="global" />">

			<%
			for (DDMTemplate companyPortletDDMTemplate : companyPortletDDMTemplates) {
				if (!DDMTemplatePermission.contains(permissionChecker, companyPortletDDMTemplate, ActionKeys.VIEW)) {
					continue;
				}
			%>

				<aui:option label="<%= HtmlUtil.escape(companyPortletDDMTemplate.getName(locale)) %>" selected="<%= (ddmTemplate != null) && (companyPortletDDMTemplate.getTemplateId() == ddmTemplate.getTemplateId()) %>" value='<%= "ddmTemplate_" + companyPortletDDMTemplate.getUuid() %>' />

			<%
			}
			%>

		</optgroup>
	</c:if>

	<c:if test="<%= (groupPortletDDMTemplates != null) && !groupPortletDDMTemplates.isEmpty() %>">
		<optgroup label="<%= themeDisplay.getScopeGroupName() %>">

		<%
		for (DDMTemplate groupPortletDDMTemplate : groupPortletDDMTemplates) {
			if (!DDMTemplatePermission.contains(permissionChecker, groupPortletDDMTemplate, ActionKeys.VIEW)) {
				continue;
			}
		%>

			<aui:option label="<%= HtmlUtil.escape(groupPortletDDMTemplate.getName(locale)) %>" selected="<%= (ddmTemplate != null) && (groupPortletDDMTemplate.getTemplateId() == ddmTemplate.getTemplateId()) %>" value='<%= "ddmTemplate_" + groupPortletDDMTemplate.getUuid() %>' />

		<%
		}
		%>

		</optgroup>
	</c:if>
</aui:select>