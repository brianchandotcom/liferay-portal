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

<%@ include file="/html/portal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

JSONObject samlSsoLoginContext = (JSONObject)request.getAttribute("SAML_SSO_LOGIN_CONTEXT");

JSONArray relevantIdpConnectionsJSONArray = samlSsoLoginContext.getJSONArray("relevantIdpConnections");
%>

<aui:form action='<%= PortalUtil.getPortalURL(request) + PortalUtil.getPathMain() + "/portal/login" %>' method="get" name="fm" style="padding: 1rem;">
	<aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<c:choose>
		<c:when test="<%= relevantIdpConnectionsJSONArray.length() > 0 %>">
			<p><liferay-ui:message key="please-select-your-identity-provider" /></p>

			<aui:select label="identity-provider" name="idpEntityId">

				<%
				for (int i = 0; i < relevantIdpConnectionsJSONArray.length(); i++) {
					JSONObject relevantIdpConnectionJSONObject = relevantIdpConnectionsJSONArray.getJSONObject(i);

					String entityId = relevantIdpConnectionJSONObject.getString("entityId");
					String name = relevantIdpConnectionJSONObject.getString("name");
				%>

					<aui:option label="<%= HtmlUtil.escape(name) %>" value="<%= HtmlUtil.escapeAttribute(entityId) %>" />

				<%
				}
				%>

			</aui:select>

			<aui:fieldset>
				<aui:button-row>
					<aui:button type="submit" value="sign-in" />
				</aui:button-row>
			</aui:fieldset>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="no-identity-provider-is-available-to-sign-you-in" />
		</c:otherwise>
	</c:choose>
</aui:form>