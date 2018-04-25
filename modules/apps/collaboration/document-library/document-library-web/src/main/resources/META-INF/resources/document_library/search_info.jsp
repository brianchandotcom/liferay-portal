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

<%@ include file="/document_library/init.jsp" %>

<%
long repositoryId = ParamUtil.getLong(request, "repositoryId");

if (repositoryId == 0) {
	repositoryId = scopeGroupId;
}

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

Folder folder = null;

if (searchFolderId > 0) {
	folder = DLAppServiceUtil.getFolder(searchFolderId);
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<nav class="subnav-tbar subnav-tbar-primary tbar">
	<div class="container-fluid container-fluid-max-xl">
		<ul class="tbar-nav">
			<li class="tbar-item tbar-item-expand">
				<div class="tbar-section">
					<span class="text-truncate-inline">
						<span class="text-truncate">

							<%
							boolean searchEverywhere = false;

							if ((folder == null) || (folder.getFolderId() == rootFolderId)) {
								searchEverywhere = true;
							}
							%>

							<c:choose>
								<c:when test="<%= !searchEverywhere %>">
									<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(folder.getName())} %>" key="searched-in-x" translateArguments="<%= false %>" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="searched-everywhere" />
								</c:otherwise>
							</c:choose>
						</span>
					</span>
				</div>
			</li>

			<c:if test="<%= folderId != rootFolderId %>">
				<li class="tbar-item">
					<portlet:renderURL var="changeSearchFolderURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/search" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="searchRepositoryId" value="<%= !searchEverywhere ? String.valueOf(scopeGroupId) : String.valueOf(repositoryId) %>" />
						<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						<portlet:param name="searchFolderId" value="<%= !searchEverywhere ? String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) : String.valueOf(folderId) %>" />
						<portlet:param name="keywords" value="<%= keywords %>" />
						<portlet:param name="showRepositoryTabs" value="<% (searchEverywhere) ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" />
						<portlet:param name="showSearchInfo" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<a class="component-link tbar-link" href="<%= changeSearchFolderURL %>">
						<liferay-ui:message key='<%= !searchEverywhere ? "search-everywhere" : "search-in-the-current-folder" %>' />
					</a>
				</li>
			</c:if>
		</ul>
	</div>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.getElementById('<portlet:namespace />keywords'));
		</aui:script>
	</c:if>
</nav>