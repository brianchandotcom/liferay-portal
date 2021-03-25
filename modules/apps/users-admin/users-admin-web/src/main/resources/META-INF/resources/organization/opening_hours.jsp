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
OrganizationScreenNavigationDisplayContext organizationScreenNavigationDisplayContext = (OrganizationScreenNavigationDisplayContext)request.getAttribute(UsersAdminWebKeys.ORGANIZATION_SCREEN_NAVIGATION_DISPLAY_CONTEXT);

long organizationId = organizationScreenNavigationDisplayContext.getOrganizationId();

List<OrgLabor> orgLabors = OrgLaborServiceUtil.getOrgLabors(organizationId);
%>

<clay:sheet-header>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-title"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text"><%= organizationScreenNavigationDisplayContext.getFormLabel() %></span>
		</clay:content-col>

		<clay:content-col>
			<span class="heading-end">
				<liferay-ui:icon
					label="<%= true %>"
					linkCssClass="add-opening-hours-link btn btn-secondary btn-sm"
					message="add"
					url='<%=
						PortletURLBuilder.createRenderURL(
							liferayPortletResponse
						).setMVCPath(
							"/organization/edit_opening_hours.jsp"
						).setRedirect(
							currentURL
						).setParameter(
							"className", Organization.class.getName()
						).setParameter(
							"classPK", organizationId
						).buildString()
					%>'
				/>
			</span>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-header>

<c:if test="<%= orgLabors.isEmpty() %>">
	<div class="contact-information-empty-results-message-wrapper">
		<liferay-ui:empty-result-message
			message="this-organization-does-not-have-any-opening-hours"
		/>
	</div>
</c:if>

<div
	class="<%=
		CSSClassNames.builder(
			"opening-hours-wrapper"
		).add(
			"hide", orgLabors.isEmpty()
		).build()
	%>"
>

	<%
	for (OrgLabor orgLabor : orgLabors) {
		OrgLaborDisplay orgLaborDisplay = new OrgLaborDisplay(locale, orgLabor);
	%>

		<div class="opening-hours-entry">
			<clay:content-row
				cssClass="opening-hours-header"
			>
				<clay:content-col>
					<h5><%= orgLaborDisplay.getTitle() %></h5>
				</clay:content-col>

				<clay:content-col
					cssClass="lfr-search-container-wrapper"
				>
					<liferay-util:include page="/organization/opening_hours_action.jsp" servletContext="<%= application %>">
						<liferay-util:param name="orgLaborId" value="<%= String.valueOf(orgLabor.getOrgLaborId()) %>" />
					</liferay-util:include>
				</clay:content-col>
			</clay:content-row>

			<div class="table-responsive">
				<table class="table table-autofit">
					<tbody>

						<%
						for (KeyValuePair dayKeyValuePair : orgLaborDisplay.getDayKeyValuePairs()) {
						%>

							<tr>
								<td class="table-cell-expand">
									<span class="table-title"><%= dayKeyValuePair.getKey() %></span>
								</td>
								<td class="table-cell-expand">
									<span><%= dayKeyValuePair.getValue() %></span>
								</td>
							</tr>

						<%
						}
						%>

					</tbody>
				</table>
			</div>
		</div>

	<%
	}
	%>

</div>