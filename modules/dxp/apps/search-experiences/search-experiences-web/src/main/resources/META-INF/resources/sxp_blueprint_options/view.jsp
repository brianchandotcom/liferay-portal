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
long sxpBlueprintId = PrefsParamUtil.getLong(portletPreferences, request, "sxpBlueprintId");

SXPBlueprint sxpBlueprint = SXPBlueprintLocalServiceUtil.fetchSXPBlueprint(sxpBlueprintId);
%>

<div class="alert alert-info text-center">
	<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
		<liferay-ui:message key="configure-blueprints-options-in-this-page" />

		<c:if test="<%= sxpBlueprint != null %>">
			<liferay-ui:message arguments="<%= HtmlUtil.escape(sxpBlueprint.getTitle(locale)) %>" key="blueprint-x" />
		</c:if>
	</aui:a>
</div>