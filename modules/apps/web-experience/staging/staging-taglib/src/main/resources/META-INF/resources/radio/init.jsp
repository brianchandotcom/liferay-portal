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

<%@ include file="/init.jsp" %>

<%
boolean checked = GetterUtil.getBoolean(request.getAttribute("liferay-staging:radio:checked"));
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-staging:radio:disabled"));
String id = GetterUtil.getString(request.getAttribute("liferay-staging:radio:id"));
boolean inline = GetterUtil.getBoolean(request.getAttribute("liferay-staging:radio:inline"));
String labelKey = GetterUtil.getString(request.getAttribute("liferay-staging:radio:label"));
String name = GetterUtil.getString(request.getAttribute("liferay-staging:radio:name"));
String popoverTextKey = GetterUtil.getString(request.getAttribute("liferay-staging:radio:popover"));
String value = GetterUtil.getString(request.getAttribute("liferay-staging:radio:value"));

if (Validator.isNull(id)) {
	id = name;
}

String checkedString = (checked) ? "checked" : "";
String disabledString = (disabled) ? "disabled" : "";
String dataQAID = (name.equals(id)) ? name : name + StringPool.UNDERLINE + id;
String domId = liferayPortletResponse.getNamespace() + id;
String domName = liferayPortletResponse.getNamespace() + name;
String inlineString = (inline) ? "custom-control-inline" : "";
String label = LanguageUtil.get(request, labelKey);
String popoverName = name + "_popover";
String popoverText = (Validator.isNull(popoverTextKey)) ? StringPool.SPACE : LanguageUtil.get(request, popoverTextKey);
%>