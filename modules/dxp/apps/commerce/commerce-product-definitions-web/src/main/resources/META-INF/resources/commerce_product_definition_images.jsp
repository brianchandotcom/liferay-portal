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
CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION);

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-definition-images");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "viewProductDefinitionImages");

request.setAttribute("view.jsp-portletURL", portletURL);

PortletURL backUrl = liferayPortletResponse.createRenderURL();

backUrl.setParameter("mvcPath", "/view.jsp");

String backURLString = backUrl.toString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURLString);

renderResponse.setTitle((commerceProductDefinition == null) ? LanguageUtil.get(request, "add-product-definition") : commerceProductDefinition.getTitle(locale));
%>

<%@ include file="/commerce_product_definition_navbar.jspf" %>