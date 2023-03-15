import React, {createContext, useMemo, useReducer} from 'react';
import {Attribute, Breakdown, Filter} from 'event-analysis/utils/types';
import {deletePropertyFromObject} from 'shared/util/object';
import {isEqual} from 'lodash';
import {moveItem} from 'shared/util/array';

type Breakdowns = {[key: string]: Breakdown};
type Filters = {[key: string]: Filter};

export const hasOrderChanged = (
	initialOrder: string[],
	order: string[]
): boolean =>
	initialOrder.length !== order.length ||
	!order.every((id, i) => id === initialOrder[i]);

export const isAttributeInUse = (
	attributeId: string,
	...items: (Breakdowns | Filters)[]
): boolean =>
	items.some(item =>
		Object.values(item).some(item => item.attributeId === attributeId)
	);

export enum ActionTypes {
	AddBreakdown = 'ADD_BREAKDOWN',
	AddFilter = 'ADD_FILTER',
	DeleteBreakdown = 'DELETE_BREAKDOWN',
	DeleteFilter = 'DELETE_FILTER',
	DeleteAllAttributes = 'DELETE_ALL_ATTRIBUTES',
	EditBreakdown = 'EDIT_BREAKDOWN',
	EditFilter = 'EDIT_FILTER',
	MoveBreakdown = 'MOVE_BREAKDOWN',
	MoveFilter = 'MOVE_FILTER'
}

export type AddBreakdownParams = {
	attribute: Attribute;
	breakdown: Breakdown;
};

export type AddBreakdown = (params: AddBreakdownParams) => void;

export type AddFilter = (params: {
	attribute: Attribute;
	filter: Filter;
}) => void;

export type DeleteAllAttributes = () => void;

export type DeleteBreakdown = (params: {id: string}) => void;
export type DeleteFilter = (params: {id: string}) => void;

export type EditBreakdown = (
	params: AddBreakdownParams & {
		id: string;
	}
) => void;

export type EditFilter = (params: {
	attribute: Attribute;
	filter: Filter;
	id: string;
}) => void;

export type MoveBreakdown = (params: {from: number; to: number}) => void;
export type MoveFilter = (params: {from: number; to: number}) => void;

export type AttributesState = {
	attributes: {[key: string]: Attribute};
	breakdownOrder: string[];
	breakdowns: Breakdowns;
	filterOrder: string[];
	filters: Filters;
};

export const AttributesContext = createContext<
	AttributesState & {
		addBreakdown?: AddBreakdown;
		addFilter?: AddFilter;
		changed: boolean;
		deleteAllAttributes?: DeleteAllAttributes;
		deleteBreakdown?: DeleteBreakdown;
		deleteFilter?: DeleteFilter;
		editBreakdown?: EditBreakdown;
		editFilter?: EditFilter;
		moveBreakdown?: MoveBreakdown;
		moveFilter?: MoveFilter;
	}
>({
	attributes: {},
	breakdownOrder: [],
	breakdowns: {},
	changed: false,
	filterOrder: [],
	filters: {}
});

type Action = {
	payload: {
		attribute?: Attribute;
		breakdown?: Breakdown;
		filter?: Filter;
		from?: number;
		id?: string;
		to?: number;
	};
	type: ActionTypes;
};

