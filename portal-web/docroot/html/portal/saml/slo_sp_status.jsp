<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
JSONObject samlSloRequestInfoJSONObject = (JSONObject)request.getAttribute("SAML_SLO_REQUEST_INFO");

int status = samlSloRequestInfoJSONObject.getInt("status");
%>

<noscript>
	<div class="portlet-msg-info">
		<liferay-ui:message key="your-browser-does-not-support-javascript.-once-you-have-completed-signing-out,-close-this-window-and-continue-with-the-next-service-provider" />
	</div>

	<c:choose>
		<c:when test="<%= status == 2 %>">
			<div class="portlet-msg-success">
				<liferay-ui:message arguments='<%= samlSloRequestInfoJSONObject.getString("name") %>' key="you-have-successfully-signed-out-from-x" />
			</div>
		</c:when>
		<c:when test="<%= status == 3 %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="single-sign-out-request-failed" />

				<a href="?cmd=logout&entityId=<%= samlSloRequestInfoJSONObject.getString("entityId") %>">
					<liferay-ui:message key="retry" />
				</a>
			</div>
		</c:when>
		<c:when test="<%= status == 4 %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="this-service-provider-does-not-support-single-sign-out" />
			</div>
		</c:when>
		<c:when test="<%= status == 5 %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="single-sign-out-request-timed-out" />
			</div>
		</c:when>
	</c:choose>
</noscript>

<aui:script>
	if (window.parent.Liferay.SAML.SLO) {
		window.parent.Liferay.SAML.SLO.updateStatus(<%= samlSloRequestInfoJSONObject %>);
	}
</aui:script>