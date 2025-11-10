import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';
import CrossPageSelect from 'shared/hoc/CrossPageSelect';
import Modal from 'shared/components/modal';
import NoResultsDisplay from '../NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {CREATE_TIME, createOrderIOMap} from 'shared/util/pagination';
import {Sizes} from 'shared/util/constants';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';
import {useRequest} from 'shared/hooks/useRequest';
import {
	useSelectionContext,
	withSelectionProvider
} from 'shared/context/selection';

interface ISelectChannelsModalProps {
	onClose: () => {};
	onSelect: (channels: string[]) => {};
	groupId: string;
}

const SelectChannelsModal: React.FC<ISelectChannelsModalProps> = ({
	/**
	 * const {groupId} = useParams() doesn't work on Modals
	 */
	groupId,
	onClose,
	onSelect
}) => {
	const {selectedItems} = useSelectionContext();

	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(CREATE_TIME)
	});

	const {data, error, loading} = useRequest({
		dataSourceFn: API.channels.search,
		variables: {
			cur: page,
			delta,
			groupId,
			orderIOMap,
			query
		}
	});

	return (
		<Modal size='lg'>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('select-properties')}
			/>

			<ClayForm
				onSubmit={event => {
					event.preventDefault();

					onSelect(
						!selectedItems.isEmpty()
							? selectedItems.keySeq().toArray()
							: []
					);

					onClose();
				}}
			>
				<Modal.Body className='p-0' inlineScroller>
					<CrossPageSelect
						columns={[
							{
								accessor: 'name',
								className: 'table-cell-expand',
								label: Liferay.Language.get(
									'available-properties'
								),
								title: true
							},
							{
								accessor: 'commerceChannelsCount',
								className: 'text-right',
								label: Liferay.Language.get(
									'dxp-commerce-channels'
								),
								sortable: false
							},
							{
								accessor: 'groupsCount',
								className: 'text-right',
								label: Liferay.Language.get('sites'),
								sortable: false
							}
						]}
						delta={delta}
						entityLabel={Liferay.Language.get('properties')}
						error={error}
						items={data?.items}
						loading={loading}
						noResultsRenderer={
							<NoResultsDisplay
								description={
									<>
										{Liferay.Language.get(
											'create-a-property-to-get-started'
										)}

										<ClayLink
											className='d-block mb-3'
											href={URLConstants.CreateProperty}
											key='DOCUMENTATION'
											target='_blank'
										>
											{Liferay.Language.get(
												'access-our-documentation-to-learn-more'
											)}
										</ClayLink>
									</>
								}
								icon={{
									border: false,
									size: Sizes.XXXLarge,
									symbol: 'ac_satellite'
								}}
								title={Liferay.Language.get(
									'no-properties-found'
								)}
							/>
						}
						orderIOMap={orderIOMap}
						page={page}
						query={query}
						rowIdentifier='id'
						showCheckbox
						total={data?.total}
					/>
				</Modal.Body>

				<Modal.Footer>
					<ClayButton displayType='secondary' onClick={onClose}>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton displayType='primary' type='submit'>
						{Liferay.Language.get('select')}
					</ClayButton>
				</Modal.Footer>
			</ClayForm>
		</Modal>
	);
};

export default withSelectionProvider(SelectChannelsModal);
