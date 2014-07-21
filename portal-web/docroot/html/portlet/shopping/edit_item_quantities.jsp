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

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String[] fieldsQuantities = StringUtil.split(ParamUtil.getString(request, "fieldsQuantities"));

List<String> names = new ArrayList<String>();
List<String[]> values = new ArrayList<String[]>();

for (int i = 0; i < 9; i++) {
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
		<table border="1" cellpadding="4" cellspacing="0">
		<tr>

			<%
			for (String name : names) {
			%>

				<td>
					<strong><%= HtmlUtil.escape(name) %></strong>
				</td>

			<%
			}
			%>

			<td>
				<strong><liferay-ui:message key="quantity" /></strong>
			</td>
		</tr>

		<%
		List<String[]> pagePermutations = _getPagePermutations(values, repeats, 0, rowsCount);

		for (int i = 0; i < ; i++) {
			String[] pagePermutation = pagePermutations.get(i);
		%>

			<tr>

				<%
				for (String columnValue : pagePermutation) {
				%>

					<td>
						<%= HtmlUtil.escape(columnValue) %>
					</td>

				<%
				}

				int fieldsQuantity = 0;

				if (i < fieldsQuantities.length) {
					fieldsQuantity = GetterUtil.getInteger(fieldsQuantities[i]);
				}
				%>

				<td>
					<aui:input label="" name='<%= "fieldsQuantity" + i %>' size="4" title="quantity" type="text" value="<%= fieldsQuantity %>" />
				</td>
			</tr>

		<%
		}
		%>

		</table>
	</aui:fieldset>

	<aui:button-row>
		<aui:button onClick='<%= renderResponse.getNamespace() + "updateItemQuantities();" %>' value="update" />

		<aui:button onClick="self.close();" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateItemQuantities() {
		var itemQuantities = '';

		<%
		for (int i = 0; i < rowsCount; i++) {
		%>

			itemQuantities = itemQuantities + document.<portlet:namespace />fm.<portlet:namespace />fieldsQuantity<%= i %>.value + ',';

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