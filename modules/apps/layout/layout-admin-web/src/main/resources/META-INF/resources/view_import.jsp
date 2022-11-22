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

<portlet:actionURL name="/layout_admin/import_layout_utility_page_entries" var="importURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= importURL %>"
	enctype="multipart/form-data"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<liferay-ui:message key="import-help" />

		<a href="https://portal.liferay.dev/docs" target="_blank">
			<liferay-ui:message key="read-more" />
		</a>

		<br /><br />

		<liferay-frontend:fieldset>
			<aui:input label="file" name="file" type="file">
				<aui:validator name="required" />

				<aui:validator name="acceptFiles">
					'zip'
				</aui:validator>
			</aui:input>

			<aui:input checked="<%= true %>" label="overwrite-existing-utility-pages" name="overwrite" type="checkbox" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			submitLabel="import"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>