import CriteriaCard from 'segment/components/criteria-card';
import Membership from './Membership';

import React from 'react';
import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from 'shared/util/records';
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
	const {criteriaString, includeAnonymousUsers} = segment;
	const {timeZoneId} = useTimeZone();

	return (
		<div>
			<ReferencedObjectsProvider segment={segment}>
				<CriteriaCard
					criteriaString={criteriaString}
					includeAnonymousUsers={includeAnonymousUsers}
					timeZoneId={timeZoneId}
				/>
			</ReferencedObjectsProvider>
			<Membership
				channelId={channelId}
				groupId={groupId}
				segment={segment}
				timeZoneId={timeZoneId}
			/>
		</div>
	);
};

export default RealTimeSegmentOverview;
