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
%>

<portlet:actionURL name="/commerce_catalogs/edit_commerce_catalog_external_reference_code" var="editCommerceCatalogExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceCatalogExternalReferenceCodeURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceCatalogId" type="hidden" value="<%= commerceCatalog.getCommerceCatalogId() %>" />

		<aui:model-context bean="<%= commerceCatalog %>" model="<%= CommerceCatalog.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= HtmlUtil.escape(commerceCatalog.getExternalReferenceCode()) %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>