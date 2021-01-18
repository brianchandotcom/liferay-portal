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
		<portlet:param name="mvcRenderCommandName" value="editOption" />
	</portlet:renderURL>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"editOptionURL", editOptionURL
			).put(
				"windowState", LiferayWindowState.MAXIMIZED.toString()
			).put(
				"defaultLanguageId", LanguageUtil.getLanguageId(locale)
			).put(
				"cpOptionId", cpOptionId
			).put(
				"defaultLanguageId", LanguageUtil.getLanguageId(locale)
			).build()
		%>'
		module="js/add_option_value"/>

<%--	<aui:script require="commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/modals/index as ModalUtils, commerce-frontend-js/ServiceProvider/index as ServiceProvider">--%>
<%--		var <portlet:namespace />defaultLanguageId = null;--%>

<%--		var AdminCatalogResource = ServiceProvider.default.AdminCatalogAPI('v1');--%>

<%--		var cpOptionId = <%= cpOptionId %>;--%>

<%--		Liferay.provide(--%>
<%--			window,--%>
<%--			'<portlet:namespace />apiSubmit',--%>
<%--			function () {--%>
<%--				ModalUtils.isSubmitting();--%>

<%--				var formattedData =--%>
<%--					{--%>
<%--						key : '',--%>
<%--						name: {},--%>
<%--						priority : ''--%>
<%--					};--%>

<%--				formattedData.key = document.getElementById('<portlet:namespace />key').value;--%>

<%--				formattedData.name[--%>
<%--					<portlet:namespace />defaultLanguageId--%>
<%--				] = document.getElementById('<portlet:namespace />name').value;--%>

<%--				formattedData.priority = document.getElementById('<portlet:namespace />priority').value;--%>

<%--				AdminCatalogResource.createOptionValue(cpOptionId, formattedData)--%>
<%--					.then(function (cpOptionValue) {--%>
<%--						var redirectURL = new Liferay.PortletURL.createURL(--%>
<%--							'<%= editOptionValueURL %>'--%>
<%--						);--%>

<%--						redirectURL.setParameter(--%>
<%--							'p_p_state',--%>
<%--							'<%= LiferayWindowState.MAXIMIZED.toString() %>'--%>
<%--						);--%>

<%--						redirectURL.setParameter('cpOptionValueId', cpOptionValue.id);--%>

<%--						ModalUtils.closeAndRedirect(redirectURL);--%>
<%--					})--%>
<%--					.catch(ModalUtils.onSubmitFail);--%>
<%--			},--%>
<%--			['liferay-portlet-url']--%>
<%--		);--%>

<%--	</aui:script>--%>
</commerce-ui:modal-content>