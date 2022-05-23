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
String authServerWellKnownURI = (String)SessionErrors.get(renderRequest, "authServerWellKnownURI");

if (Validator.isNull(authServerWellKnownURI)) {
	authServerWellKnownURI = (String)request.getAttribute("authServerWellKnownURI");
}

String infoJSON = (String)SessionErrors.get(renderRequest, "infoJSON");

if (Validator.isNull(infoJSON)) {
	infoJSON = (String)request.getAttribute("infoJSON");
}

Long oAuthClientEntryId = (Long)SessionErrors.get(renderRequest, "oAuthClientEntryId");

if (oAuthClientEntryId == null) {
	oAuthClientEntryId = (Long)request.getAttribute("oAuthClientEntryId");
}

String parametersJSON = (String)SessionErrors.get(renderRequest, "parametersJSON");

if (Validator.isNull(parametersJSON)) {
	parametersJSON = (String)request.getAttribute("parametersJSON");
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect"));
%>

<portlet:actionURL name="/oauth_client_admin/update_o_auth_client" var="updateOAuthClientEntryURL">
	<portlet:param name="backURL" value='<%= ParamUtil.getString(request, "redirect") %>' />
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_o_auth_client" />
</portlet:actionURL>

<aui:form action="<%= updateOAuthClientEntryURL %>" id="oauth-client-fm" method="post" name="oauth-client-fm" onSubmit="event.preventDefault();">
	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet">
			<aui:fieldset label="oauth-client">
				<aui:input helpMessage="oauth-client-as-well-known-uri-help" label="oauth-client-as-well-known-uri" name="authServerWellKnownURI" value="<%= authServerWellKnownURI %>" />

				<aui:input helpMessage="oauth-client-info-json-help" label="oauth-client-info-json" name="infoJSON" style="min-height: 600px;" type="textarea" />

				<aui:input name="oAuthClientEntryId" type="hidden" value="<%= oAuthClientEntryId %>" />

				<aui:input helpMessage="oauth-client-parameters-json-help" label="oauth-client-parameters-json" name="parametersJSON" style="min-height: 200px;" type="textarea" />

				<aui:button-row>
					<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "doSubmit();" %>' type="submit" />
					<aui:button href='<%= ParamUtil.getString(request, "redirect") %>' type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</div>
	</clay:container-fluid>
</aui:form>

<aui:script>
	<portlet:namespace />init();

	function <portlet:namespace />doSubmit() {
		var infoJSON = document.getElementById('<portlet:namespace />infoJSON')
			.value;

		try {
			infoJSON = JSON.stringify(JSON.parse(infoJSON), null, 0);
		}
		catch (e) {
			alert('Ill-formatted Info JSON');
			return;
		}

		document.getElementById('<portlet:namespace />infoJSON').value = infoJSON;

		var parametersJSON = document.getElementById(
			'<portlet:namespace />parametersJSON'
		).value;

		try {
			parametersJSON = JSON.stringify(JSON.parse(parametersJSON), null, 0);
		}
		catch (e) {
			alert('Ill-formatted Parameters JSON');
			return;
		}

		document.getElementById(
			'<portlet:namespace />parametersJSON'
		).value = parametersJSON;

		submitForm(document.getElementById('<portlet:namespace />oauth-client-fm'));
	}

	function <portlet:namespace />init() {
		document.getElementById(
			'<portlet:namespace />infoJSON'
		).value = JSON.stringify(JSON.parse('<%= infoJSON %>'), null, 4);

		document.getElementById(
			'<portlet:namespace />parametersJSON'
		).value = JSON.stringify(JSON.parse('<%= parametersJSON %>'), null, 4);
	}
</aui:script>