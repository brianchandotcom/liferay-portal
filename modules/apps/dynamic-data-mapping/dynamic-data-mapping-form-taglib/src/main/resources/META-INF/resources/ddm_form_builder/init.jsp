<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String defaultLanguageId = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:defaultLanguageId"));
String editingLanguageId = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:editingLanguageId"));
String refererPortletNamespace = GetterUtil.getString((String)request.getAttribute("liferay-form:ddm-form-builder:refererPortletNamespace"));
boolean showPagination = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:showPagination")), true);
%>

<%@ include file="/ddm_form_builder/init-ext.jspf" %>