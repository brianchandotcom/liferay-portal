<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
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
				cssClass="align-baseline p-0"
				displayType="link"
				id='<%= namespace + "empty" %>'
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

			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key='<%= (String)request.getAttribute("liferay-trash:empty:confirmMessage") %>' />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						var form = document.getElementById(
							'<portlet:namespace />emptyForm'
						);

						if (form) {
							submitForm(form);
						}
					}
				},
			});
		});
	}
</aui:script>