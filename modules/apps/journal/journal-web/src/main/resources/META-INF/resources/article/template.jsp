<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<input class="field form-control lfr-input-text lfr-portal-tooltip" id="<portlet:namespace />ddmTemplateName" readonly="readonly" title="<%= LanguageUtil.get(request, "template-name") %>" type="text" value="<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(request, "no-template") %>" />
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
			<clay:button
				displayType="secondary"
				id='<%= liferayPortletResponse.getNamespace() + "selectDDMTemplate" %>'
				label="select"
			/>

			<c:if test="<%= (ddmTemplate != null) && DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE) %>">
				<clay:button
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "editDDMTemplate" %>'
					label="edit"
				/>
			</c:if>

			<c:if test="<%= ddmTemplate != null %>">
				<clay:button
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "clearDDMTemplate" %>'
					label="clear"
				/>
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