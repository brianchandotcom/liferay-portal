<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/password_generator/init.jsp" %>

<%
int length = ParamUtil.get(request, "length", 8);
boolean numbers = ParamUtil.get(request, "numbers", true);
boolean lowerCaseLetters = ParamUtil.get(request, "lowerCaseLetters", true);
boolean upperCaseLetters = ParamUtil.get(request, "upperCaseLetters", true);

String key = StringPool.BLANK;

if (numbers) {
	key += PwdGenerator.KEY1;
}

if (lowerCaseLetters) {
	key += PwdGenerator.KEY3;
}

if (upperCaseLetters) {
	key += PwdGenerator.KEY2;
}

String newPassword = StringPool.BLANK;

try {
	newPassword = PwdGenerator.getPassword(key, length);
}
catch (Exception e) {
}
%>

<form action="<liferay-portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/password_generator/view" /></liferay-portlet:renderURL>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="numbers" />
	</td>
	<td>
		<select name="<portlet:namespace />numbers">
			<option <%= numbers ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
			<option <%= !numbers ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="lower-case-letters" />
	</td>
	<td>
		<liferay-ui:input-checkbox defaultValue="<%= lowerCaseLetters %>" param="lowerCaseLetters" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="upper-case-letters" />
	</td>
	<td>
		<liferay-ui:input-checkbox defaultValue="<%= upperCaseLetters %>" param="upperCaseLetters" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="length" />
	</td>
	<td>
		<select name="<portlet:namespace />length">

			<%
			for (int i = 4; i <= 16; i++) {
			%>

				<option <%= (i == length) ? "selected" : "" %> value="<%= i %>"><%= i %></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
</table>

<br />

<strong><%= newPassword %></strong>

<br /><br />

<input type="submit" value="<liferay-ui:message key="generate" />" />

</form>

<aui:script use="aui-io-request,aui-parse-content">
	var form = A.one('#<portlet:namespace />fm');
	var parentNode = form.get('parentNode');

	parentNode.plug(A.Plugin.ParseContent);

	form.on(
		'submit',
		function(event) {
			var uri = form.getAttribute('action');

			A.io.request(
				uri,
				{
					form: {
						id: form
					},
					on: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							parentNode.setContent(responseData);
						}
					}
				}
			);

			event.halt();
		}
	);
</aui:script>