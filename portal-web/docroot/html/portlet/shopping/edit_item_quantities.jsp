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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
List<String> names = new ArrayList<String>();
List<String[]> values = new ArrayList<String[]>();

for (int i = 0;; i++) {
	String n = request.getParameter("n" + i);
	String v = request.getParameter("v" + i);

	if ((n == null) || (v == null)) {
		break;
	}

	names.add(n);
	values.add(StringUtil.split(v));
}

int[] repeats = new int[values.size()];

int rowsCount = 1;

for (int i = values.size() - 1; i >= 0; i--) {
	repeats[i] = rowsCount;

	String[] vArray = values.get(i);

	rowsCount *= vArray.length;
}
%>

<aui:form method="post" name="fm">
	<aui:fieldset>
		<liferay-ui:search-container
			headerNames="<%= StringUtil.merge(names) %>"
			iteratorURL="<%= currentURLObj %>"
			total="<%= rowsCount %>"
		>

		<liferay-ui:search-container-results
			results="<%= _getPagePermutations(values, repeats, searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

			<liferay-ui:search-container-row
				className="String[]"
				modelVar="rowValues"
			>

				<%
				for (int i = 0; i < rowValues.length; i++) {
				%>

					<liferay-ui:search-container-column-text
						name="<%= names.get(i) %>"
						value="<%= rowValues[i] %>"
					/>

				<%
				}

				request.setAttribute("start", searchContainer.getStart());
				%>

				<liferay-ui:search-container-column-jsp
					name="quantity"
					path="/html/portlet/shopping/edit_item_quantities_column.jsp"
				/>

			</liferay-ui:search-container-row>

			<aui:script>
				var fieldsQuantities = opener.document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantities.value;
				var itemQuantities = [];

				if (fieldsQuantities) {
					itemQuantities = fieldsQuantities.split(',');
				}

				while (itemQuantities.length < <%= searchContainer.getResultEnd() %>) {
					itemQuantities.push(0);
				}

				<%
				for (int i = searchContainer.getStart(); i < searchContainer.getResultEnd(); i++) {
				%>

					document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantity<%= i %>.value = itemQuantities[<%= i %>];

				<%
				}
				%>

				function <portlet:namespace />setItemQuantities() {

					<%
					for (int i = searchContainer.getStart(); i < searchContainer.getResultEnd(); i++) {
					%>

						itemQuantities.splice(<%= i %>, 1, document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantity<%= i %>.value);

					<%
					}
					%>

					opener.document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantities.value = itemQuantities.join(',');
				}

				function <portlet:namespace />updateItemQuantities() {
					<portlet:namespace />setItemQuantities();

					self.close();
				}

				AUI().all('.taglib-page-iterator li a').each(
					function(node) {
						node.on('click', <portlet:namespace />setItemQuantities);
					}
				);
			</aui:script>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</aui:fieldset>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateItemQuantities();" %>' value="update" />

		<aui:button onClick="self.close();" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateItemQuantities() {
		var itemQuantities = "";

		<%
		for (int i = 0; i < rowsCount; i++) {
		%>

			itemQuantities = itemQuantities + document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantity<%= i %>.value + ",";

		<%
		}
		%>

		opener.document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantities.value = itemQuantities;

		self.close();
	}
</aui:script>

<%!
private List<String[]> _getPagePermutations(List<String[]> values, int[] repeats, int start, int resultEnd) {
	List<String[]> rows = new ArrayList<String[]>(resultEnd - start);

	for (int i = start; i < resultEnd; i++) {
		String[] row = new String[values.size()];

		for (int j = 0; j < row.length; j++) {
			String[] vArray = values.get(j);

			row[j] = vArray[(i / repeats[j]) % vArray.length];
		}

		rows.add(row);
	}

	return rows;
}
%>
