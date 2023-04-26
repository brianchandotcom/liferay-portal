<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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