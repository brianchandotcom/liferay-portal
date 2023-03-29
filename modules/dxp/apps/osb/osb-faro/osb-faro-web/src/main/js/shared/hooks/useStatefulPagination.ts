import Constants from 'shared/util/constants';
import {FilterByType} from 'shared/types';
import {Map, OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {useReducer} from 'react';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = Constants;

export enum ActionType {
	resetPage = 'resetPage',
	setDelta = 'setDelta',
	setFilterBy = 'setFilterBy',
	setOrderIOMap = 'setOrderIOMap',
	setPage = 'setPage',
	setQuery = 'setQuery'
}

interface Action {
	payload?: any;
	type: ActionType;
}

interface State {
	delta: number;
	filterBy: FilterByType;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query: string;
}

interface StatefulPaginationResult extends State {
	resetPage: () => void;
	onDeltaChange: (delta: number) => void;
	onFilterByChange: (filterBy: FilterByType) => void;
	onOrderIOMapChange: (orderIOMap: OrderedMap<string, OrderParams>) => void;
	onPageChange: (page: number) => void;
	onQueryChange: (query: string) => void;
}

const DEFAULT_INITIAL_PAGINATION_PROPS = {
	initialDelta: DEFAULT_DELTA,
	initialFilterBy: Map(),
	initialOrderIOMap: null,
	initialPage: DEFAULT_PAGE,
	initialQuery: ''
};

const statefulPaginationReducer = (state: State, {payload, type}: Action) => {
	switch (type) {
		case 'resetPage':
			return {
				...state,
				page: DEFAULT_PAGE
			};
		case 'setDelta':
			return {
				...state,
				delta: payload,
				page: DEFAULT_PAGE
			};
		case 'setFilterBy':
			return {
				...state,
				filterBy: payload,
				page: DEFAULT_PAGE
			};
		case 'setOrderIOMap':
			return {
				...state,
				orderIOMap: payload,
				page: DEFAULT_PAGE
			};
		case 'setQuery':
			return {
				...state,
				page: DEFAULT_PAGE,
				query: payload
			};
		case 'setPage':
			return {
				...state,
				page: payload
			};
		default:
			return state;
	}
};

export default function useStatefulPagination(
	mapPropsFn = undefined,
	initialPaginationProps = {}
): StatefulPaginationResult {
	const paginationProps = {
		...DEFAULT_INITIAL_PAGINATION_PROPS,
		...initialPaginationProps
	};

	const {
		initialDelta,
		initialFilterBy,
		initialOrderIOMap,
		initialPage,
		initialQuery
	} = paginationProps;

	const [state, setState] = useReducer(statefulPaginationReducer, {
		delta: initialDelta,
		filterBy: initialFilterBy,
		orderIOMap: initialOrderIOMap,
		page: initialPage,
		query: initialQuery
	});

	const resetPage = (): void => {
		setState({
			type: ActionType.resetPage
		});
	};

	const setDelta = (delta: string): void => {
		setState({
			payload: delta,
			type: ActionType.setDelta
		});
	};

	const setFilterBy = (filterBy: string): void => {
		setState({
			payload: filterBy,
			type: ActionType.setFilterBy
		});
	};

	const setOrderIOMap = (orderIOMap: OrderedMap<string, OrderParams>) => {
		setState({
			payload: orderIOMap,
			type: ActionType.setOrderIOMap
		});
	};

	const setPage = (page: string): void => {
		setState({
			payload: page,
			type: ActionType.setPage
		});
	};

	const setQuery = (query: string): void => {
		setState({
			payload: query,
			type: ActionType.setQuery
		});
	};

	const mappedProps = mapPropsFn ? mapPropsFn(state) : state;

	return {
		...mappedProps,
		onDeltaChange: setDelta,
		onFilterByChange: setFilterBy,
		onOrderIOMapChange: setOrderIOMap,
		onPageChange: setPage,
		onQueryChange: setQuery,
		resetPage
	};
}
