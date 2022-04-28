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
EditRemoteAppEntryDisplayContext editRemoteAppEntryDisplayContext = (EditRemoteAppEntryDisplayContext)renderRequest.getAttribute(RemoteAppAdminWebKeys.EDIT_REMOTE_APP_ENTRY_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editRemoteAppEntryDisplayContext.getRedirect());

renderResponse.setTitle(editRemoteAppEntryDisplayContext.getTitle());
%>

<portlet:actionURL name="/remote_app_admin/edit_remote_app_entry" var="editRemoteAppEntryURL" />

<liferay-frontend:edit-form
	action="<%= editRemoteAppEntryURL %>"
	method="post"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= editRemoteAppEntryDisplayContext.getCmd() %>" />
	<aui:input name="redirect" type="hidden" value="<%= editRemoteAppEntryDisplayContext.getRedirect() %>" />
	<aui:input name="remoteAppEntryId" type="hidden" value="<%= editRemoteAppEntryDisplayContext.getRemoteAppEntryId() %>" />

	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementCSSURLsException.class %>" message="please-enter-valid-css-urls" />
	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementHTMLElementNameException.class %>" message="please-enter-a-valid-html-element-name" />
	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementURLsException.class %>" message="please-enter-valid-remote-app-urls" />
	<liferay-ui:error exception="<%= RemoteAppEntryFriendlyURLMappingException.class %>" message="please-enter-a-valid-friendly-url-mapping" />
	<liferay-ui:error exception="<%= RemoteAppEntryIFrameURLException.class %>" message="please-enter-a-unique-remote-app-url" />

	<aui:model-context bean="<%= editRemoteAppEntryDisplayContext.getRemoteAppEntry() %>" model="<%= RemoteAppEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:field-wrapper label="name" name="name">
				<liferay-ui:input-localized
					autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
					name="name"
					xml="<%= editRemoteAppEntryDisplayContext.getName() %>"
				/>
			</aui:field-wrapper>

			<liferay-editor:editor
				contents="<%= editRemoteAppEntryDisplayContext.getDescription() %>"
				editorName="contentEditor"
				name="description"
				placeholder="description"
			/>

			<aui:input label="source-code-url" name="sourceCodeURL" type="text" />

			<clay:select
				disabled="<%= editRemoteAppEntryDisplayContext.isTypeDisabled() %>"
				label="type"
				name="type"
				options='<%=
					Arrays.asList(new SelectOption(LanguageUtil.get(request, "custom-element"), RemoteAppConstants.TYPE_CUSTOM_ELEMENT, editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT)), new SelectOption(LanguageUtil.get(request, "iframe"), RemoteAppConstants.TYPE_IFRAME, editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME)))
				%>'
				propsTransformer="admin/js/remoteAppEntryTypeSelectPropsTransformer"
			/>

			<liferay-frontend:fieldset
				cssClass='<%= editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_iframe" %>'
			>
				<aui:input label="url" name="iFrameURL">
					<aui:validator name="urlAllowRelative" />
				</aui:input>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				cssClass='<%= editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !editRemoteAppEntryDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_customElement" %>'
			>
				<aui:input label="html-element-name" name="customElementHTMLElementName">
					<aui:validator name="customElementName" />
				</aui:input>

				<aui:input label="use-esm" name="customElementUseESM" type="checkbox" value="<%= editRemoteAppEntryDisplayContext.isCustomElementUseESM() %>" />

				<div id="<portlet:namespace />_type_customElementURLs">

					<%
					for (String customElementURL : editRemoteAppEntryDisplayContext.getCustomElementURLs()) {
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
					for (String customElementCSSURL : editRemoteAppEntryDisplayContext.getCustomElementCSSURLs()) {
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

			<aui:input disabled="<%= editRemoteAppEntryDisplayContext.isInstanceableDisabled() %>" label="instanceable" name="instanceable" type="checkbox" value="<%= editRemoteAppEntryDisplayContext.isInstanceable() %>" />

			<clay:select
				label="portlet-category-name"
				name="portletCategoryName"
				options="<%=
					editRemoteAppEntryDisplayContext.getPortletCategoryNameSelectOptions()
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
			label='<%= WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), 0L, RemoteAppEntry.class.getName()) ? "submit-for-publication" : "publish" %>'
			type="submit"
		/>

		<clay:link
			displayType="secondary"
			href="<%= editRemoteAppEntryDisplayContext.getRedirect() %>"
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