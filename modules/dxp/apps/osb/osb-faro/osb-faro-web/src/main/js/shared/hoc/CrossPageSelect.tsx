import ClayButton from '@clayui/button';
import ListComponent from 'shared/hoc/ListComponent';
import React, {useEffect, useState} from 'react';
import {ACTION_TYPES, useSelectionContext} from 'shared/context/selection';
import {FilterByType, IPagination} from 'shared/types';
import {get} from 'lodash';
import {getDisplayName} from 'shared/util/react';
import {getSafeDisplayValue} from 'shared/util/util';
import {OrderByDirections} from 'shared/util/constants';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {sub} from 'shared/util/lang';
import {useStatefulPagination} from 'shared/hooks';

type SearchArgs = {
	filterBy?: FilterByType;
	items: OrderedMap<any, any>;
	query: string;
};

export type SearchFnType = ({items, query}: SearchArgs) => OrderedMap<any, any>;

/**
 * Function for local search on items.
 */
export const defaultSearch: SearchFnType = ({items, query}: SearchArgs) =>
	items.filter(
		item =>
			Object.values(get(item, 'properties', {})).some((value: any) =>
				String(getSafeDisplayValue(value, ''))
					.toLowerCase()
					.match(query.toLowerCase())
			) ||
			(item.name || item.emailAddress || '')
				.toLowerCase()
				.match(query.toLowerCase())
	) as OrderedMap<any, any>;

/**
 * Function for local sort on items.
 */
export const defaultSort = (
	items: OrderedMap<any, any>,
	orderIOMap: OrderedMap<string, OrderParams>
): OrderedMap<any, any> => {
	const {field, sortOrder} = orderIOMap.first();

	const sorted = items.sortBy(item => {
		if (item[field]) {
			return item[field];
		} else if (get(item, ['properties', field])) {
			return item.properties[field];
		} else {
			return item;
		}
	});

	return sortOrder === OrderByDirections.Ascending
		? (sorted as OrderedMap<any, any>)
		: (sorted.reverse() as OrderedMap<any, any>);
};

export const fetchLocalData = ({
	delta,
	filterBy,
	items,
	orderIOMap,
	page,
	query,
	searchSelectedFn = defaultSearch
}: {
	delta: number;
	items: OrderedMap<any, any>;
	filterBy?: FilterByType;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query?: string;
	searchSelectedFn?: ({
		filterBy,
		items,
		query
	}: SearchArgs) => OrderedMap<any, any>;
}) => {
	const start = (page - 1) * delta;

	const end = start + delta;

	const result =
		query || filterBy
			? defaultSort(
					searchSelectedFn({filterBy, items, query}),
					orderIOMap
			  )
			: defaultSort(items, orderIOMap);

	return {
		empty: !result.size,
		items: result.slice(start, end).toArray(),
		total: result.size
	};
};

export const withLocalData = () => WrappedComponent => props => {
	const {delta, filterBy, orderIOMap, page, query, searchSelectedFn} = props;

	const {selectedItems} = useSelectionContext();

	return (
		<WrappedComponent
			{...props}
			{...fetchLocalData({
				delta,
				filterBy,
				items: selectedItems,
				orderIOMap,
				page,
				query,
				searchSelectedFn
			})}
		/>
	);
};

interface IwithSelectionProps {
	checkDisabled?: (item?: object) => boolean;
	items: {id: string}[];
	[key: string]: any;
}

/**
 * HOC for mapping information about items
 * and SelectionContext into props for Toolbar and Table
 * @param WrappedComponent
 * @returns {Function}
 */
export const withSelection: (
	WrappedComponent
) => React.FC<IwithSelectionProps> = WrappedComponent => {
	const WithSelection: React.FC<IwithSelectionProps> = ({
		checkDisabled = () => false,
		items = [],
		showCheckbox = true,
		...otherProps
	}) => {
		const {selectedItems, selectionDispatch} = useSelectionContext();

		const allChecked =
			!selectedItems.isEmpty() &&
			items.every(
				item => selectedItems.has(item.id) || checkDisabled(item)
			);

		const selectionProps = {
			alwaysShowSearch: true,
			onSelectEntirePage: checked => {
				selectionDispatch({
					payload: {
						items: items.filter(item => !checkDisabled(item))
					},
					type: checked ? ACTION_TYPES.add : ACTION_TYPES.remove
				});
			},
			onSelectItemsChange: item =>
				selectionDispatch({payload: {item}, type: ACTION_TYPES.toggle}),
			selectedItemsIOMap: selectedItems,
			selectEntirePage: allChecked,
			selectEntirePageIndeterminate:
				!allChecked && !selectedItems.isEmpty(),
			showCheckbox
		};

		return (
			<WrappedComponent
				{...otherProps}
				{...selectionProps}
				checkDisabled={checkDisabled}
				items={items}
			/>
		);
	};

	WithSelection.displayName = `WithSelection(${getDisplayName(
		WrappedComponent
	)})`;

	return WithSelection;
};

