<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceSubscriptionEntryDisplayContext commerceSubscriptionEntryDisplayContext = (CommerceSubscriptionEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "items") %>'
		>
			<frontend-data-set:classic-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceSubscriptionEntryId", String.valueOf(commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntryId())
					).build()
				%>'
				dataProviderKey="<%= CommerceSubscriptionFDSNames.SUBSCRIPTION_PAYMENTS %>"
				id="<%= CommerceSubscriptionFDSNames.SUBSCRIPTION_PAYMENTS %>"
				itemsPerPage="<%= 10 %>"
				showManagementBar="<%= false %>"
				style="stacked"
			/>
		</commerce-ui:panel>
	</div>
</div>