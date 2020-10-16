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

<%@ include file="/search_input/init.jsp" %>

<%
PortletURL actionURL = (PortletURL)request.getAttribute("liferay-commerce:search-input:actionURL");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-commerce:search-input:data");
%>

<aui:form action="<%= String.valueOf(actionURL) %>" method="get" name='<%= (String)request.getAttribute("liferay-commerce:search-input:formName") %>'>

	<%
	for (Map.Entry<String, Object> entry : data.entrySet()) {
	%>

		<aui:input name="<%= HtmlUtil.escape(entry.getKey()) %>" type="hidden" useNamespace="<%= false %>" value="<%= entry.getValue() %>" />

	<%
	}
	%>

	<liferay-ui:input-search
		markupView="lexicon"
	/>
</aui:form>