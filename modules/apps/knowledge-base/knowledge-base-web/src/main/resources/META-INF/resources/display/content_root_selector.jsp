<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-portlet:actionURL name="/knowledge_base/update_root_kb_folder_id" var="updateRootKBFolderIdURL">
	<c:if test="<%= kbArticle != null %>">
		<portlet:param name="urlTitle" value="<%= kbArticle.getUrlTitle() %>" />
	</c:if>
</liferay-portlet:actionURL>

<div class="kbarticle-root-selector">
	<aui:form action="<%= updateRootKBFolderIdURL %>" name="updateRootKBFolderIdFm">
		<aui:select disabled="<%= kbFolders.isEmpty() %>" label="" name="rootKBFolderId">
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

<script>
	var <portlet:namespace />form = document.getElementById(
		'<portlet:namespace />updateRootKBFolderIdFm'
	);

	if (<portlet:namespace />form) {
		document
			.getElementById('<portlet:namespace />rootKBFolderId')
			.addEventListener('change', () => {
				<portlet:namespace />form.submit();
			});
	}
</script>