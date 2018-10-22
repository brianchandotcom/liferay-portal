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