<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/subscription_info/init.jsp" %>

<c:if test="<%= Validator.isNotNull(subscriptionPeriod) || Validator.isNotNull(durationPeriod) %>">
	<div class="row">
		<div>
			<liferay-ui:message key="payment-subscription" />
		</div>

		<div>
			<c:if test="<%= Validator.isNotNull(subscriptionPeriod) %>">
				<span class="product-subscription-period">(<%= HtmlUtil.escape(subscriptionPeriod) %>)</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(durationPeriod) %>">
				<span class="product-subscription-period"> <%= HtmlUtil.escape(durationPeriod) %></span>
			</c:if>
		</div>
	</div>
</c:if>

<c:if test="<%= Validator.isNotNull(deliverySubscriptionPeriod) || Validator.isNotNull(deliveryDurationPeriod) %>">
	<div class="row">
		<div>
			<liferay-ui:message key="delivery-subscription" />
		</div>

		<div>
			<c:if test="<%= Validator.isNotNull(deliverySubscriptionPeriod) %>">
				<span class="product-subscription-period">(<%= HtmlUtil.escape(deliverySubscriptionPeriod) %>)</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(deliveryDurationPeriod) %>">
				<span class="product-subscription-period"> <%= HtmlUtil.escape(deliveryDurationPeriod) %></span>
			</c:if>
		</div>
	</div>
</c:if>