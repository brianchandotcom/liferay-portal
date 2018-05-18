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
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String redirect = editConfigurationDisplayContext.getViewCategoryURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(editConfigurationDisplayContext.getCategoryName());
%>

<div class="container-fluid container-fluid-max-xl">
	<div class="col-12">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</div>
</div>

<liferay-portlet:actionURL name="editCommerceCloudClientConfiguration" portletName="<%= ConfigurationAdminPortletKeys.SYSTEM_SETTINGS %>" var="editConfigurationActionURL" />

<aui:form action="<%= editConfigurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault();" + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:screen-navigation
		context="<%= editConfigurationDisplayContext %>"
		key="<%= CommerceCloudClientScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_CLOUD_CLIENT_CONFIGURATION %>"
		portletURL="<%= currentURLObj %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>