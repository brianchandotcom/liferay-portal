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
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() || dlPortletInstanceSettingsHelper.isShowSearch() %>">
	<aui:nav-bar cssClass='<%= dlPortletInstanceSettingsHelper.isShowSearch() ? "collapse-basic-search" : StringPool.BLANK %>' markupView="lexicon">
		<c:if test="<%= dlPortletInstanceSettingsHelper.isShowTabs() %>">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="folders" selected="<%= true %>" />
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