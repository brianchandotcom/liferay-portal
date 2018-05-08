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

<%@ include file="/edit_form/init.jsp" %>

<%
String fullName = namespace.concat(HtmlUtil.escapeAttribute(name));
%>

<form action="<%= HtmlUtil.escapeAttribute(action) %>" class="container container-no-gutters-sm-down container-view form <%= cssClass %> <%= inlineLabels ? "field-labels-inline" : StringPool.BLANK %>" data-fm-namespace="<%= namespace %>" id="<%= fullName %>" method="<%= method %>" name="<%= fullName %>" <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>>
	<c:if test="<%= !themeDisplay.isStatePopUp() %>">
		<div class="sheet <%= fluid ? StringPool.BLANK : "sheet-lg" %>">
	</c:if>

		<div class="panel-group panel-group-flush">
			<c:if test="<%= Validator.isNotNull(onSubmit) %>">
				<fieldset class="input-container" disabled="disabled">
			</c:if>

			<aui:input name="formDate" type="hidden" value="<%= System.currentTimeMillis() %>" />