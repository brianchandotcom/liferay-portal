import ClayButton from '@clayui/button';
import CrossPageSelect from 'shared/hoc/CrossPageSelect';
import Modal from 'shared/components/modal';
import React, {useEffect} from 'react';
import {
	ACTION_TYPES,
	useSelectionContext,
	withSelectionProvider
} from 'shared/context/selection';
import {Columns, IPagination} from 'shared/types';
import {DocumentNode} from 'graphql';
import {noop} from 'lodash';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {QueryHookOptions, useQuery} from '@apollo/react-hooks';
import {safeResultToProps} from 'shared/util/mappers';
import {useStatefulPagination} from 'shared/hooks';

interface ISearchableTableModalGraphQLProps extends IPagination {
	className: string;
	columns: Columns;
	graphqlQuery: DocumentNode;
	initialDelta?: number;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	instruction?: string;
	mapPropsToOptions: (props: {[key: string]: any}) => QueryHookOptions;
	mapResultToProps: (result: {
		[key: string]: any;
	}) => {
		items: any[];
		total: number;
	};
	onClose?: () => void;
	onSubmit: (selectedItems: OrderedMap<string, any>) => void;
	requireSelection?: boolean;
	selectedItems?: any[];
	submitMessage?: string;
	title?: string;
}

const SearchableTableModalGraphql: React.FC<ISearchableTableModalGraphQLProps> = ({
	className,
	columns,
	graphqlQuery,
	initialDelta = 5,
	initialOrderIOMap,
	instruction = '',
	mapPropsToOptions,
	mapResultToProps,
	onClose = noop,
	onSubmit,
	requireSelection = true,
	selectedItems = [],
	submitMessage = Liferay.Language.get('submit'),
	title = Liferay.Language.get('select-items'),
	...otherProps
}) => {
	const {
		delta,
		onDeltaChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query
	} = useStatefulPagination(null, {initialDelta, initialOrderIOMap});

	const {data, error, loading} = useQuery(
		graphqlQuery,
		mapPropsToOptions({delta, orderIOMap, page, query, ...otherProps})
	);

	const {
		selectedItems: contextSelectedItems,
		selectionDispatch
	} = useSelectionContext();

	useEffect(() => {
		if (selectedItems.length) {
			selectionDispatch({
				payload: {items: selectedItems},
				type: ACTION_TYPES.add
			});
		}
	}, []);

	const handleSubmit = () => onSubmit(contextSelectedItems);

	const {empty, items, total} = safeResultToProps(mapResultToProps)({
		data: {error, loading, ...data},
		ownProps: {}
	});

	return (
		<Modal className={className} size='lg'>
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body>
				<div className='text-secondary'>{instruction}</div>
			</Modal.Body>

			<CrossPageSelect
				{...otherProps}
				autoFocus
				columns={columns}
				delta={delta}
				empty={empty}
				items={items}
				loading={loading}
				onDeltaChange={onDeltaChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onPageChange}
				onQueryChange={onQueryChange}
				orderIOMap={orderIOMap}
				page={page}
				pageDisplay={false}
				query={query}
				total={total}
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
					disabled={requireSelection && !contextSelectedItems.size}
					displayType='primary'
					onClick={handleSubmit}
				>
					{submitMessage}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default withSelectionProvider(SearchableTableModalGraphql);
