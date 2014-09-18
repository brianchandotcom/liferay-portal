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
	WindowState windowState = liferayPortletRequest.getWindowState();

	int emailMessageMaxRecipients = InvitationUtil.getEmailMessageMaxRecipients(liferayPortletRequest.getPreferences());

	PortletURL actionURL = renderResponse.createActionURL();
	actionURL.setParameter(ActionRequest.ACTION_NAME, "view");
	actionURL.setWindowState(WindowState.NORMAL);
	String portletURL = actionURL.toString();
%>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.NORMAL) %>">
		<liferay-ui:success key="invitationSent" message="your-invitations-have-been-sent" />

		<portlet:renderURL var="viewURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		</portlet:renderURL>

		<aui:a href="<%= viewURL %>" label="invite-friends" />
	</c:when>
	<c:otherwise>

		<portlet:renderURL var="redirectURL" windowState="<%= WindowState.NORMAL.toString() %>" />

		<aui:form action="<%= portletURL %>" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

			<div class="alert alert-info">
				<liferay-ui:message arguments="<%= emailMessageMaxRecipients %>" key="enter-up-to-x-email-addresses-of-friends-you-would-like-to-invite" translateArguments="<%= false %>" />
			</div>

			<%
			Set invalidEmailAddresses = (Set)SessionErrors.get(renderRequest, "emailAddresses");

			for (int i = 0; i < emailMessageMaxRecipients; i++) {
				String emailAddress = ParamUtil.getString(request, "emailAddress" + i);
			%>

				<c:if test='<%= (invalidEmailAddresses != null) && invalidEmailAddresses.contains("emailAddress" + i) %>'>
					<div class="alert alert-danger">
						<liferay-ui:message key="please-enter-a-valid-email-address" />
					</div>
				</c:if>

				<aui:input cssClass="lfr-input-text-container" label="" name='<%= "emailAddress" + i %>' size="65" title='<%= LanguageUtil.get(request, "email-address") + StringPool.SPACE + (i + 1) %>' type="text" value="<%= emailAddress %>" />

			<%
			}
			%>

			<aui:button-row>
				<aui:button type="submit" value="invite-friends" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>