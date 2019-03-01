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

<aui:input name="groupId" type="hidden" value="<%= journalEditArticleDisplayContext.getGroupId() %>" />
<aui:input name="ddmTemplateKey" type="hidden" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : StringPool.BLANK %>" />

<c:if test="<%= ListUtil.isNotEmpty(ddmStructure.getTemplates()) %>">
	<div class="article-template">
		<span class="text-secondary"><liferay-ui:message key="this-template-will-be-used-when-showing-the-content-within-a-widget" /></span>
			<div class="input-group mt-4">
				<aui:input disabled="<%= true %>" label="" name="ddmTemplateName" value='<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : LanguageUtil.get(request, "none") %>' wrapperCssClass="input-group-item mb-0" />

				<c:if test="<%= (ddmTemplate != null) && DDMTemplatePermission.contains(permissionChecker, ddmTemplate, ActionKeys.UPDATE) %>">
					<clay:button
						elementClasses="ml-1 mr-1"
						icon="pencil"
						id='<%= liferayPortletResponse.getNamespace() + "editDDMTemplate" %>'
						size="sm"
						style="secondary"
					/>
				</c:if>

				<c:if test="<%= (article != null) && (ddmTemplate != null) %>">
					<clay:button
						elementClasses="ml-1 mr-1"
						icon="view"
						id='<%= liferayPortletResponse.getNamespace() + "previewWithTemplate" %>'
						size="sm"
						style="secondary"
					/>
				</c:if>
			</div>
		</span>

		<div class="button-holder">
			<aui:button id="selectDDMTemplate" value="select" />
		</div>
	</div>
</c:if>

<aui:script>
	<c:if test="<%= (article != null) && (ddmTemplate != null) %>">
		var previewWithTemplate = document.getElementById('<portlet:namespace />previewWithTemplate');

		if (previewWithTemplate) {
			previewWithTemplate.addEventListener(
				'click',
				function(event) {
					Liferay.Util.openWindow(
						{
							dialog: {
								destroyOnHide: true
							},
							dialogIframe: {
								bodyCssClass: 'dialog-with-footer'
							},
							title: '<liferay-ui:message key="preview" />',
							uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/preview_article_content.jsp" /><portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" /><portlet:param name="articleId" value="<%= String.valueOf(article.getArticleId()) %>" /><portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" /></portlet:renderURL>'
						}
					);
				}
			);
		}
	</c:if>

	var selectDDMTemplateButton = document.getElementById('<portlet:namespace />selectDDMTemplate');

	if (selectDDMTemplateButton) {
		selectDDMTemplateButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true
						},
						eventName: '<portlet:namespace />selectDDMTemplate',
						id: '<portlet:namespace />selectDDMTemplate',
						title: '<%= UnicodeLanguageUtil.get(request, "templates") %>',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_template.jsp" /><portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" /></portlet:renderURL>'
					},
					function(event) {
						var ddmTemplateId = '<%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : 0 %>';

						if (document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value != '') {
							ddmTemplateId = document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value;
						}

						if (ddmTemplateId != event.ddmtemplateid) {
							if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-template-deletes-all-unsaved-content") %>')) {
								document.<portlet:namespace />fm1.<portlet:namespace />ddmTemplateId.value = event.ddmtemplateid;

								submitForm(document.<portlet:namespace />fm1, null, false, false);
							}
						}
					}
				);
			}
		);
	}

	var editDDMTemplateLink = document.getElementById('<portlet:namespace />editDDMTemplate');

	if (editDDMTemplateLink) {
		editDDMTemplateLink.addEventListener(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "editing-the-current-template-deletes-all-unsaved-content") %>')) {
					Liferay.Util.openWindow(
						{
							dialog: {
								destroyOnHide: true
							},
							dialogIframe: {
								bodyCssClass: 'dialog-with-footer'
							},
							id: '<portlet:namespace />editDDMTemplate',
							title: '<%= (ddmTemplate != null) ? HtmlUtil.escape(ddmTemplate.getName(locale)) : StringPool.BLANK %>',

							<portlet:renderURL var="editDDMTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
								<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
								<portlet:param name="closeRedirect" value="<%= currentURL %>" />
								<portlet:param name="ddmTemplateId" value="<%= (ddmTemplate != null) ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK %>" />
							</portlet:renderURL>

							uri: '<%= editDDMTemplateURL %>'
						}
					);
				}
			}
		);
	}
</aui:script>