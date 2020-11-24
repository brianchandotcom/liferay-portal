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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);
%>

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="default-account-addresses" /></span>
		</clay:content-col>
	</clay:content-row>

	<div class="form-group-autofit">
		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="billing" />

				<clay:icon
					symbol="credit-card"
				/>
			</div>

			<%
			Address defaultBillingAddress = accountEntryDisplay.getDefaultBillingAddress();
			%>

			<c:choose>
				<c:when test="<%= defaultBillingAddress == null %>">
					<liferay-ui:message key="not-set" />
				</c:when>
				<c:otherwise>
					<h4><%= defaultBillingAddress.getName() %></h4>

					<liferay-text-localizer:address-display
						address="<%= defaultBillingAddress %>"
					/>

					<span class="autofit-row"><%= defaultBillingAddress.getPhoneNumber() %></span>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="form-group-item">
			<div class="sheet-text">
				<liferay-ui:message key="shipping" />

				<clay:icon
					symbol="truck"
				/>
			</div>

			<%
			Address defaultShippingAddress = accountEntryDisplay.getDefaultShippingAddress();
			%>

			<c:choose>
				<c:when test="<%= defaultShippingAddress == null %>">
					<liferay-ui:message key="not-set" />
				</c:when>
				<c:otherwise>
					<h4><%= defaultShippingAddress.getName() %></h4>

					<liferay-text-localizer:address-display
						address="<%= defaultShippingAddress %>"
					/>

					<span class="autofit-row"><%= defaultShippingAddress.getPhoneNumber() %></span>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</clay:sheet-section>