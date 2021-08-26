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

WebHookEntry webHookEntry = (WebHookEntry)request.getAttribute(WebHookAdminWebKeys.WEB_HOOK_ENTRY);

long webHookEntryId = BeanParamUtil.getLong(webHookEntry, request, "webHookEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((webHookEntry == null) ? LanguageUtil.get(request, "new-web-hook") : webHookEntry.getName(locale));
%>

<portlet:actionURL name="/web_hook_admin/edit_web_hook_entry" var="editwebHookEntryURL" />

<clay:container-fluid>
	<aui:form action="<%= editwebHookEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveWebHookEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="webHookEntryId" type="hidden" value="<%= webHookEntryId %>" />

		<liferay-ui:error exception="<%= DuplicateWebHookEntryException.class %>" message="please-enter-a-unique-web-hook-destination-and-url" />

		<aui:model-context bean="<%= webHookEntry %>" model="<%= WebHookEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:field-wrapper label="name">
					<liferay-ui:input-localized
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						name="name"
						xml='<%= BeanPropertiesUtil.getString(webHookEntry, "name") %>'
					/>
				</aui:field-wrapper>

				<aui:select label="destination" name="destination">

					<%
					for (String destination : webHookAdminDisplayContext.getDestinations()) {
					%>

						<aui:option label="<%= destination %>" selected="<%= (webHookEntry != null) && (webHookEntry.getDestination() == destination) %>" value="<%= destination %>" />

					<%
					}
					%>

				</aui:select>

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
	function <portlet:namespace />saveWebHookEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value =
			'<%= (webHookEntry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>