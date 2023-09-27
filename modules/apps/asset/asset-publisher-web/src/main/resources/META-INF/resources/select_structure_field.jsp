<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectStructureFieldDisplayContext selectStructureFieldDisplayContext = new SelectStructureFieldDisplayContext(request, liferayPortletResponse);
%>

<clay:alert
	cssClass="hide"
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