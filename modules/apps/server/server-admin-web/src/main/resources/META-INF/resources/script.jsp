<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String language = ParamUtil.getString(renderRequest, "language", "groovy");

if (SessionMessages.contains(renderRequest, "language")) {
	language = (String)SessionMessages.get(renderRequest, "language");
}

String output = ParamUtil.getString(renderRequest, "output", "text");

if (SessionMessages.contains(renderRequest, "output")) {
	output = (String)SessionMessages.get(renderRequest, "output");
}

String script = "// ### Groovy Sample ###\n\nnumber = com.liferay.portal.kernel.service.UserLocalServiceUtil.getUsersCount();\n\nout.println(number);";

if (SessionMessages.contains(renderRequest, "script")) {
	script = (String)SessionMessages.get(renderRequest, "script");
}

String scriptOutput = (String)SessionMessages.get(renderRequest, "scriptOutput");
%>

<liferay-ui:error exception="<%= ScriptingException.class %>">

	<%
	ScriptingException se = (ScriptingException)errorException;
	%>

	<pre><%= HtmlUtil.escape(se.getMessage()) %></pre>
</liferay-ui:error>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<aui:select name="language">

			<%
			for (String supportedLanguage : ServerScriptingUtil.getSupportedLanguages()) {
			%>

				<aui:option label="<%= TextFormatter.format(supportedLanguage, TextFormatter.J) %>" selected="<%= supportedLanguage.equals(language) %>" value="<%= supportedLanguage %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select name="output">
			<aui:option label="text" selected='<%= output.equals("text") %>' value="text" />
			<aui:option label="html" selected='<%= output.equals("html") %>' value="html" />
		</aui:select>

		<aui:input cssClass="lfr-textarea-container" name="script" resizable="<%= true %>" type="textarea" value="<%= script %>" />

		<aui:button-row>
			<aui:button cssClass="save-server-button" data-cmd="runScript" primary="<%= true %>" value="execute" />
		</aui:button-row>
	</div>
</div>

<c:if test="<%= Validator.isNotNull(scriptOutput) %>">
	<b><liferay-ui:message key="output" /></b>

	<c:choose>
		<c:when test='<%= output.equals("html") %>'>
			<div><%= scriptOutput %></div>
		</c:when>
		<c:otherwise>
			<pre><%= HtmlUtil.escape(scriptOutput) %></pre>
		</c:otherwise>
	</c:choose>
</c:if>

<aui:script>
	var <portlet:namespace />selectLanguage = document.getElementById(
		'<portlet:namespace />language'
	);
	var <portlet:namespace />textArea = document.getElementById(
		'<portlet:namespace />script'
	);

	if (<portlet:namespace />selectLanguage && <portlet:namespace />textArea) {
		<portlet:namespace />selectLanguage.addEventListener('change', () => {
			<portlet:namespace />textArea.value = '';
		});
	}
</aui:script>