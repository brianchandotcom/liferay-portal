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
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse();

String title = LanguageUtil.get(request, "add-warehouse");

if (commerceInventoryWarehouse != null) {
	title = LanguageUtil.format(request, "edit-x", commerceInventoryWarehouse.getName(), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), PortalUtil.escapeRedirect(redirect), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceInventoryWarehouse" var="editCommerceInventoryWarehouseActionURL" />

<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceInventoryWarehouse();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />
	<aui:input name="commerceChannelIds" type="hidden" />
	<aui:input name="mvccVersion" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getMvccVersion() %>" />

	<liferay-ui:form-navigator
		formModelBean="<%= commerceInventoryWarehouse %>"
		id="<%= CommerceInventoryWarehouseFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_WAREHOUSE %>"
		markupView="lexicon"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceInventoryWarehouse() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>