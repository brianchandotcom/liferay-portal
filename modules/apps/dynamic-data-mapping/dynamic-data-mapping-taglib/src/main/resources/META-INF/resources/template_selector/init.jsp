<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
java.lang.String className = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:className"));
java.lang.String defaultDisplayStyle = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:defaultDisplayStyle"), com.liferay.petra.string.StringPool.BLANK);
java.lang.String displayStyle = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:template-selector:displayStyleGroupId")));
java.util.List<java.lang.String> displayStyles = (java.util.List<java.lang.String>)request.getAttribute("liferay-ddm:template-selector:displayStyles");
java.lang.String icon = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:icon"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:label"), "display-template");
java.lang.String refreshURL = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-selector:refreshURL"));
boolean showEmptyOption = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-ddm:template-selector:showEmptyOption")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:template-selector:dynamicAttributes");
%>

<%@ include file="/template_selector/init-ext.jspf" %>