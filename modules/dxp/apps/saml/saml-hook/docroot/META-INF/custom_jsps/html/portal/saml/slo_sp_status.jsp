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

<%@ include file="/html/portal/init.jsp" %>

<%
JSONObject samlSloRequestInfoJSONObject = (JSONObject)request.getAttribute("SAML_SLO_REQUEST_INFO");

String entityId = samlSloRequestInfoJSONObject.getString("entityId");
String name = samlSloRequestInfoJSONObject.getString("name");
int status = samlSloRequestInfoJSONObject.getInt("status");
%>

<noscript>
	<div class="portlet-msg-info">
		<liferay-ui:message key="your-browser-does-not-support-javascript.-once-you-have-completed-signing-out,-close-this-window-and-continue-with-the-next-service-provider" />
	</div>

	<c:choose>
		<c:when test="<%= status == 2 %>">
			<div class="portlet-msg-success">
				<liferay-ui:message arguments="<%= name %>" key="you-have-successfully-signed-out-from-x" />
			</div>
		</c:when>
		<c:when test="<%= status == 3 %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="single-sign-out-request-failed" />

				<a href="?cmd=logout&entityId=<%= entityId %>">
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