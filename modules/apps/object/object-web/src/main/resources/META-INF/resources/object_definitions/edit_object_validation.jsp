<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinitionsValidationsDisplayContext objectDefinitionsValidationsDisplayContext = (ObjectDefinitionsValidationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectValidationRule objectValidationRule = (ObjectValidationRule)request.getAttribute(ObjectWebKeys.OBJECT_VALIDATION);
%>

<react:component
	module="js/components/ObjectValidation/EditObjectValidation"
	props="<%= objectDefinitionsValidationsDisplayContext.getProps(objectValidationRule) %>"
/>