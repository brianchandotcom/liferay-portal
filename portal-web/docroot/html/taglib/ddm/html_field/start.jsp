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

<%@ include file="/html/taglib/ddm/html_field/init.jsp" %>

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= Validator.isNotNull(xsd) %>">

		<%
		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(xsd);

		Map<String, DDMFormField> ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(field.getName());

		DDMFormFieldRenderer ddmFormFieldRenderer = DDMFormFieldRendererRegistryUtil.getDDMFormFieldRenderer(ddmFormField.getType());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext = new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setField(field);
		ddmFormFieldRenderingContext.setHttpServletRequest(request);
		ddmFormFieldRenderingContext.setHttpServletResponse(response);
		ddmFormFieldRenderingContext.setLocale(requestedLocale);
		ddmFormFieldRenderingContext.setMode(mode);
		ddmFormFieldRenderingContext.setNamespace(fieldsNamespace);
		ddmFormFieldRenderingContext.setPortletNamespace(portletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(readOnly);
		ddmFormFieldRenderingContext.setShowEmptyFieldLabel(showEmptyFieldLabel);
		%>

		<%= ddmFormFieldRenderer.render(ddmFormField, ddmFormFieldRenderingContext) %>

		<aui:input name="<%= ddmFormValuesInputName %>" type="hidden" />

		<aui:script use="liferay-ddm-form">
			Liferay.component(
				'<portlet:namespace /><%= fieldsNamespace %>ddmForm',
				function() {
					return new Liferay.DDM.Form(
						{
							container: '#<%= randomNamespace %>',
							ddmFormValuesInput: '#<portlet:namespace /><%= ddmFormValuesInputName %>',
							definition: <%= DDMFormJSONSerializerUtil.serialize(ddmForm) %>,
							doAsGroupId: <%= scopeGroupId %>,
							fieldsNamespace: '<%= fieldsNamespace %>',
							mode: '<%= mode %>',
							p_l_id: <%= themeDisplay.getPlid() %>,
							portletNamespace: '<portlet:namespace />',
							repeatable: <%= repeatable %>
						}
					);
				}
			);
		</aui:script>
	</c:if>