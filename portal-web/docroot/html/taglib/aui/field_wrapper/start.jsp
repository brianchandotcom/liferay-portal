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

<%@ include file="/html/taglib/aui/field_wrapper/init.jsp" %>

<%
String fieldCss = AUIUtil.buildCss("field-wrapper", false, first, last, cssClass);
%>

<div class="<%= controlGroupCss %><aui:spacer /><%= fieldCss %>" <%= AUIUtil.buildData(data) %>>
	<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
		<label <%= AUIUtil.buildLabel("wrapper", inlineField, showForLabel, name) %>>
			<liferay-ui:message key="<%= label %>" />

			<c:if test="<%= required %>">
				<span class="label-required">(<liferay-ui:message key="required" />)</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>
		</label>
	</c:if>