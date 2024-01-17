/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-modified-facet',
	(A) => {
		const DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		const Util = Liferay.Util;

		const ModifiedFacetFilter = function (config) {
			const instance = this;

			instance.form = config.form;
			instance.fromInputName = config.fromInputName;
			instance.namespace = config.namespace;
			instance.searchCustomRangeButton = config.searchCustomRangeButton;
			instance.toInputName = config.toInputName;

			instance.fromInputDatePicker = Liferay.component(
				instance.fromInputName + 'DatePicker'
			);
			instance.toInputDatePicker = Liferay.component(
				instance.toInputName + 'DatePicker'
			);

			instance.fromInput = A.one('#' + instance.fromInputName);
			instance.toInput = A.one('#' + instance.toInputName);

			instance._initializeFormValidator();

			if (instance.searchCustomRangeButton) {
				instance.searchCustomRangeButton.on(
					'click',
					A.bind(instance.filter, instance)
				);
			}
		};

		const ModifiedFacetFilterUtil = {
			addURLParameter(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			clearSelections() {
				const param = this.getParameterName();
				const paramFrom = param + 'From';
				const paramTo = param + 'To';

				let parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = this.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = this.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = this.removeURLParameters(
					paramTo,
					parameterArray
				);

				this.submitSearch(parameterArray.join('&'));
			},

			getParameterName() {
				return 'modified';
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

		A.mix(ModifiedFacetFilter.prototype, {
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

				const modifiedFromParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					fromDate
				);

				const modifiedToParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					toDate
				);

				const param = ModifiedFacetFilterUtil.getParameterName();
				const paramFrom = param + 'From';
				const paramTo = param + 'To';

				let parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = ModifiedFacetFilterUtil.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = ModifiedFacetFilterUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = ModifiedFacetFilterUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				const startParameterNameElement = document.getElementById(
					instance.namespace + 'start-parameter-name'
				);

				if (startParameterNameElement) {
					parameterArray = ModifiedFacetFilterUtil.removeURLParameters(
						startParameterNameElement.value,
						parameterArray
					);
				}

				parameterArray = ModifiedFacetFilterUtil.addURLParameter(
					paramFrom,
					modifiedFromParameter,
					parameterArray
				);

				parameterArray = ModifiedFacetFilterUtil.addURLParameter(
					paramTo,
					modifiedToParameter,
					parameterArray
				);

				ModifiedFacetFilterUtil.submitSearch(parameterArray.join('&'));
			},
		});

		Liferay.namespace('Search').ModifiedFacetFilter = ModifiedFacetFilter;

		Liferay.namespace(
			'Search'
		).ModifiedFacetFilterUtil = ModifiedFacetFilterUtil;
	},
	'',
	{
		requires: ['aui-form-validator'],
	}
);
