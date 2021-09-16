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
CommerceCatalogDisplayContext commerceCatalogDisplayContext = (CommerceCatalogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCatalog commerceCatalog = commerceCatalogDisplayContext.getCommerceCatalog();

portletDisplay.setShowBackIcon(true);

if (Validator.isNull(redirect)) {
	portletDisplay.setURLBack(String.valueOf(renderResponse.createRenderURL()));
}
else {
	portletDisplay.setURLBack(redirect);
}
%>

<liferay-portlet:renderURL var="editCommerceCatalogExternalReferenceCodeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/commerce_catalogs/edit_commerce_catalog_external_reference_code" />
	<portlet:param name="commerceCatalogId" value="<%= String.valueOf(commerceCatalog.getCommerceCatalogId()) %>" />
</liferay-portlet:renderURL>

<commerce-ui:header
	actions="<%= commerceCatalogDisplayContext.getHeaderActionModels() %>"
	bean="<%= commerceCatalog %>"
	beanIdLabel="id"
	externalReferenceCode="<%= HtmlUtil.escape(commerceCatalog.getExternalReferenceCode()) %>"
	externalReferenceCodeEditUrl="<%= editCommerceCatalogExternalReferenceCodeURL %>"
	model="<%= CommerceCatalog.class %>"
	title="<%= commerceCatalog.getName() %>"
/>

<div id="<portlet:namespace />editCatalogContainer">
	<liferay-frontend:screen-navigation
		containerWrapperCssClass="container"
		key="<%= CommerceCatalogScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_CATALOG_GENERAL %>"
		modelBean="<%= commerceCatalog %>"
		portletURL="<%= currentURLObj %>"
	/>
</div>