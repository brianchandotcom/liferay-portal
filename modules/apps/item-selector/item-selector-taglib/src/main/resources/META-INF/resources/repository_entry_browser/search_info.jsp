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

<%@ include file="/repository_entry_browser/init.jsp" %>

<nav class="subnav-tbar subnav-tbar-primary tbar">
	<div class="container-fluid container-fluid-max-xl">
		<ul class="tbar-nav">
			<li class="tbar-item">
				<div class="tbar-section">
					<span class="text-truncate-inline">
						<span class="text-truncate">

							<%
							long folderId = ParamUtil.getLong(request, "folderId");
							String keywords = ParamUtil.getString(request, "keywords");
							String tabName = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:tabName"));

							Folder folder = null;
							boolean searchEverywhere = true;

							String searchInfoMessage = StringPool.BLANK;
							boolean showRerunSearchButton = true;

							boolean showBreadcrumb = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showBreadcrumb"));

							if (!showBreadcrumb) {
								searchInfoMessage = LanguageUtil.format(resourceBundle, "searched-in-x", new Object[] {HtmlUtil.escape(tabName)}, false);

								showRerunSearchButton = false;
							}
							else {
								long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

								if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
									searchEverywhere = false;

									folder = DLAppServiceUtil.getFolder(folderId);
								}
								else {
									folderId = searchFolderId;
								}

								if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (searchFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
									showRerunSearchButton = false;
								}

								searchInfoMessage = !searchEverywhere ? LanguageUtil.format(resourceBundle, "searched-in-x", new Object[] {HtmlUtil.escape(folder.getName())}, false) : LanguageUtil.format(resourceBundle, "searched-everywhere", false);
							}
							%>

							<%= searchInfoMessage %>
						</span>
					</span>
				</div>
			</li>

			<c:if test="<%= showRerunSearchButton %>">
				<li class="tbar-item">

					<%
					PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:portletURL");

					PortletURL searchURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

					searchURL.setParameter("folderId", !searchEverywhere ? String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) : String.valueOf(folderId));
					searchURL.setParameter("searchFolderId", String.valueOf(folderId));
					searchURL.setParameter("keywords", keywords);
					%>

					<a class="component-link tbar-link" href="<%= searchURL.toString() %>">
						<liferay-ui:message key='<%= !searchEverywhere ? "search-everywhere" : "search-in-the-current-folder" %>' />
					</a>
				</li>
			</c:if>
		</ul>
	</div>
</nav>