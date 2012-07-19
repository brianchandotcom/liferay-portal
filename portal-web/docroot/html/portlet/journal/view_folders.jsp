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

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

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

						<liferay-portlet:renderURL varImpl="viewArticlesHomeURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						PortletURL expandArticlesHomeURL = PortletURLUtil.clone(viewArticlesHomeURL, liferayPortletResponse);

						expandArticlesHomeURL.setParameter("expandFolder", Boolean.TRUE.toString());

						String navigation = ParamUtil.getString(request, "navigation", "home");

						String structureId = ParamUtil.getString(request, "structureId");

						request.setAttribute("view_entries.jsp-folder", folder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));
						%>

						<li class="folder <%= (navigation.equals("home") && (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) && Validator.isNull(structureId) ? "selected" : StringPool.BLANK %>">

							<%
							request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
							%>

							<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />

							<c:if test="<%= (foldersCount > 0) %>">
								<a class="expand-folder" data-expand-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>" data-view-entries="<%= Boolean.FALSE.toString() %>" href="<%= expandArticlesHomeURL.toString() %>">
									<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="expand" />
								</a>
							</c:if>

							<a class="browse-folder" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID %>" data-navigation="home" data-title="<%= LanguageUtil.get(pageContext, "home") %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewArticlesHomeURL.toString() %>">
								<liferay-ui:icon image="../aui/home" message="" />

								<span class="article-title">
									<%= LanguageUtil.get(pageContext, "home") %>
								</span>
							</a>
						</li>

						<liferay-portlet:renderURL varImpl="viewRecentArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="navigation" value="recent" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<li class="folder <%= navigation.equals("recent") && Validator.isNull(structureId) ? "selected" : StringPool.BLANK %>">
							<a class="browse-folder" data-navigation="recent" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewRecentArticlesURL.toString() %>">
								<liferay-ui:icon image="../aui/clock" message="" />

								<span class="article-title">
									<%= LanguageUtil.get(pageContext, "recent") %>
								</span>
							</a>
						</li>

						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<liferay-portlet:renderURL varImpl="viewMyArticlesURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="navigation" value="mine" />
								<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<li class="folder <%= navigation.equals("mine") && Validator.isNull(structureId) ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-navigation="mine" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewMyArticlesURL.toString() %>">
									<liferay-ui:icon image="../aui/person" message="" />

									<span class="article-title">
										<%= LanguageUtil.get(pageContext, "mine") %>
									</span>
								</a>
							</li>
						</c:if>

						<%
						List<JournalStructure> structures = JournalStructureLocalServiceUtil.getStructures(scopeGroupId);
						%>

						<c:if test="<%= !structures.isEmpty() %>">
							<liferay-portlet:renderURL varImpl="viewBasicJournalStructureArticlesURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="structureId" value="0" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<li class="folder structure <%= (structureId == "0") ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-structure-id="<%= String.valueOf(0) %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewBasicJournalStructureArticlesURL.toString() %>">
									<liferay-ui:icon image="history" message="" />

										<span class="entry-title">
											<%= LanguageUtil.get(pageContext, "basic-web-content") %>
										</span>
								</a>
							</li>
						</c:if>

						<%
						for (JournalStructure structure : structures) {
						%>

							<liferay-portlet:renderURL varImpl="viewJournalStructureArticlesURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="structureId" value="<%= structure.getStructureId() %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<li class="folder structure <%= structureId.equals(structure.getStructureId()) ? "selected" : StringPool.BLANK %>">
								<a class="browse-folder" data-structure-id="<%= structure.getStructureId() %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewJournalStructureArticlesURL.toString() %>">
									<liferay-ui:icon image="history" message="" />

									<span class="article-title">
										<%= structure.getName(locale) %>
									</span>
								</a>
							</li>

						<%
						}
						%>

					</c:when>
					<c:otherwise>
						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<%
						PortletURL expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

						expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());
						%>

						<li class="folder">
							<a class="expand-folder" data-direction-right="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(parentFolderId) %>" data-view-entries="<%= Boolean.FALSE.toString() %>" href="<%= expandViewURL.toString() %>">
								<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-l" message="collapse" />
							</a>

							<a class="browse-folder" data-direction-right="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(parentFolderId) %>" href="<%= viewURL.toString() %>">
								<liferay-ui:icon message="up" src='<%= themeDisplay.getPathThemeImages() + "/arrows/01_up.png" %>' />

								<%= LanguageUtil.get(pageContext, "up") %>
							</a>
						</li>

						<%
						for (JournalFolder curFolder : folders) {
							int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId());
							int articlesCount = JournalArticleServiceUtil.getArticlesCount(scopeGroupId, curFolder.getFolderId());

							request.setAttribute("view_entries.jsp-folder", curFolder);
							request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
							request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));
						%>

							<liferay-portlet:renderURL varImpl="viewURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<%
							expandViewURL = PortletURLUtil.clone(viewURL, liferayPortletResponse);

							expandViewURL.setParameter("expandFolder", Boolean.TRUE.toString());
							%>

							<li class="folder <%= (curFolder.getFolderId() == folderId) ? "selected" : StringPool.BLANK %>">

								<%
								request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
								%>

								<liferay-util:include page="/html/portlet/journal/folder_action.jsp" />

								<c:if test="<%= foldersCount > 0 %>">
									<a class="expand-folder" data-expand-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(curFolder.getFolderId()) %>" data-view-entries="<%= Boolean.FALSE.toString() %>" href="<%= expandViewURL.toString() %>">
										<liferay-ui:icon cssClass="expand-folder-arrow" image="../aui/carat-1-r" message="expand" />
									</a>
								</c:if>

								<a class="browse-folder" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= String.valueOf(curFolder.getFolderId()) %>" data-title="<%= curFolder.getName() %>" data-view-folders="<%= Boolean.FALSE.toString() %>" href="<%= viewURL.toString() %>">
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

			<aui:script>
				Liferay.fire(
					'<portlet:namespace />pageLoaded',
					{
						paginator: {
							name: 'folderPaginator',
							state: {
								page: <%= folderEnd / (folderEnd - folderStart) %>,
								rowsPerPage: <%= (folderEnd - folderStart) %>,
								total: <%= total %>
							}
						}
					}
				);
			</aui:script>
		</div>
	</div>
</div>