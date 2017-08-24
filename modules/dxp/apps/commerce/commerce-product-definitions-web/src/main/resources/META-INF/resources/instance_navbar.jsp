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
CPInstance cpInstance = (CPInstance)request.getAttribute("view.jsp-cpInstance");
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"), "");

long cpDefinitionId = 0;
long cpInstanceId = 0;

if (cpInstance != null) {
	cpDefinitionId = cpInstance.getCPDefinitionId();
	cpInstanceId = cpInstance.getCPInstanceId();
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<liferay-portlet:renderURL varImpl="viewProductInstanceDetailsURL">
			<portlet:param name="mvcRenderCommandName" value="editProductInstance" />
			<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
			<portlet:param name="cpInstanceId" value="<%= String.valueOf(cpInstanceId) %>" />
			<portlet:param name="toolbarItem" value="edit-product-instance-details" />
		</liferay-portlet:renderURL>

		<aui:nav-item
			href="<%= viewProductInstanceDetailsURL.toString() %>"
			label='<%= LanguageUtil.get(request, "details") %>'
			selected='<%= toolbarItem.equals("edit-product-instance-details") %>'
		/>

		<c:if test="<%= cpInstance != null %>">
			<liferay-portlet:renderURL varImpl="editProductInstancePricingInfoURL">
				<portlet:param name="mvcRenderCommandName" value="editProductInstancePricingInfo" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="cpInstanceId" value="<%= String.valueOf(cpInstanceId) %>" />
				<portlet:param name="toolbarItem" value="edit-product-instance-pricing-info" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= editProductInstancePricingInfoURL.toString() %>"
				label='<%= LanguageUtil.get(request, "pricing-adjustment") %>'
				selected='<%= toolbarItem.equals("edit-product-instance-pricing-info") %>'
			/>

			<liferay-portlet:renderURL varImpl="editProductInstanceShippingInfoURL">
				<portlet:param name="mvcRenderCommandName" value="editProductInstanceShippingInfo" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="cpInstanceId" value="<%= String.valueOf(cpInstanceId) %>" />
				<portlet:param name="toolbarItem" value="edit-product-instance-shipping-info" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= editProductInstanceShippingInfoURL.toString() %>"
				label='<%= LanguageUtil.get(request, "shipping-adjustment") %>'
				selected='<%= toolbarItem.equals("edit-product-instance-shipping-info") %>'
			/>
		</c:if>
	</aui:nav>
</aui:nav-bar>