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
String confirmMessage = (String)request.getAttribute("liferay-trash:empty:confirmMessage");
String infoMessage = (String)request.getAttribute("liferay-trash:empty:infoMessage");
int totalEntries = GetterUtil.getInteger(request.getAttribute("liferay-trash:empty:totalEntries"));
%>

<c:if test="<%= totalEntries > 0 %>">
	<liferay-util:buffer
		var="stripeMessage"
	>
		<aui:form action='<%= (String)request.getAttribute("liferay-trash:empty:portletURL") %>' cssClass="d-inline" name="emptyForm">
			<c:if test="<%= Validator.isNotNull(infoMessage) %>">
				<liferay-ui:message key="<%= infoMessage %>" />
			</c:if>

			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EMPTY_TRASH %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<clay:button
				cssClass="trash-empty-button"
				displayType="link"
				id='<%= liferayPortletResponse.getNamespace() + "empty" %>'
				label='<%= (String)request.getAttribute("liferay-trash:empty:emptyMessage") %>'
				small="<%= true %>"
				type="submit"
			/>
		</aui:form>
	</liferay-util:buffer>

	<clay:stripe
		message="<%= stripeMessage %>"
	/>
</c:if>

<aui:script>
	var <%= namespace %>empty = document.getElementById('<%= namespace %>empty');

	if (<%= namespace %>empty) {
		<%= namespace %>empty.addEventListener('click', (event) => {
			event.preventDefault();

			if (
				confirm('<%= UnicodeLanguageUtil.get(request, confirmMessage) %>')
			) {
				var form = document.getElementById(
					'<portlet:namespace />emptyForm'
				);

				if (form) {
					submitForm(form);
				}
			}
		});
	}
</aui:script>