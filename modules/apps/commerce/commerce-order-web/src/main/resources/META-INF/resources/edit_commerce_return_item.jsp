<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceReturnEditDisplayContext commerceReturnEditDisplayContext = (CommerceReturnEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderItem commerceOrderItem = commerceReturnEditDisplayContext.getCommerceReturnItemCommerceOrderItem();
CommerceReturn commerceReturn = commerceReturnEditDisplayContext.getCommerceReturn();
CommerceReturnItem commerceReturnItem = commerceReturnEditDisplayContext.getCommerceReturnItem();
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommerceReturnItemScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_RETURN_ITEM_GENERAL %>"
	screenNavigatorModelBean="<%= commerceReturnItem %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title='<%= LanguageUtil.format(request, "edit-x", (commerceOrderItem == null) ? "sku" : commerceOrderItem.getSku()) %>'
/>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"autocompleteAPIURL", commerceReturnEditDisplayContext.getListTypeEntriesByExternalReferenceCodeURL()
		).put(
			"autocompleteInitialLabel", commerceReturnEditDisplayContext.getResolutionMethodName()
		).put(
			"autocompleteInitialValue", (commerceReturnItem == null) ? StringPool.BLANK : commerceReturnItem.getReturnResolutionMethod()
		).put(
			"commerceReturnItemId", commerceReturnEditDisplayContext.getCommerceReturnItemId()
		).put(
			"readOnly", ArrayUtil.contains(CommerceReturnConstants.RETURN_STATUSES_LATEST, commerceReturn.getReturnStatus()) || StringUtil.equals(CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED, commerceReturnItem.getReturnItemStatus())
		).build()
	%>'
	module="{editCommerceReturnItem} from commerce-order-web"
/>