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
User user2 = (User)request.getAttribute("view_user.jsp-user");

boolean viewRelationActions = true;

if (SocialRelationLocalServiceUtil.hasRelation(user2.getUserId(), themeDisplay.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY)) {
	viewRelationActions = false;
}
else if (SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY)) {
	viewRelationActions = false;
}
%>

<c:if test="<%= viewRelationActions %>">
	<c:choose>
		<c:when test="<%= SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION) %>">
			<portlet:actionURL name="deleteSocialRelation" var="removeConnectionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
				<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_BI_CONNECTION) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				cssClass="action remove-connection"
				image="../aui/minus-sign"
				label="<%= true %>"
				message="disconnect"
				method="get"
				url="<%= removeConnectionURL %>"
			/>
		</c:when>
		<c:when test="<%= SocialRequestLocalServiceUtil.hasRequest(themeDisplay.getUserId(), User.class.getName(), themeDisplay.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION, user2.getUserId(), SocialRequestConstants.STATUS_PENDING) %>">
			<liferay-ui:icon
				cssClass="disabled"
				image="../aui/user"
				label="<%= true %>"
				message="connection-requested"
			/>
		</c:when>
		<c:when test="<%= SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION) %>">
			<portlet:actionURL name="requestSocialRelation" var="addConnectionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
				<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_BI_CONNECTION) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				cssClass="action add-connection"
				image="../aui/plus-sign"
				label="<%= true %>"
				message="connect"
				method="get"
				url="<%= addConnectionURL %>"
			/>
		</c:when>
	</c:choose>

	<c:choose>
		<c:when test="<%= SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER) %>">
			<portlet:actionURL name="deleteSocialRelation" var="unfollowURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
				<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_FOLLOWER) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				cssClass="action unfollow"
				image="../aui/minus-sign"
				label="<%= true %>"
				message="unfollow"
				method="get"
				url="<%= unfollowURL %>"
			/>
		</c:when>
		<c:when test="<%= SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER) %>">
			<portlet:actionURL name="addSocialRelation" var="followURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
				<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_FOLLOWER) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				cssClass="action follow"
				image="../aui/plus-sign"
				label="<%= true %>"
				message="follow"
				method="get"
				url="<%= followURL %>"
			/>
		</c:when>
	</c:choose>
</c:if>

<c:choose>
	<c:when test="<%= SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY) %>">
		<portlet:actionURL name="deleteSocialRelation" var="unblockURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_ENEMY) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			cssClass="action unblock"
			image="../aui/ok"
			label="<%= true %>"
			message="unblock"
			method="get"
			url="<%= unblockURL %>"
		/>
	</c:when>
	<c:when test="<%= SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY) %>">
		<portlet:actionURL name="addSocialRelation" var="blockURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_ENEMY) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			cssClass="action block"
			image="../aui/ban-circle"
			label="<%= true %>"
			message="block"
			method="get"
			url="<%= blockURL %>"
		/>
	</c:when>
</c:choose>

<portlet:resourceURL id="exportVCard" var="exportURL">
	<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
</portlet:resourceURL>

<liferay-ui:icon
	image="../aui/save"
	label="<%= true %>"
	message="vcard"
	url="<%= exportURL %>"
/>

<aui:script use="aui-base,aui-io-plugin-deprecated">
	var contactAction = A.one('.contacts-portlet .contacts-action');

	if (contactAction) {
		contactAction.delegate(
			'click',
			function(event) {
				event.preventDefault();

				Liferay.Util.fetch(event.currentTarget.getAttribute('href'))
					.then(function(response) {
						return response.text();
					})
					.then(function(data) {
						var contactProfile = A.one(
							'.contacts-portlet .contacts-container'
						);

						if (!contactProfile.io) {
							contactProfile.plug(A.Plugin.IO, {
								autoLoad: false
							});
						}

						<liferay-portlet:renderURL var="viewSummaryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
							<portlet:param name="mvcPath" value="/view_user.jsp" />
							<portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" />
						</liferay-portlet:renderURL>

						contactProfile.io.set('uri', '<%= viewSummaryURL %>');
						contactProfile.io.start();
					});
			},
			'.action a'
		);
	}
</aui:script>