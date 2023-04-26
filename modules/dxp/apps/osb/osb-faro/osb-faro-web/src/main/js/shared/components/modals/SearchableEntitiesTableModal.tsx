import ClayButton from '@clayui/button';
import Modal, {Size} from 'shared/components/modal';
import React from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {noop} from 'lodash';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {useStatefulPagination} from 'shared/hooks';

interface ISearchableEntitiesTableModalProps {
	className: string;
	defaultParams: {[key: string]: any};
	initialDelta?: number;
	initialOrderIOMap: OrderedMap<string, OrderParams>;
	onClose: () => void;
	size: Size;
	title: string;
}

const SearchableEntitiesTableModal: React.FC<ISearchableEntitiesTableModalProps> = ({
	className,
	initialDelta = 10,
	initialOrderIOMap = createOrderIOMap(NAME),
	onClose = noop,
	size = 'xxl',
	title = 'entities',
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
	} = useStatefulPagination(null, {
		initialDelta,
		initialOrderIOMap
	});

	return (
		<Modal className={className} size={size}>
			<Modal.Header onClose={onClose} title={title} />

			<SearchableEntityTable
				{...otherProps}
				autoFocusSearch
				delta={delta}
				onDeltaChange={onDeltaChange}
				onOrderIOMapChange={onOrderIOMapChange}
				onPageChange={onPageChange}
				onQueryChange={onQueryChange}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
			/>

			<Modal.Footer>
				<ClayButton
					className='button-root'
					displayType='primary'
					onClick={onClose}
				>
					{Liferay.Language.get('done')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default SearchableEntitiesTableModal;
