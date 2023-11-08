/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-date-facet',
	(A) => {
		const DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		const FacetUtil = Liferay.Search.FacetUtil;
		const Util = Liferay.Util;

		const DateFacetFilter = function (config) {
			const instance = this;

			instance.form = config.form;
			instance.fromInputName = config.fromInputName;
			instance.namespace = config.namespace;
			instance.parameterName = config.parameterName;
			instance.searchCustomRangeButton = config.searchCustomRangeButton;
			instance.searchCustomRangeToggleName =
				config.searchCustomRangeToggleName;
			instance.toInputName = config.toInputName;

			instance.fromInputDatePicker = instance._getInputDatePicker(
				config.fromInputName
			);
			instance.toInputDatePicker = instance._getInputDatePicker(
				config.toInputName
			);

			instance._initializeFormValidator();

			if (instance.searchCustomRangeButton) {
				instance.searchCustomRangeButton.on(
					'click',
					A.bind(instance.filter, instance)
				);
			}
		};

		const DateFacetFilterUtil = {
			submitSearch(parameterString) {
				document.location.search = parameterString;
			},

			/**
			 * Formats a date to 'YYYY-MM-DD' format.
			 * @param {Date} date The date to format.
			 * @returns {String} The date string.
			 */
			toLocaleDateStringFormatted(date) {
				const localDate = new Date(date);

				localDate.setMinutes(
					date.getMinutes() - date.getTimezoneOffset()
				);

				return localDate.toISOString().split('T')[0];
			},
		};

		A.mix(DateFacetFilter.prototype, {
			_getInputDatePicker(inputName) {
				const inputElements = document.getElementsByName(inputName);

				if (inputElements[0]) {
					return Liferay.component(
						`${inputElements[0].getAttribute('id')}DatePicker`
					);
				}

				return null;
			},

			_initializeFormValidator() {
				const instance = this;

				const dateRangeRuleName = instance.namespace + 'dateRange';

				A.mix(
					DEFAULTS_FORM_VALIDATOR.STRINGS,
					{
						[dateRangeRuleName]: Liferay.Language.get(
							'search-custom-range-invalid-date-range'
						),
					},
					true
				);

				A.mix(
					DEFAULTS_FORM_VALIDATOR.RULES,
					{
						[dateRangeRuleName]() {
							return A.Date.isGreaterOrEqual(
								instance.toInputDatePicker.getDate(),
								instance.fromInputDatePicker.getDate()
							);
						},
					},
					true
				);

				const customRangeValidator = new A.FormValidator({
					boundingBox: instance.form,
					fieldContainer: 'div',
					on: {
						errorField() {
							Util.toggleDisabled(
								instance.searchCustomRangeButton,
								true
							);
						},
						validField() {
							Util.toggleDisabled(
								instance.searchCustomRangeButton,
								false
							);
						},
					},
					rules: {
						[instance.fromInputName]: {
							[dateRangeRuleName]: true,
						},
						[instance.toInputName]: {
							[dateRangeRuleName]: true,
						},
					},
				});

				const onRangeSelectionChange = function () {
					customRangeValidator.validate();
				};

				if (instance.fromInputDatePicker) {
					instance.fromInputDatePicker.on(
						'selectionChange',
						onRangeSelectionChange
					);
				}

				if (instance.toInputDatePicker) {
					instance.toInputDatePicker.on(
						'selectionChange',
						onRangeSelectionChange
					);
				}
			},

			filter() {
				const instance = this;

				const fromDate = instance.fromInputDatePicker.getDate();

				const toDate = instance.toInputDatePicker.getDate();

				const dateFromParameter = DateFacetFilterUtil.toLocaleDateStringFormatted(
					fromDate
				);

				const dateToParameter = DateFacetFilterUtil.toLocaleDateStringFormatted(
					toDate
				);

				const param = instance.parameterName;
				const paramFrom = param + 'From';
				const paramTo = param + 'To';

				let parameterArray = document.location.search
					.substr(1)
					.split('&');

				const searchCustomRangeToggle = document.getElementById(
					instance.searchCustomRangeToggleName
				);

				if (!searchCustomRangeToggle?.hasAttribute('data-term-id')) {
					parameterArray = FacetUtil.removeURLParameters(
						param,
						parameterArray
					);
				}

				parameterArray = FacetUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				const startParameterNameElement = document.getElementById(
					instance.namespace + 'start-parameter-name'
				);

				if (startParameterNameElement) {
					parameterArray = FacetUtil.removeURLParameters(
						startParameterNameElement.value,
						parameterArray
					);
				}

				parameterArray = FacetUtil.addURLParameter(
					paramFrom,
					dateFromParameter,
					parameterArray
				);

				parameterArray = FacetUtil.addURLParameter(
					paramTo,
					dateToParameter,
					parameterArray
				);

				DateFacetFilterUtil.submitSearch(parameterArray.join('&'));
			},
		});

		Liferay.namespace('Search').DateFacetFilter = DateFacetFilter;

		Liferay.namespace('Search').DateFacetFilterUtil = DateFacetFilterUtil;
	},
	'',
	{
		requires: ['aui-form-validator', 'liferay-search-facet-util'],
	}
);
