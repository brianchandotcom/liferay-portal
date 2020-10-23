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

<%@ include file="/publications/init.jsp" %>

<%
CTEntryDiffDisplay ctEntryDiffDisplay = (CTEntryDiffDisplay)request.getAttribute(CTWebKeys.CT_ENTRY_DIFF_DISPLAY);
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<tr class="publications-diff-no-border-top table-divider">
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getLeftTitle()) %></td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td"><%= HtmlUtil.escape(ctEntryDiffDisplay.getRightTitle()) %></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderLeftCTRow();
					%>

				</td>
			</c:if>

			<c:if test="<%= !ctEntryDiffDisplay.isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION) %>">
				<td class="publications-diff-td">

					<%
					ctEntryDiffDisplay.renderRightCTRow();
					%>

				</td>
			</c:if>
		</tr>
	</table>
</div>