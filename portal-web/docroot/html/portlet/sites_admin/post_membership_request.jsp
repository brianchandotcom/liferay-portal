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

MembershipRequest membershipRequest = (MembershipRequest)request.getAttribute(WebKeys.MEMBERSHIP_REQUEST);
%>

<portlet:actionURL var="postMembershipRequestURL">
	<portlet:param name="struts_action" value="/sites_admin/post_membership_request" />
</portlet:actionURL>

<aui:form action="<%= postMembershipRequestURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

	<c:if test="<%= !layout.isTypeControlPanel() %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title='<%= LanguageUtil.format(pageContext, "request-membership-for-x", HtmlUtil.escape(group.getDescriptiveName(locale))) %>'
		/>
	</c:if>

	<liferay-ui:error exception="<%= MembershipRequestCommentsException.class %>" message="please-enter-valid-comments" />

	<aui:model-context bean="<%= membershipRequest %>" model="<%= MembershipRequest.class %>" />

	<c:if test="<%= Validator.isNotNull(group.getDescription()) %>">
		<aui:field-wrapper label="description">
			<p>
				<%= HtmlUtil.escape(group.getDescription()) %>
			</p>
		</aui:field-wrapper>
	</c:if>

	<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="comments" />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>