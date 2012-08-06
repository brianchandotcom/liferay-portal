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
<%@ page import="com.liferay.portlet.portletdisplaytemplates.util.PortletDisplayTemplatesUtil" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-menu:classNameId"));
List<String> defaultOptions = (List<String>)request.getAttribute("liferay-ui:ddm-template-menu:defaultOptions");
String label = (String)request.getAttribute("liferay-ui:ddm-template-menu:label");
String preferenceName = (String)request.getAttribute("liferay-ui:ddm-template-menu:preferenceName");
String preferenceValue = (String)request.getAttribute("liferay-ui:ddm-template-menu:preferenceValue");
boolean showDefaultOption = (Boolean)request.getAttribute("liferay-ui:ddm-template-menu:showDefaultOption");

DDMTemplate template = null;

long templateGroupId = PortletDisplayTemplatesUtil.getDDMTemplateGroupId(themeDisplay);

if (preferenceValue.startsWith("ddmTemplate_")) {
	template = PortletDisplayTemplatesUtil.fetchDDMTemplate(templateGroupId, preferenceValue);
}

List<DDMTemplate> companyPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getCompanyGroupId(), classNameId, 0);

List<DDMTemplate> groupPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(templateGroupId, classNameId, 0);
%>

<aui:select id="displayStyle" label="<%= label %>" name='<%= "preferences--" + preferenceName + "--" %>'>
	<c:if test="<%= showDefaultOption %>">
		<aui:option label="default" selected="<%= Validator.isNull(preferenceValue) %>" />
	</c:if>

	<c:if test="<%= (defaultOptions != null) && !defaultOptions.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="default" />">

			<%
			for (String curDisplayStyle : defaultOptions) {
			%>

				<aui:option label="<%= HtmlUtil.escape(curDisplayStyle) %>" selected="<%= preferenceValue.equals(curDisplayStyle) %>" />

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

				<aui:option label="<%= HtmlUtil.escape(companyPortletDDMTemplate.getName(locale)) %>" selected="<%= (template != null) && (companyPortletDDMTemplate.getTemplateId() == template.getTemplateId()) %>" value='<%= "ddmTemplate_" + companyPortletDDMTemplate.getUuid() %>' />

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

			<aui:option label="<%= HtmlUtil.escape(groupPortletDDMTemplate.getName(locale)) %>" selected="<%= (template != null) && (groupPortletDDMTemplate.getTemplateId() == template.getTemplateId()) %>" value='<%= "ddmTemplate_" + groupPortletDDMTemplate.getUuid() %>' />

		<%
		}
		%>

		</optgroup>
	</c:if>
</aui:select>