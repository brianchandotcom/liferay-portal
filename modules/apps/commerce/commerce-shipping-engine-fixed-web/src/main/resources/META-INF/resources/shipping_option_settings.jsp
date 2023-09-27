<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShippingFixedOptionRelsDisplayContext commerceShippingFixedOptionRelsDisplayContext = (CommerceShippingFixedOptionRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= commerceShippingFixedOptionRelsDisplayContext.isVisible() %>">
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceShippingMethodId", String.valueOf(commerceShippingFixedOptionRelsDisplayContext.getCommerceShippingMethodId())
				).build()
			%>'
			creationMenu="<%= commerceShippingFixedOptionRelsDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTION_SETTINGS %>"
			id="<%= CommerceShippingFixedOptionFDSNames.SHIPPING_FIXED_OPTION_SETTINGS %>"
			itemsPerPage="<%= 10 %>"
			showManagementBar="<%= true %>"
		/>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-no-shipping-options" />
			<liferay-ui:message key="please-add-at-least-one-shipping-option" />
		</div>
	</c:otherwise>
</c:choose>