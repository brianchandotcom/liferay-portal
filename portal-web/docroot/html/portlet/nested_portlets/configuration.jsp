<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/nested_portlets/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:fieldset label="layout-template">
		<table border="0" cellpadding="0" cellspacing="10" style="margin-top: 10px;" width="100%">

		<%
		int CELLS_PER_ROW = 4;

		String layoutTemplateId = preferences.getValue("layoutTemplateId", PropsValues.NESTED_PORTLETS_LAYOUT_TEMPLATE_DEFAULT);

		List layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(theme.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

		List unsupportedLayoutTemplates = ListUtil.fromArray(PropsUtil.getArray(PropsKeys.NESTED_PORTLETS_LAYOUT_TEMPLATE_UNSUPPORTED));

		for (int i = 0; i < layoutTemplates.size(); i++) {
			LayoutTemplate curLayoutTemplate = (LayoutTemplate)layoutTemplates.get(i);

			if (unsupportedLayoutTemplates.contains(curLayoutTemplate.getLayoutTemplateId())) {
				layoutTemplates.remove(i);
			}
		}

		for (int i = 0; i < layoutTemplates.size(); i++) {
			LayoutTemplate curLayoutTemplate = (LayoutTemplate)layoutTemplates.get(i);
		%>

			<c:if test="<%= (i % CELLS_PER_ROW) == 0 %>">
				<tr>
			</c:if>

			<td align="center" width="<%= 100 / CELLS_PER_ROW %>%">
				<img onclick="document.getElementById('<portlet:namespace />layoutTemplateId<%= i %>').checked = true;" src="<%= curLayoutTemplate.getStaticResourcePath() %><%= curLayoutTemplate.getThumbnailPath() %>" /><br />

				<aui:input checked="<%= layoutTemplateId.equals(curLayoutTemplate.getLayoutTemplateId()) %>" id='<%= "layoutTemplateId" + i %>' label="<%= curLayoutTemplate.getName() %>" name="preferences--layoutTemplateId--" type="radio" value="<%= curLayoutTemplate.getLayoutTemplateId() %>" />
			</td>

			<c:if test="<%= (i % CELLS_PER_ROW) == (CELLS_PER_ROW - 1) %>">
				</tr>
			</c:if>

		<%
		}
		%>

		</table>
	</aui:fieldset>

	<%
	boolean portletDecorateDefault = GetterUtil.getBoolean(themeDisplay.getThemeSetting("portlet-setup-show-borders-default"), true);

	boolean portletSetupShowBorders = GetterUtil.getBoolean(preferences.getValue("portletSetupShowBorders", String.valueOf(portletDecorateDefault)));
	%>

	<aui:fieldset label="display-settings">
		<aui:input label="show-borders" name="preferences--portletSetupShowBorders--" type="checkbox" value="<%= portletSetupShowBorders %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>