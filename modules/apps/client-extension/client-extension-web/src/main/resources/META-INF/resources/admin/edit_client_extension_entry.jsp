<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	<liferay-ui:error exception="<%= ClientExtensionEntryNameException.class %>" message="client-extension-name-is-required" />

	<liferay-ui:error exception="<%= ClientExtensionEntryTypeSettingsException.class %>">

		<%
		ClientExtensionEntryTypeSettingsException clientExtensionEntryTypeSettingsException = (ClientExtensionEntryTypeSettingsException)errorException;
		%>

		<liferay-ui:message arguments="<%= clientExtensionEntryTypeSettingsException.getMessageArguments() %>" key="<%= clientExtensionEntryTypeSettingsException.getMessageKey() %>" />
	</liferay-ui:error>

	<liferay-frontend:edit-form-body>
		<h3 class="mb-3"><%= editClientExtensionEntryDisplayContext.getTitle() %></h3>

		<p class="text-secondary"><%= editClientExtensionEntryDisplayContext.getHelpLabel() %>
			<liferay-learn:message
				key="<%= editClientExtensionEntryDisplayContext.getLearnResourceKey() %>"
				resource="client-extension-web"
			/>
		</p>

		<p class="text-secondary">
			<liferay-learn:message
				key="learn-browser-based-client-extensions"
				resource="client-extension-web"
			/>
		</p>

		<clay:panel-group>
			<clay:panel
				displayTitle='<%= LanguageUtil.get(request, "identity") %>'
				expanded="<%= true %>"
			>
				<div class="panel-body">
					<aui:field-wrapper label="name" name="name" required="<%= true %>">
						<liferay-ui:input-localized
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
				</div>
			</clay:panel>

			<clay:panel
				displayTitle='<%= LanguageUtil.get(request, "content") %>'
				expanded="<%= true %>"
			>
				<div class="panel-body">
					<liferay-util:include page="<%= editClientExtensionEntryDisplayContext.getEditJSP() %>" servletContext="<%= application %>" />
				</div>
			</clay:panel>

			<clay:panel
				displayTitle='<%= LanguageUtil.get(request, "additional-resources") %>'
				expanded="<%= true %>"
			>
				<div class="panel-body">
					<aui:field-wrapper cssClass="form-group">
						<aui:input label="source-code-url" name="sourceCodeURL" type="text" value="<%= editClientExtensionEntryDisplayContext.getSourceCodeURL() %>" />

						<div class="form-text">
							<liferay-ui:message key="specify-the-source-code-repository-url-for-the-client-extension" />
						</div>
					</aui:field-wrapper>

					<aui:input name="type" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getType() %>" />

					<c:if test="<%= editClientExtensionEntryDisplayContext.isPropertiesVisible() %>">
						<aui:input label="properties" name="properties" placeholder="define-the-default-properties-that-are-included-in-all-instances-of-the-client-extension-these-properties-are-passed-to-the-application-as-additional-url-attributes-so-they-can-be-accessed-programmatically" type="textarea" value="<%= editClientExtensionEntryDisplayContext.getProperties() %>" />
					</c:if>
				</div>
			</clay:panel>
		</clay:panel-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editClientExtensionEntryDisplayContext.getRedirect() %>"
			submitLabel='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), 0L, ClientExtensionEntry.class.getName()) ? "submit-for-workflow" : "publish" %>'
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>