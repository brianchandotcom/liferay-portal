<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
String articleId = ParamUtil.getString(request, "articleId");
double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/select_version.jsp"
).setRedirect(
	currentURL
).setParameter(
	"articleId", articleId
).setParameter(
	"groupId", groupId
).setParameter(
	"sourceVersion", sourceVersion
).buildPortletURL();
%>

<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectVersionFm">
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
				%>

				<aui:a
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"sourceversion", curSourceVersion
						).put(
							"targetversion", curTargetVersion
						).build()
					%>'
					href="javascript:void(0);"
				>
					<%= String.valueOf(curArticle.getVersion()) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="date"
				value="<%= curArticle.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>