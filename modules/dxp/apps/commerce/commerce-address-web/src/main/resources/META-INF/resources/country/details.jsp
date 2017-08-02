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
CommerceCountriesDisplayContext commerceCountryDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCountry commerceCountry = commerceCountryDisplayContext.getCommerceCountry();

long commerceCountryId = commerceCountryDisplayContext.getCommerceCountryId();
%>

<portlet:actionURL name="editCommerceCountry" var="editCommerceCountryActionURL" />

<aui:form action="<%= editCommerceCountryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceCountry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceCountryId" type="hidden" value="<%= String.valueOf(commerceCountryId) %>" />

	<aui:model-context bean="<%= commerceCountry %>" model="<%= CommerceCountry.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= true %>" name="name" />

		<aui:input name="allowsBilling" />

		<aui:input name="allowsShipping" />

		<aui:input name="twoLettersISOCode" />

		<aui:input name="threeLettersISOCode" />

		<aui:input name="numericISOCode" />

		<aui:input name="subjectToVAT" />

		<aui:input name="priority" />

		<aui:input name="active" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= settingsURL %>" type="cancel" />
	</aui:button-row>
</aui:form>