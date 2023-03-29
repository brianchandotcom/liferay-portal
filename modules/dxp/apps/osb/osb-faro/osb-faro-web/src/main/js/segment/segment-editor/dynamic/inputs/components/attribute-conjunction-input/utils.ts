import {BetweenNumber} from '../BetweenNumberInput';
import {
	BOOLEAN_LABELS_MAP,
	DATE_OPERATOR_LONGHAND_LABELS_MAP,
	DATE_OPTIONS,
	DURATION_OPERATOR_LONGHAND_LABELS_MAP,
	DURATION_OPTIONS,
	NUMBER_OPERATOR_LONGHAND_LABELS_MAP,
	NUMBER_OPTIONS
} from 'event-analysis/utils/utils';
import {DataTypes} from 'event-analysis/utils/types';
import {DateRange} from 'shared/components/DateRangeInput';
import {
	FunctionalOperators,
	RelationalOperators,
	STRING_OPERATOR_LABELS_MAP,
	STRING_OPTIONS
} from '../../../utils/constants';
import {isNumber} from 'lodash';
import {isValid} from '../../../utils/utils';

export const createOption = (option, dataType: DataTypes) => {
	const LABELS_MAP = {
		[DataTypes.Boolean]: BOOLEAN_LABELS_MAP,
		[DataTypes.Date]: DATE_OPERATOR_LONGHAND_LABELS_MAP,
		[DataTypes.Duration]: DURATION_OPERATOR_LONGHAND_LABELS_MAP,
		[DataTypes.Number]: NUMBER_OPERATOR_LONGHAND_LABELS_MAP,
		[DataTypes.String]: STRING_OPERATOR_LABELS_MAP // STRING_OPERATOR_LABELS_MAP is provided from the segment-editor utils as "NotContains" differs from segment-editor and event-analysis. We should be able to use the evente-analysis version once we move away from odata.
	};

	return {
		label: LABELS_MAP[dataType][option],
		value: option
	};
};

export const getOperatorOptions = (dataType: DataTypes) => {
	const OPERATOR_OPTIONS = {
		[DataTypes.Date]: DATE_OPTIONS,
		[DataTypes.Duration]: DURATION_OPTIONS,
		[DataTypes.Number]: NUMBER_OPTIONS,
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
