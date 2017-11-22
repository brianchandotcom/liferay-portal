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

<%@ include file="/init.jsp" %>

<blockquote><p>A navigation bar, navbar, is an horizontal bar that provides several access points to different parts of a system.</p></blockquote>

<%
List<Map<String, Object>> items = new ArrayList<>();

for (int i = 0; i < 8; i++) {
	Map<String, Object> item = new HashMap<>();

	if (i == 0) {
		item.put("active", true);
	}

	item.put("title", "Page " + i);

	item.put("url", "#" + i);

	items.add(item);
}
%>

<clay:navbar items="<%= items %>" />

<clay:navbar inverted="<%= true %>" items="<%= items %>" />