<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiPage wikiPage = (WikiPage)request.getAttribute("edit_page.jsp-wikiPage");

String format = BeanParamUtil.getString(wikiPage, request, "format", WikiPageConstants.DEFAULT_FORMAT);

String content = BeanParamUtil.getString(wikiPage, request, "content");
%>

<div align="right">
	<liferay-ui:toggle
		defaultShowContent="<%= false %>"
		hideMessage='<%= LanguageUtil.get(pageContext, "hide-syntax-help") + " &raquo;" %>'
		id="toggle_id_wiki_edit_wiki_syntax_help"
		showMessage='<%= "&laquo; " + LanguageUtil.get(pageContext, "show-syntax-help") %>'
	/>
</div>

<table class="lfr-table" width="100%">
<tr>
	<td class="lfr-top" width="70%">

		<%
		long resourcePrimKey = 0;

		String attachmentURLPrefix = StringPool.BLANK;

		if (wikiPage != null) {
			resourcePrimKey = wikiPage.getResourcePrimKey();

			attachmentURLPrefix = themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/wiki/get_page_attachment?p_l_id=" + themeDisplay.getPlid() + "&nodeId=" + wikiPage.getNodeId() + "&title=" + HttpUtil.encodeURL(wikiPage.getTitle()) + "&fileName=";
		}

		Map<String,String> configParams = new HashMap();

		configParams.put("attachmentURLPrefix", attachmentURLPrefix);
		configParams.put("wikiPageResourcePrimKey", String.valueOf(resourcePrimKey));

		Map<String,String> fileBrowserParams = new HashMap();

		fileBrowserParams.put("attachmentURLPrefix", attachmentURLPrefix);
		fileBrowserParams.put("Type", "Attachment");
		fileBrowserParams.put("wikiPageResourcePrimKey", String.valueOf(resourcePrimKey));
		%>

		<liferay-ui:input-editor
			configParams="<%= configParams %>"
			editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>"
			fileBrowserParams="<%= fileBrowserParams %>"
			toolbarSet="creole"
			width="100%"
		/>

		<aui:input name="content" type="hidden" />
	</td>
	<td class="syntax-help" id="toggle_id_wiki_edit_wiki_syntax_help" style="display: <liferay-ui:toggle-value defaultValue="none" id="toggle_id_wiki_edit_wiki_syntax_help" />" valign="top">
		<h3>
			<liferay-ui:message key="syntax-help" />
		</h3>

		<liferay-util:include page="<%= WikiUtil.getHelpPage(format) %>" />

		<aui:a href="<%= WikiUtil.getHelpURL(format) %>" target="_blank"><liferay-ui:message key="learn-more" /> &raquo;</aui:a>
	</td>
</tr>
</table>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.wiki.edit.creole.jsp";
%>