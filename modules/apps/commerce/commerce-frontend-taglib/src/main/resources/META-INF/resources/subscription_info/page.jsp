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

<%@ include file="/subscription_info/init.jsp" %>

<c:if test="<%= Validator.isNotNull(subscriptionPeriod) || Validator.isNotNull(durationPeriod) %>">
	<div class="row">
		<div>
			<%= LanguageUtil.get(request, "payment-subscription") %>
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
			<%= LanguageUtil.get(request, "delivery-subscription") %>
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