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

<%@ include file="/html/taglib/theme/portlet_messages/init.jsp" %>

<%
Group group = (Group)request.getAttribute("liferay-theme:portlet-messages:group");
Portlet portlet = (Portlet)request.getAttribute("liferay-theme:portlet-messages:portlet");

Group liveGroup = group;

boolean inStaging = false;

if (group.isControlPanel()) {
	long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

	if (doAsGroupId > 0) {
		try {
			liveGroup = GroupLocalServiceUtil.getGroup(doAsGroupId);

			if (liveGroup.isStagingGroup()) {
				liveGroup = liveGroup.getLiveGroup();

				inStaging = true;
			}
		}
		catch (Exception e) {
		}
	}
}
else if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();

	inStaging = true;
}
%>

<c:if test="<%= liveGroup.isStaged() && !liveGroup.isStagedPortlet(portlet.getRootPortletId()) %>">
	<c:choose>
		<c:when test="<%= !liveGroup.isStagedRemotely() && inStaging %>">
			<div class="alert alert-warning lfr-portlet-message-staging-alert">
				<liferay-ui:message key="this-portlet-is-not-staged-local-alert" />
			</div>
		</c:when>
		<c:when test="<%= liveGroup.isStagedRemotely() && themeDisplay.isSignedIn() %>">
			<div class="alert alert-warning lfr-portlet-message-staging-alert">
				<liferay-ui:message key="this-portlet-is-not-staged-remote-alert" />
			</div>
		</c:when>
	</c:choose>
</c:if>

<c:if test='<%= MultiSessionMessages.contains(renderRequest, "requestProcessed") && !MultiSessionMessages.contains(renderRequest, portlet.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE) %>'>

	<%
	String successMessage = (String)MultiSessionMessages.get(renderRequest, "requestProcessed");
	%>

	<liferay-util:buffer
		var="successHtml"
	>
		<c:choose>
			<c:when test='<%= Validator.isNotNull(successMessage) && !successMessage.equals("request_processed") %>'>
				<%= HtmlUtil.escape(successMessage) %>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="your-request-completed-successfully" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= themeDisplay.isStatePopUp() && MultiSessionMessages.contains(renderRequest, portlet.getPortletId() + SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT) %>">

			<%
			String taglibMessage = "class=\"lfr-hide-dialog\" href=\"javascript:;\"";
			%>

			<liferay-ui:message arguments="<%= taglibMessage %>" key="the-page-will-be-refreshed-when-you-close-this-dialog.alternatively-you-can-hide-this-dialog-x" translateArguments="<%= false %>" />
		</c:if>
	</liferay-util:buffer>

	<liferay-ui:success key="requestProcessed" message="<%= successHtml %>" />
</c:if>

<liferay-ui:success key="<%= portlet.getPortletId() + SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION %>" message="you-have-successfully-updated-the-setup" />
<liferay-ui:success key="<%= portlet.getPortletId() + SessionMessages.KEY_SUFFIX_UPDATED_PREFERENCES %>" message="you-have-successfully-updated-your-preferences" />

<c:if test="<%= !MultiSessionMessages.contains(renderRequest, portlet.getPortletId() + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE) %>">
	<liferay-ui:error embed="<%= false %>" />
</c:if>