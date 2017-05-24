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
List<CPAttachmentFileEntry> cpAttachmentFileEntries = (List<CPAttachmentFileEntry>)request.getAttribute(CPWebKeys.CP_ATTACHMENT_FILE_ENTRIES);

if (ListUtil.isEmpty(cpAttachmentFileEntries)) {
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
					<liferay-util:include
						page="/attachment_file_entry_action.jsp"
						servletContext="<%= application %>"
					/>
				</li>
			</ul>

			<h4><%= HtmlUtil.escape("TITLE") %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpAttachmentFileEntry.getCPAttachmentFileEntryId())) %>
			</p>

			<h5><liferay-ui:message key="status" /></h5>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpAttachmentFileEntries.size() %>" key="x-items-are-selected" /></h4>
		</div>

		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message arguments="<%= cpAttachmentFileEntries.size() %>" key="x-items-are-selected" /></h5>
		</div>
	</c:otherwise>
</c:choose>