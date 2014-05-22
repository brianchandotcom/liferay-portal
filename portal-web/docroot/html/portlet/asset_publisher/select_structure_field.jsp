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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String className = ParamUtil.getString(request, "className");
long classTypeId = ParamUtil.getLong(request, "classTypeId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectDDMStructureField");

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

ClassTypeReader classTypeReader = assetRendererFactory.getClassTypeReader();
ClassType classType = classTypeReader.getClassType(classTypeId, locale);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/asset_publisher/select_structure_field");
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("className", className);
portletURL.setParameter("classTypeId", String.valueOf(classTypeId));
%>

<div class="alert alert-error hide" id="<portlet:namespace />message">
	<span class="error-message"><%= LanguageUtil.get(pageContext, "the-field-value-is-invalid") %></span>
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
			String fieldType = field.getType();
			long ddmStructureId = field.getClassTypeId();
			%>

			<liferay-ui:search-container-column-text>
				<input data-button-id="<%= renderResponse.getNamespace() + "applyButton" + name %>" data-form-id="<%= renderResponse.getNamespace() + name + "fieldForm" %>" name="<portlet:namespace />selectStructureFieldSubtype" type="radio" <%= name.equals(assetPublisherDisplayContext.getDDMStructureFieldName()) ? "checked" : StringPool.BLANK %> />
			</liferay-ui:search-container-column-text>

			<%
			String fieldsNamespace = StringUtil.randomId();
			%>

			<liferay-ui:search-container-column-text
				name="field"
			>
				<liferay-portlet:resourceURL portletConfiguration="true" var="structureFieldURL">
					<portlet:param name="<%= Constants.CMD %>" value="getFieldValue" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
					<portlet:param name="structureId" value="<%= String.valueOf(ddmStructureId) %>" />
					<portlet:param name="name" value="<%= name %>" />
					<portlet:param name="fieldsNamespace" value="<%= fieldsNamespace %>" />
				</liferay-portlet:resourceURL>

				<aui:form action="<%= structureFieldURL %>" name='<%= name + "fieldForm" %>' onSubmit="event.preventDefault()">
					<aui:input disabled="<%= true %>" name="buttonId" type="hidden" value='<%= renderResponse.getNamespace() + "applyButton" + name %>' />

					<%
					com.liferay.portlet.dynamicdatamapping.storage.Field ddmField = new com.liferay.portlet.dynamicdatamapping.storage.Field();

					ddmField.setDefaultLocale(themeDisplay.getLocale());
					ddmField.setDDMStructureId(ddmStructureId);
					ddmField.setName(name);

					Serializable ddmStructureFieldValue = assetPublisherDisplayContext.getDDMStructureFieldValue();

					if (name.equals(assetPublisherDisplayContext.getDDMStructureFieldName())) {
						if (fieldType.equals(DDMImpl.TYPE_DDM_DATE)) {
							ddmStructureFieldValue = GetterUtil.getDate(ddmStructureFieldValue, DateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss"));
						}

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

				data.put("form", renderResponse.getNamespace() + name + "fieldForm");
				data.put("label", label);
				data.put("name", name);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= name.equals(assetPublisherDisplayContext.getDDMStructureFieldName()) ? false : true %>" id='<%= renderResponse.getNamespace() + "applyButton" + name %>' value="apply" />
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

							Util.getWindow().hide();
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

	toggleDisabledFormFields(fieldSubtypeForms, true);
</aui:script>