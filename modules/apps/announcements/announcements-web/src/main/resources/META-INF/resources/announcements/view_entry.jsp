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

<%@ include file="/announcements/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(AnnouncementsWebKeys.ANNOUNCEMENTS_ENTRY);
int flagValue = GetterUtil.getInteger(request.getAttribute(AnnouncementsWebKeys.VIEW_ENTRY_FLAG_VALUE));

if (flagValue != AnnouncementsFlagConstants.HIDDEN) {
	try {
		AnnouncementsFlagLocalServiceUtil.getFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagConstants.READ);
	}
	catch (NoSuchFlagException nsfe) {
		AnnouncementsFlagLocalServiceUtil.addFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagConstants.READ);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(entry.getTitle());
}
%>

<c:if test="<%= portletTitleBasedNavigation %>">
	<liferay-frontend:info-bar>
		<span class="text-secondary">
			<liferay-ui:message arguments="<%= new String[] {entry.getUserName(), LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getModifiedDate().getTime(), true)} %>" key="x-modified-x-ago" translateArguments="<%= false %>" />
		</span>
	</liferay-frontend:info-bar>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<div class="main-content-card panel" id="<portlet:namespace /><%= entry.getEntryId() %>">
		<div class="panel-heading">
			<div class="card-row">
				<div class="card-col-field">
					<div class="list-group-card-icon">
						<liferay-ui:user-portrait
							userId="<%= entry.getUserId() %>"
						/>
					</div>
				</div>

				<div class="card-col-content card-col-gutters">

					<%
					String userDisplayText = PortalUtil.getUserName(entry) + StringPool.COMMA_AND_SPACE + Time.getRelativeTimeDescription(entry.getDisplayDate(), locale, timeZone, announcementsDisplayContext.getDateFormatDate());
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

					<%@ include file="/announcements/entry_scope.jspf" %>
				</div>

				<div class="card-col-field">
					<%@ include file="/announcements/entry_action.jspf" %>
				</div>
			</div>
		</div>

		<div class="entry-content panel-body">
			<%= entry.getContent() %>
		</div>
	</div>
</div>