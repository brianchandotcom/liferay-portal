import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import OccurenceConjunctionDisplay from './OccurenceConjunctionDisplay';
import React from 'react';
import RealTimePeriodDisplay from './RealTimePeriodDisplay';
import {CustomValue} from 'shared/util/records';
import {getFilterCriterionIMap} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import {IDisplayComponentProps} from '../types';
import {Map} from 'immutable';
import {SegmentTypes} from 'shared/util/constants';

const EventDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	segmentType
}) => {
	const {operatorName, value} = criterion;

	const valueIMap = value as CustomValue;

	const {label, options, type} = property;

	const operatorKey = maybeFormatToKnownType(operatorName, name);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const eventOperator = valueIMap.get('operator');

	const occurenceCount = valueIMap.get('value');

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 2) ||
		Map({propertyName: 'completeDate'})
	).toJS();

	if (
		options?.length &&
		options.some(option => option.label === 'hidden' && option.value)
	) {
		return (
			<b className='undefined-property'>
				{Liferay.Language.get('custom-event-no-longer-exists')}
			</b>
		);
	}

	return (
		<>
			<span className='sentence-start'>
				{Liferay.Language.get('individual')}
			</span>

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('performed-fragment')}</span>

			<b>{label}</b>

			<OccurenceConjunctionDisplay
				operatorName={eventOperator}
				value={occurenceCount}
			/>

			{segmentType === SegmentTypes.RealTime ? (
				<RealTimePeriodDisplay
					conjunctionCriterion={conjunctionCriterion}
				/>
			) : (
				<DateFilterConjunctionDisplay
					conjunctionCriterion={conjunctionCriterion}
				/>
			)}
		</>
	);
};

export default EventDisplay;
