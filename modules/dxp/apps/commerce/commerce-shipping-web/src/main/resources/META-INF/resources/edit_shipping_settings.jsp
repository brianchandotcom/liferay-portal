<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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