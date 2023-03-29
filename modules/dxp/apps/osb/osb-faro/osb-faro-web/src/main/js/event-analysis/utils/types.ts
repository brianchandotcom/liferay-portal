import {OrderByDirections} from 'shared/util/constants';

export enum AttributeOwnerTypes {
	Account = 'ACCOUNT',
	Event = 'EVENT',
	Individual = 'INDIVIDUAL',
	Session = 'SESSION'
}

export enum AttributeTypes {
	All = 'ALL',
	Global = 'GLOBAL',
	Local = 'LOCAL'
}

export enum CalculationTypes {
	Average = 'AVERAGE',
	Total = 'TOTAL',
	Unique = 'UNIQUE'
}

export enum DataTypes {
	Boolean = 'BOOLEAN',
	Date = 'DATE',
	Duration = 'DURATION',
	Number = 'NUMBER',
	String = 'STRING'
}

export enum DateGroupings {
	Day = 'DAY',
	Month = 'MONTH',
	Year = 'YEAR'
}

export enum EventTypes {
	All = 'ALL',
	Custom = 'CUSTOM',
	Default = 'DEFAULT'
}

export enum Operators {
	Between = 'between',
	Contains = 'contains',
	NotContains = 'notContains',
	EQ = 'eq',
	GT = 'gt',
	LT = 'lt',
	NE = 'ne'
}

export type Attribute = {
	dataType: DataTypes;
	description?: string;
	displayName?: string;
	id: string;
	name: string;
	recentValues?: {
		lastSeenDate: string;
		value: string;
	}[];
	sampleValue?: string;
	type: AttributeTypes;
};

export type Attributes = {[key: string]: Attribute};

export type BlockedCustomEvent = {
	hidden: boolean;
	id: string;
	name: string;
	lastSeenDate: string;
	lastSeenURL: string;
};

export type Breakdown = {
	attributeId: string;
	attributeType: AttributeOwnerTypes;
	binSize: number;
	dataType: DataTypes;
	dateGrouping: DateGroupings;
	description?: string;
	displayName: string;
	id?: string;
	sortType: OrderByDirections;
};

export type Breakdowns = {[key: string]: Breakdown};

export type Event = {
	description?: string;
	displayName?: string;
	eventAttributeDefinitions?: Attribute[];
	hidden: boolean;
	id: string;
	name: string;
	type: EventTypes;
};

export type Filter = {
	attributeId: string;
	attributeType: AttributeOwnerTypes;
	dataType: DataTypes;
	description?: string;
	displayName: string;
	id?: string;
	operator: Operators;
	values: string[];
};

export type Filters = {[key: string]: Filter};

export interface IBreakdownProps {
	attributeId: string;
	attributeOwnerType: AttributeOwnerTypes;
	breakdown?: Breakdown;
	description?: string;
	displayName: string;
	onSubmit: (breakdown: Breakdown) => void;
}

export interface IFilterProps {
	attributeId: string;
	attributeOwnerType: AttributeOwnerTypes;
	description?: string;
	displayName: string;
	eventId?: string;
	filter?: Filter;
	onSubmit: (filter: Filter) => void;
}

export type BreakdownDataItem = {
	breakdownItems?: BreakdownDataItem[];
	leafNode: boolean;
	name: string;
	previousValue?: number;
	value: number;
};

export type BreakdownData = {
	breakdownItems: BreakdownDataItem[];
	count: number;
	page: number;
	previousValue?: number;
	value: number;
};

export type ParsedBreakdownItem = {
	events: BreakdownDataItem[];
	index: string;
	breakdown0: BreakdownDataItem & {rowSpan: number};
	[key: string]:
		| (BreakdownDataItem & {rowSpan: number})
		| BreakdownDataItem[]
		| string;
};

export type ParsedBreakdownData = ParsedBreakdownItem[];
