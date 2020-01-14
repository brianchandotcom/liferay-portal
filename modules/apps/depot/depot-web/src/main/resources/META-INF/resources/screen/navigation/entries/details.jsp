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
DepotAdminDetailsDisplayContext depotAdminApplicationsDisplayContext = (DepotAdminDetailsDisplayContext)request.getAttribute(DepotAdminWebKeys.DEPOT_ADMIN_APPLICATIONS_DISPLAY_CONTEXT);

DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = GroupServiceUtil.getGroup(depotEntry.getGroupId());
%>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupKeyException.class %>" message="please-enter-a-valid-name" />

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset
		collapsible="false"
		label='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

		<aui:input name="repositoryId" type="resource" value="<%= String.valueOf(depotEntry.getDepotEntryId()) %>" />

		<aui:input name="name" placeholder="name" required="<%= true %>" value="<%= String.valueOf(group.getName(locale)) %>" />

		<aui:input name="description" placeholder="description" />
	</liferay-frontend:fieldset>

	<liferay-frontend:fieldset
		collapsible="true"
		label='<%= LanguageUtil.get(request, "applications") %>'
	>
		<aui:fieldset>
			<p class="text-muted">
				<liferay-ui:message key="repository-applications-description" />
			</p>
		</aui:fieldset>

		<aui:fieldset>

			<%
			for (DepotApplication depotApplication : depotAdminApplicationsDisplayContext.getDepotApplications()) {
			%>

				<aui:input label="<%= depotApplication.getLabel(locale) %>" name="<%= depotApplication.getPortletId() %>" type="checkbox" value="<%= depotAdminApplicationsDisplayContext.isEnabled(depotApplication.getPortletId(), depotEntry.getGroupId()) %>" />

			<%
			}
			%>

		</aui:fieldset>
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset-group>