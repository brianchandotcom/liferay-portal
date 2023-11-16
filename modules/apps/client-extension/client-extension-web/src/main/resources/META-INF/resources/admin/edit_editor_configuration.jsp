<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<EditorConfigurationCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

EditorConfigurationCET editorConfigurationCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="js-url" name="url" required="<%= true %>" type="text" value="<%= editorConfigurationCET.getURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="enter-the-url-of-the-javascript-file-to-provide-editor-configuration-json" />
	</div>
</aui:field-wrapper>

<div class="lfr-form-rows" id="<portlet:namespace />_portletNames_field">

	<%
	for (String _portletName : editClientExtensionEntryDisplayContext.getStrings(editorConfigurationCET.getPortletNames())) {
	%>

		<div class="lfr-form-row">
			<aui:field-wrapper cssClass="form-group">
				<aui:input ignoreRequestValue="<%= true %>" label="portlet-names" name="portletNames" type="text" value="<%= _portletName %>" />

				<div class="form-text form-text-repeat">
					<liferay-ui:message key="enter-portlet-name-to-apply-this-editor-configuration" />
				</div>
			</aui:field-wrapper>
		</div>

	<%
	}
	%>

</div>

<div class="lfr-form-rows" id="<portlet:namespace />_editorNames_field">

	<%
	for (String editorName : editClientExtensionEntryDisplayContext.getStrings(editorConfigurationCET.getEditorNames())) {
	%>

		<div class="lfr-form-row">
			<aui:field-wrapper cssClass="form-group">
				<aui:input ignoreRequestValue="<%= true %>" label="editor-name" name="editorNames" type="text" value="<%= editorName %>" />

				<div class="form-text form-text-repeat">
					<liferay-ui:message key="enter-editor-name-to-apply-this-editor-configuration" />
				</div>
			</aui:field-wrapper>
		</div>

	<%
	}
	%>

</div>

<div class="lfr-form-rows" id="<portlet:namespace />_editorConfigKeys_field">

	<%
	for (String editorConfigKey : editClientExtensionEntryDisplayContext.getStrings(editorConfigurationCET.getEditorConfigKeys())) {
	%>

		<div class="lfr-form-row">
			<aui:field-wrapper cssClass="form-group">
				<aui:input ignoreRequestValue="<%= true %>" label="editor-config-key" name="editorConfigKeys" type="text" value="<%= editorConfigKey %>" />

				<div class="form-text form-text-repeat">
					<liferay-ui:message key="enter-editor-config-key-to-apply-this-editor-configuration" />
				</div>
			</aui:field-wrapper>
		</div>

	<%
	}
	%>

</div>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_portletNames_field',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();

	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_editorNames_field',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();

	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_editorConfigKeys_field',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>