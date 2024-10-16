/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React, {useEffect} from 'react';

import InputSets, {useInputSets} from '../shared/input_sets/index';

const DEFAULT_DATE_RANGES_JSON_ARRAY = [
	{label: 'past-hour', range: '[past-hour TO *]'},
	{label: 'past-24-hours', range: '[past-24-hours TO *]'},
	{label: 'past-week', range: '[past-week TO *]'},
	{label: 'past-month', range: '[past-month TO *]'},
	{label: 'past-year', range: '[past-year TO *]'},
];

const DEFAULT_RANGES_JSON_ARRAY = [{label: '', range: ''}];

const RANGES_CONFIGURATION_OPTION = {
	LABEL: Liferay.Language.get('ranges-configuration'),
	VALUE: 'rangesConfiguration',
};

const AGGREGATION_TYPES = {
	DATE_RANGE: 'dateRange',
	RANGE: 'range',
	TERMS: 'terms',
};

/**
 * Cleans up the ranges array by removing those that have empty indexed
 * range names or labels, then updating the object to only have the
 * label and range properties.
 * @param {Array} ranges The list of ranges.
 * @return {Array} The cleaned up list of ranges.
 */
const removeEmptyRanges = (ranges) => {
	return ranges
		.filter(({label, range}) => !!range && !!label)
		.map(({label, range}) => ({label, range}));
};

