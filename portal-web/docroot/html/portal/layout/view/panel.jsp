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
<%@ include file="/html/portal/init.jsp" %>

<c:if test="<%= !themeDisplay.isStatePopUp() %>">
	<div id="main-content">
		<table class="lfr-panel-page">
		<tr>
			<td class="lfr-top panel-page-menu" width="200">
				<liferay-portlet:runtime portletName="87" />
			</td>
			<td class="lfr-top panel-page-content <%= (!layoutTypePortlet.hasStateMax()) ? "panel-page-frontpage" : "panel-page-application" %>">
</c:if>

<%
if (themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
	String ppid = ParamUtil.getString(request, "p_p_id");

	String velocityTemplateId = null;
	String velocityTemplateContent = null;

	if (themeDisplay.isStatePopUp()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else {
		ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
		velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}

	if (Validator.isNotNull(velocityTemplateId) && Validator.isNotNull(velocityTemplateContent)) {
		RuntimePageUtil.processTemplate(pageContext, ppid, new StringTemplateResource(velocityTemplateId, velocityTemplateContent));
	}
}
else {
	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

	String description = typeSettingsProperties.getProperty("description");

	if (Validator.isNull(description)) {
		description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
	}
%>

	<h2>
		<%= HtmlUtil.escape(layout.getName(locale)) %>
	</h2>

	<div class="alert alert-info">
		<%= HtmlUtil.escape(description) %>
	</div>

<%
}
%>

<c:if test="<%= !themeDisplay.isStatePopUp() %>">
			</td>
		</tr>
		</table>
	</div>
</c:if>

<%@ include file="/html/portal/layout/view/common.jspf" %>