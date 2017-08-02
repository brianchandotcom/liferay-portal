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
CommerceRegionsDisplayContext commerceRegionsDisplayContext = (CommerceRegionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCountry commerceCountry = commerceRegionsDisplayContext.getCommerceCountry();

long commerceCountryId = commerceRegionsDisplayContext.getCommerceCountryId();

CommerceRegion commerceRegion = commerceRegionsDisplayContext.getCommerceRegion();

long commerceRegionId = commerceRegionsDisplayContext.getCommerceRegionId();

String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

String contextTitle = commerceCountry.getName();

String title;

if (commerceRegion == null) {
	title = LanguageUtil.format(request, "add-region-to-x", contextTitle);
}
else {
	title = contextTitle + " - " + commerceRegion.getName();
}

renderResponse.setTitle(title);
%>

<portlet:actionURL name="editCommerceRegion" var="editCommerceRegionActionURL" />

<aui:form action="<%= editCommerceRegionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceRegion == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceCountryId" type="hidden" value="<%= String.valueOf(commerceCountryId) %>" />
	<aui:input name="commerceRegionId" type="hidden" value="<%= String.valueOf(commerceRegionId) %>" />

	<aui:model-context bean="<%= commerceRegion %>" model="<%= CommerceRegion.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= true %>" name="name" />

		<aui:input name="abbreviation" />

		<aui:input name="priority" />

		<aui:input name="active" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>