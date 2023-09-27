<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				<div class="form-group-sm journal-article-button-row tbar-section text-right">
					<clay:button
						cssClass="selector-button"
						data-id='<%=
							HashMapBuilder.<String, Object>put(
								"ddmtemplateid", journalPreviewArticleContentTemplateDisplayContext.getDDMTemplateId()
							).build()
						%>'
						displayType="secondary"
						label="apply"
						type="submit"
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