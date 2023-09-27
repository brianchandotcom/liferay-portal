<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:resourceURL id="/audit/export_audit_events" var="exportURL" />

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportAuditEvents',
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