<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="microblogs-status-update">
	<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

	<liferay-frontend:edit-form
		action="<%= configurationActionURL %>"
		method="post"
		name="fm"
	>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

		<liferay-frontend:edit-form-body>
			<liferay-frontend:fieldset
				collapsible="<%= true %>"
				label="display-settings"
			>
				<aui:input label="display-most-recent-status" name="preferences--showStatus--" type="checkbox" value="<%= showStatus %>" />
			</liferay-frontend:fieldset>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons />
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>