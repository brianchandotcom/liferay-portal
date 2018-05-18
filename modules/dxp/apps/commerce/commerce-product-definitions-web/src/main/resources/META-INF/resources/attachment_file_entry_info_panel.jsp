<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<CPAttachmentFileEntry> cpAttachmentFileEntries = (List<CPAttachmentFileEntry>)request.getAttribute(CPWebKeys.CP_ATTACHMENT_FILE_ENTRIES);

if (cpAttachmentFileEntries == null) {
	cpAttachmentFileEntries = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpAttachmentFileEntries.size() == 1 %>">

		<%
		CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntries.get(0);

		request.setAttribute("info_panel.jsp-entry", cpAttachmentFileEntry);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/attachment_file_entry_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpAttachmentFileEntry.getTitle(languageId)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpAttachmentFileEntry.getCPAttachmentFileEntryId())) %>
			</p>

			<h5><liferay-ui:message key="status" /></h5>

			<p>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= cpAttachmentFileEntry.getStatus() %>" />
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpAttachmentFileEntries.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>