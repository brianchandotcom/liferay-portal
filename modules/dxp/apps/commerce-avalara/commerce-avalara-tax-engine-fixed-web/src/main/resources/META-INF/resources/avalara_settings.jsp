<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAvalaraConnectorConfiguration commerceAvalaraConnectorConfiguration = (CommerceAvalaraConnectorConfiguration)request.getAttribute(CommerceAvalaraConnectorConfiguration.class.getName());
%>

<portlet:actionURL name="editCommerceAvalaraConnector" var="editCommerceAvalaraConnectorActionURL" />

<aui:form action="<%= editCommerceAvalaraConnectorActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:error exception="<%= CommerceAvalaraConnectionException.class %>" message="could-not-verify-the-connection-the-provided-credentials-are-not-correct" />

	<commerce-ui:panel>
		<%@ include file="/edit_avalara_settings.jspf" %>

		<aui:button cssClass="btn-lg btn-secondary" onClick='<%= liferayPortletResponse.getNamespace() + "verifyConnection();" %>' type="submit" value="verify-connection" />
	</commerce-ui:panel>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(window, '<portlet:namespace />verifyConnection', function (
		evt
	) {
		const inputCmd = document.querySelector(
			'#<portlet:namespace /><%= Constants.CMD %>'
		);

		inputCmd.value = 'verifyConnection';
	});
</aui:script>