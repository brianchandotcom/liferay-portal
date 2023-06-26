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
SelectStructureFieldDisplayContext selectStructureFieldDisplayContext = (SelectStructureFieldDisplayContext)request.getAttribute(AssetListWebKeys.SELECT_STRUCTURE_FIELD_DISPLAY_CONTEXT);
%>

<clay:alert
	cssClass="error-message hide"
	displayType="danger"
	id='<%= liferayPortletResponse.getNamespace() + "message" %>'
	message="the-field-value-is-invalid"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureFieldForm" %>'
>
	<clay:select
		containerCssClass="mt-3"
		id='<%= liferayPortletResponse.getNamespace() + "fieldName" %>'
		label="select"
		name="fieldName"
		options="<%= selectStructureFieldDisplayContext.getSelectOptions() %>"
	/>

	<aui:form action="<%= selectStructureFieldDisplayContext.getFieldValueURL() %>" name="fieldForm" onSubmit="event.preventDefault()">
		<aui:input name="name" type="hidden" />

		<div id="<portlet:namespace />selectDDMStructureFieldContainer"></div>

		<clay:button
			displayType="primary"
			id='<%= liferayPortletResponse.getNamespace() + "applyButton" %>'
			label="apply"
			type="button"
		/>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "selectStructureField" %>'
	context="<%= selectStructureFieldDisplayContext.getComponentContextData() %>"
	module="js/SelectStructureField"
/>