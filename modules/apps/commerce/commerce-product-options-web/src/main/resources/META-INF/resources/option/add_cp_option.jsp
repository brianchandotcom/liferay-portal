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
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-option") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input autoFocus="<%= true %>" name="name" required="<%= true %>" type="text" />

		<aui:select label="option-field-type" name="DDMFormFieldTypeName" required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			for (DDMFormFieldType ddmFormFieldType : cpOptionDisplayContext.getDDMFormFieldTypes()) {
			%>

				<aui:option label="<%= cpOptionDisplayContext.getDDMFormFieldTypeLabel(ddmFormFieldType, locale) %>" value="<%= ddmFormFieldType.getName() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input helpMessage="key-help" name="key" required="<%= true %>" />
	</aui:form>

	<portlet:renderURL var="editOptionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_options/edit_cp_option" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", LanguageUtil.getLanguageId(locale)
			).put(
				"editOptionURL", editOptionURL
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).build()
		%>'
		module="js/add_cp_option"
	/>
</commerce-ui:modal-content>