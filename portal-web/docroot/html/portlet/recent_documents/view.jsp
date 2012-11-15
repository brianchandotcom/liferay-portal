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

<%@ include file="/html/portlet/recent_documents/init.jsp" %>

<%
List fileRanks = DLAppLocalServiceUtil.getFileRanks(scopeGroupId, user.getUserId());
%>

<c:choose>
	<c:when test="<%= fileRanks.isEmpty() %>">
		<liferay-ui:message key="there-are-no-recent-downloads" />
	</c:when>
	<c:otherwise>
		<table class="lfr-table">

		<%
		for (int i = 0; i < fileRanks.size(); i++) {
			DLFileRank fileRank = (DLFileRank)fileRanks.get(i);

			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileRank.getFileEntryId());

				fileEntry = fileEntry.toEscapedModel();

				PortletURL rowURL = renderResponse.createActionURL();

				rowURL.setWindowState(LiferayWindowState.EXCLUSIVE);

				rowURL.setParameter("struts_action", "/recent_documents/get_file");
				rowURL.setParameter("fileEntryId", String.valueOf(fileRank.getFileEntryId()));
		%>

				<tr>
					<td>
						<a href="<%= rowURL.toString() %>"><img align="left" alt="" border="0" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= fileEntry.getIcon() %>.png" /><%= fileEntry.getTitle() %></a>
					</td>
				</tr>

		<%
			}
			catch (Exception e) {
			}
		}
		%>

		</table>
	</c:otherwise>
</c:choose>