import moment from 'moment';
import {
	Attribute,
	AttributeOwnerTypes,
	Breakdown,
	BreakdownData,
	BreakdownDataItem,
	DataTypes,
	DateGroupings,
	Filter,
	Operators,
	ParsedBreakdownData,
	ParsedBreakdownItem
} from './types';
import {formatTime} from 'shared/util/time';
import {formatUTCDate} from 'shared/util/date';
import {OrderByDirections} from 'shared/util/constants';

const DEFAULT_DATE_GROUPING = DateGroupings.Month;
const DEFAULT_DURATION_BIN = 60000;
const DEFAULT_NUMBER_BIN = 10;

const ATTRIBUTE_TYPE_LABEL_MAP = {
	[AttributeOwnerTypes.Account]: Liferay.Language.get('account'),
	[AttributeOwnerTypes.Event]: Liferay.Language.get('event'),
	[AttributeOwnerTypes.Individual]: Liferay.Language.get('individual'),
	[AttributeOwnerTypes.Session]: Liferay.Language.get('session')
};

export const BOOLEAN_OPTIONS = ['true', 'false'];

export const DATE_GROUPING_OPTIONS = [
	DateGroupings.Day,
	DateGroupings.Month,
	DateGroupings.Year
];

export const DATE_OPTIONS = [
	Operators.EQ,
	Operators.LT,
	Operators.GT,
	Operators.Between
];

export const DURATION_OPTIONS = [Operators.GT, Operators.LT];

export const NUMBER_OPTIONS = [Operators.GT, Operators.LT, Operators.Between];

export const STRING_OPTIONS = [
	Operators.Contains,
	Operators.NotContains,
	Operators.EQ,
	Operators.NE
];

export const BOOLEAN_LABELS_MAP = {
	false: Liferay.Language.get('false'),
	true: Liferay.Language.get('true')
};

export const DATA_TYPE_ICONS_MAP = {
	[DataTypes.Boolean]: 'check',
	[DataTypes.Date]: 'date',
	[DataTypes.Duration]: 'time',
	[DataTypes.Number]: 'integer',
	[DataTypes.String]: 'text'
};

export const DATA_TYPE_LABELS_MAP = {
	[DataTypes.Boolean]: Liferay.Language.get('boolean'),
	[DataTypes.Date]: Liferay.Language.get('date'),
	[DataTypes.Duration]: Liferay.Language.get('duration'),
	[DataTypes.Number]: Liferay.Language.get('number'),
	[DataTypes.String]: Liferay.Language.get('string')
};

export const DATE_GROUPING_LABELS_MAP = {
	[DateGroupings.Day]: Liferay.Language.get('date'),
	[DateGroupings.Month]: Liferay.Language.get('month'),
	[DateGroupings.Year]: Liferay.Language.get('year')
};

export const DATE_OPERATOR_LABELS_MAP = {
	[Operators.Between]: '-',
	[Operators.EQ]: '=',
	[Operators.GT]: Liferay.Language.get('after-fragment'),
	[Operators.LT]: Liferay.Language.get('before-fragment')
};

export const DATE_OPERATOR_LONGHAND_LABELS_MAP = {
	[Operators.Between]: Liferay.Language.get('is-between-fragment'),
	[Operators.EQ]: Liferay.Language.get('is-fragment'),
	[Operators.GT]: Liferay.Language.get('after-fragment'),
	[Operators.LT]: Liferay.Language.get('before-fragment')
};

export const DURATION_OPERATOR_LABELS_MAP = {
	[Operators.GT]: '>',
	[Operators.LT]: '<'
};

export const DURATION_OPERATOR_LONGHAND_LABELS_MAP = {
	[Operators.GT]: Liferay.Language.get('is-greater-than-fragment'),
	[Operators.LT]: Liferay.Language.get('is-less-than-fragment')
};

export const NUMBER_OPERATOR_LABELS_MAP = {
	[Operators.Between]: '-',
	[Operators.GT]: '>',
	[Operators.LT]: '<'
};

export const NUMBER_OPERATOR_LONGHAND_LABELS_MAP = {
	[Operators.Between]: Liferay.Language.get('between-fragment'),
	[Operators.GT]: Liferay.Language.get('is-greater-than-fragment'),
	[Operators.LT]: Liferay.Language.get('is-less-than-fragment')
};

export const STRING_OPERATOR_LABELS_MAP = {
	[Operators.Contains]: Liferay.Language.get('contains-fragment'),
	[Operators.NotContains]: Liferay.Language.get('not-contains-fragment'),
	[Operators.EQ]: Liferay.Language.get('is-fragment'),
	[Operators.NE]: Liferay.Language.get('is-not-fragment')
};