const actionHandlers = {
	[ActionTypes.AddBreakdown]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {attribute, breakdown} = {}}: Action
	): AttributesState => {
		const id = Date.now().toString();

		return {
			attributes: Object.assign({}, attributes, {
				[attribute.id]: attribute
			}),
			breakdownOrder: [...breakdownOrder, id],
			breakdowns: Object.assign(
				{
					[id]: {...breakdown, id}
				},
				breakdowns
			),
			filterOrder,
			filters
		};
	},
	[ActionTypes.AddFilter]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {attribute, filter} = {}}: Action
	): AttributesState => {
		const id = Date.now().toString();

		return {
			attributes: Object.assign({}, attributes, {
				[attribute.id]: attribute
			}),
			breakdownOrder,
			breakdowns,
			filterOrder: [...filterOrder, id],
			filters: Object.assign({[id]: {...filter, id}}, filters)
		};
	},
	[ActionTypes.DeleteAllAttributes]: (): AttributesState => ({
		attributes: {},
		breakdownOrder: [],
		breakdowns: {},
		filterOrder: [],
		filters: {}
	}),
	[ActionTypes.DeleteBreakdown]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {id} = {}}: Action
	): AttributesState => {
		const {attributeId} = breakdowns[id];

		const updatedBreakdowns = deletePropertyFromObject(id, breakdowns);

		return {
			attributes: isAttributeInUse(
				attributeId,
				updatedBreakdowns,
				filters
			)
				? attributes
				: deletePropertyFromObject(attributeId, attributes),
			breakdownOrder: breakdownOrder.filter(
				breakdownId => breakdownId !== id
			),
			breakdowns: updatedBreakdowns,
			filterOrder,
			filters
		};
	},
	[ActionTypes.DeleteFilter]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {id} = {}}: Action
	): AttributesState => {
		const {attributeId} = filters[id];

		const updatedFilters = deletePropertyFromObject(id, filters);

		return {
			attributes: isAttributeInUse(
				attributeId,
				updatedFilters,
				breakdowns
			)
				? attributes
				: deletePropertyFromObject(attributeId, attributes),
			breakdownOrder,
			breakdowns,
			filterOrder: filterOrder.filter(filterId => filterId !== id),
			filters: updatedFilters
		};
	},
	[ActionTypes.EditBreakdown]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {attribute, breakdown, id} = {}}: Action
	): AttributesState => {
		const {attributeId: oldAttributeId} = breakdowns[id];

		const updatedBreakdowns = Object.assign(
			deletePropertyFromObject(id, breakdowns),
			{
				[id]: {...breakdown, id}
			}
		);

		return {
			attributes: Object.assign(
				{},
				isAttributeInUse(oldAttributeId, updatedBreakdowns, filters)
					? attributes
					: deletePropertyFromObject(oldAttributeId, attributes),
				{
					[attribute.id]: attribute
				}
			),
			breakdownOrder,
			breakdowns: updatedBreakdowns,
			filterOrder,
			filters
		};
	},
	[ActionTypes.EditFilter]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {attribute, filter, id} = {}}: Action
	): AttributesState => {
		const {attributeId: oldAttributeId} = filters[id];

		const updatedFilters = Object.assign(
			deletePropertyFromObject(id, filters),
			{
				[id]: {...filter, id}
			}
		);

		return {
			attributes: Object.assign(
				{},
				isAttributeInUse(oldAttributeId, updatedFilters, breakdowns)
					? attributes
					: deletePropertyFromObject(oldAttributeId, attributes),
				{
					[attribute.id]: attribute
				}
			),
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters: updatedFilters
		};
	},
	[ActionTypes.MoveBreakdown]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {from, to} = {}}: Action
	): AttributesState => ({
		attributes,
		breakdownOrder: moveItem([...breakdownOrder], from, to),
		breakdowns,
		filterOrder,
		filters
	}),
	[ActionTypes.MoveFilter]: (
		{
			attributes,
			breakdownOrder,
			breakdowns,
			filterOrder,
			filters
		}: AttributesState,
		{payload: {from, to} = {}}: Action
	): AttributesState => ({
		attributes,
		breakdownOrder,
		breakdowns,
		filterOrder: moveItem([...filterOrder], from, to),
		filters
	})
};

export const attributesReducer = (
	state: AttributesState,
	action: Action
): AttributesState => {
	const handlerFn = actionHandlers[action.type];

	if (handlerFn) {
		return handlerFn(state, action);
	}

	throw new Error('Unhandled action type: ${type}');
};

