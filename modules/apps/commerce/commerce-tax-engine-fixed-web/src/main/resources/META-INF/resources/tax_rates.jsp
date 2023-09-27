<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxFixedRatesDisplayContext commerceTaxFixedRatesDisplayContext = (CommerceTaxFixedRatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:panel
	bodyClasses="p-0"
>
	<frontend-data-set:classic-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceChannelId", String.valueOf(commerceTaxFixedRatesDisplayContext.getCommerceChannelId())
			).put(
				"commerceTaxMethodId", String.valueOf(commerceTaxFixedRatesDisplayContext.getCommerceTaxMethodId())
			).build()
		%>'
		creationMenu="<%= commerceTaxFixedRatesDisplayContext.getCreationMenu() %>"
		dataProviderKey="<%= CommerceTaxRateSettingFDSNames.TAX_RATES %>"
		id="<%= CommerceTaxRateSettingFDSNames.TAX_RATES %>"
		itemsPerPage="<%= 10 %>"
		showSearch="<%= false %>"
	/>
</commerce-ui:panel>