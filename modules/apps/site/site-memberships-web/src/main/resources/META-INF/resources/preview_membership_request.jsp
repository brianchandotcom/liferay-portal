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

renderResponse.setTitle(userName);
%>

<div class="container-fluid-1280">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<h4 class="text-default">
				<liferay-ui:message arguments="<%= userName %>" key="requested-by-x" />
			</h4>

			<div class="nameplate">
				<div class="nameplate-field">
					<liferay-ui:user-portrait
						userId="<%= membershipRequest.getUserId() %>"
					/>
				</div>

				<div class="nameplate-content">
					<small class="text-default">
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - membershipRequest.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</small>

					<p>
						<%= membershipRequest.getComments() %>
					</p>
				</div>
			</div>

			<%
			User membershipRequestReplierUser = UserLocalServiceUtil.fetchUserById(membershipRequest.getReplierUserId());

			String replier = StringPool.BLANK;

			if (membershipRequestReplierUser != null) {
				if (membershipRequestReplierUser.isDefaultUser()) {
					Company membershipRequestReplierCompany = CompanyLocalServiceUtil.getCompanyById(membershipRequestReplierUser.getCompanyId());

					replier = HtmlUtil.escape(membershipRequestReplierCompany.getName());
				}
				else {
					replier = HtmlUtil.escape(membershipRequestReplierUser.getFullName());
				}
			}
			else {
				replier = LanguageUtil.get(request, "the-user-could-not-be-found");
			}
			%>

			<h4 class="text-default">
				<liferay-ui:message arguments="<%= replier %>" key="replied-by-x" />
			</h4>

			<div class="nameplate">
				<c:if test="<%= membershipRequestReplierUser != null %>">
					<div class="nameplate-field">
						<liferay-ui:user-portrait
							user="<%= membershipRequestReplierUser %>"
						/>
					</div>
				</c:if>

				<div class="nameplate-content">
					<small class="text-default">
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - membershipRequest.getReplyDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</small>

					<p>
						<%= membershipRequest.getReplyComments() %>
					</p>
				</div>
			</div>

			<h4 class="text-default">
				<strong><liferay-ui:message key="status" /></strong>
			</h4>

			<c:choose>
				<c:when test="<%= membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_APPROVED %>">
					<p class="approved status">
						<liferay-ui:message key="approved" />
					</p>
				</c:when>
				<c:when test="<%= membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_DENIED %>">
					<p class="denied status">
						<liferay-ui:message key="denied" />
					</p>
				</c:when>
			</c:choose>
		</aui:fieldset>
	</aui:fieldset-group>
</div>