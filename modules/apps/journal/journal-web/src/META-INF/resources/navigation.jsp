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

<%@ include file="/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

String navigation = ParamUtil.getString(request, "navigation", "home");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewArticlesHomeURL">
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewArticlesHomeURL %>"
			label="folders"
			selected='<%= (navigation.equals("home") && (folderId == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) && Validator.isNull(ddmStructureKey) %>'
		/>

		<portlet:renderURL var="viewRecentArticlesURL">
			<portlet:param name="navigation" value="recent" />
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewRecentArticlesURL %>"
			label="recent"
			selected='<%= navigation.equals("recent") %>'
		/>

		<portlet:renderURL var="viewMyArticlesURL">
			<portlet:param name="navigation" value="mine" />
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewMyArticlesURL %>"
			label="mine"
			selected='<%= navigation.equals("mine") %>'
		/>

		<aui:nav-item
			dropdown="<%= true %>"
			label='<%= HtmlUtil.escape(LanguageUtil.get(request, "structures")) %>'
		>

			<%
			List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(groupIds, PortalUtil.getClassNameId(JournalArticle.class));

			for (DDMStructure ddmStructure : ddmStructures) {
			%>

				<portlet:renderURL var="viewDDMStructureArticlesURL">
					<portlet:param name="browseBy" value="structure" />
					<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
				</portlet:renderURL>

				<aui:nav-item
					href="<%= viewDDMStructureArticlesURL %>"
					label="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
					localizeLabel="<%= false %>"
					selected="<%= ddmStructureKey.equals(ddmStructure.getStructureKey()) %>"
				/>

			<%
			}
			%>

		</aui:nav-item>
	</aui:nav>

	<aui:nav-bar-search>

		<%
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("folderId", String.valueOf(folderId));
		%>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-util:include page="/article_search.jsp" servletContext="<%= application %>" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>