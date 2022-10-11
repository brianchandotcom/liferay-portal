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
CommerceChannelAccountEntryRelDisplayContext commerceChannelAccountEntryRelDisplayContext = (CommerceChannelAccountEntryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:sheet-section
	id='<%= liferayPortletResponse.getNamespace() + "defaultCommercePaymentMethod" %>'
>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="default-account-commerce-payment-methods" /></span>
		</clay:content-col>
	</clay:content-row>

	<div class="form-group-autofit">
		<div class="form-group-item">
			<div class="sheet-text">
				<frontend-data-set:classic-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"accountEntryId", String.valueOf(commerceChannelAccountEntryRelDisplayContext.getAccountEntryId())
						).build()
					%>'
					dataProviderKey="<%= CommercePaymentMethodGroupRelFDSNames.ACCOUNT_ENTRY_DEFAULT_PAYMENTS %>"
					id="<%= CommercePaymentMethodGroupRelFDSNames.ACCOUNT_ENTRY_DEFAULT_PAYMENTS %>"
					itemsPerPage="<%= 10 %>"
				/>
			</div>
		</div>
	</div>
</clay:sheet-section>