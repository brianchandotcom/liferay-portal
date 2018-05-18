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
CommerceShippingSettingsDisplayContext commerceShippingSettingsDisplayContext = (CommerceShippingSettingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="editCommerceShippingSettings" var="editCommerceShippingSettingsActionURL" />

<aui:form action="<%= editCommerceShippingSettingsActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceShippingSettings();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:form-navigator
		formModelBean="<%= commerceShippingSettingsDisplayContext.getCommerceShippingGroupServiceConfiguration() %>"
		id="<%= CommerceShippingFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_SHIPPING_SETTINGS %>"
		markupView="lexicon"
		showButtons="<%= false %>"
	/>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="save" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceShippingSettings() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>