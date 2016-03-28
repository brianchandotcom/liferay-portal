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

<%
JournalArticle latestArticle = journalContentDisplayContext.getLatestArticle();

Map<String, Object> data = new HashMap<String, Object>();

data.put("destroyOnHide", true);
data.put("id", HtmlUtil.escape(portletDisplay.getNamespace()) + "editAsset");
data.put("title", HtmlUtil.escape(latestArticle.getTitle(locale)));
%>

<liferay-ui:icon
	data="<%= data %>"
	id="editWebContentIcon"
	message="edit-web-content"
	url="<%= journalContentDisplayContext.getURLEdit() %>"
	useDialog="<%= true %>"
/>