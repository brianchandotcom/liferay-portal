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
			<frontend-data-set:classic-display
				dataProviderKey="<%= CommerceShipmentFDSNames.SHIPMENTS %>"
				id="<%= CommerceShipmentFDSNames.SHIPMENTS %>"
				itemsPerPage="<%= 10 %>"
				style="fluid"
			/>
		</div>
	</div>
</c:if>