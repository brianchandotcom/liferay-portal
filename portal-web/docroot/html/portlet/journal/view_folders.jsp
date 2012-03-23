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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalFolder folder = (JournalFolder)request.getAttribute("view.jsp-folder");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long parentFolderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

boolean expandFolder = ParamUtil.getBoolean(request, "expandFolder");

if (folder != null) {
	parentFolderId = folder.getParentFolderId();

	if (expandFolder) {
		parentFolderId = folderId;
	}

	if (parentFolderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		try {
			JournalFolderServiceUtil.getFolder(folderId);
		}
		catch (NoSuchFolderException nsfe) {
			parentFolderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}
	}
}

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

List<JournalFolder> folders = JournalFolderServiceUtil.getFolders(scopeGroupId, parentFolderId, folderStart, folderEnd);

int total = 0;

if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	total = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, parentFolderId);
}

request.setAttribute("view_folders.jsp-total", String.valueOf(total));
%>

<div class="lfr-header-row">
	<div class="lfr-header-row-content" id="<portlet:namespace />parentFolderTitleContainer">
		<div class="parent-folder-title" id="<portlet:namespace />parentFolderTitle">
			<c:choose>
				<c:when test="<%= (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId > 0) %>">

					<%
					JournalFolder grandParentFolder = JournalFolderServiceUtil.getFolder(parentFolderId);
					%>

					<span>
						<%= grandParentFolder.getName() %>
					</span>
				</c:when>
				<c:when test="<%= ((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == 0)) || ((folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == 0) && expandFolder) %>">
					<span>
						<liferay-ui:message key="home" />
					</span>
				</c:when>
			</c:choose>
		</div>
	</div>
</div>

<div class="body-row">
	<div id="<portlet:namespace />listViewContainer">
		<div class="folder-display-style lfr-list-view-content" id="<portlet:namespace />folderContainer">
			<ul class="lfr-component">
				<c:choose>
					<c:when test="<%= ((folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && !expandFolder) %>">

						<%
						int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, folderId);
						%>

						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="viewArticlesHomeURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						</liferay-portlet:renderURL>

						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="expandArticlesHomeURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="expandFolder" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:renderURL>

						<%
						String navigation = ParamUtil.getString(request, "navigation", "home");
						%>

						<li class="folder <%= (navigation.equals("home") && (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) ? "selected" : StringPool.BLANK %>">
							<c:if test="<%= (foldersCount > 0) %>">
								<a class="expand-folder" href="<%= expandArticlesHomeURL.toString() %>">
									<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="expand" />
								</a>
							</c:if>

							<a class="browse-folder" href="<%= viewArticlesHomeURL.toString() %>">
								<liferay-ui:icon image="../aui/home" message="" />

								<span class="article-title">
									<%= LanguageUtil.get(pageContext, "home") %>
								</span>
							</a>
						</li>

						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="viewRecentArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="navigation" value="recent" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						</liferay-portlet:renderURL>

						<li class="folder <%= navigation.equals("recent") ? "selected" : StringPool.BLANK %>">
							<a class="browse-folder" href="<%= viewRecentArticlesURL.toString() %>">
								<liferay-ui:icon image="../aui/clock" message="" />

								<span class="article-title">
									<%= LanguageUtil.get(pageContext, "recent") %>
								</span>
							</a>
						</li>
					</c:when>
					<c:otherwise>
						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="viewURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						</liferay-portlet:renderURL>

						<li class="folder">
							<a class="expand-folder" href="<%= viewURL.toString() %>">
								<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-l" message="collapse" />
							</a>

							<a class="browse-folder" href="<%= viewURL.toString() %>">
								<liferay-ui:icon message="up" src='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>' />

								<%= LanguageUtil.get(pageContext, "up") %>
							</a>
						</li>

						<%
						for (JournalFolder curFolder : folders) {
							int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());
							int articlesCount = JournalArticleServiceUtil.getArticlesCountByFolderId(scopeGroupId, curFolder.getFolderId());

							request.setAttribute("view_articles.jsp-folder", curFolder);
							request.setAttribute("view_articles.jsp-folderId", String.valueOf(curFolder.getFolderId()));
							request.setAttribute("view_articles.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						%>

							<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="viewURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							</liferay-portlet:renderURL>

							<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="expandURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
								<portlet:param name="expandFolder" value="<%= String.valueOf(Boolean.TRUE.toString()) %>" />
							</liferay-portlet:renderURL>

							<li class="folder <%= (curFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">
								<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />

								<c:if test="<%= foldersCount > 0 %>">
									<a class="expand-folder" href="<%= expandURL.toString() %>">
										<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="expand" />
									</a>
								</c:if>

								<a class="browse-folder" href="<%= viewURL.toString() %>">
									<c:choose>
										<c:when test="<%= (foldersCount + articlesCount) > 0 %>">
											<liferay-ui:icon image="folder_full_document" />
										</c:when>
										<c:otherwise>
											<liferay-ui:icon image="folder_empty" />
										</c:otherwise>
									</c:choose>

									<span class="article-title">
										<%= curFolder.getName() %>
									</span>
								</a>
							</li>

						<%
						}
						%>

					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</div>