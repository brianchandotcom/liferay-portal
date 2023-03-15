import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import React from 'react';
import {CustomValue} from 'shared/util/records';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getOperator,
	getPropertyValue
} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue
} from '../utils';
import {IDisplayComponentProps} from '../types';
import {isOfKnownType} from 'segment/segment-editor/dynamic/utils/utils';
import {Map} from 'immutable';
import {PropertyTypes} from 'segment/segment-editor/dynamic/utils/constants';

const SessionDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId
}) => {
	const valueIMap = criterion.value as CustomValue;

	const {entityName, label, type} = property;

	const operatorName = getOperator(valueIMap, 0);

	const value = getPropertyValue(valueIMap, 'value', 0);

	const operatorKey = maybeFormatToKnownType(operatorName, value);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	let values = [0];

	if (type === PropertyTypes.SessionGeolocation) {
		const cityIndex = getIndexFromPropertyName(valueIMap, 'context/city');
		const countryIndex = getIndexFromPropertyName(
			valueIMap,
			'context/country'
		);
		const regionIndex = getIndexFromPropertyName(
			valueIMap,
			'context/region'
		);

		values = [cityIndex, regionIndex, countryIndex].filter(
			index => index > -1
		);
	}

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 1) || Map({propertyName: 'date'})
	).toJS();

	return (
		<>
			{entityName}

			<b>{label}</b>

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && (
				<b>
					{values
						.map(index =>
							maybeFormatValue(
								getPropertyValue(valueIMap, 'value', index),
								type,
								timeZoneId
							)
						)
						.join(', ')}
				</b>
			)}

			{type !== PropertyTypes.SessionDateTime && (
				<DateFilterConjunctionDisplay
					conjunctionCriterion={conjunctionCriterion}
				/>
			)}
		</>
	);
};

export default SessionDisplay;
