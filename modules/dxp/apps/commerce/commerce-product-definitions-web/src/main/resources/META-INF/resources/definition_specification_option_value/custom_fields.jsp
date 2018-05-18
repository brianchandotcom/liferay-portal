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
CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue = (CPDefinitionSpecificationOptionValue)request.getAttribute(CPWebKeys.CP_DEFINITION_SPECIFICATION_OPTION_VALUE);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="custom-fields"
/>

<aui:model-context bean="<%= cpDefinitionSpecificationOptionValue %>" model="<%= CPDefinitionSpecificationOptionValue.class %>" />

<liferay-expando:custom-attribute-list
	className="<%= CPDefinitionSpecificationOptionValue.class.getName() %>"
	classPK="<%= (cpDefinitionSpecificationOptionValue != null) ? cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId() : 0 %>"
	editable="<%= true %>"
	label="<%= true %>"
/>