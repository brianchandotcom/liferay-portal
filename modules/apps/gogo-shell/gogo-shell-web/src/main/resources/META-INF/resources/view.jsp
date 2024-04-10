<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<clay:alert
						displayType="info"
						message="command-will-only-be-executed-on-this-node"
					/>

					<aui:input name="command" prefix='<%= (String)SessionMessages.get(renderRequest, "prompt") %>' value='<%= (String)SessionMessages.get(renderRequest, "command") %>' />
				</aui:fieldset>
			</div>
		</div>

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