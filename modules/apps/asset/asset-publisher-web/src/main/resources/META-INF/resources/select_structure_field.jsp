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
String portletResource = ParamUtil.getString(request, "portletResource");
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String ddmStructureFieldName = ParamUtil.getString(request, "ddmStructureFieldName");
Serializable ddmStructureFieldValue = ParamUtil.getString(request, "ddmStructureFieldValue");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();

ClassType classType = classTypeReader.getClassType(classTypeId, locale);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_structure_field.jsp");
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("className", className);
portletURL.setParameter("classTypeId", String.valueOf(classTypeId));
%>

<div class="alert alert-danger hide" id="<portlet:namespace />message">
	<span class="error-message"><liferay-ui:message key="the-field-value-is-invalid" /></span>
</div>

<div id="<portlet:namespace />selectDDMStructureFieldForm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= classType.getClassTypeFieldsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= classType.getClassTypeFields(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.ClassTypeField"
			modelVar="field"
		>

			<%
			String label = field.getLabel();
			String name = field.getName();
			long ddmStructureId = field.getClassTypeId();
			%>

			<liferay-ui:search-container-column-text>
				<input data-button-id="<%= renderResponse.getNamespace() + "applyButton" + name %>" data-form-id="<%= renderResponse.getNamespace() + name + "fieldForm" %>" name="<portlet:namespace />selectStructureFieldSubtype" type="radio" <%= name.equals(ddmStructureFieldName) ? "checked" : StringPool.BLANK %> />
			</liferay-ui:search-container-column-text>

			<%
			String fieldsNamespace = StringUtil.randomId();
			%>

			<liferay-ui:search-container-column-text
				name="field"
			>
				<liferay-portlet:resourceURL id="getFieldValue" portletConfiguration="<%= true %>" var="structureFieldURL">
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="structureId" value="<%= String.valueOf(ddmStructureId) %>" />
					<portlet:param name="name" value="<%= name %>" />
					<portlet:param name="fieldsNamespace" value="<%= fieldsNamespace %>" />
				</liferay-portlet:resourceURL>

				<aui:form action="<%= structureFieldURL %>" disabled="<%= !name.equals(ddmStructureFieldName) %>" name='<%= name + "fieldForm" %>' onSubmit="event.preventDefault()">
					<aui:input disabled="<%= true %>" name="buttonId" type="hidden" value='<%= renderResponse.getNamespace() + "applyButton" + name %>' />

					<%
					com.liferay.dynamic.data.mapping.storage.Field ddmField = new com.liferay.dynamic.data.mapping.storage.Field();

					ddmField.setDefaultLocale(themeDisplay.getLocale());
					ddmField.setDDMStructureId(ddmStructureId);
					ddmField.setName(name);

					if (name.equals(ddmStructureFieldName)) {
						ddmField.setValue(themeDisplay.getLocale(), ddmStructureFieldValue);
					}
					%>

					<liferay-ddm:html-field
						classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
						classPK="<%= ddmStructureId %>"
						field="<%= ddmField %>"
						fieldsNamespace="<%= fieldsNamespace %>"
					/>
				</aui:form>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("fieldsnamespace", fieldsNamespace);
				data.put("form", renderResponse.getNamespace() + name + "fieldForm");
				data.put("label", label);
				data.put("name", name);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= name.equals(ddmStructureFieldName) ? false : true %>" id='<%= "applyButton" + name %>' value="apply" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base,aui-io">
	var Util = Liferay.Util;

	var structureFormContainer = A.one('#<portlet:namespace />selectDDMStructureFieldForm');

	var fieldSubtypeForms = structureFormContainer.all('form');

	var toggleDisabledFormFields = function(form, state) {
		Util.toggleDisabled(form.all('input, select, textarea'), state);
	};

	var submitForm = function(applyButton) {
		var result = Util.getAttributes(applyButton, 'data-');

		var fieldsnamespace = result.fieldsnamespace;

		var ddmForm = Liferay.component('<portlet:namespace />' + fieldsnamespace + 'ddmForm');

		ddmForm.updateDDMFormInputValue();

		var form = A.one('#' + result.form);

		A.io.request(
			form.attr('action'),
			{
				dataType: 'JSON',
				form: {
					id: form
				},
				on: {
					success: function(event, id, obj) {
						var respondData = this.get('responseData');

						var message = A.one('#<portlet:namespace />message');

						if (respondData.success) {
							result.className = '<%= AssetPublisherUtil.getClassName(assetRendererFactory) %>';
							result.displayValue = respondData.displayValue;
							result.value = respondData.value;

							message.hide();

							Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

							Util.getWindow().destroy();
						}
						else {
							message.show();
						}
					}
				}
			}
		);
	};

	structureFormContainer.delegate(
		'click',
		function(event) {
			submitForm(event.currentTarget);
		},
		'.selector-button'
	);

	structureFormContainer.delegate(
		'submit',
		function(event) {
			var buttonId = event.currentTarget.one('#<portlet:namespace />buttonId').attr('value');

			submitForm(structureFormContainer.one('#' + buttonId));
		},
		'form'
	);

	A.one('#<portlet:namespace />classTypeFieldsSearchContainer').delegate(
		'click',
		function(event) {
			var target = event.currentTarget;

			var buttonId = target.attr('data-button-id');
			var formId = target.attr('data-form-id');

			Util.toggleDisabled(structureFormContainer.all('.selector-button'), true);

			Util.toggleDisabled('#' + buttonId, false);

			toggleDisabledFormFields(fieldSubtypeForms, true);

			toggleDisabledFormFields(A.one('#' + formId), false);
		},
		'input[name=<portlet:namespace />selectStructureFieldSubtype]'
	);
</aui:script>