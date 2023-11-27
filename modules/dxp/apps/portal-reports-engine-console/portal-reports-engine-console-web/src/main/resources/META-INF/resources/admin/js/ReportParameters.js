/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format} from 'date-fns';
import {delegate, openConfirmModal, unescapeHTML} from 'frontend-js-web';

export default function ReportParameters({namespace, parameters}) {
	const TPL_TAG_FORM =
		'<div class="form-inline {key} c-mb-4" >' +
		'<input class="form-control c-mr-4" type="text" disabled="disabled" value="{parameterKey}" /> ' +
		'<input class="form-control c-mr-4" type="text" disabled="disabled" value="{parameterValue}" /> ' +
		'<button class="btn btn-secondary remove-{key}-parameter"' +
		' data-parameterKey="{parameterKey}"' +
		' data-parameterValue="{parameterValue}"' +
		' data-parameterType="{parameterType}"' +
		' type="button">' +
		Liferay.Util.getLexiconIconTpl('times') +
		'</button>' +
		'</div>';

	const portletMessageContainer = document.querySelector('.report-message');

	let delegateHandler;

	portletMessageContainer.style.display = 'none';

	document.querySelector('.report-parameters').value = parameters;

	if (parameters) {
		const reportParameters = JSON.parse(parameters);

		for (const i in reportParameters) {
			const reportParameter = reportParameters[i];

			if (reportParameter.key && reportParameter.value) {
				addTag(
					reportParameter.key,
					reportParameter.value,
					reportParameter.type
				);
			}
		}
	}

	disableAddParameterButton(namespace);

	function addParameter(namespace) {
		portletMessageContainer.style.display = 'none';

		let parameterKey = document.querySelector('.parameters-key').value;

		let parameterType = document.querySelector('.parameters-input-type')
			.value;

		let parameterValue = document.querySelector('.parameters-value').value;

		let message = '';

		if (
			parameterKey === ',' ||
			parameterKey.indexOf(',') > 0 ||
			parameterKey === '=' ||
			parameterKey.indexOf('=') > 0 ||
			parameterValue === '=' ||
			parameterValue.indexOf('=') > 0
		) {
			message = Liferay.Language.get(
				'one-of-your-fields-contains-invalid-characters'
			);

			message = Liferay.Util.escape(message);

			sendErrorMessage(message);

			return;
		}

		const reportParameters = document.querySelector('.report-parameters')
			.value;

		if (reportParameters) {
			const reportParametersJSON = JSON.parse(reportParameters);

			for (const i in reportParametersJSON) {
				const reportParameter = reportParametersJSON[i];

				if (reportParameter.key === parameterKey) {
					message = Liferay.Language.get(
						'that-vocabulary-already-exists'
					);

					message = Liferay.Util.escape(message);

					sendErrorMessage(message);

					return;
				}
			}
		}

		if (parameterType === 'date') {
			parameterValue = getDateValue(namespace);
		}

		parameterKey = encodeURIComponent(parameterKey);
		parameterType = encodeURIComponent(parameterType);
		parameterValue = encodeURIComponent(parameterValue);

		addTag(parameterKey, parameterValue, parameterType);

		addReportParameter(parameterKey, parameterValue, parameterType);

		document.querySelector('.parameters-key').value = '';
		document.querySelector('.parameters-value').value = '';

		disableAddParameterButton(namespace);
	}

	function addReportParameter(parameterKey, parameterValue, parameterType) {
		let reportParameters = [];

		const parametersInput = document.querySelector('.report-parameters');

		if (parametersInput.value) {
			reportParameters = JSON.parse(parametersInput.value);
		}

		const reportParameter = {
			key: parameterKey,
			type: parameterType,
			value: parameterValue,
		};

		reportParameters.push(reportParameter);

		parametersInput.value = JSON.stringify(reportParameters);
	}

	function addTag(parameterKey, parameterValue, parameterType) {
		const tagsContainer = document.querySelector('.report-tags');

		const key = encodeURIComponent(
			('report-tag-' + parameterKey).replace(/ /g, 'BLANK')
		);

		const templateFormParameters = {
			key,
			parameterKey,
			parameterType,
			parameterValue,
		};

		let tagForm = TPL_TAG_FORM;

		Object.entries(templateFormParameters).forEach(([key, value]) => {
			tagForm = tagForm.replaceAll(`{${key}}`, value);
		});

		tagsContainer.innerHTML += tagForm;

		createRemoveParameterEvent(key, tagsContainer);
	}

	function createRemoveParameterEvent(key, tagsContainer) {
		delegateHandler = delegate(
			tagsContainer,
			'click',
			`.remove-${key}-parameter`,
			(event) => {
				const delegateTarget = event.delegateTarget;

				const parameterKey = delegateTarget.getAttribute(
					'data-parameterKey'
				);
				const parameterValue = delegateTarget.getAttribute(
					'data-parameterValue'
				);
				const parameterType = delegateTarget.getAttribute(
					'data-parameterType'
				);

				deleteParameter(parameterKey, parameterValue, parameterType);
			}
		);
	}

	function deleteParameter(parameterKey) {
		portletMessageContainer.style.display = 'none';

		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this-entry'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const parametersInput = document.querySelector(
						'.report-parameters'
					);

					const reportParameters = JSON.parse(parametersInput.value);

					for (const i in reportParameters) {
						const reportParameter = reportParameters[i];

						if (reportParameter.key === parameterKey) {
							reportParameters.splice(i, 1);

							break;
						}
					}

					parametersInput.value = JSON.stringify(reportParameters);

					const key = ('.report-tag-' + parameterKey).replace(
						/ /g,
						'BLANK'
					);

					document.querySelector(key).remove();
				}
			},
		});
	}

	function disableAddParameterButton() {
		document.querySelector('.add-parameter').classList.add('disabled');
	}

	function enableAddParameterButton() {
		document.querySelector('.add-parameter').classList.remove('disabled');
	}

	function getDateValue(namespace) {
		const parameterDateDay = document.getElementById(
			namespace + 'parameterDateDay'
		);

		const parameterDateMonth = document.getElementById(
			namespace + 'parameterDateMonth'
		);

		const parameterDateYear = document.getElementById(
			namespace + 'parameterDateYear'
		);

		const parameterDate = new Date();

		parameterDate.setDate(parameterDateDay.value);
		parameterDate.setMonth(parameterDateMonth.value);
		parameterDate.setYear(parameterDateYear.value);

		return format(parameterDate, 'yyyy-MM-dd');
	}

	function sendErrorMessage(message) {
		message = unescapeHTML(message);

		portletMessageContainer.classList.add('portlet-msg-error');
		portletMessageContainer.innerHTML = message;
		portletMessageContainer.style.display = 'block';
	}

	function toggleAddParameterButton(namespace) {
		const parameterKey = document.querySelector('.parameters-key').value;

		const parameterType = document.querySelector('.parameters-input-type')
			.value;

		let parameterValue = document.querySelector('.parameters-value').value;

		if (parameterType === 'date') {
			parameterValue = getDateValue(namespace);
		}

		if (parameterKey && parameterValue) {
			enableAddParameterButton();
		}
		else {
			disableAddParameterButton();
		}
	}

	document
		.querySelector('.parameters-key')
		.addEventListener('change', () => toggleAddParameterButton(namespace));

	document
		.querySelector('.parameters-value')
		.addEventListener('change', () => toggleAddParameterButton(namespace));

	document
		.querySelector('.add-parameter')
		.addEventListener('click', () => addParameter(namespace));

	document
		.querySelector('.parameters-input-type')
		.addEventListener('change', (event) => {
			const currentTarget = event.currentTarget;

			const parametersInputDate = document.querySelector(
				'.parameters-input-date'
			);

			const parametersValue = document.querySelector('.parameters-value');

			const keyInput = document.getElementById(namespace + 'key');

			if (currentTarget.value === 'text') {
				parametersValue.value = '';
				disableAddParameterButton();
				parametersValue.style.display = 'block';
				parametersInputDate.style.display = 'none';
			}

			if (currentTarget.value === 'date') {
				if (keyInput.value !== '') {
					enableAddParameterButton();
				}
				parametersValue.style.display = 'none';
				parametersInputDate.style.display = 'block';
			}
		});

	return {
		dispose() {
			delegateHandler.dispose();
		},
	};
}
