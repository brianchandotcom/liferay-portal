<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "create-new-option") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "apiSubmit();" %>'>
		<aui:input name="name" required="<%= true %>" type="text" />

		<aui:select label="option-field-type" name="commerceOptionTypeKey" required="<%= true %>" showEmptyOption="<%= true %>">

			<%
			for (CommerceOptionType commerceOptionType : cpOptionDisplayContext.getCommerceOptionTypes()) {
			%>

				<aui:option label="<%= commerceOptionType.getLabel(locale) %>" value="<%= commerceOptionType.getKey() %>" />

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