<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long teamId = ParamUtil.getLong(request, "teamId");

Team team = TeamLocalServiceUtil.fetchTeam(teamId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((team == null) ? LanguageUtil.get(request, "new-team") : team.getName());
%>

<portlet:actionURL name="editTeam" var="editTeamURL">
	<portlet:param name="mvcPath" value="/edit_team.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editTeamURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="teamId" type="hidden" value="<%= teamId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateTeamException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= TeamNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= team %>" model="<%= Team.class %>" />

		<liferay-frontend:fieldset>
			<c:if test="<%= team != null %>">
				<aui:input name="teamId" type="resource" value="<%= String.valueOf(team.getTeamId()) %>" />
			</c:if>

			<aui:input name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>