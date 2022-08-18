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

AccountEntry accountEntry = commerceChannelAccountEntryRelDisplayContext.getAccountEntry();
%>

<clay:sheet-section
	id='<%= liferayPortletResponse.getNamespace() + "defaultCommercePriceLists" %>'
>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="price-lists" /></span>
		</clay:content-col>
	</clay:content-row>

	<div id="<portlet:namespace />defaultCommercePriceLists">
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"accountEntryId", String.valueOf(accountEntry.getAccountEntryId())
				).put(
					"type", String.valueOf(CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST)
				).build()
			%>'
			creationMenu="<%= commerceChannelAccountEntryRelDisplayContext.getCreationMenu(CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST) %>"
			dataProviderKey="<%= CommercePricingFDSNames.ACCOUNT_ENTRY_PRICE_LISTS %>"
			id="<%= CommercePricingFDSNames.ACCOUNT_ENTRY_PRICE_LISTS %>"
			itemsPerPage="<%= 10 %>"
			style="fluid"
		/>
	</div>
</clay:sheet-section>