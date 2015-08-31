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
String navigation = ParamUtil.getString(request, "navigation", "home");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNull(orderByCol)) {
	orderByCol = portalPreferences.getValue(JournalPortletKeys.JOURNAL, "order-by-col", "modified-date");
	orderByType = portalPreferences.getValue(JournalPortletKeys.JOURNAL, "order-by-type", "asc");
}
else {
	boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

	if (saveOrderBy) {
		portalPreferences.setValue(JournalPortletKeys.JOURNAL, "order-by-col", orderByCol);
		portalPreferences.setValue(JournalPortletKeys.JOURNAL, "order-by-type", orderByType);
	}
}
%>

<li>
	<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="orderByCol">
		<aui:option label="display-date" selected='<%= orderByCol.equals("display-date") %>' />
		<aui:option label="modified-date" selected='<%= orderByCol.equals("modified-date") %>' />
	</aui:select>
</li>

<li class="<%= orderByType.equals("asc") ? "active" : StringPool.BLANK %>">
	<portlet:renderURL var="orderByColAscURL">
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>" />
		<portlet:param name="orderByCol" value="<%= orderByCol %>" />
		<portlet:param name="orderByType" value="asc" />
	</portlet:renderURL>

	<a class="btn hidden-xs" href="<%= orderByColAscURL %>"><span class="icon-caret-up icon-monospaced"></span></a>
</li>

<li class="<%= orderByType.equals("desc") ? "active" : StringPool.BLANK %>">
	<portlet:renderURL var="orderByColDescURL">
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>" />
		<portlet:param name="orderByCol" value="<%= orderByCol %>" />
		<portlet:param name="orderByType" value="desc" />
	</portlet:renderURL>

	<a class="btn hidden-xs" href="<%= orderByColDescURL %>"><span class="icon-caret-down icon-monospaced"></span></a>
</li>

<aui:script>
	<portlet:renderURL var="orderByTypeURL">
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="ddmStructureKey" value="<%= ddmStructureKey %>" />
		<portlet:param name="orderByType" value="<%= orderByType %>" />
	</portlet:renderURL>

	var orderByCol = $('#<portlet:namespace />orderByCol');

	orderByCol.on(
		'change',
		function(event) {
			var uri = '<%= orderByTypeURL %>';

			uri = Liferay.Util.addParams('<portlet:namespace />orderByCol=' + orderByCol.val(), uri);

			location.href = uri;
		}
	);
</aui:script>