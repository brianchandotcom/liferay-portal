/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-search-modified-facet',
	A => {
		var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		var FacetUtil = Liferay.Search.FacetUtil;
		var Language = Liferay.Language;
		var Util = Liferay.Util;

		var ModifiedFacetFilter = function(config) {
			var instance = this;

			instance.form = config.form;
			instance.fromInputDatePicker = config.fromInputDatePicker;
			instance.fromInputName = config.fromInputName;
			instance.namespace = config.namespace;
			instance.searchCustomRangeButton = config.searchCustomRangeButton;
			instance.toInputDatePicker = config.toInputDatePicker;
			instance.toInputName = config.toInputName;

			instance.fromInput = A.one('#' + instance.fromInputName);
			instance.toInput = A.one('#' + instance.toInputName);

			instance._initializeFormValidator();

			instance.searchCustomRangeButton.on(
				'click',
				A.bind(instance.filter, instance)
			);

			instance.fromInput.on('keydown', instance._onDateInputKeyDown);
			instance.toInput.on('keydown', instance._onDateInputKeyDown);
		};

		A.mix(ModifiedFacetFilter.prototype, {
			_initializeFormValidator() {
				var instance = this;

				var dateRangeRuleName = instance.namespace + 'dateRange';

				A.mix(
					DEFAULTS_FORM_VALIDATOR.STRINGS,
					{
						[dateRangeRuleName]: Language.get(
							'search-custom-range-invalid-date-range'
						)
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
						}
					},
					true
				);

				var customRangeValidator = new A.FormValidator({
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
						}
					},
					rules: {
						[instance.fromInputName]: {
							[dateRangeRuleName]: true
						},
						[instance.toInputName]: {
							[dateRangeRuleName]: true
						}
					}
				});

				var onRangeSelectionChange = function() {
					customRangeValidator.validate();
				};

				instance.fromInputDatePicker.on(
					'selectionChange',
					onRangeSelectionChange
				);

				instance.toInputDatePicker.on(
					'selectionChange',
					onRangeSelectionChange
				);
			},

			_onDateInputKeyDown(event) {
				if (!event.isKey('TAB')) {
					event.preventDefault();
				}
			},

			filter() {
				var instance = this;

				var fromDate = instance.fromInputDatePicker.getDate();

				var toDate = instance.toInputDatePicker.getDate();

				var modifiedFromParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					fromDate
				);

				var modifiedToParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					toDate
				);

				var param = ModifiedFacetFilterUtil.getParameterName();
				var paramFrom = param + 'From';
				var paramTo = param + 'To';

				var parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = FacetUtil.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				parameterArray = FacetUtil.addURLParameter(
					paramFrom,
					modifiedFromParameter,
					parameterArray
				);

				parameterArray = FacetUtil.addURLParameter(
					paramTo,
					modifiedToParameter,
					parameterArray
				);

				ModifiedFacetFilterUtil.submitSearch(parameterArray.join('&'));
			}
		});

		Liferay.namespace('Search').ModifiedFacetFilter = ModifiedFacetFilter;

		var ModifiedFacetFilterUtil = {
			clearSelections() {
				var param = this.getParameterName();
				var paramFrom = param + 'From';
				var paramTo = param + 'To';

				var parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = FacetUtil.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				this.submitSearch(parameterArray.join('&'));
			},

			getParameterName() {
				return 'modified';
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
				var localDate = new Date(date);

				localDate.setMinutes(
					date.getMinutes() - date.getTimezoneOffset()
				);

				return localDate.toISOString().split('T')[0];
			}
		};

		Liferay.namespace(
			'Search'
		).ModifiedFacetFilterUtil = ModifiedFacetFilterUtil;
	},
	'',
	{
		requires: ['aui-form-validator', 'liferay-search-facet-util']
	}
);
