<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/configuration/icon/init.jsp" %>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />emptyTrash',
		(event, data) => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-empty-the-recycle-bin" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						submitForm(document.hrefFm, data.emptyTrashURL);
					}
				},
			});
		}
	);
</aui:script>