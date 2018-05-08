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

<%@ include file="/init.jsp" %>

<%
String bodyLabel = (String)request.getAttribute("liferay-frontend:email-notification-settings:bodyLabel");
String emailBody = (String)request.getAttribute("liferay-frontend:email-notification-settings:emailBody");
Map<String, String> emailDefinitionTerms = (Map<String, String>)request.getAttribute("liferay-frontend:email-notification-settings:emailDefinitionTerms");
boolean emailEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-frontend:email-notification-settings:emailEnabled"));
String emailParam = (String)request.getAttribute("liferay-frontend:email-notification-settings:emailParam");
String emailSubject = (String)request.getAttribute("liferay-frontend:email-notification-settings:emailSubject");
String fieldPrefix = (String)request.getAttribute("liferay-frontend:email-notification-settings:fieldPrefix");
String fieldPrefixSeparator = (String)request.getAttribute("liferay-frontend:email-notification-settings:fieldPrefixSeparator");
String helpMessage = (String)request.getAttribute("liferay-frontend:email-notification-settings:helpMessage");
boolean showEmailEnabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:email-notification-settings:showEmailEnabled"));
boolean showSubject = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:email-notification-settings:showSubject"));
%>

<aui:fieldset markupView="lexicon">
	<c:if test="<%= showEmailEnabled %>">
		<aui:input label="enabled" name='<%= fieldPrefix + fieldPrefixSeparator + emailParam + "Enabled" + fieldPrefixSeparator %>' type="checkbox" value="<%= emailEnabled %>" />
	</c:if>

	<c:if test="<%= showSubject %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(emailSubject) && Validator.isXml(emailSubject) %>">
				<aui:field-wrapper label="subject">
					<liferay-ui:input-localized
						fieldPrefix="<%= fieldPrefix %>"
						fieldPrefixSeparator="<%= fieldPrefixSeparator %>"
						name='<%= emailParam + "Subject" %>'
						xml="<%= emailSubject %>"
					/>
				</aui:field-wrapper>
			</c:when>
			<c:otherwise>
				<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= fieldPrefix + fieldPrefixSeparator + emailParam + "Subject" + fieldPrefixSeparator %>' value="<%= emailSubject %>" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<aui:field-wrapper helpMessage="<%= helpMessage %>" label="<%= bodyLabel %>">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(emailBody) && Validator.isXml(emailBody) %>">
				<liferay-ui:input-localized
					editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.email_notification_settings.jsp") %>'
					fieldPrefix="<%= fieldPrefix %>"
					fieldPrefixSeparator="<%= fieldPrefixSeparator %>"
					name='<%= emailParam + "Body" %>'
					toolbarSet="email"
					type="editor"
					xml="<%= emailBody %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:input-editor
					contents="<%= emailBody %>"
					editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.email_notification_settings.jsp") %>'
					name="<%= emailParam %>"
				/>

				<aui:input name='<%= fieldPrefix + fieldPrefixSeparator + emailParam + "Body" + fieldPrefixSeparator %>' type="hidden" />
			</c:otherwise>
		</c:choose>
	</aui:field-wrapper>
</aui:fieldset>

<c:if test="<%= (emailDefinitionTerms != null) && !emailDefinitionTerms.isEmpty() %>">
	<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="definition-of-terms" markupView="lexicon">
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