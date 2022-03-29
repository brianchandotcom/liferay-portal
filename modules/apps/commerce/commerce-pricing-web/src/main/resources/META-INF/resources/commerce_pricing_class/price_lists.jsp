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
CommercePricingClassPriceListDisplayContext commercePricingClassPriceListDisplayContext = (CommercePricingClassPriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

boolean hasPermission = commercePricingClassPriceListDisplayContext.hasPermission();

CommercePricingClass commercePricingClass = commercePricingClassPriceListDisplayContext.getCommercePricingClass();
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

<aui:form action="<%= editCommercePricingClassActionURL %>" cssClass="pt-4" method="post" name="fm">
	<c:if test="<%= hasPermission %>">
		<div class="col-12 pt-4">
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commercePricingClassId", String.valueOf(commercePricingClass.getCommercePricingClassId())
					).build()
				%>'
				dataProviderKey="<%= CommercePricingFDSNames.PRICING_CLASSES_PRICE_LISTS %>"
				id="<%= CommercePricingFDSNames.PRICING_CLASSES_PRICE_LISTS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= currentURLObj %>"
				style="stacked"
			/>
		</div>
	</c:if>
</aui:form>