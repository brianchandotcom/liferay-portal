<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<div class="input-group">
		<div class="input-group-item">
			<input aria-label="<%= LanguageUtil.get(request, "search") %>" class="form-control input-group-inset input-group-inset-after search-query" data-qa-id="searchInput" id="<portlet:namespace />keywords" name="<portlet:namespace />keywords" placeholder="<%= LanguageUtil.get(request, "search") %>" title="<%= LanguageUtil.get(request, "search") %>" type="text" value="<%= HtmlUtil.escapeAttribute(ParamUtil.getString(request, "keywords")) %>" />

			<div class="input-group-inset-item input-group-inset-item-after">
				<clay:button
					data-qa-id="searchButton"
					icon="search"
					displayType="unstyled"
					monospaced="<%= false %>"
					type="submit"
				/>
			</div>
		</div>
	</div>
</aui:form>