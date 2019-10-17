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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

long oAuth2ApplicationId = oAuth2Application.getOAuth2ApplicationId();

int oAuth2AuthorizationsCount = OAuth2AuthorizationServiceUtil.getApplicationOAuth2AuthorizationsCount(oAuth2ApplicationId);

OAuth2AuthorizationsManagementToolbarDisplayContext oAuth2AuthorizationsManagementToolbarDisplayContext = new OAuth2AuthorizationsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, currentURLObj);
%>

<clay:management-toolbar
	actionDropdownItems="<%= oAuth2AuthorizationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	disabled="<%= oAuth2AuthorizationsCount == 0 %>"
	filterDropdownItems="<%= oAuth2AuthorizationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= oAuth2AuthorizationsCount %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchContainerId="oAuth2AuthorizationsSearchContainer"
	selectable="<%= true %>"
	showSearch="<%= false %>"
	sortingOrder="<%= oAuth2AuthorizationsManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(oAuth2AuthorizationsManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<portlet:actionURL name="/admin/revoke_oauth2_authorizations" var="revokeOAuth2AuthorizationsURL">
	<portlet:param name="mvcRenderCommandName" value="/admin/view_oauth2_authorizations" />
	<portlet:param name="appTab" value="application_authorizations" />
	<portlet:param name="backURL" value="<%= redirect %>" />
	<portlet:param name="oAuth2ApplicationId" value="<%= String.valueOf(oAuth2ApplicationId) %>" />
</portlet:actionURL>

<div class="container-fluid-1280">
	<aui:form action="<%= revokeOAuth2AuthorizationsURL %>" name="fm">
		<aui:input name="oAuth2ApplicationId" type="hidden" value="<%= oAuth2ApplicationId %>" />
		<aui:input name="oAuth2AuthorizationIds" type="hidden" />

		<liferay-ui:search-container
			emptyResultsMessage="no-authorizations-were-found"
			id="oAuth2AuthorizationsSearchContainer"
			iteratorURL="<%= currentURLObj %>"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			total="<%= oAuth2AuthorizationsCount %>"
		>
			<liferay-ui:search-container-results
				results="<%= OAuth2AuthorizationServiceUtil.getApplicationOAuth2Authorizations(oAuth2ApplicationId, searchContainer.getStart(), searchContainer.getEnd(), oAuth2AuthorizationsManagementToolbarDisplayContext.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.oauth2.provider.model.OAuth2Authorization"
				escapedModel="<%= true %>"
				keyProperty="OAuth2AuthorizationId"
				modelVar="oAuth2Authorization"
			>
				<liferay-ui:search-container-column-text
					property="userId"
				/>

				<liferay-ui:search-container-column-text
					property="userName"
				/>

				<liferay-ui:search-container-column-date
					name="authorization"
					property="createDate"
				/>

				<liferay-ui:search-container-column-date
					name="last-access"
					property="accessTokenCreateDate"
				/>

				<%
				Date expirationDate = oAuth2Authorization.getRefreshTokenExpirationDate();

				if (expirationDate == null) {
					expirationDate = oAuth2Authorization.getAccessTokenExpirationDate();
				}
				%>

				<liferay-ui:search-container-column-date
					name="expiration"
					value="<%= expirationDate %>"
				/>

				<liferay-ui:search-container-column-text
					name="remoteIPInfo"
					value='<%= oAuth2Authorization.getRemoteIPInfo() + ", " + oAuth2Authorization.getRemoteHostInfo() %>'
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/admin/application_authorization_actions.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchContainer="<%= searchContainer %>"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />revokeOAuth2Authorizations() {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-revoke-the-selected-authorizations-they-will-be-revoked-immediately") %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			Liferay.Util.postForm(form, {
				data: {
					oAuth2AuthorizationIds: Liferay.Util.listCheckedExcept(
						form,
						'<portlet:namespace />allRowIds'
					)
				},
				url: '<%= revokeOAuth2AuthorizationsURL %>'
			});
		}
	}

	function <portlet:namespace />revokeOAuth2Authorization(oAuth2AuthorizationId) {
		if (
			confirm(
				'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-revoke-the-authorization") %>'
			)
		) {
			var form = document.<portlet:namespace />fm;

			Liferay.Util.postForm(form, {
				data: {
					oAuth2AuthorizationIds: oAuth2AuthorizationId
				},
				url: '<%= revokeOAuth2AuthorizationsURL %>'
			});
		}
	}
</aui:script>