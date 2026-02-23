import * as API from 'shared/api';
import AssociatedSegmentsList from 'contacts/components/AssociatedSegmentsList';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {connect, ConnectedProps} from 'react-redux';
import {
	createOrderIOMap,
	getDefaultSortOrder,
	NAME
} from 'shared/util/pagination';
import {EntityTypes, Sizes} from 'shared/util/constants';
import {RootState} from 'shared/store';
import {Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {useQueryPagination} from 'shared/hooks/useQueryPagination';

const fetchAssociatedSegments = ({
	delta,
	groupId,
	id,
	orderIOMap,
	page,
	query,
	...otherParams
}) =>
	API.individualSegment.search({
		contactsEntityId: id,
		contactsEntityType: EntityTypes.Individual,
		delta,
		groupId,
		orderIOMap,
		page,
		query,
		...otherParams
	});

const connector = connect((store: RootState, {groupId}: {groupId: string}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}));

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IAssociatedSegmentsProps extends PropsFromRedux {
	channelId: string;
	groupId: string;
	id: string;
	timeZoneId: string;
}

const AssociatedSegments: React.FC<IAssociatedSegmentsProps> = ({
	channelId,
	groupId,
	id,
	timeZoneId
}) => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME, getDefaultSortOrder(NAME))
	});

	const [total, setTotal] = useState<number>(0);

	const segmentsDataSourceFn = dataSourceParams =>
		fetchAssociatedSegments({channelId, ...dataSourceParams}).then(
			response => {
				setTotal(response.total);

				return response;
			}
		);

	const renderNoResults = () => (
		<Card className='associated-segments-list-root' pageDisplay>
			<NoResultsDisplay
				description={
					<>
						{Liferay.Language.get(
							'this-individual-doesnt-belong-to-any-segment-create-one-to-get-started'
						)}
						<ClayLink
							className='d-block'
							href={
								URLConstants.IndividualProfilesDocumentSegments
							}
							key='DOCUMENTATION'
							target='_blank'
						>
							{Liferay.Language.get('learn-more-about-segments')}
							<ClayIcon
								aria-label={Liferay.Language.get(
									'learn-more-about-data-sources'
								)}
								className='ml-1'
								fontSize={8}
								symbol='shortcut'
							/>
						</ClayLink>
					</>
				}
				icon={{
					border: false,
					size: Sizes.XXXLarge,
					symbol: 'ac_satellite'
				}}
				title={Liferay.Language.get('there-are-no-segments-found')}
			>
				<ClayLink
					button
					className='button-root'
					displayType='primary'
					href={toRoute(Routes.CONTACTS_LIST_SEGMENT, {
						channelId,
						groupId,
						type: SEGMENTS
					})}
				>
					{Liferay.Language.get('create-segment')}
				</ClayLink>
			</NoResultsDisplay>
		</Card>
	);

	return (
		<AssociatedSegmentsList
			channelId={channelId}
			dataSourceFn={segmentsDataSourceFn}
			delta={delta}
			groupId={groupId}
			id={id}
			noResultsRenderer={renderNoResults}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
			timeZoneId={timeZoneId}
			total={total}
		/>
	);
};

export default connector(AssociatedSegments);
