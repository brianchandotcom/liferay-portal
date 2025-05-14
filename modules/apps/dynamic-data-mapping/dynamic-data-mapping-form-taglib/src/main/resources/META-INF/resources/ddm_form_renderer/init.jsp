<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
Long ddmFormInstanceId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:ddmFormInstanceId")));
boolean showFormBasicInfo = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:showFormBasicInfo")), true);
%>

<%@ include file="/ddm_form_renderer/init-ext.jspf" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>