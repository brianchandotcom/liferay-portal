<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<commerce-ui:modal-content
	contentCssClasses="p-0"
	showCancelButton="<%= false %>"
	showSubmitButton="<%= false %>"
	title='<%= LanguageUtil.get(request, "shipments") %>'
>
	<frontend-data-set:headless-display
		apiURL="<%= commerceOrderContentDisplayContext.getCommerceShipmentItemsAPIURL() %>"
		formName="fm"
		id="<%= CommerceOrderFDSNames.SHIPMENTS %>"
		showManagementBar="<%= false %>"
		showPagination="<%= false %>"
		style="fluid"
	/>
</commerce-ui:modal-content>