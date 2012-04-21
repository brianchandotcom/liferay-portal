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
JournalFolder folder = (JournalFolder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = JournalFolderLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

String navigation = ParamUtil.getString(request, "navigation");
%>

<div id="<portlet:namespace />journalContainer">
	<aui:layout cssClass="lfr-app-column-view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/journal/view_folders.jsp" />
		</aui:column>

		<aui:column columnWidth="80" cssClass="context-pane" last="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content">
					<div class="toolbar">
						<liferay-util:include page="/html/portlet/journal/toolbar.jsp" />
					</div>

					<div class="display-style">
						<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>
					</div>
				</div>
			</div>

			<div class="journal-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/journal/breadcrumb.jsp" />
			</div>

			<%
			PortletURL portletURL = renderResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/journal/view");
			%>

			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />

				<aui:input name="deleteArticleIds" type="hidden" />
				<aui:input name="expireArticleIds" type="hidden" />

				<div class="journal-container" id="<portlet:namespace />journalContainer">
					<c:choose>
						<c:when test='<%= (navigation.equals("recent")) %>'>
							<liferay-util:include page="/html/portlet/journal/view_recent.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/journal/view_articles.jsp" />
						</c:otherwise>
					</c:choose>
				</div>
			</aui:form>
		</aui:column>
	</aui:layout>
</div>