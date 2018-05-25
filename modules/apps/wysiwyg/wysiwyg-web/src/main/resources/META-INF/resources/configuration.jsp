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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveMessage();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<liferay-ui:input-editor
				contents="<%= message %>"
			/>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:input name="preferences--message--" type="hidden" />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveMessage() {
		var editorVal = window.<portlet:namespace />editor.getHTML();

		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var message = form.querySelector('#<portlet:namespace />message');

			if (message) {
				message.value = editorVal;
			}

			submitForm(form);
		}
	}
</aui:script>