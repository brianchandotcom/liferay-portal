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
			<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(entry.getUserName()), LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getModifiedDate().getTime(), true)} %>" key="x-modified-x-ago" translateArguments="<%= false %>" />
		</span>
	</liferay-frontend:info-bar>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl\"" : StringPool.BLANK %>>
	<div id="<portlet:namespace /><%= entry.getEntryId() %>">
		<div class="autofit-padded autofit-row">
			<div class="autofit-col">
				<liferay-ui:user-portrait
					userId="<%= entry.getUserId() %>"
				/>
			</div>

			<div class="autofit-col autofit-col-expand">

				<%
				String userDisplayText = HtmlUtil.escape(PortalUtil.getUserName(entry) + StringPool.COMMA_AND_SPACE + Time.getRelativeTimeDescription(entry.getDisplayDate(), locale, timeZone, announcementsDisplayContext.getDateFormatDate()));
				%>

				<div class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<div class="autofit-section">
							<div class="component-title entry-user-display-text" title="<%= userDisplayText %>">
								<%= userDisplayText %>
							</div>

							<div class="component-title entry-title" title="<%= HtmlUtil.escape(entry.getTitle()) %>">
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
									<span class="badge badge-danger">
										<liferay-ui:message key="important" />
									</span>
								</c:if>
							</div>

							<%@ include file="/announcements/entry_scope.jspf" %>
						</div>
					</div>

					<c:if test="<%= !StringUtil.equals(announcementsRequestHelper.getPortletId(), AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN) %>">
						<div class="autofit-col">
							<%@ include file="/announcements/entry_action.jspf" %>
						</div>
					</c:if>
				</div>

				<div class="c-mt-3 entry-content">
					<%= entry.getContent() %>
				</div>
			</div>
		</div>
	</div>
</div>