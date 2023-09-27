<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
java.lang.Long ddmFormInstanceId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:ddmFormInstanceId")));
java.lang.Long ddmFormInstanceRecordId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:ddmFormInstanceRecordId")));
java.lang.Long ddmFormInstanceRecordVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:ddmFormInstanceRecordVersionId")));
java.lang.Long ddmFormInstanceVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:ddmFormInstanceVersionId")));
java.lang.String namespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-form:ddm-form-renderer:namespace"));
boolean showFormBasicInfo = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:showFormBasicInfo")), true);
boolean showSubmitButton = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-renderer:showSubmitButton")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-form:ddm-form-renderer:dynamicAttributes");
%>

<%@ include file="/ddm_form_renderer/init-ext.jspf" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>