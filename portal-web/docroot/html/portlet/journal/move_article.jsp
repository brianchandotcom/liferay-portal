<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String articleId = BeanParamUtil.getString(article, request, "articleId");

long folderId = BeanParamUtil.getLong(article, request, "folderId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("articleId", String.valueOf(articleId));
%>

<portlet:actionURL var="moveArticleURL">
	<portlet:param name="struts_action" value="/journal/move_article" />
</portlet:actionURL>

<aui:form action="<%= moveArticleURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveArticle(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
	<aui:input name="newFolderId" type="hidden" value="<%= folderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= LanguageUtil.get(pageContext, "move") + StringPool.SPACE + article.getTitle(locale) %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="the-folder-you-selected-already-has-an-entry-with-this-name.-please-select-a-different-folder" />
	<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

	<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

	<aui:fieldset>

		<%
		String folderName = StringPool.BLANK;

		if (folderId > 0) {
			JournalFolder folder = JournalFolderServiceUtil.getFolder(folderId);

			folder = folder.toEscapedModel();

			folderId = folder.getFolderId();
			folderName = folder.getName();
		}
		else {
			folderName = LanguageUtil.get(pageContext, "home");
		}
		%>

		<aui:field-wrapper label="current-folder">
			<liferay-ui:input-resource url="<%= folderName %>" />
		</aui:field-wrapper>

		<div class="control-group">
			<aui:input label="new-folder" name="folderName" type="resource" value="<%= folderName %>" />

			<aui:button name="selectFolderButton" value="select" />
		</div>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/select_folder" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
</portlet:renderURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />selectFolderButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true,
						width: 680
					},
					id: '<portlet:namespace />selectFolder',
					title: '<liferay-ui:message arguments="folder" key="select-x" />',
					uri: '<%= selectFolderURL.toString() %>'
				},
				function(event) {
					var folderData = {
						idString: 'newFolderId',
						idValue: event.folderid,
						nameString: 'folderName',
						nameValue: event.foldername
					};

					Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
				}
			);
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveArticle() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%
JournalUtil.addPortletBreadcrumbEntries(article, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "move"), currentURL);
%>