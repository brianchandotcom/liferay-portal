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
int length = ParamUtil.getInteger(request, "length", 8);
boolean numbers = ParamUtil.getBoolean(request, "numbers");
boolean lowerCaseLetters = ParamUtil.getBoolean(request, "lowerCaseLetters");
boolean upperCaseLetters = ParamUtil.getBoolean(request, "upperCaseLetters");

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

<liferay-portlet:renderURL var="generatePasswordUrl" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" />

<aui:form action="<%= generatePasswordUrl %>" method="post" name="fm">
	<aui:fieldset>
		<aui:field-wrapper label="password-settings">
			<aui:input name="numbers" type="checkbox" value="<%= numbers %>" />

			<aui:input name="lowerCaseLetters" type="checkbox" value="<%= lowerCaseLetters %>" />

			<aui:input name="upperCaseLetters" type="checkbox" value="<%= upperCaseLetters %>" />

			<aui:select name="length">

				<%
				for (int i = 4; i <= 16; i++) {
				%>

					<aui:option label="<%= i %>" selected="<%= (i == length) %>" value="<%= i %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input name="newPassword" type="resource" value="<%= newPassword %>" />
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:button type="submit" value="generate" />
</aui:form>

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