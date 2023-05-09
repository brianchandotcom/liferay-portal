import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import AutocompleteInput from 'shared/components/AutocompleteInput';
import ClayButton from '@clayui/button';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Promise from 'metal-promise';
import React from 'react';
import {CustomValue} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {GEOLOCATION_OPTIONS} from '../utils/constants';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getOperator,
	getPropertyValue,
	removeItemsByIndex,
	setPropertyValue
} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {isNull} from 'lodash';
import {isValid} from '../utils/utils';
import {Option, Picker} from '@clayui/core';

/**
 * Location Types
 */
const CITY = 'city';
const COUNTRY = 'country';
const REGION = 'region';

/**
 * Create a new templated criterion entry for Geolocation.
 * @param {string} locationType - The location type.
 * @param {string} value - The value of the criterion.
 * @param {Map} valueIMap - The Immutable Map representing the custom input value.
 * @returns {Map} - The Immutable Map representing the new criterion entry.
 */
export function createLocationTypeEntry(
	locationType: string,
	value: string,
	valueIMap: CustomValue
): Map<string, any> {
	const selectedOperator = getOperator(
		valueIMap,
		getLocationTypeIndex(valueIMap, COUNTRY)
	);

	return Map({
		operatorName: selectedOperator,
		propertyName: `context/${locationType}`,
		value
	});
}

/**
 * Get the index of the entry in the criteria list with the matching location type.
 * @param {Map} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} locationType - The location type.
 * @returns {number} - The index of the matching entry or -1 if not found.
 */
export function getLocationTypeIndex(
	valueIMap: CustomValue,
	locationType: string
): number {
	return getIndexFromPropertyName(valueIMap, `context/${locationType}`);
}

/**
 * Get the value of the criterion that matches the locationType.
 * @param {Map} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} locationType - The location type.
 * @returns {string} - The value of the criterion that matches the locationType.
 */
export function getLocationTypeValue(
	valueIMap: CustomValue,
	locationType: string
): string {
	const locationTypeIndex = getLocationTypeIndex(valueIMap, locationType);

	return locationTypeIndex > -1
		? getPropertyValue(valueIMap, 'value', locationTypeIndex)
		: '';
}

/**
 * Set the value of the criterion that matches the locationType.
 * @param {Map} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} locationType - The location type.
 * @param {string} newValue - The new value to set for the locationType.
 * @returns {Map} - The updated valueIMap with the new value set for the locationType criterion.
 */
export function setLocationTypeValue(
	valueIMap: CustomValue,
	locationType: string,
	newValue: string
): CustomValue {
	const locationTypeIndex = getLocationTypeIndex(valueIMap, locationType);

	return locationTypeIndex > -1
		? setPropertyValue(valueIMap, 'value', locationTypeIndex, newValue)
		: (valueIMap.updateIn(['criterionGroup', 'items'], iList =>
				iList.push(
					createLocationTypeEntry(locationType, newValue, valueIMap)
				)
		  ) as CustomValue);
}

/**
 * Update all of the locationType operators, when the operator dropdown changes.
 * @param {Map} valueIMap - The Immutable Map representing the custom input value.
 * @param {string} newOperator - The new operator.
 * @returns {Map} - The updated valueIMap with the new operator set for the locationType criteria.
 */
export function updateLocationOperators(
	valueIMap: CustomValue,
	newOperator: string
): CustomValue {
	const locationPropertyNames = [COUNTRY, REGION, CITY].map(
		name => `context/${name}`
	);

	return valueIMap.updateIn(['criterionGroup', 'items'], iList =>
		iList.map(entry => {
			const locationProperty = locationPropertyNames.includes(
				entry.get('propertyName')
			);

			return locationProperty
				? entry.set('operatorName', newOperator)
				: entry;
		})
	) as CustomValue;
}

