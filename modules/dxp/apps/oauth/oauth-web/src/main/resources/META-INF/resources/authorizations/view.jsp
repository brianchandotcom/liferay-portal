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

<liferay-portlet:renderURL varImpl="iteratorURL">
	<portlet:param name="mvcPath" value="/authorizations/view.jsp" />
</liferay-portlet:renderURL>

<liferay-ui:search-container
	emptyResultsMessage="no-oauth-users-were-found"
	iteratorURL="<%= iteratorURL %>"
	total="<%= OAuthUserLocalServiceUtil.getUserOAuthUsersCount(themeDisplay.getUserId()) %>"
>
	<liferay-ui:search-container-results
		results="<%= OAuthUserLocalServiceUtil.getUserOAuthUsers(themeDisplay.getUserId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.oauth.model.OAuthUser"
		modelVar="oAuthUser"
	>

		<%
		OAuthApplication oAuthApplication = OAuthApplicationLocalServiceUtil.getOAuthApplication(oAuthUser.getOAuthApplicationId());
		%>

		<liferay-ui:search-container-column-text
			name="application-name"
			value="<%= oAuthApplication.getName() %>"
		/>

		<liferay-ui:search-container-column-text
			name="access-token"
			property="accessToken"
		/>

		<liferay-ui:search-container-column-text
			name="access-secret"
			property="accessSecret"
		/>

		<liferay-ui:search-container-column-text
			name="access-type"
			translate="<%= true %>"
			value="<%= oAuthApplication.getAccessLevelLabel() %>"
		/>

		<c:if test="<%= OAuthUserPermission.contains(permissionChecker, oAuthUser, OAuthActionKeys.DELETE) %>">
			<liferay-portlet:actionURL name="deleteOAuthUser" var="revokeURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplication.getOAuthApplicationId()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:search-container-column-text
				href="<%= revokeURL %>"
				translate="<%= true %>"
				value="revoke"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>