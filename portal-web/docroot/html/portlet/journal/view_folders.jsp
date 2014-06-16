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

String structureId = ParamUtil.getString(request, "structureId");

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

int total = 0;

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

if (browseBy.equals("structure")) {
	total = DDMStructureLocalServiceUtil.getStructuresCount(groupIds, PortalUtil.getClassNameId(JournalArticle.class));
}
else if ((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) || expandFolder) {
	total = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, parentFolderId);
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", folderEnd / (folderEnd - folderStart), (folderEnd - folderStart), portletURL, null, null);

searchContainer.setTotal(total);

String parentTitle = StringPool.BLANK;

if (browseBy.equals("structure")) {
	parentTitle = LanguageUtil.get(pageContext, "browse-by-structure");
}
else {
	if ((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId > 0)) {
		JournalFolder grandparentFolder = JournalFolderServiceUtil.getFolder(parentFolderId);

		parentTitle = grandparentFolder.getName();
	}
	else if (((folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == 0)) || ((folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (parentFolderId == 0) && expandFolder)) {
		parentTitle = LanguageUtil.get(pageContext, "home");
	}
}
%>

<div id="<portlet:namespace />listViewContainer">
	<div id="<portlet:namespace />folderContainer">
		<aui:nav cssClass="list-group">
			<c:if test="<%= Validator.isNotNull(parentTitle) %>">
				<li class="dropdown-header list-group-item">
					<%= parentTitle %>
				</li>
			</c:if>

			<c:choose>
				<c:when test='<%= ((folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) && !expandFolder) && !browseBy.equals("structure") %>'>

					<%
					String navigation = ParamUtil.getString(request, "navigation", "home");

					request.setAttribute("view_entries.jsp-folder", folder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(folderId));

					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
					dataView.put("navigation", "home");
					dataView.put("title", LanguageUtil.get(pageContext, "home"));
					%>

					<liferay-portlet:renderURL varImpl="viewArticlesHomeURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						actionJsp="/html/portlet/journal/folder_action.jsp"
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "home") %>'
						iconImage="icon-home"
						selected='<%= (navigation.equals("home") && (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) && Validator.isNull(structureId) %>'
						viewURL="<%= viewArticlesHomeURL.toString() %>"
					/>

					<%
					dataView = new HashMap<String, Object>();

					dataView.put("navigation", "recent");
					%>

					<liferay-portlet:renderURL varImpl="viewRecentArticlesURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="navigation" value="recent" />
						<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "recent") %>'
						iconImage="icon-time"
						selected='<%= navigation.equals("recent") %>'
						viewURL="<%= viewRecentArticlesURL.toString() %>"
					/>

					<c:if test="<%= themeDisplay.isSignedIn() %>">

						<%
						dataView = new HashMap<String, Object>();

						dataView.put("navigation", "mine");
						%>

						<liferay-portlet:renderURL varImpl="viewMyArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="navigation" value="mine" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:app-view-navigation-entry
							dataView="<%= dataView %>"
							entryTitle='<%= LanguageUtil.get(pageContext, "mine") %>'
							iconImage="icon-user"
							selected='<%= navigation.equals("mine") %>'
							viewURL="<%= viewMyArticlesURL.toString() %>"
						/>
					</c:if>

					<c:if test="<%= DDMStructureLocalServiceUtil.getStructuresCount(groupIds, PortalUtil.getClassNameId(JournalArticle.class)) > 0 %>">

						<%
						dataView = new HashMap<String, Object>();

						dataView.put("browse-by", "structure");
						dataView.put("view-entries", Boolean.FALSE);
						dataView.put("view-folders", Boolean.TRUE);
						%>

						<liferay-portlet:renderURL varImpl="filterDDMStructureArticlesURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="browseBy" value="structure" />
							<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:app-view-navigation-entry
							cssClass="folder structure"
							dataView="<%= dataView %>"
							entryTitle='<%= LanguageUtil.get(pageContext, "browse-by-structure") %>'
							iconImage="icon-th-large"
							selected='<%= browseBy.equals("structure") %>'
							viewURL="<%= filterDDMStructureArticlesURL.toString() %>"
						/>
					</c:if>
				</c:when>
				<c:when test='<%= browseBy.equals("structure") %>'>

					<%
					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", parentFolderId);
					dataView.put("view-folders", Boolean.TRUE);
					%>

					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="structureId" value="<%= String.valueOf(0) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "up") %>'
						iconImage="icon-level-up"
						viewURL="<%= viewURL.toString() %>"
					/>

					<c:if test="<%= total > 0 %>">

						<%
						List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(groupIds, PortalUtil.getClassNameId(JournalArticle.class), searchContainer.getStart(), searchContainer.getEnd());

						for (DDMStructure ddmStructure : ddmStructures) {
							dataView = new HashMap<String, Object>();

							dataView.put("browse-by", "structure");
							dataView.put("structure-id", ddmStructure.getStructureKey());

							AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());
						%>

							<liferay-portlet:renderURL varImpl="viewDDMStructureArticlesURL">
								<portlet:param name="struts_action" value="/journal/view" />
								<portlet:param name="browseBy" value="structure" />
								<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
								<portlet:param name="structureId" value="<%= ddmStructure.getStructureKey() %>" />
								<portlet:param name="entryStart" value="0" />
								<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
								<portlet:param name="folderStart" value="0" />
								<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
							</liferay-portlet:renderURL>

							<liferay-ui:app-view-navigation-entry
								cssClass="folder structure"
								dataView="<%= dataView %>"
								entryTitle="<%= ddmStructure.getName(locale) %>"
								iconImage="<%= assetRendererFactory.getIconCssClass() %>"
								selected="<%= structureId.equals(ddmStructure.getStructureKey()) %>"
								viewURL="<%= viewDDMStructureArticlesURL.toString() %>"
							/>

						<%
						}
						%>

					</c:if>
				</c:when>
				<c:otherwise>

					<%
					Map<String, Object> dataView = new HashMap<String, Object>();

					dataView.put("folder-id", parentFolderId);
					dataView.put("view-folders", Boolean.TRUE);
					%>

					<liferay-portlet:renderURL varImpl="viewURL">
						<portlet:param name="struts_action" value="/journal/view" />
						<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
						<portlet:param name="entryStart" value="0" />
						<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
						<portlet:param name="folderStart" value="0" />
						<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:app-view-navigation-entry
						dataView="<%= dataView %>"
						entryTitle='<%= LanguageUtil.get(pageContext, "up") %>'
						iconImage="icon-level-up"
						viewURL="<%= viewURL.toString() %>"
					/>

					<%
					List<JournalFolder> folders = JournalFolderServiceUtil.getFolders(scopeGroupId, parentFolderId, searchContainer.getStart(), searchContainer.getEnd());

					for (JournalFolder curFolder : folders) {
						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-folderSelected", String.valueOf(folderId == curFolder.getFolderId()));

						dataView = new HashMap<String, Object>();

						dataView.put("folder-id", curFolder.getFolderId());
						dataView.put("title", curFolder.getName());

						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalFolder.class.getName());

						AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
					%>

						<liferay-portlet:renderURL varImpl="viewURL">
							<portlet:param name="struts_action" value="/journal/view" />
							<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
							<portlet:param name="entryStart" value="0" />
							<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
							<portlet:param name="folderStart" value="0" />
							<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:app-view-navigation-entry
							actionJsp="/html/portlet/journal/folder_action.jsp"
							dataView="<%= dataView %>"
							entryTitle="<%= curFolder.getName() %>"
							iconImage="<%= assetRenderer.getIconCssClass() %>"
							selected="<%= (curFolder.getFolderId() == folderId) %>"
							viewURL="<%= viewURL.toString() %>"
						/>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</aui:nav>
	</div>
</div>

<%
request.setAttribute("view_folders.jsp-total", String.valueOf(total));

request.setAttribute("view_folders.jsp-folderEnd", searchContainer.getEnd());
request.setAttribute("view_folders.jsp-folderStart", searchContainer.getStart());
%>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			pagination: {
				name: 'folderPagination',
				state: {
					page: <%= (total == 0) ? 0 : searchContainer.getCur() %>,
					rowsPerPage: <%= searchContainer.getDelta() %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>