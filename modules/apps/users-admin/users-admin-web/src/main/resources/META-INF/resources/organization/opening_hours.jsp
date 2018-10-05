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

<div class="sheet-title">
	<span class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<h2 class="sheet-title">
				<%= organizationScreenNavigationDisplayContext.getFormLabel() %>
			</h2>
		</span>
		<span class="autofit-col">
			<liferay-ui:icon
				cssClass="modify-opening-hours-link"
				data="<%=
					new HashMap<String, Object>() {
						{
							put("title", LanguageUtil.get(request, "add-opening-hours"));
						}
					}
				%>"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="add"
				url="javascript:;"
			/>
		</span>
	</span>
</div>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="services"
/>

<liferay-ui:error key="<%= NoSuchListTypeException.class.getName() + Organization.class.getName() + ListTypeConstants.ORGANIZATION_SERVICE %>" message="please-select-a-type" />

<%
for (OrgLabor orgLabor : orgLabors) {
	OrgLaborDisplay orgLaborDisplay = new OrgLaborDisplay(locale, orgLabor);
%>

	<div class="opening-hours-entry">
		<div class="autofit-row opening-hours-header">
			<span class="autofit-col">
				<h5><%= orgLaborDisplay.getTitle() %></h5>
			</span>
			<span class="autofit-col lfr-search-container-wrapper">
				<liferay-util:include page="/organization/opening_hours_action.jsp" servletContext="<%= application %>">
					<liferay-util:param name="orgLaborId" value="<%= String.valueOf(orgLabor.getOrgLaborId()) %>" />
				</liferay-util:include>
			</span>
		</div>

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

<portlet:renderURL var="editOpeningHoursRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/organization/edit_opening_hours.jsp" />
</portlet:renderURL>

<aui:script require="<%= organizationScreenNavigationDisplayContext.getContactInformationJSRequire() %>">
	ContactInformation.registerContactInformationListener(
		'.modify-opening-hours-link a',
		'<%= editOpeningHoursRenderURL.toString() %>',
		690
	);
</aui:script>