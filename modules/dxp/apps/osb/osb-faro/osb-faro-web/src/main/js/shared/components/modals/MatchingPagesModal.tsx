import ClayButton from '@clayui/button';
import MetadataTag from 'settings/recommendations/components/MetadataTag';
import Modal from 'shared/components/modal';
import React from 'react';
import RecommendationPageAssetsQuery from 'settings/recommendations/queries/RecommendationPageAssetsQuery';
import {
	createOrderIOMap,
	getSortFromOrderIOMap,
	TITLE
} from 'shared/util/pagination';
import {
	EXCLUDE,
	Filter,
	getFilterValueBreakdown
} from 'settings/recommendations/utils/utils';
import {getMapResultToProps} from 'shared/hoc/mappers/metrics';
import {graphql} from '@apollo/react-hoc';
import {isArray, isString} from 'lodash';
import {omit} from 'lodash';
import {OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {withBaseResults, withStatefulPagination} from 'shared/hoc';

const withData = () =>
	graphql(RecommendationPageAssetsQuery, {
		options: ({
			delta,
			itemFilters,
			orderIOMap,
			page,
			query,
			useNegateValue
		}: {
			delta: number;
			itemFilters: Filter[];
			orderIOMap: OrderedMap<string, OrderParams>;
			page: number;
			query: string;
			useNegateValue: boolean;
		}) => ({
			fetchPolicy: 'no-cache',
			variables: {
				keywords: query,
				propertyFilters: itemFilters.map(({name, value}) => ({
					filter: value,
					negate: useNegateValue ? name === EXCLUDE : false
				})),
				size: delta,
				sort: getSortFromOrderIOMap(orderIOMap),
				start: (page - 1) * delta
			}
		}),
		props: getMapResultToProps(({pageAssets: {pageAssets, total}}) => ({
			items: pageAssets,
			total
		}))
	});

const TableWithData = withStatefulPagination(
	withBaseResults(withData, {
		emptyDescription: Liferay.Language.get(
			'there-were-no-matching-items-for-this-string'
		),
		emptyTitle: Liferay.Language.get('no-matches-found'),
		getColumns: ({secondColumnHeader}) => [
			{
				accessor: 'title',
				className: 'table-cell-expand text-truncate',
				label: Liferay.Language.get('page-name')
			},
			{
				accessor: secondColumnHeader || 'url',
				className: 'secondary-info table-cell-expand text-truncate',
				dataFormatter: val => {
					if (isString(val)) {
						return val;
					} else if (isArray(val)) {
						return val.map(({value}) => value).join(', ');
					}
				},
				label: secondColumnHeader || 'url',
				sortable: false
			}
		],
		showDropdownRangeKey: false
	}),
	{
		initialDelta: 10,
		initialOrderIOMap: createOrderIOMap(TITLE)
	},
	props => omit(props, 'onSearchValueChange'),
	false
);

interface IMatchingPagesModalProps {
	itemFilters: Filter[];
	onClose: () => void;
	useNegateValue: boolean;
}

const MatchingPagesModal: React.FC<IMatchingPagesModalProps> = ({
	itemFilters,
	onClose,
	useNegateValue = false
}) => {
	const {name, value} = itemFilters[0];

	const {exactMatchSign, metadataTag, rule} = getFilterValueBreakdown(value);

	const customFilter = itemFilters.length === 1 && metadataTag;

	return (
		<Modal className='matching-pages-modal-root' size='xl'>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('matching-pages')}
			/>

			<Modal.Body>
				{!!customFilter && !useNegateValue && (
					<div>
						<span className='include-exclude'>
							{`${
								name === EXCLUDE
									? Liferay.Language.get('exclude')
									: Liferay.Language.get('include')
							}:`}
						</span>

						<MetadataTag value={metadataTag} />

						<span className='rule'>
							{exactMatchSign ? `"${rule}"` : rule}
						</span>
					</div>
				)}
			</Modal.Body>

			<TableWithData
				itemFilters={itemFilters}
				secondColumnHeader={customFilter}
				useNegateValue={useNegateValue}
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

export default MatchingPagesModal;
