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

<%@ include file="/info_list_basic_table/init.jsp" %>

<div class="table-responsive">
	<table class="table table-autofit">
		<thead>
			<tr>

				<%
				for (String infoListObjectColumnName : infoListObjectColumnNames) {
				%>

					<th class="table-cell-expand-smallest">
						<liferay-ui:message key="<%= infoListObjectColumnName %>" />
					</th>

				<%
				}
				%>

			</tr>
		</thead>

		<tbody>

			<%
			for (Object infoListObject : infoListObjects) {
			%>

				<tr>

					<%
					infoItemRenderer.render(infoListObject, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
					%>

				</tr>

			<%
			}
			%>

		</tbody>
	</table>
</div>