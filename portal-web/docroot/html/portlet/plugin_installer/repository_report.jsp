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

<%@ include file="/html/portlet/plugin_installer/init.jsp" %>

<c:if test="<%= SessionMessages.contains(renderRequest, WebKeys.PLUGIN_REPOSITORY_REPORT) %>">
	<br />

	<table class="lfr-table">

	<%
	RepositoryReport repositoryReport = (RepositoryReport)SessionMessages.get(renderRequest, WebKeys.PLUGIN_REPOSITORY_REPORT);

	Set<String> repositoryURLs = repositoryReport.getRepositoryURLs();

	for (String repositoryURL : repositoryURLs) {
		String status = repositoryReport.getState(repositoryURL);
	%>

		<tr>
			<td>
				<%= repositoryURL %>
			</td>
			<td>
				<c:choose>
					<c:when test="<%= status.equals(RepositoryReport.SUCCESS) %>">
						<div class="alert alert-success">
							<liferay-ui:message key="ok" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="alert alert-error">
							<%= status %>
						</div>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

	<%
	}
	%>

	</table>
</c:if>