<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FragmentRendererController fragmentRendererController = (FragmentRendererController)request.getAttribute(FragmentRendererController.class.getName());

RenderFragmentEntryDisplayContext renderFragmentEntryDisplayContext = new RenderFragmentEntryDisplayContext(request, liferayPortletRequest);
%>

<%= fragmentRendererController.render(renderFragmentEntryDisplayContext.getDefaultFragmentRendererContext(), request, response) %>