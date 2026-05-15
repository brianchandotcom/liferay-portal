<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%@ include file="/force_reconsent_url.jspf" %>

<aui:script>
	var form =
		document.<portlet:namespace />fm ||
		document.forms['<portlet:namespace />fm'];

	if (form) {
		var isPreferenceHandlingPage = !!document.getElementById(
			'<portlet:namespace />consentRenewalPeriod'
		);

		var formIsDirty = false;

		var markDirty = function () {
			formIsDirty = true;
		};

		form.addEventListener('input', markDirty);
		form.addEventListener('change', markDirty);

		var hasFieldDifferences = function () {
			var fields = form.querySelectorAll('input, select, textarea');

			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];

				if (field.type === 'checkbox' || field.type === 'radio') {
					if (field.checked !== field.defaultChecked) {
						return true;
					}
				}
				else if (field.tagName === 'SELECT') {
					var options = Array.from(field.options);
					var defaultOption =
						options.find((option) => option.defaultSelected) ||
						options[0];

					if (defaultOption && field.value !== defaultOption.value) {
						return true;
					}
				}
				else if (field.value !== field.defaultValue) {
					return true;
				}
			}

			return false;
		};

		var formHasChanges = function () {
			if (isPreferenceHandlingPage) {
				return hasFieldDifferences();
			}

			return formIsDirty;
		};

		form.addEventListener('submit', (event) => {
			if (form.dataset.skipActiveWarning === 'true') {
				return;
			}

			if (!formHasChanges()) {
				return;
			}

			event.preventDefault();
			event.stopImmediatePropagation();

			var checkboxId = '<portlet:namespace />forceReconsentCheckbox';
			var renewalPeriodChanged = form.dataset.renewalPeriodChanged === 'true';
			var checkboxAttributes = renewalPeriodChanged ? 'checked disabled' : '';

			Liferay.Util.openModal({
				bodyHTML:
					'<p><liferay-ui:message key="these-changes-will-take-effect-immediately-do-you-want-to-continue" /></p>' +
					'<div class="custom-control custom-checkbox">' +
					'<input class="custom-control-input" id="' +
					checkboxId +
					'" type="checkbox" ' +
					checkboxAttributes +
					' />' +
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
								renewalPeriodChanged ||
								document.getElementById(checkboxId)?.checked;

							processClose();

							form.dataset.skipActiveWarning = 'true';

							var modifiedDateField = document.getElementById(
								'<portlet:namespace />modifiedDate'
							);

							var submitForm = function () {
								if (modifiedDateField) {
									modifiedDateField.value = new Date().getTime();
								}

								form.requestSubmit();
							};

							if (forceReconsent) {
								Liferay.Util.fetch('<%= forceReconsentURL %>', {
									method: 'POST',
								}).then((response) => {
									if (response.ok) {
										submitForm();
									}
									else {
										Liferay.Util.openToast({
											message: Liferay.Language.get(
												'your-request-failed-to-complete'
											),
											type: 'danger',
										});
									}
								});
							}
							else {
								submitForm();
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