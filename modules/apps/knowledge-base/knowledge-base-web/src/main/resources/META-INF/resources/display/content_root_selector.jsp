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

<%@ include file="/display/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

KBNavigationDisplayContext kbNavigationDisplayContext = (KBNavigationDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_NAVIGATION_DISPLAY_CONTEXT);

String currentKBFolderURLTitle = kbNavigationDisplayContext.getCurrentKBFolderURLTitle();

long rootResourcePrimKey = kbDisplayPortletInstanceConfiguration.resourcePrimKey();

long rootKBFolderId = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
String rootKBFolderName = LanguageUtil.get(resourceBundle, "home");
String rootKBFolderURLTitle = StringPool.BLANK;

if (rootResourcePrimKey != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	KBFolder rootKBFolder = KBFolderServiceUtil.getKBFolder(rootResourcePrimKey);

	rootKBFolderId = rootKBFolder.getKbFolderId();
	rootKBFolderName = rootKBFolder.getName();
	rootKBFolderURLTitle = rootKBFolder.getUrlTitle();
}

List<KBFolder> kbFolders = KBUtil.getAlternateRootKBFolders(scopeGroupId, kbDisplayPortletInstanceConfiguration.resourcePrimKey());
%>

<c:if test="<%= !kbFolders.isEmpty() %>">
	<liferay-portlet:actionURL name="updateRootKBFolderId" var="updateRootKBFolderIdURL">
		<c:if test="<%= kbArticle != null %>">
			<portlet:param name="urlTitle" value="<%= kbArticle.getUrlTitle() %>" />
		</c:if>
	</liferay-portlet:actionURL>

	<div class="kbarticle-root-selector">
		<aui:form action="<%= updateRootKBFolderIdURL %>" name="updateRootKBFolderIdFm">
			<aui:select label="" name="rootKBFolderId">
				<c:if test="<%= KBArticleServiceUtil.getKBArticlesCount(scopeGroupId, rootKBFolderId, WorkflowConstants.STATUS_APPROVED) > 0 %>">
					<aui:option selected="<%= currentKBFolderURLTitle.equals(rootKBFolderURLTitle) %>" value="<%= rootKBFolderId %>">
						<%= HtmlUtil.escape(kbDisplayPortletInstanceConfiguration.contentRootPrefix() + " " + rootKBFolderName) %>
					</aui:option>
				</c:if>

				<%
				for (KBFolder kbFolder : kbFolders) {
				%>

					<aui:option selected="<%= currentKBFolderURLTitle.equals(kbFolder.getUrlTitle()) %>" value="<%= kbFolder.getKbFolderId() %>">
						<%= HtmlUtil.escape(kbDisplayPortletInstanceConfiguration.contentRootPrefix() + " " + kbFolder.getName()) %>
					</aui:option>

				<%
				}
				%>

			</aui:select>
		</aui:form>
	</div>

	<aui:script>
		var <portlet:namespace />form = document.getElementById(
			'<portlet:namespace />updateRootKBFolderIdFm'
		);

		if (<portlet:namespace />form) {
			document
				.getElementById('<portlet:namespace />rootKBFolderId')
				.addEventListener('change', function() {
					<portlet:namespace />form.submit();
				});
		}
	</aui:script>
</c:if>