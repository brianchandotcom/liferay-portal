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
String redirect = ParamUtil.getString(request, "redirect");

String sessionId = ParamUtil.getString(request, "sessionId");

UserTracker userTracker = LiveUsers.getUserTracker(company.getCompanyId(), sessionId);

List<UserTrackerPath> paths = userTracker.getPaths();
int numHits = userTracker.getHits();

userTracker = userTracker.toEscapedModel();

renderResponse.setTitle(LanguageUtil.get(request, "live-session"));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<portlet:actionURL name="/monitoring/edit_session" var="editSessionURL" />

<aui:form action="<%= editSessionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="sessionId" type="hidden" value="<%= sessionId %>" />

	<c:choose>
		<c:when test="<%= userTracker == null %>">
			<liferay-ui:message key="session-id-could-not-be-found" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</c:when>
		<c:otherwise>

			<%
			User user2 = null;

			try {
				user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
			}
			catch (NoSuchUserException nsue) {
			}

			boolean userSessionAlive = false;
			%>

			<liferay-ui:panel-container extended="<%= true %>" id="monitoringSessionHistoryPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="monitoringSessionPanel" markupView="lexicon" persistState="<%= false %>" title="session">
					<dl>
						<dt>
							<liferay-ui:message key="session-id" />
						</dt>
						<dd>
							<%= HtmlUtil.escape(sessionId) %>
						</dd>
						<dt>
							<liferay-ui:message key="user-id" />
						</dt>
						<dd>
							<%= userTracker.getUserId() %>
						</dd>
						<dt>
							<liferay-ui:message key="name" />
						</dt>
						<dd>
							<%= (user2 != null) ? HtmlUtil.escape(user2.getFullName()) : LanguageUtil.get(request, "not-available") %>
						</dd>
						<dt>
							<liferay-ui:message key="email-address" />
						</dt>
						<dd>
							<%= (user2 != null) ? user2.getEmailAddress() : LanguageUtil.get(request, "not-available") %>
						</dd>
						<dt>
							<liferay-ui:message key="last-request" />
						</dt>
						<dd>
							<%= dateFormatDateTime.format(userTracker.getModifiedDate()) %>
						</dd>
						<dt>
							<liferay-ui:message key="num-of-hits" />
						</dt>
						<dd>
							<%= numHits %>
						</dd>
						<dt>
							<liferay-ui:message key="browser-os-type" />
						</dt>
						<dd>
							<%= userTracker.getUserAgent() %>
						</dd>
						<dt>
							<liferay-ui:message key="remote-host-ip" />
						</dt>
						<dd>
							<%= userTracker.getRemoteAddr() %> / <%= userTracker.getRemoteHost() %>
						</dd>
					</dl>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="sessionAccessedURLsPanels" markupView="lexicon" persistState="<%= true %>" title="accessed-urls">
					<dl>

						<%
						for (int i = 0; i < paths.size(); i++) {
							UserTrackerPath userTrackerPath = paths.get(i);
						%>

						<dt>
							<%= StringUtil.replace(userTrackerPath.getPath(), "&", "& ") %>
						</dt>
						<dd>
							<%= dateFormatDateTime.format(userTrackerPath.getPathDate()) %>
						</dd>

						<%
						}
						%>

					</dl>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="monitoringSessionAttributesPanel" markupView="lexicon" persistState="<%= true %>" title="session-attributes">
					<dl>

						<%
						userSessionAlive = true;

						HttpSession userSession = PortalSessionContext.get(sessionId);

						if (userSession != null) {
							try {
								Set<String> sortedAttrNames = new TreeSet<String>();

								Enumeration<String> enu = userSession.getAttributeNames();

								while (enu.hasMoreElements()) {
									String attrName = enu.nextElement();

									sortedAttrNames.add(attrName);
								}

								for (String attrName : sortedAttrNames) {
						%>

									<dt>
										<%= HtmlUtil.escape(attrName) %>
									</dt>

						<%
								}
							}
							catch (Exception e) {
								userSessionAlive = false;

								e.printStackTrace();
							}
						}
						%>

					</dl>
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<aui:button-row>
				<c:if test="<%= userSessionAlive && !session.getId().equals(sessionId) %>">
					<aui:button type="submit" value="kill-session" />
				</c:if>

				<aui:button href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</c:otherwise>
	</c:choose>
</aui:form>