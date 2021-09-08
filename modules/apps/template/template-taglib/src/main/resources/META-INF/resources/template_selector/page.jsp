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

<%@ include file="/template_selector/init.jsp" %>

<%
String displayStyle = (String)request.getAttribute("liferay-template:template-selector:displayStyle");
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-template:template-selector:displayStyleGroupId")));
List<DDMTemplate> ddmTemplates = (List<DDMTemplate>)request.getAttribute("liferay-template:template-selector:ddmTemplates");
List<String> displayStyles = (List<String>)request.getAttribute("liferay-template:template-selector:displayStyles");
DDMTemplate portletDisplayDDMTemplate = (DDMTemplate)request.getAttribute("liferay-template:template-selector:portletDisplayDDMTemplate");
%>

<clay:content-row
	floatElements=""
	verticalAlign="center"
>
	<clay:content-col
		cssClass="inline-item-before"
	>
		<aui:input id="displayStyleGroupId" name="preferences--displayStyleGroupId--" type="hidden" value="<%= String.valueOf(displayStyleGroupId) %>" />

		<aui:select id="displayStyle" label="display-template" name="preferences--displayStyle--" wrapperCssClass="c-mb-4">
			<c:if test='<%= GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-template:template-selector:showEmptyOption"))) %>'>
				<aui:option label="default" selected="<%= Validator.isNull(displayStyle) %>" />
			</c:if>

			<c:if test="<%= !ListUtil.isEmpty(displayStyles) %>">
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
			for (DDMTemplate ddmTemplate : ddmTemplates) {
			%>

				<aui:option
					data='<%=
						HashMapBuilder.<String, Object>put(
							"displaystylegroupid", ddmTemplate.getGroupId()
						).build()
					%>'
					label="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>"
					selected="<%= (portletDisplayDDMTemplate != null) && (ddmTemplate.getTemplateId() == portletDisplayDDMTemplate.getTemplateId()) %>"
					value="<%= PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + HtmlUtil.escape(ddmTemplate.getTemplateKey()) %>"
				/>

			<%
			}
			%>

		</aui:select>
	</clay:content-col>
</clay:content-row>

<aui:script sandbox="<%= true %>">
	var displayStyle = document.getElementById('<portlet:namespace />displayStyle');
	var displayStyleGroupIdInput = document.getElementById(
		'<portlet:namespace />displayStyleGroupId'
	);

	if (displayStyle && displayStyleGroupIdInput) {
		displayStyle.addEventListener('change', (event) => {
			var selectedDisplayStyle = displayStyle.querySelector('option:checked');

			if (selectedDisplayStyle) {
				var displayStyleGroupId =
					selectedDisplayStyle.dataset.displaystylegroupid;

				if (displayStyleGroupId) {
					displayStyleGroupIdInput.value = displayStyleGroupId;
				}
			}
		});
	}
</aui:script>