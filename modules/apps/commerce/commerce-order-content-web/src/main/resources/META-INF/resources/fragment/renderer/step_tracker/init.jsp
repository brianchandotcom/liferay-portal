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
List<StepModel> steps = (List<StepModel>)request.getAttribute("liferay-commerce:step-tracker:step-list");
%>