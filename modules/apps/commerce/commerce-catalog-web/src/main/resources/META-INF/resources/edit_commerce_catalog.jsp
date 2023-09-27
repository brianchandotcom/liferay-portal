<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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