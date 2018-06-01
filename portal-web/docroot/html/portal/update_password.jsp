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

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

String referer = ParamUtil.getString(request, WebKeys.REFERER, currentURL);

Ticket ticket = (Ticket)request.getAttribute(WebKeys.TICKET);

String ticketKey = ParamUtil.getString(request, "ticketKey");

if (referer.startsWith(themeDisplay.getPathMain() + "/portal/update_password") && Validator.isNotNull(ticketKey)) {
	referer = themeDisplay.getPathMain();
}
%>

<div class="sheet sheet-lg">
	<div class="sheet-header">
		<div class="autofit-padded-no-gutters-x autofit-row">
			<div class="autofit-col autofit-col-expand">
				<h2 class="sheet-title">
					<liferay-ui:message key="change-password" />
				</h2>
			</div>

			<div class="autofit-col">
				<%@ include file="/html/portal/select_language.jspf" %>
			</div>
		</div>
	</div>

	<div class="sheet-text">
		<c:choose>
			<c:when test="<%= !themeDisplay.isSignedIn() && (ticket == null) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="your-password-reset-link-is-no-longer-valid" />

					<%
					PortletURL portletURL = PortletURLFactoryUtil.create(request, PortletKeys.LOGIN, PortletRequest.RENDER_PHASE);

					portletURL.setParameter("mvcRenderCommandName", "/login/forgot_password");
					portletURL.setWindowState(WindowState.MAXIMIZED);
					%>

					<div class="reset-link-contaner">
						<aui:a href="<%= portletURL.toString() %>" label="request-a-new-password-reset-link"></aui:a>
					</div>
				</div>
			</c:when>
			<c:when test="<%= SessionErrors.contains(request, UserLockoutException.LDAPLockout.class.getName()) %>">
				<div class="alert alert-danger">
					<liferay-ui:message key="this-account-is-locked" />
				</div>
			</c:when>
			<c:when test="<%= SessionErrors.contains(request, UserLockoutException.PasswordPolicyLockout.class.getName()) %>">
				<div class="alert alert-danger">

					<%
					UserLockoutException.PasswordPolicyLockout ule = (UserLockoutException.PasswordPolicyLockout)SessionErrors.get(request, UserLockoutException.PasswordPolicyLockout.class.getName());
					%>

					<liferay-ui:message arguments="<%= ule.user.getUnlockDate() %>" key="this-account-is-locked-until-x" translateArguments="<%= false %>" />
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

					<c:if test="<%= !SessionErrors.isEmpty(request) %>">
						<div class="alert alert-danger">
							<c:choose>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustBeLonger.class.getName()) %>">

									<%
									UserPasswordException.MustBeLonger upe = (UserPasswordException.MustBeLonger)SessionErrors.get(request, UserPasswordException.MustBeLonger.class.getName());
									%>

									<liferay-ui:message arguments="<%= String.valueOf(upe.minLength) %>" key="that-password-is-too-short" translateArguments="<%= false %>" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustComplyWithModelListeners.class.getName()) %>">
									<liferay-ui:message key="that-password-is-invalid-please-enter-a-different-password" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustComplyWithRegex.class.getName()) %>">

									<%
									UserPasswordException.MustComplyWithRegex upe = (UserPasswordException.MustComplyWithRegex)SessionErrors.get(request, UserPasswordException.MustComplyWithRegex.class.getName());
									%>

									<liferay-ui:message arguments="<%= upe.regex %>" key="that-password-does-not-comply-with-the-regular-expression" translateArguments="<%= false %>" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustMatch.class.getName()) %>">
									<liferay-ui:message key="the-passwords-you-entered-do-not-match" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustNotBeEqualToCurrent.class.getName()) %>">
									<liferay-ui:message key="your-new-password-cannot-be-the-same-as-your-old-password-please-enter-a-different-password" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustNotBeNull.class.getName()) %>">
									<liferay-ui:message key="the-password-cannot-be-blank" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustNotBeRecentlyUsed.class.getName()) %>">
									<liferay-ui:message key="that-password-has-already-been-used-please-enter-a-different-password" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustNotBeTrivial.class.getName()) %>">
									<liferay-ui:message key="that-password-uses-common-words-please-enter-a-password-that-is-harder-to-guess-i-e-contains-a-mix-of-numbers-and-letters" />
								</c:when>
								<c:when test="<%= SessionErrors.contains(request, UserPasswordException.MustNotContainDictionaryWords.class.getName()) %>">
									<liferay-ui:message key="that-password-uses-common-dictionary-words" />
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="your-request-failed-to-complete" />
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>

					<aui:fieldset>
						<aui:input autoFocus="<%= true %>" class="lfr-input-text-container" label="password" name="password1" showRequiredLabel="<%= false %>" type="password">
							<aui:validator name="required" />
						</aui:input>

						<aui:input class="lfr-input-text-container" label="enter-again" name="password2" showRequiredLabel="<%= false %>" type="password">
							<aui:validator name="equalTo">
								'#<portlet:namespace />password1'
							</aui:validator>

							<aui:validator name="required" />
						</aui:input>
					</aui:fieldset>

					<aui:button-row>
						<aui:button type="submit" />
					</aui:button-row>
				</aui:form>
			</c:otherwise>
		</c:choose>
	</div>
</div>