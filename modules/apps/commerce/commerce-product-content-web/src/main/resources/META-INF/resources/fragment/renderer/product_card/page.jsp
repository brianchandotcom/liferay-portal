<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/product_card/init.jsp" %>

<%
if (cpContentInfoItemRenderer != null) {
	cpContentInfoItemRenderer.render(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
}
%>