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
		<portlet:param name="mvcRenderCommandName" value="editOption" />
	</portlet:renderURL>


	<liferay-frontend:component
		componentId='<%= liferayPortletResponse.getNamespace() + "add_option" %>'
		context='<%=
			HashMapBuilder.<String, Object>put(
				"editOptionURL", editOptionURL
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).put(
				"defaultLanguageId", "en_US"
			).build()
		%>'
		module="js/add_option"/>

<%--	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/modals/index as ModalUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">--%>
<%--		var <portlet:namespace />defaultLanguageId = null;--%>

<%--		var AdminCatalogResource = ServiceProvider.default.AdminCatalogAPI('v1');--%>

<%--		Liferay.provide(--%>
<%--			window,--%>
<%--			'<portlet:namespace />apiSubmit',--%>
<%--			function () {--%>
<%--				ModalUtils.isSubmitting();--%>

<%--				var formattedData =--%>
<%--					{--%>
<%--						fieldType : '',--%>
<%--						key : '',--%>
<%--						name: {}--%>
<%--					};--%>

<%--				formattedData.fieldType = document.getElementById('<portlet:namespace />DDMFormFieldTypeName').value;--%>

<%--				formattedData.key = document.getElementById('<portlet:namespace />key').value;--%>

<%--				formattedData.name[--%>
<%--					<portlet:namespace />defaultLanguageId--%>
<%--				] = document.getElementById('<portlet:namespace />name').value;--%>

<%--				AdminCatalogResource.createOption(formattedData)--%>
<%--					.then(function (cpOption) {--%>
<%--						var redirectURL = new Liferay.PortletURL.createURL(--%>
<%--							'<%= editOptionURL %>'--%>
<%--						);--%>

<%--						redirectURL.setParameter(--%>
<%--							'p_p_state',--%>
<%--							'<%= LiferayWindowState.MAXIMIZED.toString() %>'--%>
<%--						);--%>

<%--						redirectURL.setParameter('cpOptionId', cpOption.id);--%>

<%--						ModalUtils.closeAndRedirect(redirectURL);--%>
<%--					})--%>
<%--					.catch(ModalUtils.onSubmitFail);--%>
<%--			},--%>
<%--			['liferay-portlet-url']--%>
<%--		);--%>
<%--	</aui:script>--%>
</commerce-ui:modal-content>