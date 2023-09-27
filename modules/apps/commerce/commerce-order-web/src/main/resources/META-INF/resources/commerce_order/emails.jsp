<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "emails") %>'
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceOrderId", String.valueOf(commerceOrderEditDisplayContext.getCommerceOrderId())
			).build()
		%>'
		dataProviderKey="<%= CommerceOrderFDSNames.NOTIFICATIONS %>"
		id="<%= CommerceOrderFDSNames.NOTIFICATIONS %>"
		itemsPerPage="<%= 10 %>"
		showManagementBar="<%= false %>"
	/>
</commerce-ui:panel>