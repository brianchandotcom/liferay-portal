<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAdminModuleRegistry commerceAdminModuleRegistry = (CommerceAdminModuleRegistry)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_MODULE_REGISTRY);

NavigableMap<String, CommerceAdminModule> commerceAdminModules = commerceAdminModuleRegistry.getCommerceAdminModules();

String selectedCommerceAdminModuleKey = ParamUtil.getString(request, "commerceAdminModuleKey", commerceAdminModules.firstKey());

CommerceAdminModule selectedCommerceAdminModule = commerceAdminModules.get(selectedCommerceAdminModuleKey);
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= application %>" />

<%
selectedCommerceAdminModule.render(renderRequest, renderResponse);
%>