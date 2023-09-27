<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectTeamsDisplayContext selectTeamsDisplayContext = new SelectTeamsDisplayContext(request, renderRequest, renderResponse);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new SelectTeamsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectTeamsDisplayContext) %>"
/>

<aui:form cssClass="container-fluid container-fluid-max-xl" name="fm">
	<liferay-ui:search-container
		id="teams"
		searchContainer="<%= selectTeamsDisplayContext.getTeamSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Team"
			escapedModel="<%= true %>"
			keyProperty="teamId"
			modelVar="team"
		>
			<c:choose>
				<c:when test='<%= Objects.equals(selectTeamsDisplayContext.getDisplayStyle(), "icon") %>'>
					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new SelectTeamVerticalCard(team) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectTeamsDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<clay:link
								cssClass="selector-button"
								href="javascript:void(0);"
								id="<%= String.valueOf(team.getTeamId()) %>"
								title="<%= HtmlUtil.escape(team.getName()) %>"
							/>
						</h5>

						<h6 class="text-default">
							<span><%= HtmlUtil.escape(team.getDescription()) %></span>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(selectTeamsDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="title"
						truncate="<%= true %>"
					>
						<clay:link
							cssClass="selector-button"
							href="javascript:void(0);"
							id="<%= String.valueOf(team.getTeamId()) %>"
							title="<%= HtmlUtil.escape(team.getName()) %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="description"
						value="<%= HtmlUtil.escape(team.getDescription()) %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= selectTeamsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>