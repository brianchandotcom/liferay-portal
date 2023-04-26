import ClayButton from '@clayui/button';
import CrossPageSelect from 'shared/hoc/CrossPageSelect';
import Modal from 'shared/components/modal';
import React, {useEffect} from 'react';
import {
	ActionTypes,
	useSelectionContext,
	withSelectionProvider
} from 'shared/context/selection';
import {Column} from 'shared/components/table';
import {FilterByType} from 'shared/types';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {useRequest, useStatefulPagination} from 'shared/hooks';

interface ISearchableTableModalProps {
	checkDisabled: (item: {[key: string]: any}) => boolean;
	className: string;
	columns: Column[];
	dataSourceFn: (params: {[key: string]: any}) => Promise<any>;
	dataSourceParams: {[key: string]: any};
	entityLabel: string;
	initialDelta: number;
	initialFilterBy: FilterByType;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	initialPage: number;
	initialQuery: string;
	initialSelectedItems: {[key: string]: any}[];
	instruction: string;
	noResultsIcon: string;
	onClose: () => void;
	onSubmit: (
		selectedItemsIOMap: OrderedMap<string, any>,
		refetch: () => void
	) => void;
	orderByOptions: {label: string; value: string}[];
	requireSelection?: boolean;
	submitMessage: string;
	title: string;
}

const SearchableTableModal: React.FC<ISearchableTableModalProps> = ({
	checkDisabled,
	className,
	columns,
	dataSourceFn,
	dataSourceParams,
	entityLabel,
	initialDelta = 10,
	initialFilterBy,
	initialOrderIOMap,
	initialPage,
	initialQuery,
	initialSelectedItems,
	instruction,
	noResultsIcon,
	onClose,
	onSubmit,
	orderByOptions,
	requireSelection = true,
	submitMessage = Liferay.Language.get('submit'),
	title = Liferay.Language.get('select-items')
}) => {
	const {selectedItems, selectionDispatch} = useSelectionContext();

	const {
		delta,
		filterBy,
		onDeltaChange,
		onFilterByChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query
	} = useStatefulPagination(null, {
		initialDelta,
		initialFilterBy,
		initialOrderIOMap,
		initialPage,
		initialQuery
	});

	useEffect(() => {
		if (initialSelectedItems) {
			selectionDispatch({
				payload: {items: initialSelectedItems},
				type: ActionTypes.Add
			});
		}
	}, []);

	const {data, error, loading, refetch} = useRequest({
		dataSourceFn,
		variables: {
			...dataSourceParams,
			delta,
			filterBy,
			orderIOMap,
			page,
			query
		}
	});

	return (
		<Modal className={className} size='lg'>
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body>
				<div className='text-secondary'>{instruction}</div>
			</Modal.Body>

			<CrossPageSelect
				autoFocusSearch
				checkDisabled={checkDisabled}
				columns={columns}
				dataSourceFn={dataSourceFn}
				delta={delta}
				entityLabel={entityLabel}
				error={error}
				items={data?.items}
				loading={loading}
				noResultsIcon={noResultsIcon}
				onDeltaChange={onDeltaChange}
				onFilterByChange={onFilterByChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onPageChange}
				onQueryChange={onQueryChange}
				orderByOptions={orderByOptions}
				orderIOMap={orderIOMap}
				page={page}
				pageDisplay={false}
				query={query}
				rowIdentifier='id'
				selectedItems={selectedItems.map(({id}) => id)}
				selectedItemsIOMap={selectedItems}
				showCheckbox
				total={data?.total}
			/>

			<Modal.Footer>
				<ClayButton
					className='button-root'
					displayType='secondary'
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className='button-root'
					disabled={requireSelection && !selectedItems.size}
					displayType='primary'
					onClick={() => onSubmit(selectedItems, refetch)}
				>
					{submitMessage}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default withSelectionProvider(SearchableTableModal);
