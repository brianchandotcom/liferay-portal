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

DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();

List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();

DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();

String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<c:if test="<%= ddmTemplates.size() > 1 %>">
	<div
		class="media template-preview-content"
		data-group-id="<%= (article != null) ? article.getGroupId() : scopeGroupId %>"
		data-structure-id="<%= (ddmStructure != null) ? ddmStructure.getClassNameId() : 0 %>"
		data-structure-key="<%= (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0 %>"
		data-template-id="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : StringPool.BLANK %>"
		data-template-key="<%= ddmTemplate.getTemplateKey() %>"
	>

		<c:if test="<%= Validator.isNotNull(ddmTemplateImageURL) %>">
			<img alt="<%= ddmTemplate.getName(locale) %>" class="media-object pull-left template-image" src="<%= ddmTemplateImageURL %>">
		</c:if>
		<div class="media-body">
			<h2 class="heading4 template-title"><%= ddmTemplate.getName(locale) %></h2>
			<p class="template-description"><%= ddmTemplate.getDescription() %></p>
		</div>
	</div>
</c:if>