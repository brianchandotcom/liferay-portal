<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html_field/init.jsp" %>

<div class="lfr-ddm-container" id="<%= randomNamespace %>">
	<c:if test="<%= ddmForm != null %>">

		<%
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
		ddmFormFieldRenderingContext.setPortletNamespace(liferayPortletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(readOnly);
		ddmFormFieldRenderingContext.setShowEmptyFieldLabel(showEmptyFieldLabel);
		%>

		<%= ddmFormFieldRenderer.render(ddmFormField, ddmFormFieldRenderingContext) %>

		<aui:input name="<%= HtmlUtil.getAUICompatibleId(ddmFormValuesInputName) %>" type="hidden" />

		<aui:script use="liferay-ddm-form">

			<%
			Group group = themeDisplay.getScopeGroup();
			%>

			Liferay.component(
				'<portlet:namespace /><%= HtmlUtil.escapeJS(fieldsNamespace) %>ddmForm',
				() => {
					return new Liferay.DDM.Form({
						container: '#<%= randomNamespace %>',
						ddmFormValuesInput:
							'#<portlet:namespace /><%= HtmlUtil.getAUICompatibleId(ddmFormValuesInputName) %>',
						definition: <%= DDMUtil.getDDMFormJSONString(ddmForm) %>,
						displayLocale: '<%= LocaleUtil.toLanguageId(requestedLocale) %>',
						doAsGroupId: <%= scopeGroupId %>,
						fieldsNamespace: '<%= HtmlUtil.escapeJS(fieldsNamespace) %>',
						isPrivateLayoutsEnabled: <%= group.isPrivateLayoutsEnabled() %>,
						mode: '<%= HtmlUtil.escapeJS(mode) %>',
						p_l_id: <%= themeDisplay.getPlid() %>,
						portletNamespace: '<portlet:namespace />',
						repeatable: <%= repeatable %>,
					});
				}
			);
		</aui:script>
	</c:if>