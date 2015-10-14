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

String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);

Map<String, Object> cardData = new HashMap<String, Object>();
cardData.put("change-enabled", ddmTemplates.size() > 1);
cardData.put("group-id", (article != null) ? article.getGroupId() : scopeGroupId);
cardData.put("structure-id", (ddmStructure != null) ? ddmStructure.getClassNameId() : 0);
cardData.put("structure-key", (ddmStructure != null) ? ddmStructure.getPrimaryKey() : 0);
cardData.put("template-id", (ddmTemplate != null) ? ddmTemplate.getTemplateId() : StringPool.BLANK);
cardData.put("template-key", ddmTemplate.getTemplateKey());
%>

<liferay-frontend:card
	cssClass="template-preview-content"
	data="<%= cardData %>"
	horizontal="<%= true %>"
	imageCSSClass='<%= Validator.isNotNull(ddmTemplateImageURL) ? "icon-monospaced" : StringPool.BLANK %>'
	imageUrl="<%= ddmTemplateImageURL %>"
	title="<%= ddmTemplate.getName(locale) %>"
/>