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
DepotAdminDetailsDisplayContext depotAdminDetailsDisplayContext = (DepotAdminDetailsDisplayContext)request.getAttribute(DepotAdminWebKeys.DEPOT_ADMIN_DETAILS_DISPLAY_CONTEXT);
%>

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupKeyException.class %>" message="please-enter-a-valid-name" />

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset
		collapsible="false"
		label='<%= LanguageUtil.get(request, "details") %>'
	>
		<aui:model-context bean="<%= depotAdminDetailsDisplayContext.getGroup() %>" model="<%= Group.class %>" />

		<aui:input name="repositoryId" type="resource" value="<%= String.valueOf(depotAdminDetailsDisplayContext.getDepotEntryId()) %>" />

		<aui:input name="name" placeholder="name" required="<%= true %>" value="<%= depotAdminDetailsDisplayContext.getDepotName(locale) %>" />

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
			for (DepotApplication depotApplication : depotAdminDetailsDisplayContext.getDepotApplications()) {
			%>

				<aui:input label="<%= depotApplication.getLabel(locale) %>" name="<%= depotApplication.getPortletId() %>" type="checkbox" value="<%= depotAdminDetailsDisplayContext.isEnabled(depotApplication.getPortletId()) %>" />

			<%
			}
			%>

		</aui:fieldset>
	</liferay-frontend:fieldset>

	<liferay-frontend:fieldset
		collapsible="true"
		label='<%= LanguageUtil.get(request, "sharing") %>'
	>
		<liferay-util:include page="/screen/navigation/entries/sharing.jsp" servletContext="<%= application %>" />
	</liferay-frontend:fieldset>

	<liferay-frontend:fieldset
		collapsible="true"
		label='<%= LanguageUtil.get(request, "asset-auto-tagging") %>'
	>
		<liferay-util:include page="/screen/navigation/entries/asset_auto_tagger.jsp" servletContext="<%= application %>" />
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset-group>