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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String browseBy = ParamUtil.getString(request, "browseBy");

JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long parentFolderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

if (folder != null) {
	parentFolderId = folder.getParentFolderId();

	if (parentFolderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		JournalFolder parentFolder = JournalFolderServiceUtil.fetchFolder(folderId);

		if (parentFolder == null) {
			parentFolderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
	}
}

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

int total = 0;

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

if (browseBy.equals("structure")) {
	total = DDMStructureLocalServiceUtil.getStructuresCount(groupIds, PortalUtil.getClassNameId(JournalArticle.class));
}
else if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	total = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, parentFolderId);
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");
portletURL.setParameter("folderId", String.valueOf(folderId));

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

searchContainer.setTotal(total);

String parentTitle = StringPool.BLANK;

if (browseBy.equals("structure")) {
	parentTitle = LanguageUtil.get(request, "browse-by-structure");
}
else {
	if ((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId > 0)) {
		JournalFolder grandparentFolder = JournalFolderServiceUtil.getFolder(parentFolderId);

		parentTitle = grandparentFolder.getName();
	}
	else if (parentFolderId == 0) {
		parentTitle = LanguageUtil.get(request, "home");
	}
}
%>

<div id="<portlet:namespace />listViewContainer">
	<div id="<portlet:namespace />folderContainer">
		<aui:nav cssClass="list-group">
			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<li class="list-group-item nav-header">
					<%= HtmlUtil.escape(parentTitle) %>
				</li>
			</c:if>

			<c:choose>
				<c:when test='<%= (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && !browseBy.equals("structure") %>'>

					<%
					String navigation = ParamUtil.getString(request, "navigation", "home");

					request.setAttribute("view_entries.jsp-folder", folder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
					%>

					<portlet:renderURL var="viewArticlesHomeURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewArticlesHomeURL %>"
						iconCssClass="icon-home"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "home")) %>'
						localizeLabel="<%= false %>"
						selected='<%= (navigation.equals("home") && (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) && Validator.isNull(ddmStructureKey) %>'
					>

						<%
						request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
						%>

						<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />
					</aui:nav-item>

					<portlet:renderURL var="viewRecentArticlesURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="navigation" value="recent" />
						<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewRecentArticlesURL %>"
						iconCssClass="icon-time"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "recent")) %>'
						localizeLabel="<%= false %>"
						selected='<%= navigation.equals("recent") %>'
					/>

					<c:if test="<%= themeDisplay.isSignedIn() %>">
						<portlet:renderURL var="viewMyArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="navigation" value="mine" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry"
							href="<%= viewMyArticlesURL %>"
							iconCssClass="icon-user"
							label='<%= HtmlUtil.escape(LanguageUtil.get(request, "mine")) %>'
							localizeLabel="<%= false %>"
							selected='<%= navigation.equals("mine") %>'
						/>
					</c:if>

					<c:if test="<%= DDMStructureLocalServiceUtil.getStructuresCount(groupIds, PortalUtil.getClassNameId(JournalArticle.class)) > 0 %>">
						<portlet:renderURL var="filterDDMStructureArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="browseBy" value="structure" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry structure"
							href="<%= filterDDMStructureArticlesURL %>"
							iconCssClass="icon-th-large"
							label='<%= HtmlUtil.escape(LanguageUtil.get(request, "browse-by-structure")) %>'
							localizeLabel="<%= false %>"
							selected='<%= browseBy.equals("structure") %>'
						/>
					</c:if>
				</c:when>
				<c:when test='<%= browseBy.equals("structure") %>'>
					<portlet:renderURL var="viewURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewURL %>"
						iconCssClass="icon-level-up"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "up")) %>'
						localizeLabel="<%= false %>"
					/>

					<c:if test="<%= total > 0 %>">

						<%
						List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(groupIds, PortalUtil.getClassNameId(JournalArticle.class), searchContainer.getStart(), searchContainer.getEnd());

						for (DDMStructure ddmStructure : ddmStructures) {
							AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());
						%>

							<portlet:renderURL var="viewDDMStructureArticlesURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="browseBy" value="structure" />
								<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
							</portlet:renderURL>

							<aui:nav-item
								cssClass="folder list-group-item navigation-entry structure"
								href="<%= viewDDMStructureArticlesURL %>"
								iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
								label="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
								localizeLabel="<%= false %>"
								selected="<%= ddmStructureKey.equals(ddmStructure.getStructureKey()) %>"
							/>

						<%
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>
					<portlet:renderURL var="viewURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
					</portlet:renderURL>

					<aui:nav-item
						cssClass="folder list-group-item navigation-entry"
						href="<%= viewURL %>"
						iconCssClass="icon-level-up"
						label='<%= LanguageUtil.get(request, "up") %>'
						localizeLabel="<%= false %>"
					/>

					<%
					List<JournalFolder> folders = JournalFolderServiceUtil.getFolders(scopeGroupId, parentFolderId, searchContainer.getStart(), searchContainer.getEnd());

					for (JournalFolder curFolder : folders) {
						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));

						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalFolder.class.getName());

						AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
					%>

						<portlet:renderURL var="viewURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
						</portlet:renderURL>

						<aui:nav-item
							cssClass="folder list-group-item navigation-entry"
							href="<%= viewURL %>"
							iconCssClass="<%= assetRenderer.getIconCssClass() %>"
							label="<%= HtmlUtil.escape(curFolder.getName()) %>"
							localizeLabel="<%= false %>"
							selected="<%= (curFolder.getFolderId() == folderId) %>"
						>

							<%
							request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
							%>

							<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />
						</aui:nav-item>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</aui:nav>
	</div>
</div>

<div class="article-folders-pagination">
	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</div>