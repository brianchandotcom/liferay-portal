<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	searchContainer.on('rowToggled', (event) => {
		var allSelectedElements = event.elements.allSelectedElements;

		var selectedData = [];

		allSelectedElements.each(function () {
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
				name: data.name,
			});
		});

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escape(selectEventName) %>',
			{
				data: selectedData.length ? selectedData : null,
			}
		);
	});
</aui:script>