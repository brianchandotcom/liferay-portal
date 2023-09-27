<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceSubscriptionEntryDisplayContext commerceSubscriptionEntryDisplayContext = (CommerceSubscriptionEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

boolean hasManageCommerceSubscriptionEntryPermission = commerceSubscriptionEntryDisplayContext.hasManageCommerceSubscriptionEntryPermission();

java.util.Map<String, String> contextParams = new java.util.HashMap<>();

contextParams.put("companyId", String.valueOf(themeDisplay.getCompanyId()));
%>

<div class="row">
	<div class="col-12 mb-4">
		<c:if test="<%= hasManageCommerceSubscriptionEntryPermission %>">
			<frontend-data-set:classic-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceSubscriptionFDSNames.SUBSCRIPTION_ENTRIES %>"
				id="<%= CommerceSubscriptionFDSNames.SUBSCRIPTION_ENTRIES %>"
				itemsPerPage="<%= 10 %>"
				style="fluid"
			/>
		</c:if>
	</div>
</div>