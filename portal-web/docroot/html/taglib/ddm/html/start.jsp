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

<%@ include file="/html/taglib/ddm/html/init.jsp" %>

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= Validator.isNotNull(xsd) %>">

		<%
		pageContext.setAttribute("checkRequired", checkRequired);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(xsd);

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext = new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setFields(fields);
		ddmFormFieldRenderingContext.setHttpServletRequest(request);
		ddmFormFieldRenderingContext.setHttpServletResponse(response);
		ddmFormFieldRenderingContext.setLocale(requestedLocale);
		ddmFormFieldRenderingContext.setMode(mode);
		ddmFormFieldRenderingContext.setNamespace(fieldsNamespace);
		ddmFormFieldRenderingContext.setPortletNamespace(portletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(readOnly);
		ddmFormFieldRenderingContext.setShowEmptyFieldLabel(showEmptyFieldLabel);
		%>

		<%= DDMFormRendererUtil.render(ddmForm, ddmFormFieldRenderingContext) %>

		<aui:input name="<%= ddmFormValuesInputName %>" type="hidden" />

		<aui:script use="liferay-ddm-form">
			new Liferay.DDM.Form(
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

					<%
					long ddmStructureId = classPK;

					if (classNameId == PortalUtil.getClassNameId(DDMTemplate.class)) {
						DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(classPK);

						ddmStructureId = ddmTemplate.getClassPK();
					}

					DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(ddmStructureId);

					DDMFormValues ddmFormValues = null;

					if (fields != null) {
						ddmFormValues = FieldsToDDMFormValuesConverterUtil.convert(ddmStructure, fields);
					}
					%>

					<c:if test="<%= ddmFormValues != null %>">
						, values: <%= DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues) %>
					</c:if>
				}
			);
		</aui:script>
	</c:if>