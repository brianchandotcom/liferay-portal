import * as API from 'shared/api';
import BaseListPage from 'contacts/components/BaseListPage';
import ClayButton from '@clayui/button';
import Promise from 'metal-promise';
import React, {useContext, useEffect, useRef, useState} from 'react';
import RowActions from 'shared/components/RowActions';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import URLConstants from 'shared/util/url-constants';
import {
	ActionType,
	UnassignedSegmentsContext
} from 'shared/context/unassignedSegments';
import {addAlert} from 'shared/actions/alerts';
import {Alert, FilterByType} from 'shared/types';
import {ALERT_CONFIG_MAP, AlertTypes} from 'shared/components/Alert';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose, withCurrentUser} from 'shared/hoc';
import {connect, ConnectedProps} from 'react-redux';
import {createOrderIOMap} from 'shared/util/pagination';
import {
	getDefaultSortOrder,
	NAME,
	paginationDefaults
} from 'shared/util/pagination';
import {Link} from 'react-router-dom';
import {OrderedMap} from 'immutable';
import {OrderParams, User} from 'shared/util/records';
import {RootState} from 'shared/store';
import {
	Routes,
	SEGMENT_STATE,
	SEGMENTS,
	setUriQueryValue,
	toRoute
} from 'shared/util/router';
import {segmentsListColumns} from 'shared/util/table-columns';
import {SegmentStates, SegmentTypes, Sizes} from 'shared/util/constants';
import {setUriQueryValues} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useQueryPagination} from 'shared/hooks';

export interface FetchSegmentsParams {
	channelId: string;
	delta?: number;
	filterBy: FilterByType;
	groupId: string;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query: string;
}

function fetchSegments(params: FetchSegmentsParams): any {
	const {channelId, delta, groupId, orderIOMap, page, query} = params;

	return API.individualSegment.search({
		channelId,
		delta,
		groupId,
		orderIOMap,
		page,
		query
	});
}

function fetchDisabledSegments(
	channelId: string,
	groupId: string,
	orderIOMap: OrderedMap<string, OrderParams>
): any {
	return API.individualSegment.search({
		channelId,
		delta: 1,
		groupId,
		orderIOMap,
		state: SegmentStates.Disabled
	});
}

