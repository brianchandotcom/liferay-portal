<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
final WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
%>

<c:if test="<%= wikiPage.getAttachmentsFileEntriesCount() > 0 %>">
	<div class="page-attachments">
		<h5><liferay-ui:message key="attachments" /></h5>

		<clay:row>

			<%
			for (FileEntry fileEntry : wikiPage.getAttachmentsFileEntries()) {
			%>

				<clay:col
					md="4"
				>
					<clay:horizontal-card
						horizontalCard="<%= new WikiPageAttachmentHorizontalCard(fileEntry, request) %>"
					/>
				</clay:col>

			<%
			}
			%>

		</clay:row>
	</div>
</c:if>