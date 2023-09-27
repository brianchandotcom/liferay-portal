<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/repository_entry_browser/init.jsp" %>

<div class="search-info">

	<%
	long folderId = ParamUtil.getLong(request, "folderId");
	String keywords = ParamUtil.getString(request, "keywords");

	boolean searchEverywhere = true;

	long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

	boolean showRerunSearchButton = true;

	if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		searchEverywhere = false;
	}
	else {
		folderId = searchFolderId;
	}

	if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (searchFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
		showRerunSearchButton = false;
	}
	%>

	<c:if test="<%= showRerunSearchButton %>">

		<%
		PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:portletURL");

		PortletURL searchEverywhereURL = PortletURLBuilder.create(
			PortletURLUtil.clone(portletURL, liferayPortletResponse)
		).setKeywords(
			keywords
		).setParameter(
			"folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID
		).setParameter(
			"searchFolderId", folderId
		).buildPortletURL();
		%>

		<liferay-util:whitespace-remover>
			<liferay-ui:message key="search" />

			<clay:link
				cssClass='<%= searchEverywhere ? "active" : "" %>'
				displayType="secondary"
				href="<%= searchEverywhereURL.toString() %>"
				label="everywhere"
				small="<%= true %>"
				type="button"
			/>

			<%
			Folder folder = DLAppServiceUtil.getFolder(folderId);
			%>

			<clay:link
				cssClass='<%= !searchEverywhere ? "active" : "" %>'
				displayType="secondary"
				href='<%=
					PortletURLBuilder.create(
						PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse)
					).setParameter(
						"folderId", folderId
					).buildString()
				%>'
				icon="folder"
				label="<%= folder.getName() %>"
				type="button"
			/>
		</liferay-util:whitespace-remover>
	</c:if>
</div>