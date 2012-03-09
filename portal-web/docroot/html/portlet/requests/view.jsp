<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<c:if test="<%= requests != null %>">

	<%
	PortletURL portletURL = renderResponse.createActionURL();

	portletURL.setParameter("struts_action", "/requests/update_request");
	portletURL.setParameter("redirect", currentURL);
	%>

	<table class="lfr-table" width="100%">

	<%
	for (int i = 0; i < requests.size(); i++) {
		SocialRequest socialRequest = requests.get(i);

		SocialRequestFeedEntry requestFeedEntry = SocialRequestInterpreterLocalServiceUtil.interpret(socialRequest, themeDisplay);
	%>

		<tr>
			<td align="center" class="lfr-top">
				<liferay-ui:user-display
					displayStyle="<%= 2 %>"
					userId="<%= socialRequest.getUserId() %>"
				/>
			</td>
			<td class="lfr-top" width="99%">
				<c:choose>
					<c:when test="<%= requestFeedEntry == null %>">
						<div class="portlet-msg-error">
							<liferay-ui:message key="request-cannot-be-interpreted-because-it-does-not-have-an-associated-interpreter" />
						</div>
					</c:when>
					<c:otherwise>

						<%
						portletURL.setParameter("requestId", String.valueOf(socialRequest.getRequestId()));
						%>

						<div>
							<%= requestFeedEntry.getTitle() %>
						</div>

						<br />

						<c:if test="<%= Validator.isNotNull(requestFeedEntry.getBody()) %>">
							<div>
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
			</td>
		</tr>

		<c:if test="<%= (i + 1) < requests.size() %>">
			<tr>
				<td colspan="2">
					<div class="separator"><!-- --></div>
				</td>
			</tr>
		</c:if>

	<%
	}
	%>

	</table>
</c:if>

<c:if test="<%= requests == null %>">
	<aui:script use="aui-base">
		var portlet = A.one('#p_p_id<portlet:namespace />');

		if (portlet) {
			portlet.hide();
		}
	</aui:script>
</c:if>