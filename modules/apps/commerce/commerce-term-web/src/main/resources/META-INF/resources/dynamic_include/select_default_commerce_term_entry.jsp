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
CommerceAccountEntryDisplay commerceAccountEntryDisplay = CommerceAccountEntryDisplay.of(ParamUtil.getLong(request, "accountEntryId"));

long commerceTermEntryId = 0;

String type = ParamUtil.getString(request, "type");

if (Objects.equals(CommerceTermEntryConstants.TYPE_PAYMENT_TERMS, type)) {
	commerceTermEntryId = commerceAccountEntryDisplay.getDefaultPaymentCommerceTermEntryId();
}
else if (Objects.equals(CommerceTermEntryConstants.TYPE_DELIVERY_TERMS, type)) {
	commerceTermEntryId = commerceAccountEntryDisplay.getDefaultDeliveryCommerceTermEntryId();
}

SearchContainer<CommerceTermEntryDisplay> accountEntryCommerceTermEntryDisplaySearchContainer = AccountEntryCommerceTermEntryDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

accountEntryCommerceTermEntryDisplaySearchContainer.setRowChecker(null);
%>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDefaultCommerceTermEntry" %>'
>
	<liferay-ui:search-container
		searchContainer="<%= accountEntryCommerceTermEntryDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.term.web.internal.display.CommerceTermEntryDisplay"
			keyProperty="commerceTermEntryId"
			modelVar="commerceTermEntryDisplay"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="name"
				value="<%= commerceTermEntryDisplay.getName() %>"
			/>

			<liferay-ui:search-container-column-text>
				<clay:radio
					checked="<%= commerceTermEntryDisplay.getCommerceTermEntryId() == commerceTermEntryId %>"
					cssClass="selector-button"
					data-entityid="<%= commerceTermEntryDisplay.getCommerceTermEntryId() %>"
					label="<%= commerceTermEntryDisplay.getName() %>"
					name='<%= liferayPortletResponse.getNamespace() + "selectCommerceTermEntry" %>'
					showLabel="<%= false %>"
					value="<%= String.valueOf(commerceTermEntryDisplay.getCommerceTermEntryId()) %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>