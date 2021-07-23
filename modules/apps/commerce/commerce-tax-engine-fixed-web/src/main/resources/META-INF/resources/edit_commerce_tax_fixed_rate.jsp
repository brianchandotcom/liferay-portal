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
CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxFixedRate commerceTaxFixedRate = commerceTaxFixedRatesDisplayContext.getCommerceTaxFixedRate();
%>

<portlet:actionURL name="/commerce_tax_methods/edit_commerce_tax_fixed_rate" var="editCommerceTaxFixedRateActionURL" />

<c:choose>
	<c:when test="<%= commerceTaxFixedRate == null %>">
		<commerce-ui:modal-content
			title='<%= LanguageUtil.get(resourceBundle, "add-tax-rate") %>'
		>
			<aui:form action="<%= editCommerceTaxFixedRateActionURL %>" method="post" name="fm">
				<%@ include file="/edit_commerce_tax_fixed_rate.jspf" %>
			</aui:form>
		</commerce-ui:modal-content>
	</c:when>
	<c:otherwise>
		<liferay-frontend:side-panel-content
			title='<%= LanguageUtil.get(resourceBundle, "edit-tax-rate") %>'
		>
			<aui:form action="<%= editCommerceTaxFixedRateActionURL %>" method="post" name="fm">
				<commerce-ui:panel>
					<%@ include file="/edit_commerce_tax_fixed_rate.jspf" %>
				</commerce-ui:panel>

				<aui:button-row>
					<aui:button cssClass="btn-lg" type="submit" />
				</aui:button-row>
			</aui:form>
		</liferay-frontend:side-panel-content>
	</c:otherwise>
</c:choose>