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

<%@ include file="/html/portlet/recent_documents/init.jsp" %>

<%
List<DLFileRank> fileRanks = DLAppLocalServiceUtil.getFileRanks(scopeGroupId, user.getUserId());
%>

<c:choose>
	<c:when test="<%= fileRanks.isEmpty() %>">
		<liferay-ui:message key="there-are-no-recent-downloads" />
	</c:when>
	<c:otherwise>
		<ul class="recent-documents">

		<%
		for (DLFileRank fileRank : fileRanks) {
			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileRank.getFileEntryId());

				fileEntry = fileEntry.toEscapedModel();

				PortletURL rowURL = renderResponse.createActionURL();

				rowURL.setParameter("struts_action", "/recent_documents/get_file");
				rowURL.setParameter("fileEntryId", String.valueOf(fileRank.getFileEntryId()));
				rowURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		%>

				<li>
					<liferay-ui:icon
						iconCssClass="<%= fileEntry.getIconCssClass() %>"
						label="<%= true %>"
						message="<%= fileEntry.getTitle() %>"
						url="<%= rowURL.toString() %>"
					/>
				</li>

		<%
			}
			catch (Exception e) {
			}
		}
		%>

		</ul>
	</c:otherwise>
</c:choose>