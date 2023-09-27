<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/unsubscribe/init.jsp" %>

<%
String subscriptionTitle = ParamUtil.getString(request, "subscriptionTitle");

long userId = ParamUtil.getLong(request, "userId");

User unsubscribedUser = UserLocalServiceUtil.getUser(userId);

PortletURL manageSubscriptionsURL = PortletProviderUtil.getPortletURL(request, Subscription.class.getName(), PortletProvider.Action.MANAGE);

if (manageSubscriptionsURL != null) {
	manageSubscriptionsURL.setWindowState(LiferayWindowState.MAXIMIZED);
}
%>

<div class="successful">
	<liferay-ui:icon
		cssClass="unsubscribe-success-icon"
		icon="check-circle"
		markupView="lexicon"
	/>

	<h3>
		<liferay-ui:message key="unsubscribe-successful" />
	</h3>

	<p>
		<c:choose>
			<c:when test="<%= Validator.isNotNull(subscriptionTitle) %>">
				<liferay-ui:message arguments="<%= subscriptionTitle %>" key="you-have-been-removed-from-x" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="you-are-already-unsubscribed" />
			</c:otherwise>
		</c:choose>
	</p>

	<p>
		<liferay-ui:message arguments="<%= unsubscribedUser.getEmailAddress() %>" key="we-will-not-send-you-emails-to-x-anymore" />
	</p>

	<c:if test="<%= manageSubscriptionsURL != null %>">
		<p class="help">
			<h4>
				<liferay-ui:message key="did-you-unsubscribe-by-accident" />
			</h4>

			<a href="<%= manageSubscriptionsURL.toString() %>">
				<liferay-ui:message key="manage-your-subscriptions" />
			</a>
		</p>
	</c:if>
</div>