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

<%@ include file="/panel_category/init.jsp" %>

<c:if test="<%= !panelApps.isEmpty() %>">
	<a aria-expanded="false" class="collapse-icon <%= containsActivePortlet ? StringPool.BLANK : "collapsed" %> list-group-heading" data-toggle="collapse" href="#<%= panelPageCategoryId %>">
		<%= panelCategory.getLabel(themeDisplay.getLocale()) %>
	</a>

	<div class="collapse <%= containsActivePortlet ? "in" : StringPool.BLANK %>" id="<%= panelPageCategoryId %>">
			<div class="list-group-item">
</c:if>