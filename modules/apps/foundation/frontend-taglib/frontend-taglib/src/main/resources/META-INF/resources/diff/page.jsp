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

<%@ include file="/diff/init.jsp" %>

<%
String sourceName = (String)request.getAttribute("liferay-frontend:diff:sourceName");
String targetName = (String)request.getAttribute("liferay-frontend:diff:targetName");
List<DiffResult>[] diffResults = (List<DiffResult>[])request.getAttribute("liferay-frontend:diff:diffResults");

List<DiffResult> sourceResults = diffResults[0];
List<DiffResult> targetResults = diffResults[1];
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= !sourceResults.isEmpty() %>">
			<table class="table table-bordered table-hover table-striped" id="taglib-diff-results">
				<tr>
					<td>
						<%= HtmlUtil.escape(sourceName) %>
					</td>
					<td>
						<%= HtmlUtil.escape(targetName) %>
					</td>
				</tr>

				<%
				for (int i = 0; i < sourceResults.size(); i++) {
					DiffResult sourceResult = sourceResults.get(i);
					DiffResult targetResult = targetResults.get(i);
				%>

					<tr>
						<th class="table-header">
							<liferay-ui:message key="line" /> <%= sourceResult.getLineNumber() %>
						</th>
						<th class="table-header">
							<liferay-ui:message key="line" /> <%= targetResult.getLineNumber() %>
						</th>
					</tr>
					<tr>
						<td width="50%">
							<table class="taglib-diff-table">

								<%
								for (String changedLine : sourceResult.getChangedLines()) {
								%>

									<tr class="lfr-top">
										<%= _processColumn(changedLine) %>
									</tr>

								<%
								}
								%>

							</table>
						</td>
						<td class="lfr-top" width="50%">
							<table class="taglib-diff-table">

								<%
								for (String changedLine : targetResult.getChangedLines()) {
								%>

									<tr class="lfr-top">
										<%= _processColumn(changedLine) %>
									</tr>

								<%
								}
								%>

							</table>
						</td>
					</tr>

				<%
				}
				%>

			</table>
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(sourceName), HtmlUtil.escape(targetName)} %>" key="there-are-no-differences-between-x-and-x" translateArguments="<%= false %>" />
		</c:otherwise>
	</c:choose>
</div>

<%!
private static String _processColumn(String changedLine) {
	changedLine = StringUtil.replace(changedLine, ' ', "&nbsp;");
	changedLine = StringUtil.replace(changedLine, '\t', "&nbsp;&nbsp;&nbsp;");

	String column = "<td>" + changedLine + "</td>";

	if (changedLine.equals(StringPool.BLANK)) {
		return "<td>&nbsp;</td>";
	}
	else if (changedLine.equals(Diff.CONTEXT_LINE)) {
		return "<td class=\"taglib-diff-context\">&nbsp;</td>";
	}
	else if (changedLine.equals(Diff.OPEN_INS + Diff.CLOSE_INS)) {
		return "<td class=\"taglib-diff-addedline\">&nbsp;</td>";
	}
	else if (changedLine.equals(Diff.OPEN_DEL + Diff.CLOSE_DEL)) {
		return "<td class=\"taglib-diff-deletedline\">&nbsp;</td>";
	}

	return column;
}
%>