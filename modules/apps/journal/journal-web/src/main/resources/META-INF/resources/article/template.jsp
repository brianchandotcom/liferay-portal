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
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
DDMTemplate ddmTemplate = journalEditArticleDisplayContext.getDDMTemplate();
%>

<aui:input name="ddmTemplateKey" type="hidden" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : StringPool.BLANK %>" />

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(ddmStructure.getTemplates()) %>">
		<p class="text-secondary"><liferay-ui:message key="this-template-will-be-used-when-showing-the-content-within-a-widget" /></p>

		<div class="form-group input-group mb-2">
			<div class="input-group-item">
				<input class="field form-control lfr-input-text" id="<portlet:namespace />ddmTemplateName" readonly="readonly" title="<%= LanguageUtil.get(request, "template-name") %>" type="text" value="<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(request, "no-template") %>" />
			</div>

			<c:if test="<%= (article != null) && !article.isNew() && (journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASS_NAME_ID_DEFAULT) %>">
				<div class="input-group-item input-group-item-shrink">
					<clay:button
						displayType="secondary"
						icon="view"
						id='<%= liferayPortletResponse.getNamespace() + "previewWithTemplate" %>'
						type="button"
					/>
				</div>
			</c:if>
		</div>

		<div class="form-group">
			<aui:button id="selectDDMTemplate" value="select" />

			<c:if test="<%= (ddmTemplate != null) && DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE) %>">
				<aui:button id="editDDMTemplate" value="edit" />
			</c:if>

			<c:if test="<%= ddmTemplate != null %>">
				<aui:button id="clearDDMTemplate" value="clear" />
			</c:if>
		</div>

		<liferay-frontend:component
			componentId='<%= liferayPortletResponse.getNamespace() + "selectStructureField" %>'
			context="<%= journalEditArticleDisplayContext.getTemplateComponentContext() %>"
			module="js/article/Template"
		/>
	</c:when>
	<c:otherwise>
		<p class="text-secondary"><liferay-ui:message key="there-are-no-templates" /></p>
	</c:otherwise>
</c:choose>