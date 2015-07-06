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

<%@ include file="/html/taglib/ui/ddm_template_selector/init.jsp" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-select:classNameId"));
String displayStyle = (String)request.getAttribute("liferay-ui:ddm-template-select:displayStyle");
long displayStyleGroupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-select:displayStyleGroupId"));
List<String> displayStyles = (List<String>)request.getAttribute("liferay-ui:ddm-template-select:displayStyles");
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:ddm-template-select:icon"), "icon-cog");
String label = (String)request.getAttribute("liferay-ui:ddm-template-select:label");
DDMTemplate portletDisplayDDMTemplate = (DDMTemplate)request.getAttribute("liferay-ui:ddm-template-select:portletDisplayDDMTemplate");
String refreshURL = (String)request.getAttribute("liferay-ui:ddm-template-select:refreshURL");
boolean showEmptyOption = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ddm-template-select:showEmptyOption"));

long ddmTemplateGroupId = PortletDisplayTemplateUtil.getDDMTemplateGroupId(themeDisplay.getScopeGroupId());

Group ddmTemplateGroup = GroupLocalServiceUtil.getGroup(ddmTemplateGroupId);
%>

<aui:input id="displayStyleGroupId" name="preferences--displayStyleGroupId--" type="hidden" value="<%= String.valueOf(displayStyleGroupId) %>" />

<aui:select id="displayStyle" inlineField="<%= true %>" label="<%= label %>" name="preferences--displayStyle--">
	<c:if test="<%= showEmptyOption %>">
		<aui:option label="default" selected="<%= Validator.isNull(displayStyle) %>" />
	</c:if>

	<c:if test="<%= (displayStyles != null) && !displayStyles.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="default" />">

			<%
			for (String curDisplayStyle : displayStyles) {
			%>

				<aui:option label="<%= HtmlUtil.escape(curDisplayStyle) %>" selected="<%= displayStyle.equals(curDisplayStyle) %>" />

			<%
			}
			%>

		</optgroup>
	</c:if>

	<%
	for (DDMTemplate curDDMTemplate : DDMTemplateLocalServiceUtil.getTemplates(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), classNameId, 0L)) {
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("displaystylegroupid", curDDMTemplate.getGroupId());

		if (!DDMTemplatePermission.contains(permissionChecker, scopeGroupId, curDDMTemplate, PortletKeys.PORTLET_DISPLAY_TEMPLATE, ActionKeys.VIEW)) {
			continue;
		}
	%>

		<aui:option data="<%= data %>" label="<%= HtmlUtil.escape(curDDMTemplate.getName(locale)) %>" selected="<%= (portletDisplayDDMTemplate != null) && (curDDMTemplate.getTemplateId() == portletDisplayDDMTemplate.getTemplateId()) %>" value="<%= PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + curDDMTemplate.getTemplateKey() %>" />

	<%
	}
	%>

</aui:select>

<liferay-ui:icon
	iconCssClass="<%= icon %>"
	id="selectDDMTemplate"
	label="<%= true %>"
	message='<%= LanguageUtil.format(request, "manage-display-templates-for-x", HtmlUtil.escape(ddmTemplateGroup.getDescriptiveName(locale)), false) %>'
	url="javascript:;"
/>

<liferay-portlet:renderURL plid="<%= themeDisplay.getPlid() %>" portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="basePortletURL">
	<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
</liferay-portlet:renderURL>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />selectDDMTemplate').on(
		'click',
		function(event) {
			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= basePortletURL %>',
					classNameId: '<%= classNameId %>',
					dialog: {
						width: 1024
					},
					groupId: <%= ddmTemplateGroupId %>,
					mvcPath: '/view_template.jsp',
					refererPortletName: '<%= PortletKeys.PORTLET_DISPLAY_TEMPLATE %>',
					title: '<%= UnicodeLanguageUtil.get(request, "application-display-templates") %>'
				},
				function(event) {
					if (!event.newVal) {
						submitForm(document.<portlet:namespace />fm, '<%= refreshURL %>');
					}
				}
			);
		}
	);

	var displayStyleGroupIdInput = $('#<portlet:namespace />displayStyleGroupId');

	var displayStyle = $('#<portlet:namespace />displayStyle');

	displayStyle.on(
		'change',
		function(event) {
			var displayStyleGroupId = displayStyle.find(':selected').data('displaystylegroupid');

			if (displayStyleGroupId) {
				displayStyleGroupIdInput.val(displayStyleGroupId);
			}
		}
	);
</aui:script>