<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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