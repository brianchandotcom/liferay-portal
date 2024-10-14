<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.commerce.frontend.model.StepModel" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<%
long commerceOrderId = (long)request.getAttribute("liferay-commerce:step-tracker:commerceOrderId");
boolean open = (boolean)request.getAttribute("liferay-commerce:step-tracker:open");
List<StepModel> stepModels = (List<StepModel>)request.getAttribute("liferay-commerce:step-tracker:commerceOrderSteps");
%>