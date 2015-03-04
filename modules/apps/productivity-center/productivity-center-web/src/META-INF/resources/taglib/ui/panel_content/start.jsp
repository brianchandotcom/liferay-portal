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

<%@ include file="init.jsp" %>

<%
String portletId = (String)request.getAttribute("productivity-center-ui:panel-content:portletId");

Portlet portlet = null;

if (Validator.isNotNull(portletId)) {
	portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);
}

if (portlet != null) {
	String velocityTemplateId = null;

	String content = null;

	if (themeDisplay.isStatePopUp()) {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";

		content = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
	}
	else if (layoutTypePortlet.hasStateMax()) {
		PortletURL portletURL = PortletURLFactoryUtil.create(request, portletId, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		portletId = StringUtil.split(layoutTypePortlet.getStateMax())[0];

		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}
	else {
		velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";

		content = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
	}

	StringBundler sb = null;

	if (Validator.isNotNull(velocityTemplateId) && Validator.isNotNull(content)) {
		sb = RuntimePageUtil.getProcessedTemplate(request, response, portletId, new StringTemplateResource(velocityTemplateId, content));
	}

	if (sb != null) {
		sb.writeTo(pageContext.getOut());
	}
}
else {
%>

<div class="portlet-msg-info">
	<liferay-ui:message key="please-select-a-tool-from-the-left-menu" />
</div>

<%
}
%>