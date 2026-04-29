<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ include file="/force_reconsent_url.jspf" %>

<aui:script>
	var form = document.<portlet:namespace />fm;

	if (form) {
		form.addEventListener('submit', (event) => {
			if (form.dataset.skipActivedWarning === 'true') {
				return;
			}

			event.preventDefault();
			event.stopImmediatePropagation();

			var checkboxId = '<portlet:namespace />forceReconsentCheckbox';

			Liferay.Util.openModal({
				bodyHTML:
					'<p><liferay-ui:message key="these-changes-will-take-effect-immediately-do-you-want-to-continue" /></p>' +
					'<div class="custom-control custom-checkbox">' +
					'<input class="custom-control-input" id="' +
					checkboxId +
					'" type="checkbox" />' +
					'<label class="custom-control-label" for="' +
					checkboxId +
					'"><liferay-ui:message key="check-if-you-want-to-force-re-consent-to-the-users" /></label>' +
					'</div>',
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						autoFocus: true,
						displayType: 'primary',
						label: Liferay.Language.get('ok'),
						onClick: ({processClose}) => {
							var forceReconsent =
								document.getElementById(checkboxId)?.checked;

							processClose();

							form.dataset.skipActivedWarning = 'true';

							if (forceReconsent) {
								Liferay.Util.fetch('<%= forceReconsentURL %>', {
									method: 'POST',
								}).finally(() => form.requestSubmit());
							}
							else {
								form.requestSubmit();
							}
						},
					},
				],
				footerCssClass: 'border-0',
				headerCssClass: 'border-0',
				role: 'alertdialog',
			});
		});
	}
</aui:script>