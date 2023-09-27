<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

String referer = ParamUtil.getString(request, WebKeys.REFERER, currentURL);

if (referer.equals(themeDisplay.getPathMain() + "/portal/update_email_address")) {
	referer = themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId();
}
%>

<div class="sheet sheet-lg">
	<div class="sheet-header">
		<div class="autofit-padded-no-gutters-x autofit-row">
			<div class="autofit-col autofit-col-expand">
				<h2 class="sheet-title">
					<liferay-ui:message key="change-email-address" />
				</h2>
			</div>

			<div class="autofit-col">
				<%@ include file="/html/portal/select_language.jspf" %>
			</div>
		</div>
	</div>

	<div class="sheet-text">
		<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_email_address" %>' method="post" name="fm">
			<aui:input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
			<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= referer %>" />

			<c:if test="<%= !SessionErrors.isEmpty(request) %>">
				<div class="alert alert-danger">
					<c:choose>
						<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.MustBeEqual.class.getName()) %>">
							<liferay-ui:message key="the-email-addresses-you-entered-do-not-match" />
						</c:when>
						<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.MustNotBeDuplicate.class.getName()) %>">
							<liferay-ui:message key="the-email-address-you-requested-is-already-taken" />
						</c:when>
						<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.MustNotBeNull.class.getName()) %>">
							<liferay-ui:message key="please-enter-an-email-address" />
						</c:when>
						<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.MustNotBePOP3User.class.getName()) || SessionErrors.contains(request, UserEmailAddressException.MustNotBeReserved.class.getName()) %>">
							<liferay-ui:message key="the-email-address-you-requested-is-reserved" />
						</c:when>
						<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.MustNotUseCompanyMx.class.getName()) %>">
							<liferay-ui:message key="the-email-address-you-requested-is-not-valid-because-its-domain-is-reserved" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="please-enter-a-valid-email-address" />
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<aui:fieldset label="email-address">
				<aui:input class="lfr-input-text-container" label="email-address" name="emailAddress1" type="text" value='<%= ParamUtil.getString(request, "emailAddress1") %>' />

				<aui:input class="lfr-input-text-container" label="enter-again" name="emailAddress2" type="text" value='<%= ParamUtil.getString(request, "emailAddress2") %>' />
			</aui:fieldset>

			<aui:button-row>
				<aui:button type="submit" />

				<aui:button href="<%= themeDisplay.getPathMain() %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</div>
</div>