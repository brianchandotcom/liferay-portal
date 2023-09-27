<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassDisplayContext commercePricingClassDisplayContext = (CommercePricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col-12">
		<frontend-data-set:classic-display
			creationMenu="<%= commercePricingClassDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePricingFDSNames.PRICING_CLASSES %>"
			id="<%= CommercePricingFDSNames.PRICING_CLASSES %>"
			itemsPerPage="<%= 10 %>"
			style="fluid"
		/>
	</div>
</div>