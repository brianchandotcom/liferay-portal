<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script>
	var form = document.<portlet:namespace />fm;

	if (form) {
		form.addEventListener('submit', (event) => {
			if (form.dataset.skipActivedWarning === 'true') {
				return;
			}

			event.preventDefault();
			event.stopImmediatePropagation();

			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="these-changes-will-take-effect-immediately-do-you-want-to-continue" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						form.dataset.skipActivedWarning = 'true';

						form.submit();
					}
				},
			});
		});
	}
</aui:script>