<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					showSearch="<%= false %>"
					style="fluid"
				/>
			</div>
		</div>
	</div>
</clay:sheet-section>