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
String redirect = ParamUtil.getString(request, "redirect");

long wsrpConsumerId = ParamUtil.getLong(request, "wsrpConsumerId");

WSRPConsumer wsrpConsumer = WSRPConsumerLocalServiceUtil.fetchWSRPConsumer(wsrpConsumerId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((wsrpConsumer == null) ? LanguageUtil.get(request, "new-consumer") : wsrpConsumer.getName()));
%>

<portlet:actionURL name="updateWSRPConsumer" var="updateWSRPConsumerURL" />

<aui:form action="<%= updateWSRPConsumerURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConsumer();" %>'>
	<aui:input name="mvcPath" type="hidden" value="/admin/edit_consumer.jsp" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="wsrpConsumerId" type="hidden" value="<%= wsrpConsumerId %>" />

	<liferay-ui:error exception="<%= WSRPConsumerNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= WSRPConsumerWSDLException.class %>" message="url-does-not-point-to-a-valid-wsrp-producer" />

	<aui:model-context bean="<%= wsrpConsumer %>" model="<%= WSRPConsumer.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="url" type="textarea" />

		<aui:input name="forwardCookies" />

		<aui:input name="forwardHeaders" />

		<aui:input helpMessage="markup-character-sets-help" name="markupCharacterSets" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConsumer() {
		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
</aui:script>

<%
if (wsrpConsumer != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-consumer"), currentURL);
}
%>