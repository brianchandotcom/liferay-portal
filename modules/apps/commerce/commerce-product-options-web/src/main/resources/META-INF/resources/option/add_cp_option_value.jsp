<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<aui:input name="name" required="<%= true %>" type="text" />

		<aui:input helpMessage="key-help" name="key" required="<%= true %>" />

		<aui:input label="position" name="priority" />
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
				"defaultLanguageId", LanguageUtil.getLanguageId(LocaleUtil.getDefault())
			).put(
				"editOptionURL", editOptionURL
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).build()
		%>'
		module="js/add_cp_option_value"
	/>
</commerce-ui:modal-content>