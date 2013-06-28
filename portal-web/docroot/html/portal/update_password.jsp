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

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

String referer = ParamUtil.getString(request, WebKeys.REFERER, currentURL);

if (referer.equals(themeDisplay.getPathMain() + "/portal/update_password")) {
	referer = themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId();
}

PasswordPolicy passwordPolicy = user.getPasswordPolicy();

String ticketKey = ParamUtil.getString(request, "ticketKey");
%>

<c:choose>
	<c:when test="<%= SessionErrors.contains(request, UserLockoutException.class.getName()) %>">
		<div class="alert alert-error">
			<liferay-ui:message key="this-account-has-been-locked" />
		</div>
	</c:when>
	<c:otherwise>
		<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_password" %>' method="post" name="fm">
			<aui:input name="p_l_id" type="hidden" value="<%= layout.getPlid() %>" />
			<aui:input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
			<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= referer %>" />
			<aui:input name="ticketKey" type="hidden" value="<%= ticketKey %>" />

			<div class="alert alert-info">
				<liferay-ui:message key="please-set-a-new-password" />
			</div>

			<c:if test="<%= SessionErrors.contains(request, UserPasswordException.class.getName()) %>">

				<%
				UserPasswordException upe = (UserPasswordException)SessionErrors.get(request, UserPasswordException.class.getName());
				%>

				<div class="alert alert-error">
					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_ALREADY_USED %>">
						<liferay-ui:message key="that-password-has-already-been-used-please-enter-in-a-different-password" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_CONTAINS_TRIVIAL_WORDS %>">
						<liferay-ui:message key="that-password-uses-common-words-please-enter-in-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_INVALID %>">
						<liferay-ui:message key="that-password-is-invalid-please-enter-in-a-different-password" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_LENGTH %>">
						<%= LanguageUtil.format(pageContext, "that-password-is-too-short-or-too-long-please-make-sure-your-password-is-between-x-and-512-characters", String.valueOf(passwordPolicy.getMinLength()), false) %>
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_NOT_CHANGEABLE %>">
						<liferay-ui:message key="your-password-cannot-be-changed" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_SAME_AS_CURRENT %>">
						<liferay-ui:message key="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-in-a-different-password" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_TOO_TRIVIAL %>">
						<liferay-ui:message key="that-password-is-too-trivial" />
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORD_TOO_YOUNG %>">
						<%= LanguageUtil.format(pageContext, "you-cannot-change-your-password-yet-please-wait-at-least-x-before-changing-your-password-again", LanguageUtil.getTimeDescription(pageContext, passwordPolicy.getMinAge() * 1000), false) %>
					</c:if>

					<c:if test="<%= upe.getType() == UserPasswordException.PASSWORDS_DO_NOT_MATCH %>">
						<liferay-ui:message key="the-passwords-you-entered-do-not-match-each-other-please-re-enter-your-password" />
					</c:if>
				</div>
			</c:if>

			<aui:fieldset label="new-password">
				<aui:input autoFocus="<%= true %>" class="lfr-input-text-container" label="password" name="password1" type="password" />

				<aui:input class="lfr-input-text-container" label="enter-again" name="password2" type="password" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</c:otherwise>
</c:choose>