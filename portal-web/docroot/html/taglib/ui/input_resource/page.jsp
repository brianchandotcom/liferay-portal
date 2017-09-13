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
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:cssClass"));
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:id"));
String title = (String)request.getAttribute("liferay-ui:input-resource:title");
String url = (String)request.getAttribute("liferay-ui:input-resource:url");
%>

<input class="form-control form-text lfr-input-resource <%= cssClass %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "\"" : StringPool.BLANK %> onClick="this.select();" readonly="true" <%= Validator.isNotNull(title) ? "title=\"" + LanguageUtil.get(resourceBundle, title) + "\"" : StringPool.BLANK %> type="text" value="<%= HtmlUtil.escapeAttribute(url) %>" />

<aui:script>
	var inputField = document.getElementById('<portlet:namespace /><%= id %>');

	inputField.addEventListener(
		'click',
		function() {
			this.setSelectionRange(0, 9999);
		}
	);
</aui:script>