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
CommerceOrderImporterType commerceOrderImporterType = commerceOrderContentDisplayContext.getCommerceOrderImporterType(ParamUtil.getString(request, "commerceOrderImporterTypeKey"));
%>

<c:choose>
	<c:when test="<%= request.getAttribute(CommerceWebKeys.COMMERCE_ORDER_IMPORTER_ITEM) != null %>">

		<%
		commerceOrderImporterType.renderCommerceOrderPreview(commerceOrderContentDisplayContext.getCommerceOrder(), request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
		%>

	</c:when>
	<c:when test="<%= commerceOrderImporterType != null %>">

		<%
		commerceOrderImporterType.render(commerceOrderContentDisplayContext.getCommerceOrder(), request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
		%>

	</c:when>
</c:choose>