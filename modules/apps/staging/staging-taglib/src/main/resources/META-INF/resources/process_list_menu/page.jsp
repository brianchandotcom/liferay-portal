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

<%@ include file="/process_list_menu/init.jsp" %>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= relaunchMenu %>">
		<%@ include file="/process_list_menu/items/relaunch.jspf" %>
	</c:if>

	<c:if test="<%= deleteMenu %>">
		<%@ include file="/process_list_menu/items/delete.jspf" %>
	</c:if>

	<c:if test="<%= summaryMenu %>">
		<%@ include file="/process_list_menu/items/summary.jspf" %>
	</c:if>
</liferay-ui:icon-menu>