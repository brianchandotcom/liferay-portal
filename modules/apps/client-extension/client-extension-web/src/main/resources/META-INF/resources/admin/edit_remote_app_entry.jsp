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
	<aui:input name="clientExtensionEntryId" type="hidden" value="<%= editClientExtensionEntryDisplayContext.getClientExtensionEntryId() %>" />

	<liferay-ui:error exception="<%= ClientExtensionEntryCustomElementCSSURLsException.class %>" message="please-enter-valid-css-urls" />
	<liferay-ui:error exception="<%= ClientExtensionEntryCustomElementHTMLElementNameException.class %>" message="please-enter-a-valid-html-element-name" />
	<liferay-ui:error exception="<%= ClientExtensionEntryCustomElementURLsException.class %>" message="please-enter-valid-remote-app-urls" />
	<liferay-ui:error exception="<%= ClientExtensionEntryFriendlyURLMappingException.class %>" message="please-enter-a-valid-friendly-url-mapping" />
	<liferay-ui:error exception="<%= ClientExtensionEntryIFrameURLException.class %>" message="please-enter-a-unique-remote-app-url" />

	<aui:model-context bean="<%= editClientExtensionEntryDisplayContext.getClientExtensionEntry() %>" model="<%= ClientExtensionEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:field-wrapper label="name" name="name">
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

			<aui:input label="source-code-url" name="sourceCodeURL" type="text" />

			<clay:select
				disabled="<%= editClientExtensionEntryDisplayContext.isTypeDisabled() %>"
				label="type"
				name="type"
				options='<%=
					Arrays.asList(new SelectOption(LanguageUtil.get(request, "custom-element"), ClientExtensionConstants.TYPE_CUSTOM_ELEMENT, editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_CUSTOM_ELEMENT)), new SelectOption(LanguageUtil.get(request, "iframe"), ClientExtensionConstants.TYPE_IFRAME, editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_IFRAME)))
				%>'
				propsTransformer="admin/js/clientExtensionEntryTypeSelectPropsTransformer"
			/>

			<liferay-frontend:fieldset
				cssClass='<%= editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_IFRAME) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_IFRAME) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_iframe" %>'
			>
				<aui:input label="url" name="iFrameURL">
					<aui:validator name="urlAllowRelative" />
				</aui:input>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				cssClass='<%= editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_CUSTOM_ELEMENT) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !editClientExtensionEntryDisplayContext.isEditingClientExtensionEntryType(ClientExtensionConstants.TYPE_CUSTOM_ELEMENT) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_customElement" %>'
			>
				<aui:input label="html-element-name" name="customElementHTMLElementName">
					<aui:validator name="customElementName" />
				</aui:input>

				<aui:input label="use-esm" name="customElementUseESM" type="checkbox" value="<%= editClientExtensionEntryDisplayContext.isCustomElementUseESM() %>" />

				<div id="<portlet:namespace />_type_customElementURLs">

					<%
					for (String customElementURL : editClientExtensionEntryDisplayContext.getCustomElementURLs()) {
					%>

						<div class="lfr-form-row">
							<aui:input ignoreRequestValue="<%= true %>" label="url" name="customElementURLs" type="text" value="<%= customElementURL %>">
								<aui:validator name="urlAllowRelative" />
							</aui:input>
						</div>

					<%
					}
					%>

				</div>

				<div id="<portlet:namespace />_type_customElementCSSURLs">

					<%
					for (String customElementCSSURL : editClientExtensionEntryDisplayContext.getCustomElementCSSURLs()) {
					%>

						<div class="lfr-form-row">
							<aui:input ignoreRequestValue="<%= true %>" label="css-url" name="customElementCSSURLs" type="text" value="<%= customElementCSSURL %>">
								<aui:validator name="urlAllowRelative" />
							</aui:input>
						</div>

					<%
					}
					%>

				</div>
			</liferay-frontend:fieldset>

			<aui:input disabled="<%= editClientExtensionEntryDisplayContext.isInstanceableDisabled() %>" label="instanceable" name="instanceable" type="checkbox" value="<%= editClientExtensionEntryDisplayContext.isInstanceable() %>" />

			<clay:select
				label="portlet-category-name"
				name="portletCategoryName"
				options="<%=
					editClientExtensionEntryDisplayContext.getPortletCategoryNameSelectOptions()
				%>"
			/>

			<aui:input label="friendly-url-mapping" name="friendlyURLMapping">
				<aui:validator name="friendlyURLMapping" />
			</aui:input>

			<aui:input label="properties" name="properties" type="textarea" />
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<clay:button
			label='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), 0L, ClientExtensionEntry.class.getName()) ? "submit-for-publication" : "publish" %>'
			type="submit"
		/>

		<clay:link
			displayType="secondary"
			href="<%= editClientExtensionEntryDisplayContext.getRedirect() %>"
			label="cancel"
			type="button"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_type_customElementURLs',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();

	new Liferay.AutoFields({
		contentBox: '#<portlet:namespace />_type_customElementCSSURLs',
		minimumRows: 1,
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>