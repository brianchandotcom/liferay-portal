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

<nav class="component-tbar subnav-tbar-light tbar tbar-article">
	<ul class="tbar-nav">
		<li class="tbar-item">
			<aui:select label="" name="ddmTemplateKey" wrapperCssClass="mb-0 ml-4">
				<aui:option label="no-template" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateKey(), StringPool.BLANK) %>" value="<%= StringPool.BLANK %>" />

				<%
				for (DDMTemplate ddTemplate : journalPreviewArticleContentTemplateDisplayContext.getDDMTemplates()) {
				%>

					<aui:option label="<%= HtmlUtil.escape(ddTemplate.getName(locale)) %>" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateKey(), ddTemplate.getTemplateKey()) %>" value="<%= ddTemplate.getTemplateKey() %>" />

				<%
				}
				%>

			</aui:select>
		</li>
		<li class="tbar-item">
			<div class="journal-article-button-row tbar-section text-right">
				<aui:button name="applyButton" onClick="previewArticleContentTemplate()" value="apply" />
			</div>
		</li>
	</ul>
</nav>

<%
JournalArticleDisplay articleDisplay = journalPreviewArticleContentTemplateDisplayContext.getArticleDisplay();
%>

<div class="m-4">
	<%= articleDisplay.getContent() %>

	<c:if test="<%= articleDisplay.isPaginate() %>">
		<liferay-ui:page-iterator
			cur="<%= articleDisplay.getCurrentPage() %>"
			curParam='<%= "page" %>'
			delta="<%= 1 %>"
			id="articleDisplayPages"
			maxPages="<%= 25 %>"
			portletURL="<%= journalPreviewArticleContentTemplateDisplayContext.getPageIteratorPortletURL() %>"
			total="<%= articleDisplay.getNumberOfPages() %>"
			type="article"
		/>
	</c:if>
</div>

<aui:script>
	function previewArticleContentTemplate() {
		var ddmTemplateKey = document.getElementById('<portlet:namespace />ddmTemplateKey');

		location.href = Liferay.Util.addParams('<portlet:namespace />ddmTemplateKey=' + ddmTemplateKey.value, '<%= journalPreviewArticleContentTemplateDisplayContext.getPortletURL() %>');
	}
</aui:script>