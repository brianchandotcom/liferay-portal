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
String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

Map<String, Object> data = new HashMap<>();

data.put("qa-id", "navigation");
%>

<aui:nav-bar cssClass="collapse-basic-search" data="<%= data %>" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewArticlesHomeURL">
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
			<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewArticlesHomeURL %>"
			label="folders"
			selected="<%= (journalDisplayContext.isNavigationHome() && (journalDisplayContext.getFolderId() == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)) && Validator.isNull(ddmStructureKey) %>"
		/>

		<portlet:renderURL var="viewRecentArticlesURL">
			<portlet:param name="navigation" value="recent" />
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
			<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewRecentArticlesURL %>"
			label="recent"
			selected="<%= journalDisplayContext.isNavigationRecent() %>"
		/>

		<portlet:renderURL var="viewMyArticlesURL">
			<portlet:param name="navigation" value="mine" />
			<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
			<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewMyArticlesURL %>"
			label="mine"
			selected="<%= journalDisplayContext.isNavigationMine() %>"
		/>

		<aui:nav-item
			dropdown="<%= true %>"
			label='<%= HtmlUtil.escape(LanguageUtil.get(request, "structures")) %>'
		>

			<%
			List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(company.getCompanyId(), groupIds, PortalUtil.getClassNameId(JournalArticle.class), WorkflowConstants.STATUS_APPROVED);

			for (DDMStructure ddmStructure : ddmStructures) {
			%>

				<portlet:renderURL var="viewDDMStructureArticlesURL">
					<portlet:param name="browseBy" value="structure" />
					<portlet:param name="folderId" value="<%= String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
					<portlet:param name="showEditActions" value="<%= String.valueOf(journalDisplayContext.isShowEditActions()) %>" />
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

		portletURL.setParameter("folderId", String.valueOf(journalDisplayContext.getFolderId()));
		portletURL.setParameter("showEditActions", String.valueOf(journalDisplayContext.isShowEditActions()));
		%>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>