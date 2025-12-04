import CriteriaCard from 'segment/components/criteria-card';
import React from 'react';
import SegmentProfileCard from 'segment/components/ProfileCard';

import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {useTimeZone} from 'shared/hooks/useTimeZone';

interface IOverviewProps {
	channelId: string;
	groupId: string;
	segment: Segment;
}

const RealTimeSegmentOverview: React.FC<IOverviewProps> = ({
	channelId,
	groupId,
	segment
}) => {
	const {criteriaString, id, includeAnonymousUsers} = segment;
	const {timeZoneId} = useTimeZone();

	return (
		<div>
			<ReferencedObjectsProvider segment={segment}>
				<CriteriaCard
					criteriaString={criteriaString}
					includeAnonymousUsers={includeAnonymousUsers}
					segmentType={SegmentTypes.RealTime}
					timeZoneId={timeZoneId}
				/>
			</ReferencedObjectsProvider>
			<SegmentProfileCard
				channelId={channelId}
				groupId={groupId}
				id={id}
				segment={segment}
			/>
		</div>
	);
};

export default RealTimeSegmentOverview;
