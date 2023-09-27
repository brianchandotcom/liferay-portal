/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI().use('escape', 'aui-lang', (A) => {
	const AEscape = A.Escape;

	const TPL_TAG_FORM =
		'<div class="form-inline {key}" >' +
		'<input class="form-control" type="text" disabled="disabled" value="{parameterKey}" /> ' +
		'<input class="form-control" type="text" disabled="disabled" value="{parameterValue}" /> ' +
		'<button class="btn btn-secondary remove-parameter"' +
		' data-parameterKey="{parameterKey}"' +
		' data-parameterValue="{parameterValue}"' +
		' data-parameterType="{parameterType}"' +
		' type="button">' +
		Liferay.Util.getLexiconIconTpl('times') +
		'</button>' +
		'</div>';

	Liferay.Report = {
		_addParameter(namespace) {
			const instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			const parameterKey = A.one('.parameters-key').val();
			const parameterType = A.one('.parameters-input-type').val();
			let parameterValue = A.one('.parameters-value').val();

			let message = '';

			if (!parameterKey.length) {
				A.all('.portlet-msg-error').setStyle('display', 'none');

				message = Liferay.Language.get(
					'please-enter-a-valid-report-parameter-key'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			if (parameterType !== 'date' && !parameterValue.length) {
				A.all('.portlet-msg-error').setStyle('display', 'none');

				message = Liferay.Language.get(
					'please-enter-a-valid-report-parameter-value'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			if (
				parameterKey.indexOf(',') > 0 ||
				parameterKey.indexOf('=') > 0 ||
				parameterValue.indexOf('=') > 0
			) {
				message = Liferay.Language.get(
					'one-of-your-fields-contains-invalid-characters'
				);

				message = AEscape.html(message);

				instance._sendMessage(message);

				return;
			}

			const reportParameters = A.one('.report-parameters').val();

			if (reportParameters) {
				const reportParametersJSON = JSON.parse(reportParameters);

				for (const i in reportParametersJSON) {
					const reportParameter = reportParametersJSON[i];

					if (reportParameter.key === parameterKey) {
						message = Liferay.Language.get(
							'that-vocabulary-already-exists'
						);

						message = AEscape.html(message);

						instance._sendMessage(message);

						return;
					}
				}
			}

			if (parameterType === 'date') {
				parameterValue = instance._getDateValue(namespace);
			}

			instance._addTag(parameterKey, parameterValue, parameterType);

			instance._addReportParameter(
				parameterKey,
				parameterValue,
				parameterType
			);
			instance._createRemoveParameterEvent();

			A.one('.parameters-key').val('');
			A.one('.parameters-value').val('');
			instance._disableAddParameterButton(namespace);
		},

		_addReportParameter(parameterKey, parameterValue, parameterType) {
			let reportParameters = [];

			const parametersInput = A.one('.report-parameters');

			if (parametersInput.val()) {
				reportParameters = JSON.parse(parametersInput.val());
			}

			const reportParameter = {
				key: parameterKey,
				type: parameterType,
				value: parameterValue,
			};

			reportParameters.push(reportParameter);

			parametersInput.val(JSON.stringify(reportParameters));
		},

		_addTag(parameterKey, parameterValue, parameterType) {
			const tagsContainer = A.one('.report-tags');

			parameterKey = AEscape.html(parameterKey);
			parameterType = AEscape.html(parameterType);
			parameterValue = AEscape.html(parameterValue);

			const key = AEscape.html(
				('report-tag-' + parameterKey).replace(/ /g, 'BLANK')
			);

			const html = A.Lang.sub(TPL_TAG_FORM, {
				key,
				parameterKey,
				parameterType,
				parameterValue,
			});

			tagsContainer.append(html);
		},

		_createRemoveParameterEvent() {
			const instance = this;

			const reportTags = A.one('.report-tags');

			reportTags.delegate(
				'click',
				(event) => {
					const currentTarget = event.currentTarget;

					const parameterKey = currentTarget.getData('parameterKey');
					const parameterValue = currentTarget.getData(
						'parameterValue'
					);
					const parameterType = currentTarget.getData(
						'parameterType'
					);

					instance.deleteParameter(
						parameterKey,
						parameterValue,
						parameterType
					);
				},
				'.remove-parameter'
			);
		},

		_disableAddParameterButton() {
			A.one('.add-parameter .btn').attr('disabled', true);
		},

		_displayParameters(parameters) {
			const instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			A.one('.report-parameters').val(parameters);

			if (!parameters) {
				return;
			}

			const reportParameters = JSON.parse(parameters);

			for (const i in reportParameters) {
				const reportParameter = reportParameters[i];

				if (reportParameter.key && reportParameter.value) {
					instance._addTag(
						reportParameter.key,
						reportParameter.value,
						reportParameter.type
					);
				}
			}
		},

		_enableAddParameterButton() {
			A.one('.add-parameter .btn').attr('disabled', false);
		},

		_getDateValue(namespace) {
			const parameterDateDay = A.one(
				'#' + namespace + 'parameterDateDay'
			);
			const parameterDateMonth = A.one(
				'#' + namespace + 'parameterDateMonth'
			);
			const parameterDateYear = A.one(
				'#' + namespace + 'parameterDateYear'
			);

			const parameterDate = new Date();

			parameterDate.setDate(parameterDateDay.val());
			parameterDate.setMonth(parameterDateMonth.val());
			parameterDate.setYear(parameterDateYear.val());

			return A.DataType.Date.format(parameterDate);
		},

		_sendMessage(message) {
			const instance = this;

			message = Liferay.Util.unescapeHTML(message);

			const portletMessageContainer = instance._portletMessageContainer;

			portletMessageContainer.addClass('portlet-msg-error');
			portletMessageContainer.set('innerHTML', message);
			portletMessageContainer.setStyle('display', 'block');
		},

		_toggleAddParameterButton(namespace) {
			const instance = this;

			const parameterKey = A.one('.parameters-key').val();
			const parameterType = A.one('.parameters-input-type').val();
			let parameterValue = A.one('.parameters-value').val();

			if (parameterType === 'date') {
				parameterValue = instance._getDateValue(namespace);
			}

			if (parameterKey && parameterValue) {
				instance._enableAddParameterButton();
			}
			else {
				instance._disableAddParameterButton();
			}
		},

		deleteParameter(parameterKey) {
			const instance = this;

			instance._portletMessageContainer.setStyle('display', 'none');

			Liferay.Util.openConfirmModal({
				message: Liferay.Language.get(
					'are-you-sure-you-want-to-delete-this-entry'
				),
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						const parametersInput = A.one('.report-parameters');

						const reportParameters = JSON.parse(
							parametersInput.val()
						);

						for (const i in reportParameters) {
							const reportParameter = reportParameters[i];

							if (reportParameter.key === parameterKey) {
								reportParameters.splice(i, 1);

								break;
							}
						}

						parametersInput.val(JSON.stringify(reportParameters));

						const key = ('.report-tag-' + parameterKey).replace(
							/ /g,
							'BLANK'
						);

						A.one(key).remove(true);
					}
				},
			});
		},

		initialize(param) {
			const instance = this;

			const namespace = param.namespace;

			instance._portletMessageContainer = A.one('.report-message');

			instance._displayParameters(param.parameters);

			instance._disableAddParameterButton(namespace);

			A.one('.parameters-key').on('valueChange', () => {
				instance._toggleAddParameterButton(namespace);
			});

			A.one('.parameters-value').on('valueChange', () => {
				instance._toggleAddParameterButton(namespace);
			});

			A.one('.add-parameter .btn').on('click', () => {
				instance._addParameter(namespace);
			});

			instance._createRemoveParameterEvent();

			A.one('.remove-existing-report').on('click', () => {
				A.one('.existing-report').setStyle('display', 'none');
				A.one('.template-report').setStyle('display', 'block');
				A.one('.cancel-update-template-report').setStyle(
					'display',
					'block'
				);
			});

			A.one('.cancel-update-template-report').on('click', () => {
				A.one('.existing-report').setStyle('display', 'block');
				A.one('.template-report').setStyle('display', 'none');
				A.one('.cancel-update-template-report').setStyle(
					'display',
					'none'
				);
			});

			A.one('.parameters-input-type').on('change', (event) => {
				const currentTarget = event.currentTarget;

				const parametersInputDate = A.one('.parameters-input-date');
				const parametersValue = A.one('.parameters-value');
				const parametersValueFieldSet = A.one(
					'.parameters-value-field-set'
				);

				if (currentTarget.val() === 'text') {
					parametersValue.val('');
					parametersValue.attr('disabled', '');
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
				}

				if (currentTarget.val() === 'date') {
					parametersValueFieldSet.setStyle('display', 'none');
					parametersInputDate.setStyle('display', 'block');
				}

				if (currentTarget.val() === 'startDateDay') {
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
					parametersValue.attr('disabled', 'disabled');
					parametersValue.val('${startDateDay}');
				}

				if (currentTarget.val() === 'endDateDay') {
					parametersInputDate.setStyle('display', 'none');
					parametersValueFieldSet.setStyle('display', 'block');
					parametersValue.attr('disabled', 'disabled');
					parametersValue.val('${endDateDay}');
				}
			});
		},
	};
});