export const ViewSelectedToggle = ({
	onClick,
	selectedItemsCount,
	showSelected
}) => (
	<ClayButton
		className='button-root'
		data-testid='view-selected'
		displayType='unstyled'
		onClick={onClick}
		small
	>
		<b>
			{showSelected
				? Liferay.Language.get('return-to-list')
				: sub(Liferay.Language.get('view-selected-x'), [
						selectedItemsCount
				  ])}
		</b>
	</ClayButton>
);

interface ICrossPageSelectProps extends IPagination {
	children: (val) => React.ReactElement;
	onDeltaChange: (delta: number) => void;
	onFilterByChange: (filterBy: FilterByType) => void;
	onOrderIOMapChange: (orderIOMap: OrderedMap<string, OrderParams>) => void;
	onPageChange: (page: number) => void;
	onQueryChange: (query: string) => void;
	searchSelectedFn: SearchFnType;
	showCheckbox?: boolean;
}

const CrossPageSelect: React.FC<ICrossPageSelectProps> = ({
	children,
	delta,
	filterBy,
	onDeltaChange,
	onFilterByChange,
	onOrderIOMapChange,
	onPageChange,
	onQueryChange,
	orderIOMap,
	page,
	query,
	searchSelectedFn,
	showCheckbox,
	...otherProps
}) => {
	const {
		filterBy: stagedFilterBy,
		onFilterByChange: onStagedFilterByChange,
		onPageChange: onStagedPageChange,
		onQueryChange: onStagedQueryChange,
		page: stagedPage,
		query: stagedQuery
	} = useStatefulPagination(null, {
		initialDelta: delta,
		initialOrderIOMap: orderIOMap
	});
	const {selectedItems, selectionDispatch} = useSelectionContext();
	const [showSelected, setShowSelected] = useState(false);

	useEffect(() => {
		if (selectedItems.isEmpty() && showSelected) {
			setShowSelected(false);
		}

		if (
			stagedPage > 1 &&
			selectedItems.size <= delta * stagedPage - delta
		) {
			onStagedPageChange(stagedPage - 1);
		}
	});

	const renderViewSelectedToggle = () => (
		<ViewSelectedToggle
			onClick={() => setShowSelected(!showSelected)}
			selectedItemsCount={selectedItems.size}
			showSelected={showSelected}
		/>
	);

	const localData = fetchLocalData({
		delta,
		filterBy: stagedFilterBy,
		items: selectedItems,
		orderIOMap,
		page: stagedPage,
		query: stagedQuery,
		searchSelectedFn
	});

	if (showSelected) {
		return (
			<ListComponent
				{...otherProps}
				delta={delta}
				filterBy={stagedFilterBy}
				onDeltaChange={onDeltaChange}
				onFilterByChange={onStagedFilterByChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onStagedPageChange}
				onQueryChange={onStagedQueryChange}
				orderIOMap={orderIOMap}
				page={stagedPage}
				query={stagedQuery}
				renderViewSelectedToggle={renderViewSelectedToggle}
				selectedItems={selectedItems}
				selectionDispatch={selectionDispatch}
				showCheckbox
				{...localData}
			/>
		);
	} else {
		const sharedProps = {
			...otherProps,
			delta,
			filterBy,
			onDeltaChange,
			onFilterByChange,
			onOrderIOMapChange,
			onPageChange,
			onQueryChange,
			orderIOMap,
			page,
			query,
			renderViewSelectedToggle,
			selectedItems,
			selectionDispatch,
			showCheckbox
		};

		if (children) {
			return children(sharedProps);
		} else {
			return <ListComponent {...sharedProps} />;
		}
	}
};

export default withSelection(CrossPageSelect);
