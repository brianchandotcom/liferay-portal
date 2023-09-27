<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/icon_options/init.jsp" %>

<%
List<PortletConfigurationIcon> portletConfigurationIcons = (List<PortletConfigurationIcon>)request.getAttribute("liferay-frontend:icon-options:portletConfigurationIcons");
%>

<clay:dropdown-menu
	aria-label='<%= LanguageUtil.get(request, "options") %>'
	borderless="<%= true %>"
	cssClass="component-action portlet-options"
	displayType="secondary"
	dropdownItems='<%= (List<DropdownItem>)request.getAttribute("liferay-frontend:icon-options:dropdownItems") %>'
	icon="ellipsis-v"
	monospaced='<%= (boolean)request.getAttribute("liferay-frontend:icon-options:monospaced") %>'
	propsTransformer="icon_options/js/PortletOptionsDropdownPropsTransformer"
	small="<%= true %>"
	title='<%= LanguageUtil.get(request, "options") %>'
/>

<%
for (PortletConfigurationIcon portletConfigurationIcon : portletConfigurationIcons) {
	portletConfigurationIcon.include(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
%>