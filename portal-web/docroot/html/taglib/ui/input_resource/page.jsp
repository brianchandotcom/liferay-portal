<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/init.jsp" %>

<%
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:id"));
String title = (String)request.getAttribute("liferay-ui:input-resource:title");
String url = (String)request.getAttribute("liferay-ui:input-resource:url");
%>

<input class="form-control lfr-input-resource <%= GetterUtil.getString((String)request.getAttribute("liferay-ui:input-resource:cssClass")) %>" <%= Validator.isNotNull(id) ? "id=\"" + namespace + id + "\"" : StringPool.BLANK %> onClick="this.select();" disabled <%= Validator.isNotNull(title) ? "title=\"" + LanguageUtil.get(resourceBundle, title) + "\"" : StringPool.BLANK %> type="text" value="<%= HtmlUtil.escapeAttribute(url) %>" />

<aui:script>
	var inputField = document.getElementById('<portlet:namespace /><%= id %>');

	inputField.addEventListener(
		'click',
		function() {
			this.setSelectionRange(0, 9999);
		}
	);
</aui:script>