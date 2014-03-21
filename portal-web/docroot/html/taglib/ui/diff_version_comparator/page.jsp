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

<%@ include file="/html/taglib/ui/diff_version_comparator/init.jsp" %>

<%
String diffHtmlResults = (String)request.getAttribute("liferay-ui:diff-version-comparator:diffHtmlResults");
DiffVersionsInfo diffVersionsInfo = (DiffVersionsInfo)request.getAttribute("liferay-ui:diff-version-comparator:diffVersionsInfo");
PortletURL iteratorURL = (PortletURL)request.getAttribute("liferay-ui:diff-version-comparator:iteratorURL");
double sourceVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:sourceVersion");
double targetVersion = (Double)request.getAttribute("liferay-ui:diff-version-comparator:targetVersion");

double nextVersion = diffVersionsInfo.getNextVersion();
double previousVersion = diffVersionsInfo.getPreviousVersion();

String sourceVersionString = (previousVersion != 0) ? String.valueOf(sourceVersion) : String.valueOf(sourceVersion) + " (" + LanguageUtil.get(pageContext, "first-version") + ")";
String targetVersionString = (nextVersion != 0) ? String.valueOf(targetVersion) : String.valueOf(targetVersion) + " (" + LanguageUtil.get(pageContext, "last-version") + ")";
%>

<div class="diff-version-comparator">
	<c:choose>
		<c:when test="<%= previousVersion != 0 %>">

			<%
			iteratorURL.setParameter("sourceVersion", String.valueOf(previousVersion));
			iteratorURL.setParameter("targetVersion", String.valueOf(sourceVersion));
			%>

			<aui:a cssClass="previous" href="<%= iteratorURL.toString() %>" label="previous-change" />
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

			<%
			List<DiffVersion> diffVersions = diffVersionsInfo.getDiffVersions();

			for (DiffVersion diffVersion : diffVersions) {
				User author = UserLocalServiceUtil.getUser(diffVersion.getUserId());
			%>

				<liferay-ui:icon
					image="user_icon"
					label="<%= true %>"
					message="<%= HtmlUtil.escape(author.getFullName()) %>"
					toolTip="author"
				/>

				(<%= diffVersion.getVersion() %>)

				<c:if test="<%= diffVersions.size() == 1 %>">

					<%
					String summary = diffVersion.getSummary();
					String extraInfo = diffVersion.getExtraInfo();
					%>

					<c:if test="<%= Validator.isNotNull(summary) %>">
						<%= summary %>
					</c:if>

					<c:if test="<%= Validator.isNotNull(extraInfo) %>">
						<%= extraInfo %>
					</c:if>
				</c:if>

			<%
			}
			%>

		</div>
	</div>

	<c:choose>
		<c:when test="<%= nextVersion != 0 %>">

			<%
			iteratorURL.setParameter("sourceVersion", String.valueOf(targetVersion));
			iteratorURL.setParameter("targetVersion", String.valueOf(nextVersion));
			%>

			<aui:a cssClass="next" href="<%= iteratorURL.toString() %>" label="next-change" />
		</c:when>
		<c:otherwise>
			<span class="next"><liferay-ui:message key="next-change" /></span>
		</c:otherwise>
	</c:choose>
</div>

<liferay-ui:diff-html diffHtmlResults="<%= diffHtmlResults %>" />