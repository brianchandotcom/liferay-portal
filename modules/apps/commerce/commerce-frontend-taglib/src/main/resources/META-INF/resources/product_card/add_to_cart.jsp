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

<%@ include file="/product_card/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib") + StringPool.UNDERLINE;
%>

<c:choose>
	<c:when test="<%= Validator.isNull(sku) %>">
		<div class="add-to-cart d-flex my-2 pt-5" id="<%= randomNamespace + "add_to_cart" %>">
			<a class="btn btn-block btn-secondary" href="<%= productDetailURL %>" role="button" style="margin-top: 0.35rem;">
				<liferay-ui:message key="view-all-variants" />
			</a>
		</div>
	</c:when>
	<c:otherwise>
		<commerce-ui:add-to-cart
			block="<%= true %>"
			cpCatalogEntry="<%= cpCatalogEntry %>"
		/>
	</c:otherwise>
</c:choose>