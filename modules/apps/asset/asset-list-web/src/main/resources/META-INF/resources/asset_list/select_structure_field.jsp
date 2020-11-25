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
Serializable ddmStructureFieldValue = ParamUtil.getString(request, "ddmStructureFieldValue");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

ClassType classType = classTypeReader.getClassType(classTypeId, locale);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/asset_list/select_structure_field.jsp");
portletURL.setParameter("className", className);
portletURL.setParameter("classTypeId", String.valueOf(classTypeId));
portletURL.setParameter("eventName", eventName);
%>

<div class="alert alert-danger hide" id="<portlet:namespace />message">
	<span class="error-message"><liferay-ui:message key="the-field-value-is-invalid" /></span>
</div>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectDDMStructureFieldForm" %>'
>
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= classType.getClassTypeFieldsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= classType.getClassTypeFields(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.ClassTypeField"
			modelVar="field"
		>
			<liferay-ui:search-container-column-text>
				<input data-button-id="<%= liferayPortletResponse.getNamespace() + "applyButton" + field.getName() %>" data-form-id="<%= liferayPortletResponse.getNamespace() + field.getName() + "fieldForm" %>" name="<portlet:namespace />selectStructureFieldSubtype" type="radio" <%= Objects.equals(field.getName(), ddmStructureFieldName) ? "checked" : StringPool.BLANK %> />
			</liferay-ui:search-container-column-text>

			<%
			String fieldsNamespace = StringUtil.randomId();
			%>

			<liferay-ui:search-container-column-text
				name="field"
			>
				<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/asset_list/get_field_value" var="structureFieldURL">
					<portlet:param name="structureId" value="<%= String.valueOf(field.getClassTypeId()) %>" />
					<portlet:param name="name" value="<%= field.getName() %>" />
					<portlet:param name="fieldsNamespace" value="<%= fieldsNamespace %>" />
				</liferay-portlet:resourceURL>

				<aui:form action="<%= structureFieldURL %>" disabled="<%= !Objects.equals(field.getName(), ddmStructureFieldName) %>" name='<%= field.getName() + "fieldForm" %>' onSubmit="event.preventDefault()">
					<aui:input disabled="<%= true %>" name="buttonId" type="hidden" value='<%= liferayPortletResponse.getNamespace() + "applyButton" + field.getName() %>' />

					<%
					Field ddmField = new com.liferay.dynamic.data.mapping.storage.Field();

					ddmField.setDefaultLocale(themeDisplay.getLocale());
					ddmField.setDDMStructureId(field.getClassTypeId());
					ddmField.setName(field.getName());

					if (Objects.equals(field.getName(), ddmStructureFieldName)) {
						ddmField.setValue(themeDisplay.getLocale(), ddmStructureFieldValue);
					}
					%>

					<liferay-ddm:html-field
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= field.getClassTypeId() %>"
						field="<%= ddmField %>"
						fieldsNamespace="<%= fieldsNamespace %>"
					/>
				</aui:form>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>
				<aui:button
					cssClass="selector-button"
					data='<%=
						HashMapBuilder.<String, Object>put(
							"fieldsnamespace", fieldsNamespace
						).put(
							"form", liferayPortletResponse.getNamespace() + field.getName() + "fieldForm"
						).put(
							"label", field.getLabel()
						).put(
							"name", field.getName()
						).put(
							"value",
							JSONUtil.put(
								"ddmStructureFieldName", ddmStructureFieldName
							).put(
								"ddmStructureFieldValue", ddmStructureFieldValue
							)
						).build()
					%>'
					disabled="<%= Objects.equals(field.getName(), ddmStructureFieldName) ? false : true %>"
					id='<%= "applyButton" + field.getName() %>'
					value="apply"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"eventName", HtmlUtil.escapeJS(eventName)
		).put(
			"journalArticleAssetClassName", editAssetListDisplayContext.getClassName(assetRendererFactory)
		).build()
	%>'
	module="js/SelectStructureField"
/>