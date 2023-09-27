<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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