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
Map<String, String> definitionTerms = (Map<String, String>)request.getAttribute("liferay-ui:email-notification-settings:definitionTerms");
String emailBody = (String)request.getAttribute("liferay-ui:email-notification-settings:emailBody");
boolean emailEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:email-notification-settings:emailEnabled"));
String emailParam = (String)request.getAttribute("liferay-ui:email-notification-settings:emailParam");
String emailSubject = (String)request.getAttribute("liferay-ui:email-notification-settings:emailSubject");
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "preferences--" + emailParam + "Enabled--" %>' type="checkbox" value="<%= emailEnabled %>" />

	<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailParam + "Subject--" %>' value="<%= emailSubject %>" />

	<aui:field-wrapper label="body">
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "init" + emailParam + "BodyEditor" %>' name="<%= emailParam %>" />

		<aui:input name='<%= "preferences--" + emailParam + "Body--" %>' type="hidden" />
	</aui:field-wrapper>
</aui:fieldset>

<if test="<%= definitionTerms.size() > 0 %>">
	<aui:fieldset cssClass="definition-of-terms">
		<legend>
			<liferay-ui:message key="definition-of-terms" />
		</legend>

		<dl>

			<%
			for (Map.Entry<String, String> definitionTerm : definitionTerms.entrySet()) {
			%>

				<dt>
					<%= definitionTerm.getKey() %>
				</dt>
				<dd>
					<%= definitionTerm.getValue() %>
				</dd>

			<%
			}
			%>

		</dl>
	</aui:fieldset>
</if>

<aui:script>
	function <portlet:namespace />init<%= emailParam %>BodyEditor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.taglib.ui.email_notification_settings.jsp";
%>