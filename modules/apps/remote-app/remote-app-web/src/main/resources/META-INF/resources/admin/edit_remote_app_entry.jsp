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
String redirect = ParamUtil.getString(request, "redirect");

RemoteAppEntry remoteAppEntry = (RemoteAppEntry)request.getAttribute(RemoteAppAdminWebKeys.REMOTE_APP_ENTRY);

long remoteAppEntryId = BeanParamUtil.getLong(remoteAppEntry, request, "remoteAppEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((remoteAppEntry == null) ? LanguageUtil.get(request, "new-remote-app") : remoteAppEntry.getName(locale));
%>

<portlet:actionURL name="/remote_app_admin/edit_remote_app_entry" var="editRemoteAppEntryURL" />

<liferay-frontend:edit-form
	action="<%= editRemoteAppEntryURL %>"
	method="post"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (remoteAppEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="remoteAppEntryId" type="hidden" value="<%= remoteAppEntryId %>" />

	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementCSSURLsException.class %>" message="please-enter-valid-css-urls" />
	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementHTMLElementNameException.class %>" message="please-enter-a-valid-html-element-name" />
	<liferay-ui:error exception="<%= RemoteAppEntryCustomElementURLsException.class %>" message="please-enter-valid-remote-app-urls" />
	<liferay-ui:error exception="<%= RemoteAppEntryIFrameURLException.class %>" message="please-enter-a-unique-remote-app-url" />

	<aui:model-context bean="<%= remoteAppEntry %>" model="<%= RemoteAppEntry.class %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<aui:field-wrapper label="name" name="name">
				<liferay-ui:input-localized
					autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
					name="name"
					xml='<%= BeanPropertiesUtil.getString(remoteAppEntry, "name") %>'
				/>
			</aui:field-wrapper>

			<clay:select
				disabled="<%= remoteAppEntry != null %>"
				label="type"
				name="type"
				options='<%=
					Arrays.asList(new SelectOption(LanguageUtil.get(request, "custom-element"), RemoteAppConstants.TYPE_CUSTOM_ELEMENT, remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT)), new SelectOption(LanguageUtil.get(request, "iframe"), RemoteAppConstants.TYPE_IFRAME, remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME)))
				%>'
				propsTransformer="admin/js/remoteAppEntryTypeSelectPropsTransformer"
			/>

			<liferay-frontend:fieldset
				cssClass='<%= remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_IFRAME) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_iframe" %>'
			>
				<aui:input label="url" name="iFrameURL">
					<aui:validator name="url" />
				</aui:input>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				cssClass='<%= remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT) ? StringPool.BLANK : "d-none" %>'
				disabled="<%= !remoteAppAdminDisplayContext.isEditingRemoteAppEntryType(RemoteAppConstants.TYPE_CUSTOM_ELEMENT) %>"
				id='<%= liferayPortletResponse.getNamespace() + "_type_customElement" %>'
			>
				<aui:input label="html-element-name" name="customElementHTMLElementName">
					<aui:validator name="customElementName" />
				</aui:input>

				<%
				for (String customElementURL : remoteAppAdminDisplayContext.getCustomElementURLs()) {
				%>

					<div class="repeatable">
						<aui:input ignoreRequestValue="<%= true %>" label="url" name="customElementURLs" type="text" value="<%= customElementURL %>">
							<aui:validator name="url" />
						</aui:input>
					</div>

				<%
				}

				for (String customElementCSSURL : remoteAppAdminDisplayContext.getCustomElementCSSURLs()) {
				%>

					<div class="repeatable">
						<aui:input ignoreRequestValue="<%= true %>" label="css-url" name="customElementCSSURLs" type="text" value="<%= customElementCSSURL %>">
							<aui:validator name="url" />
						</aui:input>
					</div>

				<%
				}
				%>

			</liferay-frontend:fieldset>

			<clay:select
				label="portlet-category-name"
				name="portletCategoryName"
				options="<%=
					remoteAppAdminDisplayContext.getPortletCategoryNameSelectOptions()
				%>"
			/>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<clay:button
			label="save"
			type="submit"
		/>

		<clay:link
			displayType="secondary"
			href="<%= redirect %>"
			label="cancel"
			type="button"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		baseRows: '.repeatable',
		contentBox: '#<portlet:namespace />_type_customElement',
		namespace: '<portlet:namespace />',
	}).render();
</aui:script>