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
DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
List<DDMTemplate> ddmTemplates = journalContentDisplayContext.getDDMTemplates();
String ddmTemplateImageURL = ddmTemplate.getTemplateImageURL(themeDisplay);
%>

<c:choose>
	<c:when test="<%= journalContentDisplayContext.isDefaultTemplate() %>">
		<p class="text-muted"><liferay-ui:message key="web-content's-default-template" /> <liferay-ui:icon-help message="to-change-web-content's-default-template-you-have-to-edit-the-web-content" /></p>
	</c:when>
	<c:otherwise>
		<p class="text-muted"><liferay-ui:message key="web-content-display-template" /></p>
	</c:otherwise>
</c:choose>

<liferay-frontend:horizontal-card
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

<c:if test="<%= ddmTemplates.size() > 1 %>">
	<div class="button-holder template-preview-button">
		<aui:button cssClass="select-template" value="change" />

		<c:if test="<%= !journalContentDisplayContext.isDefaultTemplate() %>">
			<liferay-ui:message key="or" /> <aui:a cssClass="change-template" href="javascript:;" label="default-template" />
		</c:if>
	</div>
</c:if>