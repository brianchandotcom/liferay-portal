import React, {useContext} from 'react';
import {BOOLEAN_LABELS_MAP} from 'event-analysis/utils/utils';
import {Criterion} from 'segment/segment-editor/dynamic/utils/types';
import {DataTypes} from 'event-analysis/utils/types';
import {
	EntityType,
	ReferencedObjectsContext
} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {formatTime} from 'shared/util/time';
import {formatUTCDate} from 'shared/util/date';
import {FunctionalOperators} from 'segment/segment-editor/dynamic/utils/constants';
import {getOperatorOptions} from 'segment/segment-editor/dynamic/inputs/components/attribute-conjunction-input/utils';

interface IAttributeConjunctionDisplayProps {
	conjunctionCriterion: Criterion;
}

const AttributeConjunctionDisplay: React.FC<IAttributeConjunctionDisplayProps> = ({
	conjunctionCriterion: {operatorName, propertyName, value}
}) => {
	const {referencedEntities} = useContext(ReferencedObjectsContext);

	const [, id] = propertyName.split('/');

	const attributeIMap = referencedEntities.getIn([EntityType.Attributes, id]);

	const dataType = attributeIMap.get('dataType');
	const displayName = attributeIMap.get('displayName');

	const operatorOptions = getOperatorOptions(dataType);

	const {label = Liferay.Language.get('is-fragment')} =
		operatorOptions?.find(({value}) => value === operatorName) || {};

	const formatByDataType = (value, dataType) => {
		switch (dataType) {
			case DataTypes.Boolean:
				return BOOLEAN_LABELS_MAP[value];
			case DataTypes.Date:
				if (FunctionalOperators.Between === operatorName) {
					const {end, start} = value;

					return `${formatUTCDate(start, 'll')} - ${formatUTCDate(
						end,
						'll'
					)}`;
				}

				return formatUTCDate(value, 'll');
			case DataTypes.Duration:
				return formatTime(value);
			case DataTypes.Number:
				if (FunctionalOperators.Between === operatorName) {
					const {end, start} = value;

					return `${start} - ${end}`;
				}

				return value;
			case DataTypes.String:
			default:
				return `"${value}"`;
		}
	};

	const displayValue = formatByDataType(value, dataType);

	return attributeIMap ? (
		<>
			<b>{displayName}</b>

			<b>{label}</b>

			<b>{displayValue}</b>
		</>
	) : (
		<b className='undefined-entity'>
			{Liferay.Language.get('undefined-attribute')}
		</b>
	);
};

export default AttributeConjunctionDisplay;
