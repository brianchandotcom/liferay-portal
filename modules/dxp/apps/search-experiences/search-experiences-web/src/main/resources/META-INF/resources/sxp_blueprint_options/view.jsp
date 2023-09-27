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

<div class="alert alert-info text-center">
	<aui:a href="javascript:void(0);" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
		<liferay-ui:message key="configure-blueprints-options-in-this-page" />

		<c:if test="<%= sxpBlueprint != null %>">
			<liferay-ui:message arguments="<%= HtmlUtil.escape(sxpBlueprint.getTitle(locale)) %>" key="blueprint-x" />
		</c:if>
	</aui:a>
</div>