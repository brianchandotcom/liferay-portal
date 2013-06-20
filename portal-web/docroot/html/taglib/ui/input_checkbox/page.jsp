<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-checkbox:cssClass"));
String param = (String)request.getAttribute("liferay-ui:input-checkbox:param");
String id = (String)request.getAttribute("liferay-ui:input-checkbox:id");
Boolean defaultValue = (Boolean)request.getAttribute("liferay-ui:input-checkbox:defaultValue");
String onClick = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-checkbox:onClick"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-checkbox:disabled"));

boolean value = ParamUtil.getBoolean(request, param, defaultValue.booleanValue());

if (Validator.isNull(id)) {
	id = namespace + param;
}
%>

<input id="<%= id %>" name="<%= namespace %><%= param %>" type="hidden" value="<%= value %>" />

<input <%= value ? "checked" : "" %> class="<%= cssClass %>" <%= disabled ? "disabled=\"disabled\"" : "" %> id="<%= id %>Checkbox" name="<%= namespace %><%= param %>Checkbox" type="checkbox" onClick="Liferay.Util.updateCheckboxValue(this); <%= onClick %>" />