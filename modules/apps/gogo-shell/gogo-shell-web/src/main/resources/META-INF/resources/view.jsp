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
String commandOutput = (String)SessionMessages.get(renderRequest, "commandOutput");
%>

<portlet:actionURL name="executeCommand" var="executeCommandURL" />

<clay:container-fluid>
	<aui:form action="<%= executeCommandURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "executeCommand();" %>'>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:error key="gogo">

			<%
			Exception e = (Exception)errorException;
			%>

			<%= HtmlUtil.escape(e.getMessage()) %>
		</liferay-ui:error>

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="command" prefix='<%= (String)SessionMessages.get(renderRequest, "prompt") %>' value='<%= (String)SessionMessages.get(renderRequest, "command") %>' />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button primary="<%= true %>" type="submit" value="execute" />

			<div class="btn float-right">
				<liferay-learn:message
					key="general"
					resource="gogo-shell-web"
				/>
			</div>
		</aui:button-row>

		<c:if test="<%= Validator.isNotNull(commandOutput) %>">
			<b><liferay-ui:message key="output" /></b>

			<pre><%= HtmlUtil.escape(commandOutput) %></pre>
		</c:if>
	</aui:form>
</clay:container-fluid>

<aui:script>
	function <portlet:namespace />executeCommand() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			submitForm(form);
		}
	}
</aui:script>