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

<%@ include file="/html/taglib/ui/table_iterator/init.jsp" %>

</td>

<c:if test="<%= ((listPos + 1) % rowLength) == 0 %>">
	</tr>

	<c:if test="<%= Validator.isNotNull(rowBreak) %>">
		<tr>
			<td colspan="<%= rowLength * 2 - 1 %>">
				<%= rowBreak %>
			</td>
		</tr>
	</c:if>

	<c:if test="<%= (listPos + 1) < list.size() %>">
		<tr>
	</c:if>
</c:if>

<c:if test="<%= (listPos + 1) < list.size() %>">
	<c:if test="<%= ((listPos % rowLength) + 1) < rowLength %>">
		<td style="padding-left: <%= rowPadding %>px;"></td>
	</c:if>

	<td valign="<%= rowValign %>">
</c:if>