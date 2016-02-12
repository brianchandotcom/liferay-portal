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

<div class="panel" id="<portlet:namespace /><%= entry.getEntryId() %>">
	<div class="panel-heading">
		<div class="card-row">
			<div class="card-col-field">
				<div class="list-group-card-icon">
					<liferay-ui:user-portrait userId="<%= entry.getUserId() %>" />
				</div>
			</div>

			<div class="card-col-content card-col-gutters">

				<%
				String userDisplayText = user.getFullName() + StringPool.COMMA_AND_SPACE + Time.getRelativeTimeDescription(entry.getDisplayDate(), locale, timeZone, announcementsDisplayContext.getDateFormatDate());
				%>

				<h5 class="text-default" title="<%= userDisplayText %>">
					<%= userDisplayText %>
				</h5>

				<h4 title="<%= HtmlUtil.escape(entry.getTitle()) %>">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(entry.getUrl()) %>">
							<a href="<%= HtmlUtil.escapeHREF(entry.getUrl()) %>">
								<%= HtmlUtil.escape(entry.getTitle()) %>
							</a>
						</c:when>
						<c:otherwise>
							<%= HtmlUtil.escape(entry.getTitle()) %>
						</c:otherwise>
					</c:choose>

					<c:if test="<%= entry.isAlert() || (entry.getPriority() > 0) %>">
						<span class="badge badge-danger badge-sm">
							<liferay-ui:message key="important" />
						</span>
					</c:if>
				</h4>

				<div class="<%= hiddenEntry ? "hide" : StringPool.BLANK %> entry-scope">
					<%@ include file="/entry_scope.jspf" %>
				</div>
			</div>

			<c:if test="<%= !announcementsDisplayContext.isShowPreview() %>">
				<div class="card-col-field">
					<%@ include file="/entry_action.jspf" %>
				</div>
			</c:if>
		</div>
	</div>

	<div class="entry-content <%= hiddenEntry ? "hide" : StringPool.BLANK %> panel-body">
		<%= entry.getContent() %>
	</div>
</div>