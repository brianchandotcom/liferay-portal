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

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/view_membership_requests.jsp");

	redirect = portletURL.toString();
}

long membershipRequestId = ParamUtil.getLong(request, "membershipRequestId");

MembershipRequest membershipRequest = MembershipRequestLocalServiceUtil.getMembershipRequest(membershipRequestId);

String userName = PortalUtil.getUserName(membershipRequest.getUserId(), StringPool.BLANK);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "reply-membership-request-for-x", userName));
%>

<portlet:actionURL name="replyMembershipRequest" var="replyMembershipRequestURL">
	<portlet:param name="mvcPath" value="/reply_membership_request.jsp" />
	<portlet:param name="p_u_i_d" value="<%= String.valueOf(membershipRequest.getUserId()) %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupIdOrLiveGroupId()) %>" />
	<portlet:param name="membershipRequestId" value="<%= String.valueOf(membershipRequest.getMembershipRequestId()) %>" />
</portlet:actionURL>

<aui:form action="<%= replyMembershipRequestURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="membershipRequestId" type="hidden" value="<%= membershipRequest.getMembershipRequestId() %>" />

	<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= GroupKeyException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= MembershipRequestCommentsException.class %>" message="please-enter-valid-comments" />
	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
	<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

	<aui:model-context bean="<%= membershipRequest %>" model="<%= MembershipRequest.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>

			<%
			Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getSiteGroupIdOrLiveGroupId());
			%>

			<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
				<h4 class="text-default"><liferay-ui:message key="description" /></h4>

				<p class="text-default">
					<%= HtmlUtil.escape(group.getDescription(locale)) %>
				</p>
			</c:if>

			<liferay-ui:user-portrait
				userId="<%= membershipRequest.getUserId() %>"
			/>

			<aui:input name="userName" type="resource" value="<%= userName %>" />

			<aui:input name="userComments" readonly="<%= true %>" type="textarea" value="<%= membershipRequest.getComments() %>" />

			<aui:select autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" label="status" name="statusId">
				<aui:option label="approve" value="<%= MembershipRequestConstants.STATUS_APPROVED %>" />
				<aui:option label="deny" value="<%= MembershipRequestConstants.STATUS_DENIED %>" />
			</aui:select>

			<aui:input name="replyComments" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>