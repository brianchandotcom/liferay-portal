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
Group group = themeDisplay.getScopeGroup();
%>

<c:choose>
	<c:when test="<%= group.isUser() %>">

		<%
		List<User> users = UserLocalServiceUtil.getSocialUsers(group.getClassPK(), SocialRelationConstants.TYPE_BI_CONNECTION, StringPool.EQUAL, 0, 10, new UserLastNameComparator());

		PortletURL portletURL = null;

		try {
			long contactsPlid = PortalUtil.getPlidFromPortletId(group.getGroupId(), false, ContactsPortletKeys.CONTACTS_CENTER);

			if (contactsPlid > 0) {
				portletURL = PortletURLFactoryUtil.create(request, ContactsPortletKeys.CONTACTS_CENTER, contactsPlid, PortletRequest.RENDER_PHASE);
			}
		}
		catch (Exception e) {
		}
		%>

		<c:choose>
			<c:when test="<%= users.isEmpty() %>">
				<div class="alert alert-info">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" key="x-has-no-connections" translateArguments="<%= false %>" />
				</div>
			</c:when>
			<c:otherwise>
				<aui:row>
					<aui:col cssClass="my-contacts" width="<%= 100 %>">

						<%
						for (User user2 : users) {
						%>

							<aui:row>
								<aui:col cssClass="lfr-contact-grid-item" width="<%= 100 %>">
									<div class="lfr-contact-thumb">
										<a href="<%= user2.getDisplayURL(themeDisplay) %>"><img alt="<%= HtmlUtil.escapeAttribute(user2.getFullName()) %>" src="<%= user2.getPortraitURL(themeDisplay) %>" /></a>
									</div>

									<div class="lfr-contact-info">
										<div class="lfr-contact-name">
											<a href="<%= user2.getDisplayURL(themeDisplay) %>"><%= HtmlUtil.escape(user2.getFullName()) %></a>
										</div>

										<div class="lfr-contact-job-title">
											<%= HtmlUtil.escape(user2.getJobTitle()) %>
										</div>
									</div>

									<div class="clear"><!-- --></div>
								</aui:col>
							</aui:row>

						<%
						}
						%>

						<c:if test="<%= portletURL != null %>">
							<a class="lfr-contact-grid-item" href="<%= portletURL %>"><liferay-ui:message arguments="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" key="view-all-x-connections" translateArguments="<%= false %>" /></a>
						</c:if>
					</aui:col>
				</aui:row>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">
			<liferay-ui:message key="this-application-only-functions-when-placed-on-a-user-page" />
		</div>
	</c:otherwise>
</c:choose>