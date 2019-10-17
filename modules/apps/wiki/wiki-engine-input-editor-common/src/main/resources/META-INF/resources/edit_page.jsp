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
BaseWikiEngine baseWikiEngine = BaseWikiEngine.getBaseWikiEngine(request);

WikiNode node = BaseWikiEngine.getWikiNode(request);
WikiPage wikiPage = BaseWikiEngine.getWikiPage(request);

String content = BeanParamUtil.getString(wikiPage, request, "content");
%>

<div class="wiki-page-editor">
	<%@ include file="/editor_config.jspf" %>

	<liferay-ui:input-editor
		configParams="<%= configParams %>"
		contents="<%= content %>"
		editorName="<%= baseWikiEngine.getEditorName() %>"
		fileBrowserParams="<%= fileBrowserParams %>"
		name="contentEditor"
		toolbarSet="<%= baseWikiEngine.getToolbarSet() %>"
	/>

	<aui:input name="content" type="hidden" />

	<c:if test="<%= baseWikiEngine.isHelpPageDefined() %>">
		<div align="right">
			<a href="javascript:;" id="<%= renderResponse.getNamespace() + "toggle_id_wiki_editor_help" %>"><liferay-ui:message key="show-syntax-help" /> &raquo;</a>
		</div>

		<%
		String helpPageHTML = baseWikiEngine.getHelpPageHTML(pageContext);
		String helpPageTitle = baseWikiEngine.getHelpPageTitle(request);
		%>

		<aui:script use="liferay-util-window">
			var helpPageLink = A.one(
				'#<%= renderResponse.getNamespace() + "toggle_id_wiki_editor_help" %>'
			);

			helpPageLink.on('click', function(event) {
				event.preventDefault();

				var helpPageDialog = Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent: '<%= HtmlUtil.escapeJS(helpPageHTML) %>',
						destroyOnHide: true
					},
					title: '<%= HtmlUtil.escapeJS(helpPageTitle) %>'
				});

				helpPageDialog.render();
			});
		</aui:script>
	</c:if>
</div>