const getBooleanDisplay = (
	attribute: Attribute,
	{attributeType, values: [value]}: Filter
): [string, string] => [
	getBreakdownDisplay(attribute, attributeType).join(' | '),
	BOOLEAN_LABELS_MAP[String(value)]
];

const getDateDisplay = (
	attribute: Attribute,
	{attributeType, operator, values: [startDate, endDate]}: Filter
): [string, string] => {
	const operatorLabel = DATE_OPERATOR_LABELS_MAP[operator];

	const formattedStartDate = formatUTCDate(startDate as string, 'll');

	const breakdownValue =
		operator === Operators.Between
			? `${formattedStartDate} ${operatorLabel} ${formatUTCDate(
					endDate as string,
					'll'
			  )}`
			: `${operatorLabel} ${formattedStartDate}`;

	return [
		getBreakdownDisplay(attribute, attributeType).join(' | '),
		`${breakdownValue}`
	];
};

export const getBreakdownDisplay = (
	{displayName, name}: Attribute,
	attributeType: AttributeOwnerTypes
): [string, string] => [
	ATTRIBUTE_TYPE_LABEL_MAP[attributeType],
	displayName || name
];

const getDurationDisplay = (
	attribute: Attribute,
	{attributeType, operator, values: [value]}: Filter
): [string, string] => {
	const duration = formatTime(Number(value));

	return [
		getBreakdownDisplay(attribute, attributeType).join(' | '),
		`${DURATION_OPERATOR_LABELS_MAP[operator]} ${duration}`
	];
};

const getNumberDisplay = (
	attribute: Attribute,
	{attributeType, operator, values: [start, end]}: Filter
): [string, string] => {
	const operatorLabel = NUMBER_OPERATOR_LABELS_MAP[operator];

	const breakdownValue =
		operator === Operators.Between
			? `${start} ${operatorLabel} ${end}`
			: `${operatorLabel} ${start}`;

	return [
		getBreakdownDisplay(attribute, attributeType).join(' | '),
		breakdownValue
	];
};

const getStringDisplay = (
	attribute: Attribute,
	{attributeType, operator, values}: Filter
): [string, string] => [
	getBreakdownDisplay(attribute, attributeType).join(' | '),
	`${STRING_OPERATOR_LABELS_MAP[operator]} "${values}"`
];

const FILTER_DISPLAY_MAP = {
	[DataTypes.Boolean]: getBooleanDisplay,
	[DataTypes.Date]: getDateDisplay,
	[DataTypes.Duration]: getDurationDisplay,
	[DataTypes.Number]: getNumberDisplay,
	[DataTypes.String]: getStringDisplay
};

export const getFilterDisplay = (
	attribute: Attribute,
	filter: Filter
): [string, string] => {
	const displayFn = FILTER_DISPLAY_MAP[filter.dataType];

	return displayFn(attribute, filter);
};

export const isAttribute = (item: Attribute | Event): boolean =>
	(item as Attribute).dataType !== undefined;

export const createBooleanBreakdown = ({
	attributeId,
	attributeType,
	description,
	displayName
}): Breakdown => ({
	attributeId,
	attributeType,
	binSize: null,
	dataType: DataTypes.Boolean,
	dateGrouping: null,
	description,
	displayName,
	sortType: OrderByDirections.Descending
});

export const createDateBreakdown = ({
	attributeId,
	attributeType,
	dateGrouping = DEFAULT_DATE_GROUPING,
	description,
	displayName
}): Breakdown => ({
	attributeId,
	attributeType,
	binSize: null,
	dataType: DataTypes.Date,
	dateGrouping,
	description,
	displayName,
	sortType: OrderByDirections.Descending
});

export const createDurationBreakdown = ({
	attributeId,
	attributeType,
	binSize = DEFAULT_DURATION_BIN,
	description,
	displayName
}): Breakdown => ({
	attributeId,
	attributeType,
	binSize,
	dataType: DataTypes.Duration,
	dateGrouping: null,
	description,
	displayName,
	sortType: OrderByDirections.Descending
});

export const createNumberBreakdown = ({
	attributeId,
	attributeType,
	binSize = DEFAULT_NUMBER_BIN,
	description,
	displayName
}): Breakdown => ({
	attributeId,
	attributeType,
	binSize,
	dataType: DataTypes.Number,
	dateGrouping: null,
	description,
	displayName,
	sortType: OrderByDirections.Descending
});

