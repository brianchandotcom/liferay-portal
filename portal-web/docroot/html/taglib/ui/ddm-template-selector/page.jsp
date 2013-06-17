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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %>
<%@ page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-select:classNameId"));
List<String> displayStyles = (List<String>)request.getAttribute("liferay-ui:ddm-template-select:displayStyles");
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:ddm-template-select:icon"), "configuration");
String label = (String)request.getAttribute("liferay-ui:ddm-template-select:label");
String preferenceName = (String)request.getAttribute("liferay-ui:ddm-template-select:preferenceName");
String preferenceValue = (String)request.getAttribute("liferay-ui:ddm-template-select:preferenceValue");
String refreshURL = (String)request.getAttribute("liferay-ui:ddm-template-select:refreshURL");
boolean showEmptyOption = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ddm-template-select:showEmptyOption"));

long ddmTemplateGroupId = PortletDisplayTemplateUtil.getDDMTemplateGroupId(themeDisplay);

Group ddmTemplateGroup = GroupLocalServiceUtil.getGroup(ddmTemplateGroupId);
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

	<%
	DDMTemplate ddmTemplate = null;

	if (preferenceValue.startsWith("ddmTemplate_")) {
		ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(ddmTemplateGroupId, preferenceValue);
	}

	List<DDMTemplate> companyPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getCompanyGroupId(), classNameId, 0);
	%>

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

	<%
	List<DDMTemplate> groupPortletDDMTemplates = null;

	if (ddmTemplateGroupId != themeDisplay.getCompanyGroupId()) {
		groupPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(ddmTemplateGroupId, classNameId, 0);
	}
	%>

	<c:if test="<%= (groupPortletDDMTemplates != null) && !groupPortletDDMTemplates.isEmpty() %>">
		<optgroup label="<%= HtmlUtil.escape(ddmTemplateGroup.getDescriptiveName(locale)) %>">

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

<liferay-ui:icon
	cssClass="manage-display-templates"
	id="selectDDMTemplate"
	image="<%= icon %>"
	label="<%= true %>"
	message='<%= LanguageUtil.format(pageContext, "manage-display-templates-for-x", HtmlUtil.escape(ddmTemplateGroup.getDescriptiveName(locale)), false) %>'
	url="javascript:;"
/>

<aui:script use="aui-base">
	var selectDDMTemplate = A.one('#<portlet:namespace />selectDDMTemplate');

	if (selectDDMTemplate) {
		var windowId = A.guid();

		selectDDMTemplate.on(
			'click',
			function(event) {
				Liferay.Util.openDDMPortlet(
					{
						basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
						classNameId: '<%= classNameId %>',
						dialog: {
							width: 820,
							zIndex: Liferay.zIndex.WINDOW + 2
						},
						groupId: <%= ddmTemplateGroupId %>,
						refererPortletName: '<%= PortletKeys.PORTLET_DISPLAY_TEMPLATES %>',
						struts_action: '/dynamic_data_mapping/view_template',
						title: '<%= UnicodeLanguageUtil.get(pageContext, "application-display-templates") %>'
					},
					function(event) {
						if (!event.newVal) {
							submitForm(document.<portlet:namespace />fm, '<%= refreshURL %>');
						}
					}
				);
			}
		);
	}
</aui:script>