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
CommercePaymentMethodRegistry commercePaymentMethodRegistry = (CommercePaymentMethodRegistry)request.getAttribute("CommercePaymentMethodRegistry");

SearchContainer<CommercePaymentMethodDisplay> accountEntryCommercePaymentMethodDisplaySearchContainer = AccountEntryCommercePaymentMethodDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse, commercePaymentMethodRegistry);

accountEntryCommercePaymentMethodDisplaySearchContainer.setRowChecker(null);
%>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDefaultCommercePaymentMethod" %>'
>
	<liferay-ui:search-container
		searchContainer="<%= accountEntryCommercePaymentMethodDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.channel.web.internal.display.CommercePaymentMethodDisplay"
			keyProperty="commercePaymentMethodKey"
			modelVar="commercePaymentMethodDisplay"
		>

			<%
			CommerceAccountEntryDisplay commerceAccountEntryDisplay = CommerceAccountEntryDisplay.of(ParamUtil.getLong(request, "accountEntryId"));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-small table-cell-minw-150"
				name="name"
				value="<%= commercePaymentMethodDisplay.getName() %>"
			/>

			<%
			String commercePaymentMethodKey = commerceAccountEntryDisplay.getDefaultCommercePaymentMethodKey();
			%>

			<liferay-ui:search-container-column-text>
				<clay:radio
					checked="<%= commercePaymentMethodKey.equals(commercePaymentMethodDisplay.getCommercePaymentMethodKey()) %>"
					cssClass="selector-button"
					data-entityid="<%= commercePaymentMethodDisplay.getCommercePaymentMethodKey() %>"
					label="<%= commercePaymentMethodDisplay.getName() %>"
					name='<%= liferayPortletResponse.getNamespace() + "selectCommercePaymentMethod" %>'
					showLabel="<%= false %>"
					value="<%= commercePaymentMethodDisplay.getCommercePaymentMethodKey() %>"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>