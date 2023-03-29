import React from 'react';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';
import {EntityType} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {
	getOperator,
	getPropertyValue
} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue
} from '../utils';
import {ICustomDisplayComponentProps} from '../types';
import {isOfKnownType} from 'segment/segment-editor/dynamic/utils/utils';
import {PropertyTypes} from 'segment/segment-editor/dynamic/utils/constants';

const OrganizationDisplay: React.FC<ICustomDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId
}) => {
	const value = getPropertyValue(criterion.value, 'value', 0);

	const {entityName, label, type} = property;

	const referencedEntity = type === PropertyTypes.OrganizationSelectText;

	const operatorName = referencedEntity
		? criterion.operatorName
		: getOperator(criterion.value, 0);

	const propertyDataType = type.replace('organization-', '');

	const operatorKey = maybeFormatToKnownType(operatorName, value);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const renderContent = () => {
		if (referencedEntity) {
			return (
				<ReferencedEntityDisplay
					id={value}
					label={Liferay.Language.get('organization')}
					type={EntityType.Organizations}
				/>
			);
		} else {
			return (
				<b>{maybeFormatValue(value, propertyDataType, timeZoneId)}</b>
			);
		}
	};

	return (
		<>
			{entityName}

			{property.name !== 'id' && <b>{label}</b>}

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && renderContent()}
		</>
	);
};

export default OrganizationDisplay;
