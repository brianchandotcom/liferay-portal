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

<frontend-data-set:classic-display
	contextParams='<%=
		HashMapBuilder.<String, String>put(
			"commerceCatalogId", String.valueOf(commerceCatalog.getCommerceCatalogId())
		).build()
	%>'
	dataProviderKey="<%= CommerceCatalogFDSNames.CATALOG_ACCOUNT_GROUPS %>"
	id="<%= CommerceCatalogFDSNames.CATALOG_ACCOUNT_GROUPS %>"
	itemsPerPage="<%= 10 %>"
	style="fluid"
/>