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
CommerceSubscriptionEntryDisplayContext commerceSubscriptionEntryDisplayContext = (CommerceSubscriptionEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="row">
	<div class="col-12">
		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "items") %>'
		>
			<clay:data-set-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"commerceSubscriptionEntryId", String.valueOf(commerceSubscriptionEntryDisplayContext.getCommerceSubscriptionEntryId())
					).build()
				%>'
				dataProviderKey="<%= CommerceSubscriptionDataSetConstants.COMMERCE_DATA_SET_KEY_SUBSCRIPTION_SHIPMENTS %>"
				id="<%= CommerceSubscriptionDataSetConstants.COMMERCE_DATA_SET_KEY_SUBSCRIPTION_SHIPMENTS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceSubscriptionEntryDisplayContext.getPortletURL() %>"
				showManagementBar="<%= false %>"
				style="stacked"
			/>
		</commerce-ui:panel>
	</div>
</div>