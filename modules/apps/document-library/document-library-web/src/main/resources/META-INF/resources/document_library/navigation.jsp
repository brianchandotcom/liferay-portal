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
String navigation = ParamUtil.getString(request, "navigation", "home");

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() || dlPortletInstanceSettingsHelper.isShowSearch() %>">
	<aui:nav-bar cssClass='<%= dlPortletInstanceSettingsHelper.isShowSearch() ? "collapse-basic-search" : StringPool.BLANK %>' markupView="lexicon">
		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() %>">
			<aui:nav cssClass="navbar-nav">
				<portlet:renderURL var="viewDocumentsHomeURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
				</portlet:renderURL>

				<aui:nav-item
					href="<%= viewDocumentsHomeURL %>"
					label="folders"
					selected='<%= ((navigation.equals("home")) && (folderId == rootFolderId) && (fileEntryTypeId == -1)) %>'
				/>

				<portlet:renderURL var="viewRecentDocumentsURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
					<portlet:param name="navigation" value="recent" />
					<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
				</portlet:renderURL>

				<aui:nav-item
					href="<%= viewRecentDocumentsURL %>"
					label="recent"
					selected='<%= navigation.equals("recent") %>'
				/>

				<c:if test="<%= themeDisplay.isSignedIn() %>">
					<portlet:renderURL var="viewMyDocumentsURL">
						<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
						<portlet:param name="navigation" value="mine" />
						<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
					</portlet:renderURL>

					<aui:nav-item
						href="<%= viewMyDocumentsURL %>"
						label="mine"
						selected='<%= navigation.equals("mine") %>'
					/>
				</c:if>

				<%
				long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);
				%>

				<c:if test="<%= DLFileEntryTypeServiceUtil.getFileEntryTypesCount(groupIds) > 0 %>">
					<aui:nav-item
						dropdown="<%= true %>"
						label='<%= HtmlUtil.escape(LanguageUtil.get(request, "document-types")) %>'
					>

						<portlet:renderURL var="viewBasicFileEntryTypeURL">
							<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
							<portlet:param name="browseBy" value="file-entry-type" />
							<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(0) %>" />
						</portlet:renderURL>

						<aui:nav-item
							href="<%= viewBasicFileEntryTypeURL %>"
							label='<%= HtmlUtil.escape(LanguageUtil.get(request, "basic-document")) %>'
							localizeLabel="<%= false %>"
							selected="<%= (fileEntryTypeId == 0) %>"
						/>

						<%
						List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds);

						for (DLFileEntryType fileEntryType : fileEntryTypes) {
						%>

							<portlet:renderURL var="viewFileEntryTypeURL">
								<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
								<portlet:param name="browseBy" value="file-entry-type" />
								<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
								<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
							</portlet:renderURL>

							<aui:nav-item
								href="<%= viewFileEntryTypeURL %>"
								label="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>"
								localizeLabel="<%= false %>"
								selected="<%= (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) %>"
							/>

						<%
						}
						%>

					</aui:nav-item>
				</c:if>
			</aui:nav>
		</c:if>

		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowSearch() %>">
			<aui:nav-bar-search>
				<liferay-portlet:renderURL varImpl="searchURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/search" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="searchRepositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="searchFolderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="showRepositoryTabs" value="<%= (folderId == 0) ? Boolean.TRUE.toString() : Boolean.FALSE.toString() %>" />
					<portlet:param name="showSearchInfo" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= searchURL.toString() %>" name="searchFm">
					<liferay-portlet:renderURLParams varImpl="searchURL" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

					<liferay-ui:input-search markupView="lexicon" />
				</aui:form>
			</aui:nav-bar-search>
		</c:if>
	</aui:nav-bar>
</c:if>