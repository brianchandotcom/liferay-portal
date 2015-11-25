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
String className = GetterUtil.getString(request.getAttribute("view_entry.jsp-className"));
AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(WebKeys.ANNOUNCEMENTS_ENTRY);
int flagValue = GetterUtil.getInteger(request.getAttribute("view_entry.jsp-flagValue"));
String tabs1 = GetterUtil.getString(request.getAttribute("view_entry.jsp-tabs1"));

boolean hiddenEntry = false;
boolean readEntry = false;

if (flagValue == AnnouncementsFlagConstants.HIDDEN) {
	hiddenEntry = true;
	readEntry = true;
}
else {
	try {
		AnnouncementsFlagLocalServiceUtil.getFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagConstants.READ);

		readEntry = true;
	}
	catch (NoSuchFlagException nsfe) {
		AnnouncementsFlagLocalServiceUtil.addFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagConstants.READ);
	}
}

if (readEntry) {
	className += " read";
}

if (entry.getPriority() > 0) {
	className += " important";
}
%>

<div class="entry<%= className %>" id="<portlet:namespace /><%= entry.getEntryId() %>">
	<h3 class="entry-title">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(entry.getUrl()) %>">
				<a class="entry-url" href="<%= HtmlUtil.escapeHREF(entry.getUrl()) %>"><%= HtmlUtil.escape(entry.getTitle()) %></a>
			</c:when>
			<c:when test="<%= Validator.isNull(entry.getUrl()) %>">
				<%= HtmlUtil.escape(entry.getTitle()) %>
			</c:when>
		</c:choose>
	</h3>

	<c:if test='<%= !tabs1.equals("preview") %>'>
		<%@ include file="/entry_action.jspf" %>
	</c:if>

	<%
	boolean showScopeName = false;
	%>

	<div class="<%= hiddenEntry ? "hide" : "" %> entry-content entry-type-<%= HtmlUtil.escapeAttribute(entry.getType()) %>">
		<%@ include file="/entry_scope.jspf" %>

		<%= entry.getContent() %>
	</div>
</div>