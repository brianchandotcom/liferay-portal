/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-date-facet',
	(A) => {
		const DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		const Util = Liferay.Util;

		const DateFacetFilter = function (config) {
			const instance = this;

			instance.form = config.form;
			instance.aggregationType = config.aggregationType || 'dateRange';
			instance.fromInputName = config.fromInputName;
			instance.namespace = config.namespace;
			instance.parameterName = config.parameterName;
			instance.searchCustomRangeButton = config.searchCustomRangeButton;
			instance.searchCustomRangeToggleName =
				config.searchCustomRangeToggleName;
			instance.toInputName = config.toInputName;

			if (instance.aggregationType === 'range') {
				instance.fromInputPicker = document.getElementById(
					instance.fromInputName
				);
				instance.toInputPicker = document.getElementById(
					instance.toInputName
				);
			}
			else {
				instance.fromInputDatePicker = Liferay.component(
					instance.fromInputName + 'DatePicker'
				);
				instance.toInputDatePicker = Liferay.component(
					instance.toInputName + 'DatePicker'
				);
			}

			instance._initializeFormValidator();

			if (instance.searchCustomRangeButton) {
				instance.searchCustomRangeButton.on(
					'click',
					A.bind(instance.filter, instance)
				);
			}
		};

		const DateFacetFilterUtil = {
			addURLParameter(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			removeURLParameters(key, parameterArray) {
				key = encodeURIComponent(key);

				return parameterArray.filter((item) => {
					const itemSplit = item.split('=');

					return !(itemSplit && itemSplit[0] === key);
				});
			},

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

				let fromParameter;
				let toParameter;

				if (instance.aggregationType === 'range') {
					fromParameter = instance.fromInputPicker.value;
					toParameter = instance.toInputPicker.value;
				}
				else {
					const fromDate = instance.fromInputDatePicker.getDate();
					const toDate = instance.toInputDatePicker.getDate();

					fromParameter =
						DateFacetFilterUtil.toLocaleDateStringFormatted(
							fromDate
						);
					toParameter =
						DateFacetFilterUtil.toLocaleDateStringFormatted(toDate);
				}

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
					parameterArray = DateFacetFilterUtil.removeURLParameters(
						param,
						parameterArray
					);
				}

				parameterArray = DateFacetFilterUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = DateFacetFilterUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				const startParameterNameElement = document.getElementById(
					instance.namespace + 'start-parameter-name'
				);

				if (startParameterNameElement) {
					parameterArray = DateFacetFilterUtil.removeURLParameters(
						startParameterNameElement.value,
						parameterArray
					);
				}

				parameterArray = DateFacetFilterUtil.addURLParameter(
					paramFrom,
					fromParameter,
					parameterArray
				);

				parameterArray = DateFacetFilterUtil.addURLParameter(
					paramTo,
					toParameter,
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
		requires: ['aui-form-validator'],
	}
);
