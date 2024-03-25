<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.commerce.frontend.model.StepModel" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String spritemap = (String)request.getAttribute("liferay-commerce:step-tracker:spritemap");
List<StepModel> steps = (List<StepModel>)request.getAttribute("liferay-commerce:step-tracker:steps");

String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_step_tracker") + StringPool.UNDERLINE;

String stepTrackerId = randomNamespace + "step-tracker-id";
%>