import moment from 'moment';
import {formatDateToTimeZone} from 'shared/util/date';
import {formatTime} from 'shared/util/time';
import {
	GEOLOCATION_OPTIONS,
	INPUT_DATE_FORMAT,
	INPUT_DISPLAY_DATE_TIME_FORMAT,
	isKnown,
	isUnknown,
	PropertyTypes,
	RelationalOperators,
	SUPPORTED_OPERATORS_MAP
} from 'segment/segment-editor/dynamic/utils/constants';

export function getOperatorLabel(
	operatorKey: string,
	type: PropertyTypes
): string {
	let supportedOperators;

	switch (type) {
		case PropertyTypes.AccountNumber:
		case PropertyTypes.AccountText:
			supportedOperators =
				SUPPORTED_OPERATORS_MAP[type.replace('account-', '')];
			break;
		case PropertyTypes.OrganizationBoolean:
		case PropertyTypes.OrganizationDate:
		case PropertyTypes.OrganizationDateTime:
		case PropertyTypes.OrganizationNumber:
		case PropertyTypes.OrganizationText:
			supportedOperators =
				SUPPORTED_OPERATORS_MAP[type.replace('organization-', '')];
			break;
		case PropertyTypes.SessionDateTime:
		case PropertyTypes.SessionNumber:
		case PropertyTypes.SessionText:
			supportedOperators =
				SUPPORTED_OPERATORS_MAP[type.replace('session-', '')];
			break;
		case PropertyTypes.SessionGeolocation:
			supportedOperators = GEOLOCATION_OPTIONS;
			break;
		case PropertyTypes.Behavior:
		case PropertyTypes.Boolean:
		case PropertyTypes.Date:
		case PropertyTypes.DateTime:
		case PropertyTypes.Duration:
		case PropertyTypes.Number:
		case PropertyTypes.OrganizationSelectText:
		case PropertyTypes.Text:
		default:
			supportedOperators = SUPPORTED_OPERATORS_MAP[type];
	}

	const operator = supportedOperators.find(
		({key, value}) => (key || value) === operatorKey
	);

	return operator ? operator.label : null;
}

export function maybeFormatToKnownType(
	operatorName: string,
	value: any
): string {
	const valueNull = value === null;

	if (operatorName === RelationalOperators.EQ && valueNull) {
		return isUnknown;
	} else if (operatorName === RelationalOperators.NE && valueNull) {
		return isKnown;
	}

	return operatorName;
}

export function maybeFormatValue(
	value: any,
	type: string,
	timeZoneId?: string
): string | number {
	switch (type) {
		case PropertyTypes.AccountText:
		case PropertyTypes.Behavior:
		case PropertyTypes.Interest:
		case PropertyTypes.SessionGeolocation:
		case PropertyTypes.SessionText:
		case PropertyTypes.Text:
			return `"${value}"`;
		case PropertyTypes.Boolean:
			return value.toUpperCase();
		case PropertyTypes.Date:
			return moment(value).format(INPUT_DATE_FORMAT);
		case PropertyTypes.DateTime:
		case PropertyTypes.SessionDateTime:
			return formatDateToTimeZone(
				value,
				INPUT_DISPLAY_DATE_TIME_FORMAT,
				timeZoneId
			);
		case PropertyTypes.Duration:
			return formatTime(value);
		case PropertyTypes.AccountNumber:
		case PropertyTypes.Number:
		case PropertyTypes.SessionNumber:
		default:
			return value;
	}
}
