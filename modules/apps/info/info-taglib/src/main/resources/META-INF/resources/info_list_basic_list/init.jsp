<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.info.taglib.internal.list.renderer.BasicListInfoListStyle" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponseFactory" %>

<%@ page import="java.util.Objects" %>

<%
String infoListStyleKey = GetterUtil.getString(request.getAttribute("liferay-info:info-list-grid:listStyleKey"));

String listCssClass = "";
String listItemCssClass = "";

if (Objects.equals(infoListStyleKey, BasicListInfoListStyle.BORDERED.getKey())) {
	listCssClass = "list-group";
	listItemCssClass = "list-group-item";
}
else if (Objects.equals(infoListStyleKey, BasicListInfoListStyle.INLINE.getKey())) {
	listCssClass = "d-flex list-inline";
	listItemCssClass = "flex-grow-1";
}
else if (Objects.equals(infoListStyleKey, BasicListInfoListStyle.UNSTYLED.getKey())) {
	listCssClass = "list-unstyled";
}
%>