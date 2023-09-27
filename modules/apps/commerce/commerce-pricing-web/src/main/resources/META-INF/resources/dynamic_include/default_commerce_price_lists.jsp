<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			showSearch="<%= false %>"
			style="fluid"
		/>
	</div>
</clay:sheet-section>