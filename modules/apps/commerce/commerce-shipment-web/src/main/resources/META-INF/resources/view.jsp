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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceShipmentDisplayContext.hasManageCommerceShipmentsPermission() %>">
	<div class="row" id="<portlet:namespace />editShipmentContainer">
		<div class="col-12">
			<clay:data-set-display
				dataProviderKey="<%= CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENTS %>"
				id="<%= CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENTS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= commerceShipmentDisplayContext.getPortletURL() %>"
				style="fluid"
			/>
		</div>
	</div>
</c:if>