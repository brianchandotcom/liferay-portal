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