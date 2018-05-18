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
CPSpecificationOption cpSpecificationOption = (CPSpecificationOption)request.getAttribute(CPWebKeys.CP_OPTION);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="custom-fields"
/>

<aui:model-context bean="<%= cpSpecificationOption %>" model="<%= CPSpecificationOption.class %>" />

<liferay-expando:custom-attribute-list
	className="<%= CPSpecificationOption.class.getName() %>"
	classPK="<%= (cpSpecificationOption != null) ? cpSpecificationOption.getCPSpecificationOptionId() : 0 %>"
	editable="<%= true %>"
	label="<%= true %>"
/>