<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String sxpBlueprintExternalReferenceCode = PrefsParamUtil.getString(portletPreferences, request, "sxpBlueprintExternalReferenceCode");

SXPBlueprint sxpBlueprint = SXPBlueprintLocalServiceUtil.fetchSXPBlueprintByExternalReferenceCode(sxpBlueprintExternalReferenceCode, themeDisplay.getCompanyId());
%>

<clay:alert
	displayType="info"
>
	<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />

	<clay:button
		cssClass="align-baseline border-0 p-0"
		displayType="link"
		label="select-a-blueprint-to-make-it-visible"
		onClick="<%= portletDisplay.getURLConfigurationJS() %>"
		small="<%= true %>"
	/>
</clay:alert>

<c:choose>
	<c:when test="<%= sxpBlueprint != null %>">
		<liferay-ui:message arguments="<%= HtmlUtil.escape(sxpBlueprint.getTitle(locale)) %>" key="blueprint-x" />
	</c:when>
	<c:otherwise>

		<%
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

	</c:otherwise>
</c:choose>