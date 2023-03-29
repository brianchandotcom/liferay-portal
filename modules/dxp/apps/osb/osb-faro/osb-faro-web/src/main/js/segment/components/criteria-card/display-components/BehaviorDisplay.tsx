import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import OccurenceConjunctionDisplay from './OccurenceConjunctionDisplay';
import React from 'react';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';
import {ASSET_TYPE_LANG_MAP} from 'shared/util/lang';
import {CustomValue} from 'shared/util/records';
import {EntityType} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {
	getFilterCriterionIMap,
	getPropertyValue
} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import {IDisplayComponentProps} from '../types';
import {Map} from 'immutable';
import {parseActivityKey} from 'segment/segment-editor/dynamic/utils/utils';

const BehaviorDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property
}) => {
	const {operatorName, value} = criterion;

	const valueIMap = value as CustomValue;

	const {entityName, label, type} = property;

	const {id, objectType} = parseActivityKey(
		getPropertyValue(valueIMap, 'value', 0)
	);

	const operatorKey = maybeFormatToKnownType(operatorName, name);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const eventOperator = valueIMap.get('operator');

	const occurenceCount = valueIMap.get('value');

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 1) ||
		Map({propertyName: 'completeDate'})
	).toJS();

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{label}</span>

			<ReferencedEntityDisplay
				id={id}
				label={ASSET_TYPE_LANG_MAP[objectType]}
				type={EntityType.Assets}
			/>

			<OccurenceConjunctionDisplay
				operatorName={eventOperator}
				value={occurenceCount}
			/>

			<DateFilterConjunctionDisplay
				conjunctionCriterion={conjunctionCriterion}
			/>
		</>
	);
};

export default BehaviorDisplay;