function Inputs({index, namespace, onInputSetItemChange, value}) {
	const _handleChangeValue = (property) => (event) => {
		onInputSetItemChange(index, {[property]: event.target.value});
	};

	return (
		<div className="input-group-item">
			<div className="c-mb-3 form-group-autofit">
				<ClayInput.GroupItem>
					<label htmlFor={`${namespace}_label_${index}`}>
						{Liferay.Language.get('label')}

						<span className="c-ml-1 reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						id={`${namespace}_label_${index}`}
						onChange={_handleChangeValue('label')}
						placeholder={Liferay.Language.get(
							'enter-language-key-for-display-label'
						)}
						required
						type="text"
						value={value.label || ''}
					/>
				</ClayInput.GroupItem>
			</div>

			<div className="c-mb-3 form-group-autofit">
				<ClayInput.GroupItem>
					<label htmlFor={`${namespace}_range_${index}`}>
						{Liferay.Language.get('range')}

						<span className="c-ml-1 reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>

						<ClayTooltipProvider>
							<span
								className="c-ml-2 text-secondary"
								data-title={Liferay.Language.get(
									'custom-configuration-range-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</ClayTooltipProvider>
					</label>

					<ClayInput
						id={`${namespace}_range_${index}`}
						onChange={_handleChangeValue('range')}
						placeholder={Liferay.Language.get('enter-a-range')}
						required
						type="text"
						value={value.range || ''}
					/>
				</ClayInput.GroupItem>
			</div>
		</div>
	);
}

function CustomConfigurationRangeOptions({
	rangesInputName = 'ranges',
	rangesIndexInputName = 'rangesIndexes',
	rangesJSONArray = DEFAULT_RANGES_JSON_ARRAY,
	namespace = '',
}) {
	const {
		getInputSetItemProps,
		onInputSetItemChange,
		onInputSetsAdd,
		onInputSetsChange,
		value: ranges,
	} = useInputSets(rangesJSONArray);

	useEffect(() => {
		const aggregationTypeElement = document.getElementById(
			`${namespace}aggregationType`
		);
		const maxTermsContainerElement = document.getElementById(
			`${namespace}maxTermsContainer`
		);
		const preferenceKeyOrderElement = document.getElementById(
			`${namespace}preferenceKeyOrder`
		);
		const rangesConfigurationFieldsetElement = document.getElementById(
			`${namespace}ranges-configuration`
		);

		const addRangeConfigurationOptionItem = () => {
			const rangesOptionItem = document.createElement('option');

			rangesOptionItem.textContent = RANGES_CONFIGURATION_OPTION.LABEL;
			rangesOptionItem.value = RANGES_CONFIGURATION_OPTION.VALUE;
			rangesOptionItem.id = `${namespace}rangesConfigurationOption`;

			if (preferenceKeyOrderElement.options.length < 5) {
				preferenceKeyOrderElement.add(rangesOptionItem);
			}
		};

		const removeRangeConfigurationOptionItem = () => {
			const rangesOptionItem = preferenceKeyOrderElement.querySelector(
				`#${namespace}rangesConfigurationOption`
			);

			if (rangesOptionItem) {
				preferenceKeyOrderElement.removeChild(rangesOptionItem);
			}
		};

		const enableRangesConfiguration = (aggregationType) => {

			// Show Ranges Configuration Fieldset

			if (rangesConfigurationFieldsetElement.classList.contains('hide')) {
				rangesConfigurationFieldsetElement.classList.remove('hide');
			}

			// Set default ranges in the drag-and-drop list

			if (aggregationType === AGGREGATION_TYPES.DATE_RANGE) {
				onInputSetsChange(DEFAULT_DATE_RANGES_JSON_ARRAY);
			}
			else if (aggregationType === AGGREGATION_TYPES.RANGE) {
				onInputSetsChange(DEFAULT_RANGES_JSON_ARRAY);
			}
		};

		const disableRangesConfiguration = () => {

			// Hide Ranges Configuration Fieldset

			if (
				!rangesConfigurationFieldsetElement.classList.contains('hide')
			) {
				rangesConfigurationFieldsetElement.classList.add('hide');
			}

			// Remove ranges in the drag-and-drop list (avoids 'required' error)

			onInputSetsChange([]);
		};

		const _handleAggregationTypeChange = (event) => {
			const currentValue = event.target.value;

			if (
				currentValue === AGGREGATION_TYPES.DATE_RANGE ||
				currentValue === AGGREGATION_TYPES.RANGE
			) {
				addRangeConfigurationOptionItem();

				preferenceKeyOrderElement.value =
					RANGES_CONFIGURATION_OPTION.VALUE;

				enableRangesConfiguration(currentValue);

				if (!maxTermsContainerElement.classList.contains('hide')) {
					maxTermsContainerElement.classList.add('hide');
				}
			}
			else if (currentValue === AGGREGATION_TYPES.TERMS) {
				disableRangesConfiguration();

				if (
					preferenceKeyOrderElement.value ===
					RANGES_CONFIGURATION_OPTION.VALUE
				) {
					preferenceKeyOrderElement.value =
						preferenceKeyOrderElement.options[0].value;
				}

				removeRangeConfigurationOptionItem();

				if (maxTermsContainerElement.classList.contains('hide')) {
					maxTermsContainerElement.classList.remove('hide');
				}
			}
		};

		aggregationTypeElement?.addEventListener(
			'change',
			_handleAggregationTypeChange
		);

		return () => {
			aggregationTypeElement?.removeEventListener(
				'change',
				_handleAggregationTypeChange
			);
		};
	}, [namespace, onInputSetsChange]);

	return (
		<div className="sort-configurations-options">
			<InputSets>
				{ranges.map((valueItem, valueIndex) => {
					const {
						index,
						isLastItem,
						key,
						onInputSetItemDelete,
						onInputSetItemMove,
					} = getInputSetItemProps(valueItem, valueIndex);

					return (
						<InputSets.Item
							index={index}
							isLastItem={isLastItem}
							key={key}
							onInputSetItemDelete={
								ranges.length === 1
									? null // Forbid deletion of the last item
									: onInputSetItemDelete
							}
							onInputSetItemMove={onInputSetItemMove}
						>
							<Inputs
								index={valueIndex}
								namespace={namespace}
								onInputSetItemChange={onInputSetItemChange}
								value={valueItem}
							/>
						</InputSets.Item>
					);
				})}

				<ClayButton
					aria-label={Liferay.Language.get('add-range')}
					className={getCN({
						'c-mt-4': !ranges.length,
					})}
					displayType="secondary"
					onClick={onInputSetsAdd}
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>

					{Liferay.Language.get('add-range')}
				</ClayButton>
			</InputSets>

			<input
				name={`${namespace}${rangesInputName}`}
				type="hidden"
				value={JSON.stringify(removeEmptyRanges(ranges))}
			/>

			<input
				name={`${namespace}${rangesIndexInputName}`}
				type="hidden"
				value={ranges.map((_, index) => index).join(',')}
			/>
		</div>
	);
}

export default CustomConfigurationRangeOptions;
