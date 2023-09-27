<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int status = GetterUtil.getInteger(request.getAttribute(UsersAdminWebKeys.STATUS), WorkflowConstants.STATUS_APPROVED);
%>

<liferay-portlet:resourceURL id="/users_admin/export_users" var="exportURL">
	<liferay-portlet:param name="status" value="<%= String.valueOf(status) %>" />
</liferay-portlet:resourceURL>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportUsers',
		() => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="warning-this-csv-file-contains-user-supplied-inputs" unicode="<%= true %>" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						submitForm(
							document.hrefFm,
							'<%= exportURL + "&compress=0&etag=0&strip=0" %>'
						);
					}
				},
			});
		}
	);
</aui:script>