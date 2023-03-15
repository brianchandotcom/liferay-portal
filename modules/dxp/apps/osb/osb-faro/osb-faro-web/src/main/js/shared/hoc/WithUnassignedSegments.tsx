import * as API from 'shared/api';
import React, {useEffect} from 'react';
import {
	ActionType,
	useUnassignedSegmentsContext
} from 'shared/context/unassignedSegments';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {
	fetchUpgradeModalSeen,
	updateUpgradeModalSeen
} from 'shared/actions/preferences';
import {RootState} from 'shared/store';
import {useChannelContext} from 'shared/context/channel';
import {useRequest} from 'shared/hooks';

const connector = connect(
	(state: RootState) => ({
		upgradeModalSeen: state.getIn(
			['preferences', 'user', 'upgradeModalSeen', 'data'],
			true
		)
	}),
	{close, fetchUpgradeModalSeen, open, updateUpgradeModalSeen}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IUnassignedSegmentsProps extends PropsFromRedux {
	groupId: string;
}

const withUnassignedSegments = (
	WrappedComponent: React.ComponentType<{
		groupId: any;
	}>
) => {
	const UnassignedSegments: React.FC<IUnassignedSegmentsProps> = ({
		close,
		fetchUpgradeModalSeen,
		groupId,
		open,
		updateUpgradeModalSeen,
		upgradeModalSeen,
		...otherProps
	}) => {
		const {unassignedSegmentsDispatch} = useUnassignedSegmentsContext();

		const {channels} = useChannelContext();

		const {data, error, loading} = useRequest({
			dataSourceFn: API.individualSegment.searchUnassigned,
			variables: {
				delta: 10000,
				groupId
			}
		});

		useEffect(() => {
			fetchUpgradeModalSeen(groupId);
		}, []);

		useEffect(() => {
			if (data && !error) {
				const {items, total} = data;

				unassignedSegmentsDispatch({
					payload: items,
					type: ActionType.setSegments
				});

				if (
					!upgradeModalSeen &&
					!loading &&
					!!total &&
					!!channels.length
				) {
					open(
						modalTypes.UNASSIGNED_SEGMENTS_MODAL,
						{
							groupId,
							onClose: () => {
								updateUpgradeModalSeen({
									groupId,
									upgradeModalSeen: true
								});

								close();
							}
						},
						{closeOnBlur: false}
					);
				}
			}
		}, [data, error, loading, upgradeModalSeen]);

		return <WrappedComponent groupId={groupId} {...otherProps} />;
	};

	return connector(UnassignedSegments);
};

export default withUnassignedSegments;
