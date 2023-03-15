import React from 'react';
import {CustomValue} from 'shared/util/records';
import {getPropertyValue} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {IDisplayComponentProps} from '../types';
import {INTEREST_BOOLEAN_OPTIONS} from 'segment/segment-editor/dynamic/utils/constants';
import {maybeFormatValue} from '../utils';

const InterestDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId
}) => {
	const valueIMap = criterion.value as CustomValue;

	const {entityName, type} = property;

	const interestName = getPropertyValue(valueIMap, 'value', 0);

	const operatorLabel = INTEREST_BOOLEAN_OPTIONS.find(
		({value}) => value === getPropertyValue(valueIMap, 'value', 1)
	).label;

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('interested-in-fragment')}</span>

			<b>{maybeFormatValue(interestName, type, timeZoneId)}</b>
		</>
	);
};

export default InterestDisplay;
