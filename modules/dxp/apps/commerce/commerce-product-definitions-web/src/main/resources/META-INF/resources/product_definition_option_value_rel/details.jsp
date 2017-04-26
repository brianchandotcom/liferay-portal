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
CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel = (CommerceProductDefinitionOptionValueRel)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL);
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= commerceProductDefinitionOptionValueRel %>" model="<%= CommerceProductDefinitionOptionValueRel.class %>" />

<aui:fieldset>
	<aui:input name="title" />
	<aui:input name="priority" />
</aui:fieldset>