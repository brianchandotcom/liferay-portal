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
ViewEntryDisplayContext viewEntryDisplayContext = (ViewEntryDisplayContext)request.getAttribute(CTWebKeys.VIEW_ENTRY_DISPLAY_CONTEXT);
%>

<div class="publications-diff-table-wrapper">
	<table class="table table-autofit">
		<tr class="publications-diff-no-border-top table-divider">
			<td class="publications-diff-td"><%= HtmlUtil.escape(viewEntryDisplayContext.getDividerTitle(resourceBundle)) %></td>
		</tr>
		<tr>
			<td class="publications-diff-td">

				<%
				viewEntryDisplayContext.renderEntry(request, response);
				%>

			</td>
		</tr>
	</table>
</div>