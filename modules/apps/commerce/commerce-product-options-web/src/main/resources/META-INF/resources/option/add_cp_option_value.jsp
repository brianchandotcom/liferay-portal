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
long cpOptionId = ParamUtil.getLong(request, "cpOptionId");
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-option-value") %>'
>
	<aui:form cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input autoFocus="<%= true %>" name="name" required="<%= true %>" type="text" />

		<aui:input helpMessage="key-help" name="key" required="<%= true %>" />

		<aui:input name="priority" />
	</aui:form>

	<portlet:renderURL var="editOptionURL">
		<portlet:param name="mvcRenderCommandName" value="/cp_options/edit_cp_option" />
		<portlet:param name="cpOptionId" value="<%= String.valueOf(cpOptionId) %>" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"cpOptionId", cpOptionId
			).put(
				"defaultLanguageId", LanguageUtil.getLanguageId(locale)
			).put(
				"defaultLanguageId", LanguageUtil.getLanguageId(locale)
			).put(
				"editOptionURL", editOptionURL
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).build()
		%>'
		module="js/add_cp_option_value"
	/>
</commerce-ui:modal-content>