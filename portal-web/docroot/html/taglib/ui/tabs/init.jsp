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

// Names

String[] names = (String[])request.getAttribute("liferay-ui:tabs:names");
String namesJS = JS.toScript(names);

// Values

String[] values = (String[])request.getAttribute("liferay-ui:tabs:values");

if ((values == null) || (values.length < names.length)) {
	values = names;
}

// Form name

String formName = (String)request.getAttribute("liferay-ui:tabs:formName");

// Param

String param = (String)request.getAttribute("liferay-ui:tabs:param");

// Value

String value = (String)request.getAttribute("liferay-ui:tabs:value");

if (value == null) {
	value = ParamUtil.getString(request, param, values[0]);
}
%>