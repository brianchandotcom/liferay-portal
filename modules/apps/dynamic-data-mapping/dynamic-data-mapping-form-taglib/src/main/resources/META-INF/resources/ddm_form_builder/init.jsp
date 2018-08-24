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
java.lang.Long ddmStructureId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:ddmStructureId")));
java.lang.Long ddmStructureVersionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:ddmStructureVersionId")));
java.lang.String defaultLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-form:ddm-form-builder:defaultLanguageId"));
java.lang.String editingLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-form:ddm-form-builder:editingLanguageId"));
long fieldSetClassNameId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:fieldSetClassNameId")));
java.lang.String refererPortletNamespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-form:ddm-form-builder:refererPortletNamespace"));
boolean showPagination = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-form:ddm-form-builder:showPagination")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-form:ddm-form-builder:dynamicAttributes");
%>

<%@ include file="/ddm_form_builder/init-ext.jspf" %>