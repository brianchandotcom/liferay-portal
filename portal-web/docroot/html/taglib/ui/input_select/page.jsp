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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-select:cssClass"));
String param = (String)request.getAttribute("liferay-ui:input-select:param");
Boolean defaultValue = (Boolean)request.getAttribute("liferay-ui:input-select:defaultValue");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-select:disabled"));

boolean value = ParamUtil.getBoolean(request, param, defaultValue.booleanValue());
%>

<select class="form-control <%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> name="<%= namespace %><%= param %>">
	<option <%= (value) ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
	<option <%= (!value) ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
</select>