function fetchCountries({
	channelId,
	groupId
}: {
	channelId: string;
	groupId: string;
}): (query: string) => Promise<string[]> {
	return query =>
		API.session
			.fetchFieldValues({
				channelId,
				fieldName: `context/${COUNTRY}`,
				groupId,
				query
			})
			.then(({items}) => items);
}

function fetchRegions({
	channelId,
	groupId,
	valueIMap
}: {
	channelId: string;
	groupId: string;
	valueIMap: CustomValue;
}): (query: string) => Promise<string[]> {
	const countryInputValue = getLocationTypeValue(valueIMap, COUNTRY);

	let filter = [];

	if (countryInputValue) {
		filter = [...filter, `context/${COUNTRY} eq '${countryInputValue}'`];
	}

	return query =>
		API.session
			.fetchFieldValues({
				channelId,
				fieldName: `context/${REGION}`,
				filter: filter.join(' and '),
				groupId,
				query
			})
			.then(({items}) => items);
}

function fetchCities({
	channelId,
	groupId,
	valueIMap
}: {
	channelId: string;
	groupId: string;
	valueIMap: CustomValue;
}): (query: string) => Promise<string[]> {
	const countryInputValue = getLocationTypeValue(valueIMap, COUNTRY);
	const regionInputValue = getLocationTypeValue(valueIMap, REGION);

	let filter = [];

	if (countryInputValue) {
		filter = [...filter, `context/${COUNTRY} eq '${countryInputValue}'`];
	}

	if (regionInputValue) {
		filter = [...filter, `context/${REGION} eq '${regionInputValue}'`];
	}

	return query =>
		API.session
			.fetchFieldValues({
				channelId,
				fieldName: `context/${CITY}`,
				filter: filter.join(' and '),
				groupId,
				query
			})
			.then(({items}) => items);
}

interface IButtonInputTriggerProps {
	className: string;
	dataSourceFn: (value: string) => Promise<any>;
	editing: boolean;
	label: string;
	onBlur: () => void;
	onChange: (value: string) => void;
	onClick: () => void;
	placeholder: string;
	value: string;
}

const ButtonInputTrigger: React.FC<IButtonInputTriggerProps> = ({
	editing,
	label,
	onClick,
	value,
	...otherProps
}) =>
	editing ? (
		<AutocompleteInput focusOnInit={!value} value={value} {...otherProps} />
	) : (
		<ClayButton
			className='button-root'
			displayType='secondary'
			onClick={onClick}
		>
			{label}
		</ClayButton>
	);

interface IGeolocationInputProps extends ISegmentEditorCustomInputBase {
	touched: {country: boolean; dateFilter: boolean};
	valid: {country: boolean; dateFilter: boolean};
}

export default class GeolocationInput extends React.Component<
	IGeolocationInputProps,
	{editCity: boolean; editRegion: boolean}
