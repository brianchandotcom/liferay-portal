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
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long dispatchTriggerId = 0;

DispatchTrigger dispatchTrigger = dispatchTriggerDisplayContext.getDispatchTrigger();

String dispatchTaskExecutorType = ParamUtil.getString(request, "dispatchTaskExecutorType");
String taskSettings = StringPool.BLANK;

if (dispatchTrigger != null) {
	dispatchTriggerId = dispatchTrigger.getDispatchTriggerId();
	dispatchTaskExecutorType = dispatchTrigger.getDispatchTaskExecutorType();
	taskSettings = dispatchTrigger.getDispatchTaskSettings();
}
%>

<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="editDispatchTriggerActionURL" />

<div class="closed container-fluid container-fluid-max-xl" id="<portlet:namespace />editDispatchTriggerId">
	<div class="container main-content-body sheet">
		<liferay-ui:error exception="<%= NoSuchLogException.class %>" message="the-log-could-not-be-found" />
		<liferay-ui:error exception="<%= NoSuchTriggerException.class %>" message="the-trigger-could-not-be-found" />

		<liferay-ui:error-principal />

		<aui:form action="<%= editDispatchTriggerActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTriggerId) %>" />
			<aui:input name="dispatchTaskExecutorType" type="hidden" value="<%= dispatchTaskExecutorType %>" />
			<aui:input name="taskSettings" type="hidden" />

			<div class="lfr-form-content">
				<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

				<aui:fieldset>
					<aui:input disabled="<%= (dispatchTrigger != null) && dispatchTrigger.isSystem() %>" name="name" required="<%= true %>" />
				</aui:fieldset>

				<div id="<portlet:namespace />taskSettingsEditor"></div>

				<aui:button-row>

					<%
					String taglibSaveOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTrigger');";
					%>

					<aui:button onClick="<%= taglibSaveOnClick %>" value="save" />

					<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
				</aui:button-row>
			</div>
		</aui:form>
	</div>
</div>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />selectType',
		function () {
			var A = AUI();

			var processType = A.one(<portlet:namespace />type).val();

			var portletURL = new Liferay.PortletURL.createURL(
				'<%= currentURLObj %>'
			);

			portletURL.setParameter('type', processType);

			window.location.replace(portletURL.toString());
		},
		['liferay-portlet-url']
	);
</aui:script>

<aui:script use="aui-ace-editor,liferay-xml-formatter">
	var STR_VALUE = 'value';

	var contentEditor = new A.AceEditor({
		boundingBox: '#<portlet:namespace />taskSettingsEditor',
		height: 600,
		mode: 'xml',
		tabSize: 4,
		width: '100%',
	}).render();

	var xmlFormatter = new Liferay.XMLFormatter();

	var content = xmlFormatter.format('<%= HtmlUtil.escapeJS(taskSettings) %>');

	if (content) {
		content = content.trim();
	}

	contentEditor.set(STR_VALUE, content);

	Liferay.on('<portlet:namespace />saveTrigger', function (event) {
		var form = window.document['<portlet:namespace />fm'];

		form['<portlet:namespace />taskSettings'].value = contentEditor.get(
			STR_VALUE
		);

		submitForm(
			form,
			'<portlet:actionURL name="/dispatch/edit_dispatch_trigger" />'
		);
	});
</aui:script>