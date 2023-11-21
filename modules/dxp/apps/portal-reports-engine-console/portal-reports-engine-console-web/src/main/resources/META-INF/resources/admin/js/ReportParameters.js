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

	const portletMessageContainer = document.getElementsByClassName(
		'report-message'
	)[0];

	let delegateHandler;

	portletMessageContainer.style.display = 'none';

	document.getElementsByClassName('report-parameters')[0].value = parameters;

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

		let parameterKey = document.getElementsByClassName('parameters-key')[0]
			.value;

		let parameterType = document.getElementsByClassName(
			'parameters-input-type'
		)[0].value;

		let parameterValue = document.getElementsByClassName(
			'parameters-value'
		)[0].value;

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

		const reportParameters = document.getElementsByClassName(
			'report-parameters'
		)[0].value;

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

		document.getElementsByClassName('parameters-key')[0].value = '';
		document.getElementsByClassName('parameters-value')[0].value = '';
		disableAddParameterButton(namespace);
	}

	function addReportParameter(parameterKey, parameterValue, parameterType) {
		let reportParameters = [];

		const parametersInput = document.getElementsByClassName(
			'report-parameters'
		)[0];

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
		const tagsContainer = document.getElementsByClassName('report-tags')[0];

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
					const parametersInput = document.getElementsByClassName(
						'report-parameters'
					)[0];

					const reportParameters = JSON.parse(parametersInput.value);

					for (const i in reportParameters) {
						const reportParameter = reportParameters[i];

						if (reportParameter.key === parameterKey) {
							reportParameters.splice(i, 1);

							break;
						}
					}

					parametersInput.value = JSON.stringify(reportParameters);

					const key = ('report-tag-' + parameterKey).replace(
						/ /g,
						'BLANK'
					);

					document.getElementsByClassName(key)[0].remove();
				}
			},
		});
	}

	function disableAddParameterButton() {
		document
			.getElementsByClassName('add-parameter')[0]
			.classList.add('disabled');
	}

	function enableAddParameterButton() {
		document
			.getElementsByClassName('add-parameter')[0]
			.classList.remove('disabled');
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
		const parameterKey = document.getElementsByClassName(
			'parameters-key'
		)[0].value;

		const parameterType = document.getElementsByClassName(
			'parameters-input-type'
		)[0].value;

		let parameterValue = document.getElementsByClassName(
			'parameters-value'
		)[0].value;

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
		.getElementsByClassName('parameters-key')[0]
		.addEventListener('input', () => {
			toggleAddParameterButton(namespace);
		});

	document
		.getElementsByClassName('parameters-value')[0]
		.addEventListener('input', () => {
			toggleAddParameterButton(namespace);
		});

	document
		.getElementsByClassName('add-parameter')[0]
		.addEventListener('click', () => {
			addParameter(namespace);
		});

	document
		.getElementsByClassName('parameters-input-type')[0]
		.addEventListener('change', (event) => {
			const currentTarget = event.currentTarget;

			const parametersInputDate = document.getElementsByClassName(
				'parameters-input-date'
			)[0];

			const parametersValue = document.getElementsByClassName(
				'parameters-value'
			)[0];

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
