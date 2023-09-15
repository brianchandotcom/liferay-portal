<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<div class="management-bar management-bar-light navbar navbar-expand-md">
		<clay:container-fluid>
			<ul class="m-auto navbar-nav"></ul>

			<ul class="middle navbar-nav">
				<li class="nav-item">
					<span class="text-secondary">
						<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(entry.getUserName()), LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getModifiedDate().getTime(), true)} %>" key="x-modified-x-ago" translateArguments="<%= false %>" />
					</span>
				</li>
			</ul>

			<ul class="end m-auto navbar-nav"></ul>
		</clay:container-fluid>
	</div>
</c:if>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl\"" : StringPool.BLANK %>>
	<div id="<portlet:namespace /><%= entry.getEntryId() %>">
		<div class="autofit-padded autofit-row">
			<div class="autofit-col">
				<liferay-user:user-portrait
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