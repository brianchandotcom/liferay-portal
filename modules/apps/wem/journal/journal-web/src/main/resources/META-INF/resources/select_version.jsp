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
long groupId = ParamUtil.getLong(request, "groupId");
String articleId = ParamUtil.getString(request, "articleId");
double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectVersionFm");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_version.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("articleId", articleId);
portletURL.setParameter("sourceVersion", String.valueOf(sourceVersion));
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="versions" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectVersionFm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= JournalArticleLocalServiceUtil.getArticlesCount(groupId, articleId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= JournalArticleLocalServiceUtil.getArticles(groupId, articleId, searchContainer.getStart(), searchContainer.getEnd(), new ArticleVersionComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.journal.model.JournalArticle"
			modelVar="curArticle"
		>
			<liferay-ui:search-container-column-text
				name="version"
			>

				<%
				double curSourceVersion = sourceVersion;
				double curTargetVersion = curArticle.getVersion();

				if (curTargetVersion < curSourceVersion) {
					double tempVersion = curTargetVersion;

					curTargetVersion = curSourceVersion;
					curSourceVersion = tempVersion;
				}

				Map<String, Object> data = new HashMap<String, Object>();

				data.put("sourceversion", curSourceVersion);
				data.put("targetversion", curTargetVersion);
				%>

				<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
					<%= String.valueOf(curArticle.getVersion()) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="date"
				value="<%= curArticle.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectVersionFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>