> {
	_completedAnalytics = false;

	constructor(props) {
		super(props);

		const {value} = props;

		this.state = {
			editCity: !!getLocationTypeValue(value, CITY),
			editRegion: !!getLocationTypeValue(value, REGION)
		};
	}

	componentDidUpdate() {
		const {
			id,
			property: {entityName, type},
			valid: {country, dateFilter}
		} = this.props;

		const valid = country && dateFilter;

		if (!id && valid && !this._completedAnalytics) {
			this._completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}

	getConjunctionDateFilterIMap(value) {
		const conjunctionDateFilterIndex = getIndexFromPropertyName(
			value,
			'completeDate'
		);

		if (conjunctionDateFilterIndex >= 0) {
			return getFilterCriterionIMap(
				value,
				conjunctionDateFilterIndex
			).toJS();
		}

		return {propertyName: 'completeDate'};
	}

	@autobind
	handleConjunctionChange(criterion) {
		const {onChange, touched, valid, value} = this.props;

		onChange({
			touched: {...touched, dateFilter: criterion && criterion.touched},
			valid: {...valid, dateFilter: isNull(criterion) || criterion.valid},
			value: isNull(criterion)
				? value.deleteIn(['criterionGroup', 'items', 1])
				: value.mergeIn(
						['criterionGroup', 'items', 1],
						fromJS(criterion)
				  )
		});
	}

	@autobind
	handleCountryBlur() {
		const {onChange, touched, valid, value} = this.props;

		onChange({
			touched: {...touched, country: true},
			valid: {
				...valid,
				country: isValid(getLocationTypeValue(value, COUNTRY))
			}
		});
	}

	@autobind
	handleLocationOnBlur(locationType) {
		const {onChange, value} = this.props;

		const inputValue = getLocationTypeValue(value, locationType);

		if (!inputValue.length) {
			const criterionIndex = getLocationTypeIndex(value, locationType);

			onChange({
				value: removeItemsByIndex(value, [criterionIndex])
			});
		}
	}

	@autobind
	handleLocationTypeChange(inputValue, locationType) {
		const {onChange, valid, value} = this.props;

		let params: {
			value: Map<any, any>;
			valid?: {country: boolean; dateFilter: boolean};
		} = {
			value: setLocationTypeValue(value, locationType, inputValue)
		};

		if (locationType === COUNTRY) {
			params = {
				...params,
				valid: {...valid, country: isValid(inputValue)}
			};
		}

		onChange(params);
	}

	@autobind
	handleOperatorChange(newValue) {
		const {onChange, value} = this.props;

		onChange({value: updateLocationOperators(value, newValue)});
	}

	render() {
		const {
			props: {
				channelId,
				displayValue,
				groupId,
				property: {entityName},
				touched,
				valid,
				value
			},
			state: {editCity, editRegion}
		} = this;

		const cityInputValue = getLocationTypeValue(value, CITY);
		const regionInputValue = getLocationTypeValue(value, REGION);

		const conjunctionCriterion = this.getConjunctionDateFilterIMap(value);

		return (
			<div className='criteria-statement geolocation-input'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{displayValue}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<Picker
							className='operator-input'
							items={GEOLOCATION_OPTIONS}
							onSelectionChange={this.handleOperatorChange}
							selectedKey={getOperator(
								value,
								getLocationTypeIndex(value, COUNTRY)
							)}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<AutocompleteInput
							className={getCN({
								'has-error': !valid && touched
							})}
							dataSourceFn={fetchCountries({channelId, groupId})}
							onBlur={this.handleCountryBlur}
							onChange={value =>
								this.handleLocationTypeChange(value, COUNTRY)
							}
							placeholder={Liferay.Language.get('country')}
							value={getLocationTypeValue(value, COUNTRY)}
						/>
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<ButtonInputTrigger
							className='region'
							dataSourceFn={fetchRegions({
								channelId,
								groupId,
								valueIMap: value
							})}
							editing={editRegion || !!regionInputValue.length}
							label={Liferay.Language.get('add-region')}
							onBlur={() => this.handleLocationOnBlur(REGION)}
							onChange={value =>
								this.handleLocationTypeChange(value, REGION)
							}
							onClick={() => this.setState({editRegion: true})}
							placeholder={Liferay.Language.get('region')}
							value={regionInputValue}
						/>
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<ButtonInputTrigger
							className='city'
							dataSourceFn={fetchCities({
								channelId,
								groupId,
								valueIMap: value
							})}
							editing={editCity || !!cityInputValue.length}
							label={Liferay.Language.get('add-city')}
							onBlur={() => this.handleLocationOnBlur(CITY)}
							onChange={value =>
								this.handleLocationTypeChange(value, CITY)
							}
							onClick={() => this.setState({editCity: true})}
							placeholder={Liferay.Language.get('city')}
							value={cityInputValue}
						/>
					</Form.GroupItem>
				</Form.Group>

				<Form.Group autoFit>
					<DateFilterConjunctionInput
						conjunctionCriterion={conjunctionCriterion}
						onChange={this.handleConjunctionChange}
					/>
				</Form.Group>
			</div>
		);
	}
}
