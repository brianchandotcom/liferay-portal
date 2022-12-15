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

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editClientExtensionEntryDisplayContext.getRedirect());

renderResponse.setTitle(editClientExtensionEntryDisplayContext.getTitle());
%>

<portlet:actionURL name="/client_extension_admin/edit_client_extension_entry" var="editClientExtensionEntryURL" />

<liferay-frontend:edit-form
	action="<%= editClientExtensionEntryURL %>"
	method="post"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getCmd() %>" />
	<aui:input name="redirect" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getRedirect() %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getExternalReferenceCode() %>" />

	<liferay-ui:error exception="<%= ClientExtensionEntryTypeSettingsException.class %>">

		<%
		ClientExtensionEntryTypeSettingsException clientExtensionEntryTypeSettingsException = (ClientExtensionEntryTypeSettingsException)errorException;
		%>

		<liferay-ui:message arguments="<%= clientExtensionEntryTypeSettingsException.getMessageArguments() %>" key="<%= clientExtensionEntryTypeSettingsException.getMessageKey() %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= ClientExtensionEntryNameException.class %>" message="client-extension-name-is-required" />

	<liferay-frontend:edit-form-body>
		<h3 class="mb-3"><%= editClientExtensionEntryDisplayContext.getTitle() %></h3>

		<p class="text-secondary"><%= editClientExtensionEntryDisplayContext.getHelpLabel() %> <a href="https://learn.liferay.com/dxp/latest/en/building-applications/remote-apps/understanding-remote-app-types.html" target="_blank"><liferay-ui:message arguments="Client Extensions" key="learn-more-about-x" /></a></p>

		<liferay-ui:panel-container
			cssClass="panel-group-flush "
			extended="<%= true %>"
			id="clientExtensionOptions"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="panel-unstyled text-secondary"
				defaultState="opened"
				extended="<%= true %>"
				id="clientExtensionOptionIdentiy"
				markupView="lexicon"
				persistState="<%= true %>"
				title="identity"
			>
				<aui:field-wrapper label="name" name="name" required="<%= true %>">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml="<%= editClientExtensionEntryDisplayContext.getName() %>"
					/>
				</aui:field-wrapper>

				<liferay-editor:editor
					contents="<%= editClientExtensionEntryDisplayContext.getDescription() %>"
					editorName="contentEditor"
					name="description"
					placeholder="description"
				/>
			</liferay-ui:panel>

			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="panel-unstyled text-secondary"
				defaultState="opened"
				extended="<%= true %>"
				id="clientExtensionOptionContent"
				markupView="lexicon"
				persistState="<%= true %>"
				title="content"
			>
				<liferay-util:include page="<%= editClientExtensionEntryDisplayContext.getEditJSP() %>" servletContext="<%= application %>" />
			</liferay-ui:panel>

			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="panel-unstyled text-secondary"
				defaultState="opened"
				extended="<%= true %>"
				id="clientExtensionOptionTools"
				markupView="lexicon"
				persistState="<%= true %>"
				title="additional-resources"
			>
				<aui:field-wrapper cssClass="form-group">
					<aui:input label="source-code-url" name="sourceCodeURL" type="text" value="<%= editClientExtensionEntryDisplayContext.getSourceCodeURL() %>" />

					<div class="form-text">
						<liferay-ui:message key="a-url-to-the-external-application-source-code-for-convenience" />
					</div>
				</aui:field-wrapper>

				<aui:input name="type" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getType() %>" />

				<c:if test="<%= editClientExtensionEntryDisplayContext.isPropertiesVisible() %>">
					<aui:input label="properties" name="properties" placeholder="define-the-default-properties-that-are-included-in-all-instances-of-the-client-extension-these-properties-are-passed-to-the-application-as-additional-url-attributes-so-they-be-accessed-programmatically" type="textarea" value="<%= editClientExtensionEntryDisplayContext.getProperties() %>" />
				</c:if>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editClientExtensionEntryDisplayContext.getRedirect() %>"
			submitLabel='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), 0L, ClientExtensionEntry.class.getName()) ? "submit-for-publication" : "publish" %>'
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>