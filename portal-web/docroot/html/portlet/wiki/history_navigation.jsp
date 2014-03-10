<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
long nodeId = (Long)request.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)request.getAttribute(WebKeys.TITLE);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

double previousVersion = 0;
double nextVersion = 0;

List<WikiPage> allPages = WikiPageLocalServiceUtil.getPages(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
List<WikiPage> intermediatePages = new ArrayList<WikiPage>();

for (WikiPage wikiPage : allPages) {
	if ((wikiPage.getVersion() < sourceVersion) && (wikiPage.getVersion() > previousVersion)) {
		previousVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > targetVersion) && ((wikiPage.getVersion() < nextVersion) || (nextVersion == 0))) {
		nextVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > sourceVersion) && (wikiPage.getVersion() <= targetVersion)) {
		intermediatePages.add(wikiPage);
	}
}

String sourceVersionString = (previousVersion != 0) ? String.valueOf(sourceVersion) : String.valueOf(sourceVersion) + " (" + LanguageUtil.get(pageContext, "first-version") + ")";
String targetVersionString = (nextVersion != 0) ? String.valueOf(targetVersion) : String.valueOf(targetVersion) + " (" + LanguageUtil.get(pageContext, "last-version") + ")";
%>

<portlet:renderURL var="previousChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(previousVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(sourceVersion) %>" />
</portlet:renderURL>

<portlet:renderURL var="nextChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(targetVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(nextVersion) %>" />
</portlet:renderURL>

<div class="history-navigation">
	<c:choose>
		<c:when test="<%= previousVersion != 0 %>">
			<aui:a cssClass="previous" href="<%= previousChange %>" label="previous-change" />
		</c:when>
		<c:otherwise>
			<span class="previous"><liferay-ui:message key="previous-change" /></span>
		</c:otherwise>
	</c:choose>

	<div class="central-info">
		<liferay-ui:icon
			cssClass="central-title"
			image="pages"
			label="<%= true %>"
			message='<%= LanguageUtil.format(pageContext, "comparing-versions-x-and-x", new Object[] {sourceVersionString, targetVersionString}, false) %>'
		/>

		<div class="central-author">
			<c:choose>
				<c:when test="<%= intermediatePages.size() > 1 %>">

					<%
					StringBundler sb = new StringBundler(intermediatePages.size() * 7);

					for (WikiPage wikiPage: intermediatePages) {
						sb.append(HtmlUtil.escape(wikiPage.getUserName()));
						sb.append(StringPool.SPACE);
						sb.append(StringPool.OPEN_PARENTHESIS);
						sb.append(wikiPage.getVersion());
						sb.append(StringPool.CLOSE_PARENTHESIS);
						sb.append(StringPool.COMMA);
						sb.append(StringPool.SPACE);
					}

					sb.setIndex(sb.index() - 2);

					sb.append(StringPool.SPACE);
					%>

					<liferay-ui:icon
						image="user_icon"
						label="<%= true %>"
						message="<%= sb.toString() %>"
						toolTip="authors"
					/>
				</c:when>
				<c:otherwise>

					<%
					WikiPage wikiPage = intermediatePages.get(0);
					%>

					<liferay-ui:icon
						cssClass="central-username"
						image="user_icon"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(wikiPage.getUserName()) %>"
						toolTip="author"
					/>

					<c:if test="<%= Validator.isNotNull(wikiPage.getSummary()) %>">
						<%= StringPool.COLON + StringPool.SPACE + HtmlUtil.escape(wikiPage.getSummary()) %>
					</c:if>

					<c:if test="<%= wikiPage.isMinorEdit() %>">
						<%= StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "minor-edit") + StringPool.CLOSE_PARENTHESIS %>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<c:choose>
		<c:when test="<%= nextVersion != 0 %>">
			<aui:a cssClass="next" href="<%= nextChange %>" label="next-change" />
		</c:when>
		<c:otherwise>
			<span class="next"><liferay-ui:message key="next-change" /></span>
		</c:otherwise>
	</c:choose>
</div>