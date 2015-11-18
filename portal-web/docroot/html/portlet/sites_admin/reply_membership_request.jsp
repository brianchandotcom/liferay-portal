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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ActionUtil.getGroup(request);

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");

ActionUtil.getMembershipRequest(request);

MembershipRequest membershipRequest = (MembershipRequest)request.getAttribute(WebKeys.MEMBERSHIP_REQUEST);
%>

<portlet:actionURL var="replyMembershipRequestURL">
	<portlet:param name="struts_action" value="/sites_admin/reply_membership_request" />
</portlet:actionURL>

<aui:form action="<%= replyMembershipRequestURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="membershipRequestId" type="hidden" value="<%= membershipRequest.getMembershipRequestId() %>" />

	<c:if test="<%= !layout.isTypeControlPanel() %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title='<%= LanguageUtil.format(pageContext, "reply-membership-request-for-x", HtmlUtil.escape(group.getDescriptiveName(locale))) %>'
		/>
	</c:if>

	<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= MembershipRequestCommentsException.class %>" message="please-enter-valid-comments" />

	<liferay-ui:error exception="<%= RequiredGroupException.class %>">

		<%
		RequiredGroupException rge = (RequiredGroupException)errorException;
		%>

		<c:if test="<%= rge.getType() == RequiredGroupException.CURRENT_GROUP %>">
			<liferay-ui:message key="you-cannot-delete-this-site-because-you-are-currently-accessing-this-site" />
		</c:if>

		<c:if test="<%= rge.getType() == RequiredGroupException.PARENT_GROUP %>">
			<liferay-ui:message key="you-cannot-delete-sites-that-have-subsites" />
		</c:if>

		<c:if test="<%= rge.getType() == RequiredGroupException.SYSTEM_GROUP %>">
			<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />
		</c:if>
	</liferay-ui:error>

	<aui:model-context bean="<%= membershipRequest %>" model="<%= MembershipRequest.class %>" />

	<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
		<aui:field-wrapper label="description">
			<p>
				<%= HtmlUtil.escape(group.getDescription()) %>
			</p>
		</aui:field-wrapper>
	</c:if>

	<aui:fieldset>
		<aui:input name="userName" type="resource" value="<%= PortalUtil.getUserName(membershipRequest.getUserId(), StringPool.BLANK) %>" />

		<aui:input name="userComments" readonly="<%= true %>" type="textarea" value="<%= HtmlUtil.escape(membershipRequest.getComments()) %>" />

		<aui:select autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" label="status" name="statusId">
			<aui:option label="approve" value="<%= MembershipRequestConstants.STATUS_APPROVED %>" />
			<aui:option label="deny" value="<%= MembershipRequestConstants.STATUS_DENIED %>" />
		</aui:select>

		<aui:input name="replyComments" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>