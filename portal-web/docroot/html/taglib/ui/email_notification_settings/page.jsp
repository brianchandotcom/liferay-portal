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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "emailFromName");
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress");

Boolean emailEnabled = ParamUtil.getBoolean(request, "emailEnabled");
String emailParam = ParamUtil.getString(request, "emailParam");
String defaultEmailSubject = ParamUtil.getString(request, "defaultEmailSubject");
String defaultEmailBody = ParamUtil.getString(request, "defaultEmailBody");

String emailSubjectParam = emailParam + "Subject";
String emailBodyParam = emailParam + "Body";

String emailSubject = PrefsParamUtil.getString(portletPreferences, request, emailSubjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(portletPreferences, request, emailBodyParam, defaultEmailBody);
%>

<aui:fieldset>
	<aui:input label="enabled" name='<%= "preferences--" + emailParam + "Enabled--" %>' type="checkbox" value="<%= emailEnabled %>" />

	<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailSubjectParam + "--" %>' value="<%= emailSubject %>" />

	<aui:field-wrapper label="body">
		<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" initMethod='<%= "init" + emailBodyParam + "Editor" %>' name="<%= emailParam %>" />

		<aui:input name='<%= "preferences--" + emailBodyParam + "--" %>' type="hidden" />
	</aui:field-wrapper>
</aui:fieldset>

<aui:fieldset cssClass="definition-of-terms">
	<%@ include file="/html/portlet/journal/definition_of_terms.jspf" %>
</aui:fieldset>

<aui:script>
	function <portlet:namespace />init<%= emailBodyParam %>Editor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.configuration.jsp";
%>