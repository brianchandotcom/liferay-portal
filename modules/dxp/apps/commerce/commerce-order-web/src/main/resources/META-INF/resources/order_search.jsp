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
CommerceOrderListDisplayContext commerceOrderListDisplayContext = (CommerceOrderListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrder> commerceOrderSearchContainer = commerceOrderListDisplayContext.getSearchContainer();

CommerceOrderDisplayTerms commerceOrderDisplayTerms = (CommerceOrderDisplayTerms)commerceOrderSearchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	buttonLabel="search"
	displayTerms="<%= commerceOrderDisplayTerms %>"
	id="toggle_id_commerce_order_search"
	markupView="lexicon"
>
	<aui:fieldset>
		<aui:field-wrapper label="order-date">
			<liferay-ui:input-date
				dayParam="<%= CommerceOrderDisplayTerms.START_CREATE_DATE_DAY %>"
				dayValue="<%= commerceOrderDisplayTerms.getStartCreateDateDay() %>"
				monthParam="<%= CommerceOrderDisplayTerms.START_CREATE_DATE_MONTH %>"
				monthValue="<%= commerceOrderDisplayTerms.getStartCreateDateMonth() %>"
				name="<%= CommerceOrderDisplayTerms.START_CREATE_DATE %>"
				yearParam="<%= CommerceOrderDisplayTerms.START_CREATE_DATE_YEAR %>"
				yearValue="<%= commerceOrderDisplayTerms.getStartCreateDateYear() %>"
			/>

			<liferay-ui:message key="to" />

			<liferay-ui:input-date
				dayParam="<%= CommerceOrderDisplayTerms.END_CREATE_DATE_DAY %>"
				dayValue="<%= commerceOrderDisplayTerms.getStartCreateDateDay() %>"
				monthParam="<%= CommerceOrderDisplayTerms.END_CREATE_DATE_MONTH %>"
				monthValue="<%= commerceOrderDisplayTerms.getStartCreateDateMonth() %>"
				name="<%= CommerceOrderDisplayTerms.END_CREATE_DATE %>"
				yearParam="<%= CommerceOrderDisplayTerms.END_CREATE_DATE_YEAR %>"
				yearValue="<%= commerceOrderDisplayTerms.getStartCreateDateYear() %>"
			/>
		</aui:field-wrapper>
	</aui:fieldset>
</liferay-ui:search-toggle>