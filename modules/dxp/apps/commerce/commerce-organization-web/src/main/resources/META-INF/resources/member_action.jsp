<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

User organizationUser = (User)row.getObject();

String editURL = commerceOrganizationMembersDisplayContext.getEditURL(organizationUser);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= Validator.isNotNull(editURL) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<portlet:actionURL name="inviteUser" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REMOVE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
		<portlet:param name="userId" value="<%= String.valueOf(organizationUser.getUserId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>