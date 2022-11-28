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
				<aui:select cssClass="mr-3" label="" name="ddmTemplateId" onChange="previewArticleContentTemplate()" wrapperCssClass="form-group-sm mb-0 ml-4">
					<aui:option label="no-template" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), -1) %>" value="<%= -1 %>" />

					<%
					for (DDMTemplate ddmTemplate : journalPreviewArticleContentTemplateDisplayContext.getDDMTemplates()) {
					%>

						<aui:option label="<%= HtmlUtil.escape(ddmTemplate.getName(locale)) %>" selected="<%= Objects.equals(journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId(), ddmTemplate.getTemplateId()) %>" value="<%= ddmTemplate.getTemplateId() %>" />

					<%
					}
					%>

				</aui:select>
			</li>
			<li class="tbar-item">
				<div class="journal-article-button-row tbar-section text-right">
					<aui:button
						cssClass="btn-sm selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"ddmtemplateid", journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId()
							).build()
						%>'
						value="apply"
					/>
				</div>
			</li>
		</ul>
	</nav>
</aui:form>

<div class="m-4">
	<liferay-journal:journal-article-display
		articleDisplay="<%= journalPreviewArticleContentTemplateDisplayContext.getArticleDisplay() %>"
		paginationURL="<%= journalPreviewArticleContentTemplateDisplayContext.getPageIteratorPortletURL() %>"
	/>
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
</script>