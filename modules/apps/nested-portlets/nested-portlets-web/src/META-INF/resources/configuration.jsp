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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset label="layout-template">
		<table border="0" cellpadding="0" cellspacing="10" style="margin-top: 10px;" width="100%">

		<%
		int CELLS_PER_ROW = 4;

		String layoutTemplateId = portletPreferences.getValue("layoutTemplateId", nestedPortletsConfiguration.getLayoutTemplateDefault());

		List<LayoutTemplate> layoutTemplates = LayoutTemplateLocalServiceUtil.getLayoutTemplates(theme.getThemeId());

		layoutTemplates = PluginUtil.restrictPlugins(layoutTemplates, user);

		List<String> unsupportedLayoutTemplates = ListUtil.fromArray(nestedPortletsConfiguration.getLayoutTemplatesUnsupported());

		int i = 0;

		for (LayoutTemplate layoutTemplate : layoutTemplates) {
			if (!unsupportedLayoutTemplates.contains(layoutTemplate.getLayoutTemplateId())) {
		%>

				<c:if test="<%= (i % CELLS_PER_ROW) == 0 %>">
					<tr>
				</c:if>

				<td align="center" width="<%= 100 / CELLS_PER_ROW %>%">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" onclick="document.getElementById('<portlet:namespace />layoutTemplateId<%= i %>').checked = true;" src="<%= layoutTemplate.getStaticResourcePath() %><%= HtmlUtil.escapeAttribute(layoutTemplate.getThumbnailPath()) %>" /><br />

					<aui:input checked="<%= layoutTemplateId.equals(layoutTemplate.getLayoutTemplateId()) %>" id='<%= "layoutTemplateId" + i %>' label="<%= layoutTemplate.getName(locale) %>" name="preferences--layoutTemplateId--" type="radio" value="<%= layoutTemplate.getLayoutTemplateId() %>" />
				</td>

				<c:if test="<%= (i % CELLS_PER_ROW) == (CELLS_PER_ROW - 1) %>">
					</tr>
				</c:if>

		<%
				i++;
			}
		}
		%>

		</table>
	</aui:fieldset>

	<%
	boolean portletDecorateDefault = GetterUtil.getBoolean(themeDisplay.getThemeSetting("portlet-setup-show-borders-default"), true);

	boolean portletSetupShowBorders = GetterUtil.getBoolean(portletPreferences.getValue("portletSetupShowBorders", String.valueOf(portletDecorateDefault)));
	%>

	<aui:fieldset label="display-settings">
		<aui:input label="show-borders" name="preferences--portletSetupShowBorders--" type="checkbox" value="<%= portletSetupShowBorders %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>