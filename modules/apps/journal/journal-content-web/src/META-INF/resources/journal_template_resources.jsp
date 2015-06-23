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
JournalArticle article = journalContentDisplayContext.getArticle();

DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
String templateTitle = ddmTemplate.getName(locale);
String templateDescription = ddmTemplate.getDescription();
String templateImgUrl = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<div class="media template-preview-content">
	<img alt="<%= templateTitle %>" class="media-object pull-left template-image" src="<%= templateImgUrl %>">
	<div class="media-body">
		<h2 class="heading4 template-title"><%= templateTitle %></h2>
		<p class="template-description"><%= templateDescription %></p>
	</div>
</div>