<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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