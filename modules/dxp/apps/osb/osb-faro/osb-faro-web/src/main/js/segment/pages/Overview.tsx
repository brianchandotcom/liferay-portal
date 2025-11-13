import CriteriaCard from 'segment/components/criteria-card';
import React from 'react';
import SegmentProfileCard from 'segment/components/ProfileCard';

import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from 'shared/util/records';
import {useTimeZone} from 'shared/hooks/useTimeZone';

interface IOverviewProps {
	channelId: string;
	groupId: string;
	id: string;
	segment: Segment;
	tabId?: string;
	timeZoneId: string;
}

const Overview: React.FC<IOverviewProps> = ({
	channelId,
	groupId,
	id,
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
			<SegmentProfileCard
				channelId={channelId}
				groupId={groupId}
				id={id}
				segment={segment}
			/>
		</div>
	);
};

export default Overview;
