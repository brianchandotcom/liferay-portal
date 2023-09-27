<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/template_renderer/init.jsp" %>

<%
DDMTemplate portletDisplayDDMTemplate = (DDMTemplate)request.getAttribute("liferay-ddm:template-renderer:portletDisplayDDMTemplate");
%>

<c:if test="<%= portletDisplayDDMTemplate != null %>">
	<%= PortletDisplayTemplateUtil.renderDDMTemplate(request, response, portletDisplayDDMTemplate.getTemplateId(), entries, contextObjects) %>
</c:if>