const connector = connect(
	(store: RootState, {groupId}: {groupId: string}) => ({
		timeZoneId: store.getIn([
			'projects',
			groupId,
			'data',
			'timeZone',
			'timeZoneId'
		])
	}),
	{addAlert, close, open}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IListProps extends PropsFromRedux {
	channelId: string;
	currentUser: User;
	groupId: string;
	history: any;
}

export const List: React.FC<IListProps> = ({
	addAlert,
	channelId,
	close,
	currentUser,
	groupId,
	history,
	open,
	timeZoneId
}) => {
	const _tableRef = useRef<HTMLDivElement & SearchableEntityTable>();

	const {delta, orderIOMap, page, query} = useQueryPagination({
		filterFields: [SEGMENT_STATE],
		initialDelta: paginationDefaults.delta,
		initialOrderIOMap: createOrderIOMap(NAME, getDefaultSortOrder(NAME)),
		initialPage: paginationDefaults.page,
		initialQuery: paginationDefaults.query
	});

	const [alerts, setAlerts] = useState([]);

	const _disableSegmentsRequestRef = useRef<typeof Promise>();
	const {
		showUnassignedAlert,
		unassignedSegments,
		unassignedSegmentsDispatch
	} = useContext(UnassignedSegmentsContext);

	useEffect(() => {
		_disableSegmentsRequestRef.current = getDisabledSegmentsAlert();

		return () => _disableSegmentsRequestRef.current.cancel();
	}, []);

	const getDisabledSegmentsAlert = () =>
		fetchDisabledSegments(channelId, groupId, orderIOMap).then(
			({total}) => {
				if (total) {
					setAlerts(() => handleDisabledSegmentsAlert());
				}
			}
		);

	const getAlerts = () =>
		[
			...alerts,
			showUnassignedAlert &&
				unassignedSegments.length &&
				handleUnassignedSegmentsAlert()
		].filter(Boolean);

	const handleDisabledSegmentsAlert = () => [
		{
			message: sub(
				Liferay.Language.get(
					'some-of-your-segments-are-disabled-because-a-data-source-has-been-removed-x'
				),
				[
					<Link
						key='DISABLED_SEGMENTS'
						to={setUriQueryValue(
							window.location.href,
							SEGMENT_STATE,
							SegmentStates.Disabled
						)}
					>
						{Liferay.Language.get('view-disabled-segments')}
					</Link>
				],
				false
			),
			onClose: () => setAlerts(() => []),
			...ALERT_CONFIG_MAP[AlertTypes.Warning]
		}
	];

	const handleUnassignedSegmentsAlert = () => {
		const openModal = () => {
			open(
				modalTypes.UNASSIGNED_SEGMENTS_MODAL,
				{
					groupId,
					onClose: close
				},
				{closeOnBlur: false}
			);
		};

		return {
			message: sub(
				Liferay.Language.get(
					'there-are-existing-segments-that-have-not-been-assigned-to-a-property-x'
				),
				[
					<ClayButton
						className='button-root p-0'
						displayType='unstyled'
						key='UNASSIGNED_SEGMENTS'
						onClick={openModal}
						small
					>
						{Liferay.Language.get('view-unassigned-segments')}
					</ClayButton>
				],
				false
			),
			onClose: () =>
				unassignedSegmentsDispatch({type: ActionType.updateShowAlert}),
			...ALERT_CONFIG_MAP[AlertTypes.Warning]
		};
	};

	const handleDeleteSegment = ({id, items, name}) => {
		open(modalTypes.CONFIRMATION_MODAL, {
			message: (
				<div>
					<h4 className='text-secondary'>
						{Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this-segment'
						)}
					</h4>

					<p>
						{Liferay.Language.get(
							'you-will-lose-all-data-related-to-this-segment.-you-will-not-be-able-to-undo-this-operation'
						)}
					</p>
				</div>
			),
			modalVariant: 'modal-warning',
			onClose: close,
			onSubmit: () =>
				API.individualSegment
					.delete({
						groupId,
						id
					})
					.then(() => {
						_tableRef?.current?.reload();

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'the-segment-has-been-deleted'
							)
						});

						if (items.length === 1 && page !== 1) {
							history.push(
								setUriQueryValue(
									window.location.href,
									'page',
									Number(page) - 1
								)
							);
						}
					})
					.catch(() => {
						addAlert({
							alertType: Alert.Types.Error,
							message: Liferay.Language.get('error')
						});
					}),
			submitButtonDisplay: 'warning',
			submitMessage: Liferay.Language.get('delete'),
			title: sub(Liferay.Language.get('deleting-x'), [name]),
			titleIcon: 'warning-full'
		});
	};

	const renderRowActions = ({data: {id, name}, items}) => {
		const commonActions = [
			{
				href: toRoute(Routes.CONTACTS_SEGMENT_EDIT, {
					channelId,
					groupId,
					id,
					type: SEGMENTS
				}),
				iconSymbol: 'pencil',
				label: Liferay.Language.get('edit')
			},
			{
				iconSymbol: 'trash',
				label: Liferay.Language.get('delete'),
				onClick: () => handleDeleteSegment({id, items, name})
			}
		];

		const actions = commonActions.map(({href, label, onClick}) => ({
			href,
			label,
			onClick
		}));

		return <RowActions actions={actions} quickActions={commonActions} />;
	};

	const pageActions = [
		{
			href: setUriQueryValues(
				{type: SegmentTypes.Dynamic},
				toRoute(Routes.CONTACTS_SEGMENT_CREATE, {
					channelId,
					groupId
				})
			),
			label: Liferay.Language.get('dynamic-segment'),
			onClick: () =>
				analytics.track('Dynamic Segment Creation - Clicked Create')
		},
		{
			href: setUriQueryValues(
				{type: SegmentTypes.Static},
				toRoute(Routes.CONTACTS_SEGMENT_CREATE, {
					channelId,
					groupId
				})
			),
			label: Liferay.Language.get('static-segment'),
			onClick: () =>
				analytics.track('Static Segment Creation - Clicked Create')
		}
	];

	const pageActionsLabel = Liferay.Language.get('create-segment');

	return (
		<BaseListPage
			alerts={getAlerts()}
			className='segment-list-root'
			columns={[
				segmentsListColumns.getName({channelId, groupId}),
				segmentsListColumns.getOwnerName(timeZoneId)
			]}
			currentUser={currentUser}
			dataSourceFn={fetchSegments}
			delta={delta}
			emptyStateTitle={Liferay.Language.get('no-data-sources-connected')}
			entityLabel={Liferay.Language.get('segments')}
			noResultsConfig={{
				description: (
					<>
						{Liferay.Language.get(
							'create-a-segment-to-get-started'
						)}

						<a
							className='d-block'
							href={URLConstants.SegmentsDocumentationLink}
							key='DOCUMENTATION'
							target='_blank'
						>
							{Liferay.Language.get(
								'access-our-documentation-to-learn-more'
							)}
						</a>
					</>
				),
				icon: {
					border: false,
					size: Sizes.XXXLarge,
					symbol: 'ac-satellite'
				},
				title: Liferay.Language.get('there-are-no-segments-found')
			}}
			orderByOptions={[
				{
					label: Liferay.Language.get('name'),
					value: NAME
				},
				{
					label: Liferay.Language.get('last-modified'),
					value: 'dateModified'
				}
			]}
			orderIOMap={orderIOMap}
			page={page}
			pageActions={pageActions}
			pageActionsLabel={pageActionsLabel}
			query={query}
			ref={_tableRef}
			renderRowActions={renderRowActions}
		/>
	);
};

export default compose(connector, withCurrentUser)(List);
