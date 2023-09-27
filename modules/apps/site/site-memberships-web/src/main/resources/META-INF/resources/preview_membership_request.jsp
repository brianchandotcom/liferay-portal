<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view_membership_requests.jsp"
	).buildString();
}

long membershipRequestId = ParamUtil.getLong(request, "membershipRequestId");

MembershipRequest membershipRequest = MembershipRequestLocalServiceUtil.getMembershipRequest(membershipRequestId);

String userName = PortalUtil.getUserName(membershipRequest.getUserId(), StringPool.BLANK);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(userName);
%>

<clay:container-fluid>
	<div class="sheet">
		<div class="panel-group panel-group-flush">
			<aui:fieldset>
				<h4 class="text-default">
					<liferay-ui:message arguments="<%= userName %>" key="requested-by-x" />
				</h4>

				<div class="nameplate">
					<div class="nameplate-field">
						<liferay-user:user-portrait
							userId="<%= membershipRequest.getUserId() %>"
						/>
					</div>

					<div class="nameplate-content">
						<small class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - membershipRequest.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</small>

						<p>
							<%= HtmlUtil.escape(membershipRequest.getComments()) %>
						</p>
					</div>
				</div>

				<%
				User membershipRequestReplierUser = UserLocalServiceUtil.fetchUserById(membershipRequest.getReplierUserId());

				String replier = StringPool.BLANK;

				if (membershipRequestReplierUser != null) {
					if (membershipRequestReplierUser.isGuestUser()) {
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
							<liferay-user:user-portrait
								user="<%= membershipRequestReplierUser %>"
							/>
						</div>
					</c:if>

					<div class="nameplate-content">
						<small class="text-default">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - membershipRequest.getReplyDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</small>

						<p>
							<%= HtmlUtil.escape(membershipRequest.getReplyComments()) %>
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
		</div>
	</div>
</clay:container-fluid>