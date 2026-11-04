<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.taglib.portletext.PreviewTag" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponseFactory" %>

<%
PreviewTag.doTag("/preview_portlet_page.jsp", ParamUtil.getString(request, "portletResource"), null, false, null, application, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
%>