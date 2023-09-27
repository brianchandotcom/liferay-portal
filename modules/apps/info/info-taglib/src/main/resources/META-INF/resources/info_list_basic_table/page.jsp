<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info_list_basic_table/init.jsp" %>

<c:if test="<%= ListUtil.isNotEmpty(infoListObjectColumnNames) && ListUtil.isNotEmpty(infoListObjects) %>">
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
</c:if>