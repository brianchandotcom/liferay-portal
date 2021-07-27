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

<%@ include file="/remote_custom_element_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

RemoteCustomElementEntry remoteCustomElementEntry = (RemoteCustomElementEntry)request.getAttribute(RemoteAppAdminWebKeys.REMOTE_CUSTOM_ELEMENT_ENTRY);

long remoteCustomElementEntryId = BeanParamUtil.getLong(remoteCustomElementEntry, request, "remoteCustomElementEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((remoteCustomElementEntry == null) ? LanguageUtil.get(request, "new-remote-custom-element") : remoteCustomElementEntry.getName(locale));
%>

<portlet:actionURL name="/remote_custom_element_admin/edit_remote_custom_element_entry" var="editRemoteCustomElementEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editRemoteCustomElementEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveRemoteCustomElementEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="remoteCustomElementEntryId" type="hidden" value="<%= remoteCustomElementEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateRemoteCustomElementEntryURLException.class %>" message="please-enter-a-unique-remote-custom-element-url" />

		<aui:model-context bean="<%= remoteCustomElementEntry %>" model="<%= RemoteCustomElementEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper label="name">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml='<%= BeanPropertiesUtil.getString(remoteCustomElementEntry, "name") %>'
					/>
				</aui:field-wrapper>

				<aui:input name="tagName">
					<aui:validator name="tagName" />
				</aui:input>

				<aui:input name="url">
					<aui:validator name="url" />
				</aui:input>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />saveRemoteCustomElementEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (remoteCustomElementEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>