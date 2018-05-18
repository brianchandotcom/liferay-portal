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

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchCPInstanceException.class %>" message="the-sku-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchPriceEntryException.class %>" message="the-entry-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchPriceListException.class %>" message="the-price-list-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchTierPriceEntryException.class %>" message="the-tier-price-entry-could-not-be-found" />

<liferay-ui:error-principal />