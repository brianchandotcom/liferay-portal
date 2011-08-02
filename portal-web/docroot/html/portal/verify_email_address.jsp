<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
PasswordPolicy passwordPolicy = user.getPasswordPolicy();

String ticketKey = ParamUtil.getString(request, "ticketKey");
%>

<aui:form action='<%= themeDisplay.getPathMain() + "/portal/verify_email_address" %>' method="post" name="fm">
	<aui:input name="p_l_id" type="hidden" value="<%= layout.getPlid() %>" />
	<aui:input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
	<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value='<%= themeDisplay.getPathMain() + "?doAsUserId=" + themeDisplay.getDoAsUserId() %>' />

	<div class="portlet-msg-info">
		<liferay-ui:message key="please-enter-your-verification-code" />
	</div>

	<c:if test="<%= !SessionErrors.isEmpty(request) %>">
		<c:choose>
			<c:when test="<%= SessionErrors.contains(request, DuplicateUserEmailAddressException.class.getName()) %>">
				<div class="portlet-msg-error">
					<liferay-ui:message key="the-email-address-you-requested-is-already-taken" />
				</div>
			</c:when>
			<c:when test="<%= SessionErrors.contains(request, ReservedUserEmailAddressException.class.getName()) %>">
				<div class="portlet-msg-error">
					<liferay-ui:message key="the-email-address-you-requested-is-reserved" />
				</div>
			</c:when>
			<c:when test="<%= SessionErrors.contains(request, UserEmailAddressException.class.getName()) %>">
				<div class="portlet-msg-error">
					<liferay-ui:message key="please-enter-a-valid-email-address" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="portlet-msg-error">
					<liferay-ui:message key="please-enter-a-valid-verification-code" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>

	<aui:input class="lfr-input-text-container" label="email-verification-code" name="ticketKey" type="text" value="<%= ticketKey %>" size="36" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.fm.ticketKey);
</aui:script>