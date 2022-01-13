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
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String ddmStructureFieldName = ParamUtil.getString(request, "ddmStructureFieldName");
String ddmStructureFieldValue = ParamUtil.getString(request, "ddmStructureFieldValue");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

ClassType classType = classTypeReader.getClassType(classTypeId, locale);

List<SelectOption> selectOptions = new ArrayList<>();

selectOptions.add(new SelectOption(LanguageUtil.get(themeDisplay.getLocale(), "none"), StringPool.BLANK));

for (ClassTypeField classTypeField : classType.getClassTypeFields()) {
	selectOptions.add(new SelectOption(classTypeField.getLabel(), classTypeField.getName()));
}
%>

<div class="alert alert-danger hide" id="<portlet:namespace />message">
	<span class="error-message"><liferay-ui:message key="the-field-value-is-invalid" /></span>
</div>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureFieldForm" %>'
>
	<clay:select
		id='<%= liferayPortletResponse.getNamespace() + "fieldName" %>'
		label="select"
		name="fieldName"
		options="<%= selectOptions %>"
	/>

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/asset_list/get_field_value" var="structureFieldURL">
		<portlet:param name="structureId" value="<%= String.valueOf(classTypeId) %>" />
	</liferay-portlet:resourceURL>

	<aui:form action="<%= structureFieldURL %>" name="fieldForm" onSubmit="event.preventDefault()">
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

<%
ResourceURL getFieldItemURL = renderResponse.createResourceURL();

getFieldItemURL.setParameter("className", className);
getFieldItemURL.setParameter("classTypeId", String.valueOf(classTypeId));
getFieldItemURL.setParameter("ddmStructureFieldName", ddmStructureFieldName);
getFieldItemURL.setParameter("ddmStructureFieldValue", ddmStructureFieldValue);

getFieldItemURL.setResourceID("/asset_list/get_field_item");
%>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "selectStructureField" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"assetClassName", editAssetListDisplayContext.getClassName(assetRendererFactory)
		).put(
			"eventName", HtmlUtil.escapeJS(eventName)
		).put(
			"getFieldItemURL", getFieldItemURL
		).build()
	%>'
	module="js/SelectStructureField"
/>