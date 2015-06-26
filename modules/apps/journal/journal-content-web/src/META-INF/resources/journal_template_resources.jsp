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

String groupId = article != null ? String.valueOf(article.getGroupId()) : String.valueOf(scopeGroupId);

DDMStructure ddmStructure = journalContentDisplayContext.getDDMStructure();
String ddmStructureId = ddmStructure != null ? String.valueOf(ddmStructure.getClassNameId()) : "0";
String ddmStructureKey = ddmStructure != null ? String.valueOf(ddmStructure.getPrimaryKey()) : "0";

List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
String ddmTemplateId = ddmTemplate != null ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK;
String ddmTemplateKey = ddmTemplate.getTemplateKey();
String templateTitle = ddmTemplate.getName(locale);
String templateDescription = ddmTemplate.getDescription();
String templateImgUrl = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<c:if test="<%= ddmTemplates.size() > 1 %>">
	<div class="media template-preview-content" data-group-id="<%= groupId %>" data-structure-id="<%= ddmStructureId %>" data-structure-key="<%= ddmStructureKey %>" data-template-id="<%= ddmTemplateId %>" data-template-key="<%= ddmTemplateKey %>">
		<c:if test="<%= templateImgUrl != null %>">
			<img alt="<%= templateTitle %>" class="media-object pull-left template-image" src="<%= templateImgUrl %>">
		</c:if>
		<div class="media-body">
			<h2 class="heading4 template-title"><%= templateTitle %></h2>
			<p class="template-description"><%= templateDescription %></p>
		</div>
	</div>
</c:if>