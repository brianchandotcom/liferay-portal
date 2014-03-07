<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String bodyLabel = (String)request.getAttribute("liferay-ui:email-notification-settings:bodyLabel");
String emailBody = (String)request.getAttribute("liferay-ui:email-notification-settings:emailBody");
Map<String, String> emailDefinitionTerms = (Map<String, String>)request.getAttribute("liferay-ui:email-notification-settings:emailDefinitionTerms");
boolean emailEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:email-notification-settings:emailEnabled"));
String emailParam = (String)request.getAttribute("liferay-ui:email-notification-settings:emailParam");
String emailSubject = (String)request.getAttribute("liferay-ui:email-notification-settings:emailSubject");
String fieldPrefix = (String)request.getAttribute("liferay-ui:email-notification-settings:fieldPrefix");
String helpMessage = (String)request.getAttribute("liferay-ui:email-notification-settings:helpMessage");
String languageId = (String)request.getAttribute("liferay-ui:email-notification-settings:languageId");
boolean showEmailEnabled = GetterUtil.getBoolean(request.getAttribute("liferay-ui:email-notification-settings:showEmailEnabled"));
boolean showSubject = GetterUtil.getBoolean(request.getAttribute("liferay-ui:email-notification-settings:showSubject"));
%>

<aui:fieldset>
	<c:if test="<%= showEmailEnabled %>">
		<aui:input label="enabled" name='<%= fieldPrefix + "--" + emailParam + "Enabled--" %>' type="checkbox" value="<%= emailEnabled %>" />
	</c:if>

	<c:if test="<%= showSubject %>">
		<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= fieldPrefix + "--" + emailParam + "Subject" + (Validator.isNotNull(languageId) ? ("_" + languageId) : StringPool.BLANK) + "--" %>' value="<%= emailSubject %>" />
	</c:if>

	<aui:field-wrapper helpMessage="<%= helpMessage %>" label="<%= bodyLabel %>">
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "init" + emailParam + "BodyEditor" %>' name="<%= emailParam %>" />

		<aui:input name='<%= fieldPrefix + "--" + emailParam + "Body" + (Validator.isNotNull(languageId) ? ("_" + languageId) : StringPool.BLANK) + "--" %>' type="hidden" />
	</aui:field-wrapper>
</aui:fieldset>

<c:if test="<%= (emailDefinitionTerms != null) && !emailDefinitionTerms.isEmpty() %>">
	<aui:fieldset cssClass="definition-of-terms" label="definition-of-terms">
		<dl>

			<%
			for (Map.Entry<String, String> entry : emailDefinitionTerms.entrySet()) {
			%>

				<dt>
					<%= entry.getKey() %>
				</dt>
				<dd>
					<%= entry.getValue() %>
				</dd>

			<%
			}
			%>

		</dl>
	</aui:fieldset>
</c:if>

<aui:script>
	function <portlet:namespace />init<%= emailParam %>BodyEditor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.taglib.ui.email_notification_settings.jsp";
%>