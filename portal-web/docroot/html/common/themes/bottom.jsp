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

<%@ include file="/html/common/themes/init.jsp" %>

<liferay-util:dynamic-include key="/html/common/themes/bottom.jsp#pre" />

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute(WebKeys.LAYOUT_PORTLETS);
%>

<%-- Portlet CSS References --%>

<%@ include file="/html/common/themes/bottom_portlet_resources_css.jspf" %>

<%-- Portlet JavaScript References --%>

<%@ include file="/html/common/themes/bottom_portlet_resources_js.jspf" %>

<c:if test="<%= PropsValues.JAVASCRIPT_LOG_ENABLED %>">
	<%@ include file="/html/common/themes/bottom_js_logging.jspf" %>
</c:if>

<%@ include file="/html/common/themes/bottom_js.jspf" %>

<%@ include file="/html/common/themes/password_expiring_soon.jspf" %>

<%@ include file="/html/common/themes/session_timeout.jspf" %>

<%
ScriptTag.flushScriptData(pageContext);
%>

<%-- Raw Text --%>

<%
com.liferay.petra.string.StringBundler pageBottomSB = OutputTag.getDataSB(request, WebKeys.PAGE_BOTTOM);
%>

<c:if test="<%= pageBottomSB != null %>">

	<%
	pageBottomSB.writeTo(out);
	%>

</c:if>

<%-- Theme JavaScript --%>

<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeJavaScript() + "/main.js")) %>" type="text/javascript"></script>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />
<liferay-util:include page="/html/common/themes/bottom-test.jsp" />

<liferay-util:dynamic-include key="/html/common/themes/bottom.jsp#post" />