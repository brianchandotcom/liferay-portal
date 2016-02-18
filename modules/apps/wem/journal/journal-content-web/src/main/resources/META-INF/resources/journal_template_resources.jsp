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
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();

Map<String, Object> data = new HashMap<String, Object>();

data.put("change-enabled", ddmTemplates.size() > 1);
data.put("group-id", (article != null) ? article.getGroupId() : scopeGroupId);
data.put("structure-id", (ddmStructure != null) ? ddmStructure.getClassNameId() : 0);
data.put("structure-key", (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0);
data.put("template-id", (ddmTemplate != null) ? ddmTemplate.getTemplateId() : StringPool.BLANK);
data.put("template-key", ddmTemplate.getTemplateKey());

String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<liferay-frontend:horizontal-card
	cssClass="template-preview-content"
	data="<%= data %>"
	text="<%= ddmTemplate.getName(locale) %>"
>
	<liferay-frontend:horizontal-card-col>
		<c:choose>
			<c:when test="<%= Validator.isNotNull(ddmTemplateImageURL) %>">
				<img alt="" class="<%= Validator.isNotNull(ddmTemplateImageURL) ? "icon-monospaced" : StringPool.BLANK %>" src="<%= ddmTemplateImageURL %>" />
			</c:when>
			<c:otherwise>
				<liferay-frontend:horizontal-card-icon
					icon="edit-layout"
				/>
			</c:otherwise>
		</c:choose>
	</liferay-frontend:horizontal-card-col>
</liferay-frontend:horizontal-card>