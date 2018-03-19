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

<blockquote><p>A link (also known as hyperlink) is a clickable (text or image) element. The link control is used for navigation.</p></blockquote>

<h3>SINGLE LINK</h3>

<blockquote><p>Used for stand-alone hyperlinks. Can be a text or an image.</p></blockquote>

<%
Map<String, String> data = new HashMap<>();

data.put("customProperty", "customValue");
%>

<clay:link
	data="<%= data %>"
	href="#"
	label="link text"
/>