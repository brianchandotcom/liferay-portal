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
String dispatchTaskSettings = StringPool.BLANK;

if (dispatchTrigger != null) {
	dispatchTriggerId = dispatchTrigger.getDispatchTriggerId();
	dispatchTaskExecutorType = dispatchTrigger.getDispatchTaskExecutorType();
	dispatchTaskSettings = dispatchTrigger.getDispatchTaskSettings();
}
%>

<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="editDispatchTriggerActionURL" />

<clay:container-fluid
	cssClass="closed container-form-lg"
	id='<%= liferayPortletResponse.getNamespace() + "editDispatchTriggerId" %>'
>
	<div class="sheet">
		<liferay-ui:error exception="<%= NoSuchLogException.class %>" message="the-log-could-not-be-found" />
		<liferay-ui:error exception="<%= NoSuchTriggerException.class %>" message="the-trigger-could-not-be-found" />

		<liferay-ui:error-principal />

		<aui:form action="<%= editDispatchTriggerActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTriggerId) %>" />
			<aui:input name="dispatchTaskExecutorType" type="hidden" value="<%= dispatchTaskExecutorType %>" />
			<aui:input name="dispatchTaskSettings" type="hidden" />

			<div class="lfr-form-content">
				<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

				<aui:fieldset>
					<aui:input disabled="<%= (dispatchTrigger != null) && dispatchTrigger.isSystem() %>" name="name" required="<%= true %>" />
				</aui:fieldset>

				<div id="<portlet:namespace />dispatchTaskSettingsEditor"></div>

				<div class="sheet-footer">

					<%
					String taglibSaveOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTrigger');";
					%>

					<aui:button onClick="<%= taglibSaveOnClick %>" primary="<%= true %>" value="save" />

					<aui:button href="<%= backURL %>" type="cancel" />
				</div>
			</div>
		</aui:form>
	</div>
</clay:container-fluid>

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
		boundingBox: '#<portlet:namespace />dispatchTaskSettingsEditor',
		height: 600,
		mode: 'xml',
		tabSize: 4,
		width: '100%',
	}).render();

	var xmlFormatter = new Liferay.XMLFormatter();

	var content = xmlFormatter.format(
		'<%=
			HtmlUtil.escapeJS(dispatchTaskSettings)
		%>'
	);

	if (content) {
		content = content.trim();
	}

	contentEditor.set(STR_VALUE, content);

	Liferay.on('<portlet:namespace />saveTrigger', function (event) {
		var form = window.document['<portlet:namespace />fm'];

		form['<portlet:namespace />dispatchTaskSettings'].value = contentEditor.get(
			STR_VALUE
		);

		submitForm(
			form,
			'<portlet:actionURL name="/dispatch/edit_dispatch_trigger" />'
		);
	});
</aui:script>