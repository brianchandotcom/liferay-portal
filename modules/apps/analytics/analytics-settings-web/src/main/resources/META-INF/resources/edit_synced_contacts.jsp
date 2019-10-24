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

<portlet:actionURL name="/analytics/edit_synced_contacts" var="editSyncedContactsURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="choose-contacts-to-sync" />
		</span>
	</h2>

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<h3 class="autofit-row">
			<span class="autofit-col autofit-col-expand">
				<liferay-ui:message key="sync-all-contacts" />
			</span>
		</h3>

		<div class="form-text">
			<liferay-ui:message key="sync-all-contacts-help" />
		</span>

		<label class="toggle-switch">
			<input class="toggle-switch-check" name="<portlet:namespace />syncAllContacts" type="checkbox" />

			<span aria-hidden="true" class="toggle-switch-bar">
				<span class="toggle-switch-handle" />
			</span>
			<span class="toggle-switch-text toggle-switch-text-right">
				<liferay-ui:message arguments="<%= UserServiceUtil.getCompanyUsersCount(themeDisplay.getCompanyId()) %>" key="sync-all-x-contacts" />
			</span>
		</label>

		<aui:button-row>
			<aui:button type="submit" value="save-and-sync" />
		</aui:button-row>
	</aui:form>
</div>