const defaultState = {
	attributes: {},
	breakdownOrder: [],
	breakdowns: {},
	filterOrder: [],
	filters: {}
};

interface IAttributesProviderProps extends React.HTMLAttributes<HTMLElement> {
	initialState?: AttributesState;
}

export const AttributesProvider: React.FC<IAttributesProviderProps> = ({
	children,
	initialState = defaultState
}) => {
	const [
		{attributes, breakdownOrder, breakdowns, filterOrder, filters},
		attributesDispatch
	] = useReducer(attributesReducer, initialState);

	const {
		breakdownOrder: initialBreakdownOrder,
		breakdowns: initialBreakdowns,
		filterOrder: initialFilterOrder,
		filters: initialFilters
	} = initialState;

	const breakdownOrderChanged: boolean = useMemo(
		() => hasOrderChanged(initialBreakdownOrder, breakdownOrder),
		[initialBreakdownOrder, breakdownOrder]
	);

	const filterOrderChanged: boolean = useMemo(
		() => hasOrderChanged(initialFilterOrder, filterOrder),
		[initialFilterOrder, filterOrder]
	);

	const breakdownsChanged: boolean = useMemo(
		() => !isEqual(initialBreakdowns, breakdowns),
		[initialBreakdowns, breakdowns]
	);

	const filtersChanged: boolean = useMemo(
		() => !isEqual(initialFilters, filters),
		[initialFilters, filters]
	);

	const contextValue: {
		addBreakdown: AddBreakdown;
		addFilter: AddFilter;
		attributes: {[key: string]: Attribute};
		breakdownOrder: string[];
		breakdowns: Breakdowns;
		changed: boolean;
		deleteAllAttributes: DeleteAllAttributes;
		deleteBreakdown: DeleteBreakdown;
		deleteFilter: DeleteFilter;
		editBreakdown: EditBreakdown;
		editFilter: EditFilter;
		filterOrder: string[];
		filters: Filters;
		moveBreakdown: MoveBreakdown;
		moveFilter: MoveFilter;
	} = {
		addBreakdown: payload =>
			attributesDispatch({
				payload,
				type: ActionTypes.AddBreakdown
			}),
		addFilter: payload =>
			attributesDispatch({
				payload,
				type: ActionTypes.AddFilter
			}),
		attributes,
		breakdownOrder,
		breakdowns,
		changed:
			breakdownOrderChanged ||
			filterOrderChanged ||
			breakdownsChanged ||
			filtersChanged,
		deleteAllAttributes: () =>
			attributesDispatch({
				payload: {},
				type: ActionTypes.DeleteAllAttributes
			}),
		deleteBreakdown: payload =>
			attributesDispatch({payload, type: ActionTypes.DeleteBreakdown}),
		deleteFilter: payload =>
			attributesDispatch({payload, type: ActionTypes.DeleteFilter}),
		editBreakdown: payload =>
			attributesDispatch({
				payload,
				type: ActionTypes.EditBreakdown
			}),
		editFilter: payload =>
			attributesDispatch({
				payload,
				type: ActionTypes.EditFilter
			}),
		filterOrder,
		filters,
		moveBreakdown: payload =>
			attributesDispatch({payload, type: ActionTypes.MoveBreakdown}),
		moveFilter: payload =>
			attributesDispatch({payload, type: ActionTypes.MoveFilter})
	};

	return (
		<AttributesContext.Provider value={contextValue}>
			{children}
		</AttributesContext.Provider>
	);
};

export const withAttributesProvider = WrappedComponent => props => (
	<AttributesProvider>
		<WrappedComponent {...props} />
	</AttributesProvider>
);

export const withAttributesConsumer = WrappedComponent => props => (
	<AttributesContext.Consumer>
		{attributes => <WrappedComponent {...props} {...attributes} />}
	</AttributesContext.Consumer>
);
