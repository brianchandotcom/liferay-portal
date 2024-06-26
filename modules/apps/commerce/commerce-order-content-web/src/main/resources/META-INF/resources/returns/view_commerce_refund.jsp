<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/returns/init.jsp" %>

<commerce-ui:modal-content
	showCancelButton="<%= false %>"
	showSubmitButton="<%= false %>"
	title='<%= LanguageUtil.get(request, "refunds") %>'
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceOrderId", ParamUtil.getString(request, "commerceOrderId")
			).build()
		%>'
		dataProviderKey="<%= CommerceOrderFDSNames.REFUND_ITEMS %>"
		id="<%= CommerceOrderFDSNames.REFUND_ITEMS %>"
		itemsPerPage="<%= 10 %>"
		showManagementBar="<%= false %>"
		showSearch="<%= false %>"
		style="fluid"
	/>
</commerce-ui:modal-content>