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
JournalPreviewArticleContentTemplateDisplayContext journalPreviewArticleContentTemplateDisplayContext = new JournalPreviewArticleContentTemplateDisplayContext(renderRequest, renderResponse);
%>

<aui:form name="previewFm">
	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<ul class="tbar-nav">
			<li class="tbar-item">
				<aui:select label="" name="ddmTemplateId" onChange="previewArticleContentTemplate()" wrapperCssClass="form-group-sm mb-0 ml-4">
					<aui:option label="no-template" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), -1) %>" value="<%= -1 %>" />

					<%
					for (DDMTemplate ddTemplate : journalPreviewArticleContentTemplateDisplayContext.getDDMTemplates()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(ddTemplate.getName(locale)) %>" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), ddTemplate.getTemplateId()) %>" value="<%= ddTemplate.getTemplateId() %>" />

					<%
					}
					%>

				</aui:select>
			</li>
			<li class="tbar-item">
				<div class="journal-article-button-row tbar-section text-right">

					<%
					Map<String, Object> data = new HashMap<>();

					data.put("ddmtemplateid", journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId());
					%>

					<aui:button cssClass="btn-sm selector-button" data="<%= data %>" value="apply" />
				</div>
			</li>
		</ul>
	</nav>
</aui:form>

<%
JournalArticleDisplay articleDisplay = journalPreviewArticleContentTemplateDisplayContext.getArticleDisplay();
%>

<div class="m-4">
	<%= articleDisplay.getContent() %>

	<c:if test="<%= articleDisplay.isPaginate() %>">
		<liferay-ui:page-iterator
			cur="<%= articleDisplay.getCurrentPage() %>"
			curParam="page"
			delta="<%= 1 %>"
			id="articleDisplayPages"
			maxPages="<%= 25 %>"
			portletURL="<%= journalPreviewArticleContentTemplateDisplayContext.getPageIteratorPortletURL() %>"
			total="<%= articleDisplay.getNumberOfPages() %>"
			type="article"
		/>
	</c:if>
</div>

<script>
	function previewArticleContentTemplate() {
		var ddmTemplateId = document.getElementById(
			'<portlet:namespace />ddmTemplateId'
		);

		location.href = Liferay.Util.addParams(
			'<portlet:namespace />ddmTemplateId=' + ddmTemplateId.value,
			'<%= journalPreviewArticleContentTemplateDisplayContext.getPortletURL() %>'
		);
	}

	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />previewFm',
		'<%= HtmlUtil.escapeJS(journalPreviewArticleContentTemplateDisplayContext.getEventName()) %>'
	);
</script>