export const createStringBreakdown = ({
	attributeId,
	attributeType,
	description,
	displayName
}): Breakdown => ({
	attributeId,
	attributeType,
	binSize: null,
	dataType: DataTypes.String,
	dateGrouping: null,
	description,
	displayName,
	sortType: OrderByDirections.Descending
});

export const BREAKDOWN_FNS_MAP = {
	[DataTypes.Boolean]: createBooleanBreakdown,
	[DataTypes.Date]: createDateBreakdown,
	[DataTypes.Duration]: createDurationBreakdown,
	[DataTypes.Number]: createNumberBreakdown,
	[DataTypes.String]: createStringBreakdown
};

export const getRowSpan = (breakdownItems: BreakdownDataItem[]): number => {
	let rowSpan = breakdownItems.length || 1;

	breakdownItems.forEach(({breakdownItems, leafNode}) => {
		if (!leafNode) {
			rowSpan = rowSpan + (getRowSpan(breakdownItems) - 1);
		}
	});

	return rowSpan;
};

export const formatDateName = (
	name: string,
	dateGrouping: DateGroupings
): string => {
	switch (dateGrouping) {
		case DateGroupings.Day:
			return moment(name, 'YYYY-MM-DD').format('ll');
		case DateGroupings.Month:
			return moment(name, 'YYYY-MM').format('MMM YYYY');
		case DateGroupings.Year:
		default:
			return name;
	}
};

export const formatDurationName = (name: string): string => {
	const [durationStart, durationEnd] = name.split('-');

	return `${formatTime(Number(durationStart))} - ${formatTime(
		Number(durationEnd)
	)}`;
};

export const formatBreakdownNameByDataType = (
	name: string,
	breakdown: Breakdown
): string | number => {
	if (name === 'undefined') {
		return name;
	}

	switch (breakdown?.dataType) {
		case DataTypes.Date:
			return formatDateName(name, breakdown.dateGrouping);
		case DataTypes.Duration:
			return formatDurationName(name);
		case DataTypes.Boolean:
		case DataTypes.String:
		case DataTypes.Number:
		default:
			return name;
	}
};

export const parseBreakdownData = (
	{breakdownItems}: BreakdownData | BreakdownDataItem,
	orderedBreakdowns: Breakdown[],
	rows: ParsedBreakdownData = [{index: '0'} as ParsedBreakdownItem],
	level: number = 0
): ParsedBreakdownData => {
	breakdownItems.forEach(data => {
		const {
			breakdownItems: nextBreakdownItems,
			leafNode: isLeafCurrentNode,
			name,
			...node
		} = data;

		const currentRowIndex = rows.length - 1;

		if (isLeafCurrentNode && level === 0) {
			Object.assign(rows[currentRowIndex], {
				events: [data]
			});

			return;
		}

		const isLeafNextNode =
			nextBreakdownItems.length > 0 && nextBreakdownItems[0].leafNode;

		Object.assign(rows[currentRowIndex], {
			[`breakdown${level}`]: {
				...node,
				name: isLeafCurrentNode
					? name
					: formatBreakdownNameByDataType(
							name,
							orderedBreakdowns[level]
					  ),
				rowSpan:
					!isLeafCurrentNode && !isLeafNextNode
						? getRowSpan(nextBreakdownItems)
						: 1
			},
			index: currentRowIndex
		});

		if (!nextBreakdownItems.length) {
			rows.push({} as ParsedBreakdownItem);
		}

		if (!isLeafNextNode) {
			parseBreakdownData(data, orderedBreakdowns, rows, level + 1);
		} else {
			Object.assign(rows[currentRowIndex], {
				events: nextBreakdownItems
			});

			rows.push({} as ParsedBreakdownItem);
		}
	});

	return level === 0 && !breakdownItems.length
		? rows
		: rows.filter(obj => Object.keys(obj).length !== 0);
};

export const getMaxEventValue = (parsedData, compareToPrevious: boolean) =>
	parsedData.reduce(
		(prev, {events = []}) =>
			events.reduce(
				(
					prev2,
					{
						breakdownItems: segments = [],
						previousValue = 0,
						value = 0
					}
				) =>
					segments.length <= 1
						? Math.max(
								value,
								prev2,
								compareToPrevious && previousValue
						  )
						: segments.reduce(
								(prev3, {previousValue, value}) =>
									Math.max(
										value,
										prev3,
										compareToPrevious && previousValue
									),
								prev2
						  ),
				prev
			),
		0
	);
