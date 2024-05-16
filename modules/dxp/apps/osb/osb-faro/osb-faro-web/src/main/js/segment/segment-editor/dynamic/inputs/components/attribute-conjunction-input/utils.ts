import {BetweenNumber} from '../BetweenNumberInput';
import {BOOLEAN_LABELS_MAP} from 'event-analysis/utils/utils';
import {DataTypes, Operators} from 'event-analysis/utils/types';
import {DateRange} from 'shared/components/DateRangeInput';
import {
	FunctionalOperators,
	NotOperators,
	RelationalOperators,
	STRING_OPTIONS
} from '../../../utils/constants';
import {isNumber} from 'lodash';
import {isValid} from '../../../utils/utils';

const ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP = {
	[Operators.EQ]: Liferay.Language.get('is-fragment'),
	[Operators.GT]: Liferay.Language.get('is-after-fragment'),
	[Operators.LT]: Liferay.Language.get('is-before-fragment')
};

const ATTRIBUTES_DATE_AND_DURATION_OPTIONS = [
	Operators.LT,
	Operators.EQ,
	Operators.GT
];

export const ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP = {
	[Operators.EQ]: Liferay.Language.get('is-equal-to-fragment'),
	[Operators.GT]: Liferay.Language.get('greater-than-fragment'),
	[Operators.LT]: Liferay.Language.get('less-than-fragment'),
	[Operators.NE]: Liferay.Language.get('is-not-equal-to-fragment')
};

const ATTRIBUTE_NUMBER_OPTIONS = [
	Operators.EQ,
	Operators.GT,
	Operators.LT,
	Operators.NE
];

const ATTRIBUTES_STRING_OPERATOR_LABELS_MAP = {
	[FunctionalOperators.Contains]: Liferay.Language.get('contains-fragment'),
	[NotOperators.NotContains]: Liferay.Language.get(
		'does-not-contain-fragment'
	),
	[RelationalOperators.EQ]: Liferay.Language.get('is-fragment'),
	[RelationalOperators.NE]: Liferay.Language.get('is-not-fragment')
};

export const createOption = (option, dataType: DataTypes) => {
	const LABELS_MAP = {
		[DataTypes.Boolean]: BOOLEAN_LABELS_MAP,
		[DataTypes.Date]: ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP,
		[DataTypes.Duration]: ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP,
		[DataTypes.Number]: ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP,
		[DataTypes.String]: ATTRIBUTES_STRING_OPERATOR_LABELS_MAP // "NotContains" differs from segment-editor and event-analysis. We should be able to use the evente-analysis version once we move away from odata.
	};

	return {
		label: LABELS_MAP[dataType][option],
		value: option
	};
};

export const getOperatorOptions = (dataType: DataTypes) => {
	const OPERATOR_OPTIONS = {
		[DataTypes.Date]: ATTRIBUTES_DATE_AND_DURATION_OPTIONS,
		[DataTypes.Duration]: ATTRIBUTES_DATE_AND_DURATION_OPTIONS,
		[DataTypes.Number]: ATTRIBUTE_NUMBER_OPTIONS,
		[DataTypes.String]: STRING_OPTIONS // STRING_OPTIONS is provided from the segment-editor utils as "NotContains" differs from segment-editor and event-analysis. We should be able to use the evente-analysis version once we move away from odata.
	};

	return OPERATOR_OPTIONS[dataType]?.map(option =>
		createOption(option, dataType)
	);
};

export const getDefaultAttributeOperator = (
	dataType: DataTypes
): RelationalOperators | FunctionalOperators => {
	switch (dataType) {
		case DataTypes.Boolean:
		case DataTypes.Date:
			return RelationalOperators.EQ;
		case DataTypes.Duration:
		case DataTypes.Number:
			return RelationalOperators.GT;
		case DataTypes.String:
		default:
			return FunctionalOperators.Contains;
	}
};

export const getDefaultAttributeValue = (
	dataType: DataTypes,
	operatorName: RelationalOperators | FunctionalOperators
): string | {end: number | string; start: number | string} => {
	if (
		operatorName === FunctionalOperators.Between &&
		[DataTypes.Number, DataTypes.Date].filter(type => type === dataType)
	) {
		return {end: '', start: ''};
	}

	switch (dataType) {
		case DataTypes.Boolean:
			return 'true';
		case DataTypes.Date:
		case DataTypes.Number:
		case DataTypes.Duration:
		case DataTypes.String:
		default:
			return '';
	}
};

export const validateAttributeValue = (
	value: string | number | BetweenNumber | DateRange,
	dataType: DataTypes,
	operatorName?: FunctionalOperators | RelationalOperators
): boolean => {
	if (
		operatorName === FunctionalOperators.Between &&
		[DataTypes.Number, DataTypes.Date].filter(type => type === dataType)
	) {
		const {end, start} = value as BetweenNumber;

		return isValid(end) && isValid(start);
	}

	switch (dataType) {
		case DataTypes.Boolean:
			return value === 'true' || value === 'false';
		case DataTypes.Duration:
		case DataTypes.Number:
			return isNumber(value);
		case DataTypes.Date:
		case DataTypes.String:
		default:
			return isValid(value);
	}
};
