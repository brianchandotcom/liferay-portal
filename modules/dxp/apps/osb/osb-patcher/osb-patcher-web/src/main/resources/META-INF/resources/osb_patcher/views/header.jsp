<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<%
String title = ParamUtil.getString(request, "title");
%>

<c:if test="<%= Validator.isNotNull(title) %>">

	<%
	String backURL = ParamUtil.getString(request, "backURL");
	String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");
	String redirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNull(redirect) && !windowState.equals(LiferayWindowState.POP_UP)) {
		backURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			mvcRenderCommandName
		).buildString();
	}
	%>

	<liferay-ui:header
		backURL="<%= backURL %>"
		title="<%= title %>"
	/>
</c:if>