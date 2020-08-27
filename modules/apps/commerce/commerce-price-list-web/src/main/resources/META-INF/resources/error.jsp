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

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchCPInstanceException.class %>" message="the-sku-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchPriceEntryException.class %>" message="the-entry-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchPriceListException.class %>" message="the-price-list-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchTierPriceEntryException.class %>" message="the-tier-price-entry-could-not-be-found" />

<liferay-ui:error-principal />