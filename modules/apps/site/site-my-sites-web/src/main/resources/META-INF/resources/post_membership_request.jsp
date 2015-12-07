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

	portletURL.setParameter("tabs1", "available-sites");

	redirect = portletURL.toString();
}

long groupId = ParamUtil.getLong(request, "groupId");

Group group = GroupLocalServiceUtil.fetchGroup(groupId);

MembershipRequest membershipRequest = (MembershipRequest)request.getAttribute(WebKeys.MEMBERSHIP_REQUEST);
%>

<portlet:actionURL name="postMembershipRequest" var="postMembershipRequestURL">
	<portlet:param name="mvcPath" value="/post_membership_request.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:actionURL>

<aui:form action="<%= postMembershipRequestURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<c:if test="<%= !layout.isTypeControlPanel() %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title='<%= LanguageUtil.format(request, "request-membership-for-x", HtmlUtil.escape(group.getDescriptiveName(locale)), false) %>'
		/>
	</c:if>

	<liferay-ui:error exception="<%= MembershipRequestCommentsException.class %>" message="please-enter-valid-comments" />

	<aui:model-context bean="<%= membershipRequest %>" model="<%= MembershipRequest.class %>" />

	<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
		<div class="alert alert-info">
			<%= HtmlUtil.escape(group.getDescription(locale)) %>
		</div>
	</c:if>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="comments" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>