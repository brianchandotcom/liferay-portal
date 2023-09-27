<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassDiscountDisplayContext commercePricingClassDiscountDisplayContext = (CommercePricingClassDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

boolean hasPermission = commercePricingClassDiscountDisplayContext.hasPermission();

CommercePricingClass commercePricingClass = commercePricingClassDiscountDisplayContext.getCommercePricingClass();
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
				dataProviderKey="<%= CommercePricingFDSNames.PRICING_CLASSES_DISCOUNTS %>"
				id="<%= CommercePricingFDSNames.PRICING_CLASSES_DISCOUNTS %>"
				itemsPerPage="<%= 10 %>"
				style="stacked"
			/>
		</div>
	</c:if>
</aui:form>