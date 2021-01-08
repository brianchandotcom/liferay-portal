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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long oAuthApplicationId = ParamUtil.getLong(request, "oAuthApplicationId");

OAuthApplication oAuthApplication = OAuthApplicationLocalServiceUtil.fetchOAuthApplication(oAuthApplicationId);
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= oAuthApplication == null %>"
	title='<%= (oAuthApplication == null) ? "new-application" : oAuthApplication.getName() %>'
/>

<portlet:actionURL name='<%= (oAuthApplication == null) ? "addOAuthApplication" : "updateOAuthApplication" %>' var="editApplicationURL" />

<aui:form action="<%= editApplicationURL %>" method="post" name="fm">
	<aui:input name="mvcPath" type="hidden" value="/admin/edit_application.jsp" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="oAuthApplicationId" type="hidden" value="<%= String.valueOf(oAuthApplicationId) %>" />

	<liferay-ui:error exception="<%= MalformedURLException.class %>" message="please-enter-a-valid-url" />
	<liferay-ui:error exception="<%= RequiredFieldException.class %>" message="this-field-is-required" />

	<aui:model-context bean="<%= oAuthApplication %>" model="<%= OAuthApplication.class %>" />

	<aui:fieldset>
		<aui:input label="application-name" name="name" />

		<aui:input name="description" />

		<aui:input label="website-url" name="websiteURL" />

		<aui:input label="callback-uri" name="callbackURI" />

		<aui:input helpMessage="check-to-allow-multiple-application-instances-per-user" label="share-access-token" name="shareableAccessToken" />

		<c:if test="<%= oAuthApplication == null %>">
			<aui:select label="access-type" name="accessType">
				<aui:option label="<%= OAuthApplicationConstants.LABEL_ACCESS_READ %>" value="<%= OAuthApplicationConstants.ACCESS_READ %>" />
				<aui:option label="<%= OAuthApplicationConstants.LABEL_ACCESS_WRITE %>" value="<%= OAuthApplicationConstants.ACCESS_WRITE %>" />
			</aui:select>
		</c:if>

		<c:if test="<%= oAuthApplication != null %>">
			<aui:field-wrapper label="application-credentials">
				<liferay-ui:message key="consumer-key" />:

				<%= oAuthApplication.getConsumerKey() %><br />

				<liferay-ui:message key="consumer-secret" />:

				<%= oAuthApplication.getConsumerSecret() %>
			</aui:field-wrapper>

			<aui:field-wrapper label="logo">
				<portlet:renderURL var="editLogoURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/admin/edit_application_logo.jsp" />
					<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplicationId) %>" />
				</portlet:renderURL>

				<liferay-ui:logo-selector
					currentLogoURL='<%= HtmlUtil.escape(themeDisplay.getPathImage() + "/logo?img_id=" + oAuthApplication.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(oAuthApplication.getLogoId())) %>'
					defaultLogo="<%= oAuthApplication.getLogoId() == 0 %>"
					defaultLogoURL='<%= themeDisplay.getPathThemeImages() + "/spacer.png" %>'
					logoDisplaySelector=".oauth-application-logo"
					showBackground="<%= false %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>