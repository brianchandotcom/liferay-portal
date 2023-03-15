import AttributeConjunctionDisplay from './AttributeConjunctionDisplay';
import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import OccurenceConjunctionDisplay from './OccurenceConjunctionDisplay';
import React from 'react';
import {getFilterCriterionIMap} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import {IDisplayComponentProps} from '../types';
import {Map} from 'immutable';

const EventDisplay: React.FC<IDisplayComponentProps> = ({
	criterion: {operatorName, value: valueIMap},
	property: {label, type}
}) => {
	const operatorKey = maybeFormatToKnownType(operatorName, name);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const dateFilterConjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 2) ||
		Map({propertyName: 'completeDate'})
	).toJS();

	return (
		<>
			<span className='sentence-start'>{operatorLabel}</span>

			<span>{Liferay.Language.get('performed-fragment')}</span>

			<b>{label}</b>

			<OccurenceConjunctionDisplay
				operatorName={valueIMap.get('operator')}
				value={valueIMap.get('value')}
			/>

			<DateFilterConjunctionDisplay
				conjunctionCriterion={dateFilterConjunctionCriterion}
			/>

			<span>{Liferay.Language.get('where-fragment')}</span>

			<AttributeConjunctionDisplay
				conjunctionCriterion={getFilterCriterionIMap(
					valueIMap,
					1
				).toJS()}
			/>
		</>
	);
};

export default EventDisplay;
