<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CookiesPreferenceHandlingConfigurationDisplayContext cookiesPreferenceHandlingConfigurationDisplayContext = (CookiesPreferenceHandlingConfigurationDisplayContext)request.getAttribute(CookiesBannerWebKeys.COOKIES_PREFERENCE_HANDLING_CONFIGURATION_DISPLAY_CONTEXT);
%>

<aui:script>
	var form =
		document.<portlet:namespace />fm ||
		document.forms['<portlet:namespace />fm'];

	if (form) {
		var isPreferenceHandlingPage = !!document.getElementById(
			'<portlet:namespace />consentRenewalPeriod'
		);

		var formChanged = false;

		var setFormChanged = function () {
			formChanged = true;
		};

		form.addEventListener('input', setFormChanged);
		form.addEventListener('change', setFormChanged);

		var hasFieldChanges = function () {
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

		var hasFormChanges = function () {
			if (isPreferenceHandlingPage) {
				return hasFieldChanges();
			}

			return formChanged;
		};

		form.addEventListener('submit', (event) => {
			if (form.dataset.skipActivationWarn === 'true') {
				return;
			}

			if (!hasFormChanges()) {
				return;
			}

			event.preventDefault();
			event.stopImmediatePropagation();

			var forceReconsentCheckboxId =
				'<portlet:namespace />forceReconsentCheckbox';
			var renewalPeriodChanged = form.dataset.renewalPeriodChanged === 'true';
			var forceReconsentCheckboxAttributes = renewalPeriodChanged
				? 'checked disabled'
				: '';

			Liferay.Util.openModal({
				bodyHTML:
					'<p><liferay-ui:message key="these-changes-will-take-effect-immediately-do-you-want-to-continue" /></p>' +
					'<div class="c-mt-2 custom-control custom-checkbox">' +
					'<input class="custom-control-input" id="' +
					forceReconsentCheckboxId +
					'" type="checkbox" ' +
					forceReconsentCheckboxAttributes +
					' />' +
					'<label class="c-pl-2 custom-control-label" for="' +
					forceReconsentCheckboxId +
					'"><liferay-ui:message key="force-re-consent-for-all-users" /></label>' +
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
								document.getElementById(forceReconsentCheckboxId)
									?.checked;

							processClose();

							form.dataset.skipActivationWarn = 'true';

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
								Liferay.Util.fetch(
									'<%= (cookiesPreferenceHandlingConfigurationDisplayContext != null) ? cookiesPreferenceHandlingConfigurationDisplayContext.getForceReconsentURL(renderResponse) : StringPool.BLANK %>',
									{
										method: 'POST',
									}
								).then((response) => {
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