<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceModifier commercePriceModifier = commercePriceListDisplayContext.getCommercePriceModifier();
long commercePriceModifierId = commercePriceListDisplayContext.getCommercePriceModifierId();
%>

<c:if test="<%= commercePriceListDisplayContext.hasPermission(commercePriceListDisplayContext.getCommercePriceListId(), ActionKeys.UPDATE) %>">
	<div id="item-finder-root"></div>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"commercePriceModifierId", commercePriceModifierId
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"portletId", portletDisplay.getRootPortletId()
			).put(
				"priceModifierExternalReferenceCode", commercePriceModifier.getExternalReferenceCode()
			).put(
				"pricingFDSName", CommercePricingFDSNames.PRICE_MODIFIER_PRICING_CLASSES
			).put(
				"spritemap", themeDisplay.getPathThemeSpritemap()
			).build()
		%>'
		module="{priceModifierPricingClasses} from commerce-pricing-web"
	/>

	<commerce-ui:panel
		bodyClasses="p-0"
		title='<%= LanguageUtil.get(request, "product-groups") %>'
	>
		<frontend-data-set:headless-display
			apiURL="<%= commercePriceListDisplayContext.getPriceModifierPricingClassesAPIURL() %>"
			fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceModifierPricingClassFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.PRICE_MODIFIER_PRICING_CLASSES %>"
			itemsPerPage="<%= 10 %>"
		/>
	</commerce-ui:panel>
</c:if>