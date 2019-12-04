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
String displayStyle = ParamUtil.getString(request, "displayStyle");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
String selectEventName = ParamUtil.getString(request, "selectEventName");
%>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace /><%= HtmlUtil.escape(searchContainerId) %>'
	);

	searchContainer.on('rowToggled', function(event) {
		var allSelectedElements = event.elements.allSelectedElements;

		var selectedData = [];

		allSelectedElements.each(function() {
			<c:choose>
				<c:when test='<%= Objects.equals(displayStyle, "list") %>'>
					var row = this.ancestor('tr');
				</c:when>
				<c:otherwise>
					var row = this.ancestor('li');
				</c:otherwise>
			</c:choose>

			var data = row.getDOM().dataset;

			selectedData.push({
				id: data.id,
				name: data.name
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escape(selectEventName) %>',
			{
				data: selectedData.length ? selectedData : null
			}
		);
	});
</aui:script>