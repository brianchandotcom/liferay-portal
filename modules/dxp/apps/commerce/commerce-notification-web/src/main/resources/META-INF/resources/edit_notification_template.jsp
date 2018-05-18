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
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceNotificationsDisplayContext commerceNotificationsDisplayContext = (CommerceNotificationsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceNotificationTemplate commerceNotificationTemplate = commerceNotificationsDisplayContext.getCommerceNotificationTemplate();

String title = LanguageUtil.get(resourceBundle, "add-notification-template");

if (commerceNotificationTemplate != null) {
	title = LanguageUtil.format(request, "edit-x", commerceNotificationTemplate.getName(), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceNotificationTemplate" var="editCommerceNotificationTemplateActionURL" />

<aui:form action="<%= editCommerceNotificationTemplateActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceNotificationTemplate();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceNotificationTemplate == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="addCommerceUserSegmentEntryIds" type="hidden" value="" />
	<aui:input name="commerceNotificationTemplateId" type="hidden" value="<%= (commerceNotificationTemplate == null) ? 0 : commerceNotificationTemplate.getCommerceNotificationTemplateId() %>" />
	<aui:input name="deleteCommerceNotificationTemplateUserSegmentRelIds" type="hidden" value="" />

	<div class="col-md-8 offset-md-2">
		<liferay-ui:form-navigator
			formModelBean="<%= commerceNotificationTemplate %>"
			id="<%= CommerceNotificationTemplateFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_NOTIFICATION_TEMPLATE %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceNotificationTemplate() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
	window,
	'<portlet:namespace />selectType',
		function() {
			var A = AUI();

			var name = A.one('#<portlet:namespace />name').val();
			var description = A.one('#<portlet:namespace />description').val();
			var cc = A.one('#<portlet:namespace />cc').val();
			var ccn = A.one('#<portlet:namespace />ccn').val();
			var type = A.one('#<portlet:namespace />type').val();

			var portletURL = new Liferay.PortletURL.createURL('<%= currentURLObj %>');

			portletURL.setParameter('name', name);
			portletURL.setParameter('description', description);
			portletURL.setParameter('cc', cc);
			portletURL.setParameter('ccn', ccn);
			portletURL.setParameter('type', type);

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>