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

<%@ include file="/dynamic_include/init.jsp" %>

<%
AttributeMappingDisplayContext attributeMappingDisplayContext = (AttributeMappingDisplayContext)request.getAttribute(AttributeMappingDisplayContext.class.getName());
%>

<liferay-ui:error exception="<%= UserAttributeMappingException.class %>">
	<liferay-ui:message arguments="<%= attributeMappingDisplayContext.getMessageArguments((UserAttributeMappingException)errorException) %>" key="<%= attributeMappingDisplayContext.getMessageKey((UserAttributeMappingException)errorException) %>" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= UserIdentifierExpressionException.class %>">
	<liferay-ui:message key="<%= attributeMappingDisplayContext.getMessageKey((UserIdentifierExpressionException)errorException) %>" translateArguments="<%= false %>" />
</liferay-ui:error>