<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/requests/init.jsp" %>

<%
List<SocialRequest> requests = (List<SocialRequest>)request.getAttribute(WebKeys.SOCIAL_REQUESTS);
%>

<c:choose>
	<c:when test="<%= (requests == null) || requests.isEmpty() %>">

		<%
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

	</c:when>
	<c:otherwise>

		<%
		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter("struts_action", "/requests/update_request");
		portletURL.setParameter("redirect", currentURL);

		for (int i = 0; i < requests.size(); i++) {
			SocialRequest socialRequest = requests.get(i);

			SocialRequestFeedEntry requestFeedEntry = SocialRequestInterpreterLocalServiceUtil.interpret(socialRequest, themeDisplay);
		%>

			<aui:row>
				<aui:col width="<%= 50 %>">
					<liferay-ui:user-display
						displayStyle="<%= 2 %>"
						userId="<%= socialRequest.getUserId() %>"
					/>
				</aui:col>
				<aui:col width="<%= 50 %>">
					<c:choose>
						<c:when test="<%= requestFeedEntry == null %>">
							<div class="alert alert-error">
								<liferay-ui:message key="request-cannot-be-interpreted-because-it-does-not-have-an-associated-interpreter" />
							</div>
						</c:when>
						<c:otherwise>

							<%
							portletURL.setParameter("requestId", String.valueOf(socialRequest.getRequestId()));
							%>

							<div class="request-title">
								<%= requestFeedEntry.getTitle() %>
							</div>

							<br />

							<c:if test="<%= Validator.isNotNull(requestFeedEntry.getBody()) %>">
								<div class="request-body">
									<%= requestFeedEntry.getBody() %>
								</div>

								<br />
							</c:if>

							<liferay-ui:icon-list>

								<%
								portletURL.setParameter("status", String.valueOf(SocialRequestConstants.STATUS_CONFIRM));
								%>

								<liferay-ui:icon
									image="activate"
									message="confirm"
									url="<%= portletURL.toString() %>"
								/>

								<%
								portletURL.setParameter("status", String.valueOf(SocialRequestConstants.STATUS_IGNORE));
								%>

								<liferay-ui:icon
									image="deactivate"
									message="ignore"
									url="<%= portletURL.toString() %>"
								/>
							</liferay-ui:icon-list>
						</c:otherwise>
					</c:choose>
				</aui:col>
			</aui:row>

			<c:if test="<%= (i + 1) < requests.size() %>">
				<div class="separator"><!-- --></div>
			</c:if>

		<%
		}
		%>

	</c:otherwise>
</c:choose>