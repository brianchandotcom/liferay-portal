import React from 'react';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';
import {ENTITY_MAP} from 'segment/segment-editor/dynamic/inputs/IndividualSelectInput';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue
} from '../utils';
import {IDisplayComponentProps} from '../types';
import {isOfKnownType} from 'segment/segment-editor/dynamic/utils/utils';
import {PropertyTypes} from 'segment/segment-editor/dynamic/utils/constants';

const IndividualDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId
}) => {
	const {operatorName, propertyName, value} = criterion;

	const {entityName, label, type} = property;

	const renderContent = () => {
		switch (type) {
			case PropertyTypes.SelectText:
				return (
					<ReferencedEntityDisplay
						id={value}
						label={label}
						type={ENTITY_MAP[propertyName]}
					/>
				);
			default:
				return <b>{maybeFormatValue(value, type, timeZoneId)}</b>;
		}
	};

	const operatorKey = maybeFormatToKnownType(operatorName, value);
	const operatorLabel = getOperatorLabel(operatorKey, type);

	return (
		<>
			{entityName}

			<b>{label}</b>

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && renderContent()}
		</>
	);
};

export default IndividualDisplay;
