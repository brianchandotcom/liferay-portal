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

<%@ include file="/compare_product/init.jsp" %>

<%
boolean checked = (boolean)request.getAttribute("liferay-commerce:compare-product:checked");
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("liferay-commerce:compare-product:cpDefinition");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_commerce_compare_product_page") + StringPool.UNDERLINE;
%>

<liferay-portlet:actionURL name="editCompareProduct" portletName="<%= CPPortletKeys.CP_COMPARE_CONTENT_WEB %>" var="editCompareProductActionURL" />

<div class="commerce-compare-product-container">
	<aui:form action="<%= editCompareProductActionURL %>" name='<%= randomNamespace + "Fm" %>' portletNamespace="<%= PortalUtil.getPortletNamespace(CPPortletKeys.CP_COMPARE_CONTENT_WEB) %>">
		<aui:input name="redirect" type="hidden" value="<%= PortalUtil.getCurrentURL(request) %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />

		<aui:input checked="<%= checked %>" ignoreRequestValue="<%= true %>" label="compare" name='<%= cpDefinition.getCPDefinitionId() + "Compare" %>' onClick="this.form.submit();" type="checkbox" />
	</aui:form>
</div>