<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
ProductAnalyticsConfiguration productAnalyticsConfiguration = (ProductAnalyticsConfiguration)request.getAttribute(ProductAnalyticsConfiguration.class.getName());
%>

<aui:script>
	var form = document.<portlet:namespace />fm;

	if (form) {
		form.addEventListener('submit', (event) => {
			var consentRenewalPeriod = document.querySelector(
				'[id*="consentRenewalPeriod"]'
			);

			if (
				!consentRenewalPeriod.value ||
				isNaN(consentRenewalPeriod.value) ||
				consentRenewalPeriod.value > 12 ||
				consentRenewalPeriod.value < 1
			) {
				Liferay.Util.openToast({
					message:
						'<liferay-ui:message arguments="<%= new Object[] {1, 12} %>" key="please-enter-a-value-between-x-and-x" />',
					type: 'danger',
				});

				event.preventDefault();
				event.stopImmediatePropagation();
				return;
			}

			var enabled = document.querySelector('[name*="enabled"]');

			if (enabled.checked && <%= productAnalyticsConfiguration.enabled() %>) {
				event.preventDefault();
				event.stopImmediatePropagation();

				Liferay.Util.openConfirmModal({
					message:
						'<liferay-ui:message key="you-are-about-to-change-the-consent-renewal-period" />',
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							form.submit();
						}
					},
				});
			}
		});
	}
</aui:script>